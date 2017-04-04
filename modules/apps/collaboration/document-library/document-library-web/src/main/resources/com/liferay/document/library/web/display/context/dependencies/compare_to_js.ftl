function ${jsNamespace}compareVersionDialog(eventUri) {
	Liferay.Util.selectEntity(
		{
			dialog: {
				constrain: true,
				destroyOnHide: true,
				modal: true
			},
			eventName: '${namespace}selectFileVersionFm',
			id: '${jsNamespace}compareFileVersions',
			title: '${dialogTitle}',
			uri: eventUri
		},
		function(event) {
			var uri = '${compareVersionURL}';

			uri = Liferay.Util.addParams('${namespace}sourceFileVersionId=' + event.sourceversion, uri);
			uri = Liferay.Util.addParams('${namespace}targetFileVersionId=' + event.targetversion, uri);

			location.href = uri;
		}
	);
}