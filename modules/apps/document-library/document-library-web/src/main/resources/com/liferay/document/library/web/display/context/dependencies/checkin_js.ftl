Liferay.provide(
	window,
	'${namespace}showVersionDetailsDialog',
	function(saveURL) {
		Liferay.Portlet.DocumentLibrary.Checkin.showDialog(
			'${namespace}versionDetails',
			'${dialogTitle}',
			['${namespace}versionDetailsMajorVersion', '${namespace}versionDetailsChangeLog'],
			function(event, nodes) {
				var portletURL = new Liferay.PortletURL(null, null, saveURL);

				var majorVersionNode = nodes[0];

				portletURL.setParameter('majorVersion', majorVersionNode.attr('checked'));

				var changeLogNode = nodes[1];

				portletURL.setParameter('changeLog', changeLogNode.val());

				window.location.href = portletURL.toString();
			}
		);
	},
	['document-library-checkin']
);