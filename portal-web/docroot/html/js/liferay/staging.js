AUI.add(
	'liferay-staging',
	function(A) {
		var Lang = A.Lang;

		var StagingBar = {
			init: function(config) {
				var instance = this;

				var namespace = config.namespace;

				instance._namespace = namespace;

				instance._stagingBar = A.oneNS(namespace, '#stagingBar');

				instance._bindUI();

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
			},

			_bindUI: function() {
				var instance = this;

				var stagingBar = instance._stagingBar;

				if (stagingBar) {
					stagingBar.delegate(
						'change',
						function(event) {
							A.config.win.location.href = event.currentTarget.val();
						},
						'select.variation-options'
					);
				}
			}
		};

		Liferay.StagingBar = StagingBar;
	},
	'',
	{
		requires: ['aui-io-plugin-deprecated', 'liferay-node', 'liferay-util-window']
	}
);