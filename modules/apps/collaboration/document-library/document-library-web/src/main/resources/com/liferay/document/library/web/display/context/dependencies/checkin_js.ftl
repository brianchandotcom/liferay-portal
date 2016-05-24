Liferay.provide(
	window,
	'${namespace}showVersionDetailsDialog',
	function(saveURL) {
		Liferay.Portlet.DocumentLibrary.Checkin.showDialog(
			'${namespace}versionDetails',
			'${dialogTitle}',
			{
				label: '${dialogSaveButtonLabel}',
				callback: function(event) {
					var $ = AUI.$;

					var portletURL = saveURL;

					var majorVersionNode = $("input:radio[name='${namespace}versionDetailsMajorVersion']:checked");

					portletURL += '&majorVersion=' + encodeURIComponent(majorVersionNode.val());

					var changeLogNode = $('#${namespace}versionDetailsChangeLog');

					portletURL += '&changeLog=' + encodeURIComponent(changeLogNode.val());

					window.location.href = portletURL;
				}
			},
			'${dialogCancelButtonLabel}'
		);
	},
	['document-library-checkin', 'liferay-portlet-url']
);