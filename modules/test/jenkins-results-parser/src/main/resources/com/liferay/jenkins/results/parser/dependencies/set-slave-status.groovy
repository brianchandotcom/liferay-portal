import hudson.slaves.OfflineCause;
import hudson.slaves.OfflineCause.ByCLI;
import hudson.slaves.SlaveComputer;

String slaves = "${slaves}";

for (String slave : slaves.split(",")) {
	Hudson hudson = Hudson.instance;

	Slave slaveObject = hudson.getNode(slave.trim());

	SlaveComputer slaveComputer = slaveObject.getComputer();

	try {
		println(slaveComputer.getName() + " isOnline: " + slaveComputer.isOnline());

		boolean offlineStatus = ${offline.status};

		OfflineCause offlineCause = new OfflineCause.ByCLI("${offline.reason}");

		if (offlineStatus) {
			println("Setting " + slaveComputer.getName() + " to offline");
		}
		else {
			println("Setting " + slaveComputer.getName() + " to online");
		}

		slaveComputer.setTemporarilyOffline(offlineStatus, offlineCause);

		println(slaveComputer.getName() + " isOnline: " + slaveComputer.isOnline());
	}
	catch (Exception exception) {
		exception.printStackTrace();
	}
}