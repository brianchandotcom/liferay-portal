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

					var versionIncreaseNode = $("input:radio[name='${namespace}versionDetailsVersionIncrease']:checked");

					portletURL += '&${namespace}versionIncrease=' + encodeURIComponent(versionIncreaseNode.val());

					var changeLogNode = $('#${namespace}versionDetailsChangeLog');

					portletURL += '&${namespace}changeLog=' + encodeURIComponent(changeLogNode.val());

					window.location.href = portletURL;
				}
			},
			'${dialogCancelButtonLabel}'
		);
	},
	['document-library-checkin', 'liferay-portlet-url']
);