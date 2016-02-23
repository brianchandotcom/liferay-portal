AUI.add(
	'liferay-notification',
	function(A) {
		var Notification = A.Component.create(
			{
				EXTENDS: Liferay.Alert,

				NAME: 'liferaynotification',

				prototype: {
					TPL_ALERT_NODE: '<div class="container-fluid-1280 lfr-notification-wrapper"></div>',

					TPL_ALERTS_CONTAINER: '<div class="lfr-notification-container"></div>',

					_getAlertsContainer: function(targetNode) {
						var instance = this;

						targetNode = targetNode || A.one('body');

						var alertsContainer = instance._alertsContainer;

						if (!alertsContainer) {
							var rootNode = targetNode || instance.get('rootNode') || A;

							alertsContainer = (targetNode && targetNode.one('.lfr-notification-container')) || rootNode.one('.lfr-notification-container');

							if (!alertsContainer) {
								alertsContainer = A.Node.create(instance.TPL_ALERTS_CONTAINER);

								targetNode.prepend(alertsContainer);
							}

							instance._alertsContainer = alertsContainer;
						}

						return alertsContainer;
					}
				}
			}
		);

		Liferay.Notification = Notification;
	},
	'',
	{
		requires: ['liferay-alert']
	}
);