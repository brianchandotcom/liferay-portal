Map<String, TopLevelItem> topLevelItems = Jenkins.instance.getItemMap();

TopLevelItem topLevelItem = topLevelItems.get("${jenkinsJobName}");

for (def build : topLevelItem.getBuilds()) {
	if (build.getQueueId() == ${jenkinsQueueId}) {
		println(Jenkins.instance.getRootUrl() + build.getUrl());

		break;
	}
}