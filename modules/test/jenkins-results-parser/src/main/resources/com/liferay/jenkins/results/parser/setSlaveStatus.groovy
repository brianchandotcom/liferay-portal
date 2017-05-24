import hudson.slaves.SlaveComputer

hudson = Hudson.instance

String nodeNames = "${node.names}"
boolean offlineStatus = ${offline.status}

for (String nodeName : nodeNames.split(",")) {
	Slave slave = hudson.getNode(nodeName.trim())

	SlaveComputer slaveComputer = slave.getComputer()

	try {
		slaveComputer.setTemporarilyOffline(offlineStatus)
	}
	catch (NullPointerException npe) {
	}
}