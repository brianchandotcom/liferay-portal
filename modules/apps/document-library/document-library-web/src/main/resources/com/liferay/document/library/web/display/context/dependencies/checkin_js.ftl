Liferay.provide(
	window,
	'${namespace}showVersionDetailsDialog',
	function(saveURL) {
		Liferay.Portlet.DocumentLibrary.Checkin.showDialog(
			'${namespace}versionDetails',
			'${dialogTitle}',
			['${namespace}versionDetailsMajorVersion', '${namespace}versionDetailsChangeLog'],
			function(event, nodes) {
				var majorVersionNode = nodes[0];
				var changeLogNode = nodes[1];

				var portletURL = new Liferay.PortletURL(null, null, saveURL);

				portletURL.setParameter('majorVersion', majorVersionNode.attr('checked'));
				portletURL.setParameter('changeLog', changeLogNode.val());

				window.location.href = portletURL.toString();
			}
		);
	},
	['document-library-checkin']
);