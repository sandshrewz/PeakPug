package com.github.peakz;

import com.github.peakz.DAO.PlayerDAO;
import com.github.peakz.DAO.PlayerDAOImp;
import com.github.peakz.DAO.PlayerObject;
import com.github.peakz.queues.QueueManager;
import sx.blah.discord.api.events.EventSubscriber;
import sx.blah.discord.handle.impl.events.ReadyEvent;
import sx.blah.discord.handle.obj.IGuild;

import java.util.ArrayList;

public class AnnotationListener {
	private QueueHelper queueHelper = new QueueHelper();
	private static PlayerDAO playerDAO = new PlayerDAOImp();
	private static PlayerObject temp_player = new PlayerObject();
	private static ArrayList<PlayerObject> temp_team_red = new ArrayList<>();
	private static ArrayList<PlayerObject> temp_team_blue = new ArrayList<>();

	@EventSubscriber
	public void onReadyEvent(ReadyEvent event) {
		event.getClient().changePlayingText("propugs");
		for(IGuild guild : event.getClient().getGuilds()){
			PugBot.queueManagers.put(guild, new QueueManager(guild));
		}
	}

	/**@EventSubscriber
	public void onUserJoinVoiceChannelEvent(UserVoiceChannelJoinEvent event) {
		IVoiceChannel channel = event.getVoiceChannel();
		PlayerObject player = playerDAO.getPlayer(event.getUser().getStringID());
		switch (channel.getName()) {
			// Using switch for future features and multiple queues
			case "propugsQueue1":
				if(player != null) {
					QueueHelper.addPrimaryRole(player, queueHelper);
					if(QueueHelper.checkRolesAvailable(queueHelper)){
						MatchObject match = QueueHelper.makeTeams(temp_team_red, temp_team_blue, queueHelper);
						QueueHelper.newMatchMessage(event.getVoiceChannel().getGuild(), match);
						MatchDAO matchDAO = new MatchDAOImp();
						matchDAO.insertMatch(match);

						int temp_match_id = matchDAO.getLastMatchID();

						// create new VerificationObject for the match
						VerificationDAO verificationDAO = new VerificationDAOImp();

						// create verification for team red, false by default
						verificationDAO.insertVerification(temp_match_id, match.getTeam_red().getCaptain().getId(), false);

						// create verification for team blue, false by default
						verificationDAO.insertVerification(temp_match_id, match.getTeam_blue().getCaptain().getId(), false);
					}
					break;
				}
			default:
				break;
		}
	}

	@EventSubscriber
	public void onUserLeaveVoiceChannelEvent(UserVoiceChannelLeaveEvent event) {
		IVoiceChannel channel = event.getVoiceChannel();
		PlayerObject player = playerDAO.getPlayer(event.getUser().getStringID());
		switch(channel.getName()){
			case "propugsQueue1":
				if(queueHelper.getPlayers().size() == 1){
					// If the queue becomes empty, the whole queue helper is renewed
					queueHelper = new QueueHelper();

				} else if (queueHelper.getPlayers().size() > 1){
					// Removes the player from the queue if it contains at least 1 player
					queueHelper.getPlayers().remove(player);

					// Checks primary role queue and removes the player from the list they're in
					if(queueHelper.getTanks().contains(player)){
						queueHelper.getTanks().remove(player);

					} else if (queueHelper.getDps().contains(player)){
						queueHelper.getDps().remove(player);

					} else if (queueHelper.getSupps().contains(player)){
						queueHelper.getSupps().remove(player);


					} else if (queueHelper.getFlexes().contains(player)){
						queueHelper.getFlexes().remove(player);
					}
				}
				break;
			default:
				break;
		}
	}*/
}