AUI().add(
	'liferay-staging',
	function(A) {
		var Lang = A.Lang;

		var Stagingbar = {
			init: function(config) {
				var instance = this;

				instance._namespace = config.namespace;

				Liferay.publish(
					{
						fireOnce: true
					}
				);

				Liferay.after(
					'initStagingbar',
					function(event) {
						A.getBody().addClass('staging-ready');
					}
				);

				Liferay.fire('initStagingbar', config);
			}
		};

		Liferay.Stagingbar = Stagingbar;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-plugin']
	}
);