function ${jsNamespace}compareVersionDialog(eventUri) {
	Liferay.Util.openSelectionModal({
		id: '${jsNamespace}compareFileVersions',
		onSelect: function(selectedItem) {
			var uri = '${compareVersionURL}';

			uri = Liferay.Util.addParams('${namespace}sourceFileVersionId=' + selectedItem.sourceversion, uri);
			uri = Liferay.Util.addParams('${namespace}targetFileVersionId=' + selectedItem.targetversion, uri);

			Liferay.Util.navigate(uri);
		},
		selectEventName: '${namespace}selectFileVersionFm',
		title: '${dialogTitle}',
		url: eventUri
	});
}