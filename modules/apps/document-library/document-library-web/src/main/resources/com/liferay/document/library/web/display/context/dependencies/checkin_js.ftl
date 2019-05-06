Liferay.provide(
	window,
	'${namespace}showVersionDetailsDialog',
	function(saveURL) {
		Liferay.DocumentLibraryCheckin.showDialog(
			'${namespace}',
			function(versionIncrease, changeLog) {
				var portletURL = saveURL;

				if (versionIncrease) {
					portletURL += '&${namespace}versionIncrease=' + encodeURIComponent(versionIncrease);
				}

				if (changeLog) {
					portletURL += '&${namespace}changeLog=' + encodeURIComponent(changeLog);
				}

				portletURL += '&${namespace}updateVersionDetails=true'

				window.location.href = portletURL;
			}
		);
	},
	['document-library-checkin', 'liferay-portlet-url']
);