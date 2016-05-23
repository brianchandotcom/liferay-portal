Liferay.provide(
	window,
	'${namespace}showVersionDetailsDialog',
	function(saveURL) {
		Liferay.Portlet.DocumentLibrary.Checkin.showDialog(
			'${namespace}versionDetails',
			'${dialogTitle}',
			function(event) {
				var $ = AUI.$;

				var portletURL = new Liferay.PortletURL(null, null, saveURL);

				var majorVersionNode = $("input:radio[name='${namespace}versionDetailsMajorVersion']:checked");

				portletURL.setParameter('majorVersion', majorVersionNode.val());

				var changeLogNode = $('#${namespace}versionDetailsChangeLog');

				portletURL.setParameter('changeLog', changeLogNode.val());

				window.location.href = portletURL.toString();
			}
		);
	},
	['document-library-checkin', 'liferay-portlet-url']
);