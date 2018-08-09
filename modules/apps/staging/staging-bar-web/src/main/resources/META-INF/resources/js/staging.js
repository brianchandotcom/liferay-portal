AUI.add(
	'liferay-staging',
	function(A) {
		var Lang = A.Lang;

		var StagingBar = {
			init: function(config) {
				var instance = this;

				var namespace = config.namespace;

				instance.markAsReadyForPublicationURL = config.markAsReadyForPublicationURL;

				instance.layoutRevisionStatusURL = config.layoutRevisionStatusURL;

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
						var stagingLevel3 = A.one('.staging-bar-level-3-message');

						A.getBody().addClass(stagingLevel3 === null ? 'staging-ready' : 'staging-ready-level-3');
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