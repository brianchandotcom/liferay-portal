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
					var portletURL = saveURL;

					var versionIncreaseElement = document.querySelector("input[name='${namespace}versionDetailsVersionIncrease']:checked");

					if (versionIncreaseElement) {
						portletURL += '&${namespace}versionIncrease=' + encodeURIComponent(versionIncreaseElement.value);
					}

					var changeLogElement = document.getElementById('${namespace}versionDetailsChangeLog');

					if (changeLogElement) {
						portletURL += '&${namespace}changeLog=' + encodeURIComponent(changeLogElement.value);
					}

					portletURL += '&${namespace}updateVersionDetails=true'

					window.location.href = portletURL;
				}
			},
			'${dialogCancelButtonLabel}'
		);
	},
	['document-library-checkin', 'liferay-portlet-url']
);