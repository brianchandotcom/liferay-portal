AUI.add(
	'liferay-staging',
	function(A) {
		var Lang = A.Lang;

		var StagingBar = {
			init: function(config) {
				var instance = this;

				var namespace = config.namespace;

				instance.markAsReadyForPublicationURL = config.markAsReadyForPublicationURL;

				instance._namespace = namespace;

				instance._stagingBar = A.oneNS(namespace, '#stagingBar');

				instance.viewHistoryURL = config.viewHistoryURL;

				Liferay.publish(
					{
						fireOnce: true
					}
				);

				Liferay.after(
					'initStagingBar',
					function(event) {
						A.getBody().addClass('staging-ready');
					}
				);

				Liferay.fire('initStagingBar', config);
			}
		};

		Liferay.StagingBar = StagingBar;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'aui-modal', 'liferay-node']
	}
);