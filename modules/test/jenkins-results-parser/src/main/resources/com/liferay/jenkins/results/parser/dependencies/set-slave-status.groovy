import hudson.slaves.OfflineCause;
import hudson.slaves.OfflineCause.ByCLI;
import hudson.slaves.SlaveComputer;

String slaves = "${slaves}";

for (String slave : slaves.split(",")) {
	Hudson hudson = Hudson.instance;

	Slave slaveObject = hudson.getNode(slave.trim());

	SlaveComputer slaveComputer = slaveObject.getComputer();

	try {
		boolean offlineStatus = ${offline.status};

		OfflineCause offlineCause = new OfflineCause.ByCLI("${offline.reason}");

		slaveComputer.setTemporarilyOffline(offlineStatus, offlineCause);
	}
	catch (NullPointerException npe) {
	}
}