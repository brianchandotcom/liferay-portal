AUI.add(
	'liferay-uad-export',
	function(A) {
		var Lang = A.Lang;

		var RENDER_INTERVAL_IDLE = 60000;

		var RENDER_INTERVAL_IN_PROGRESS = 2000;

		var UADExport = A.Component.create(
			{
				ATTRS: {
					exportProcessesNode: {
						setter: '_setNode'
					}
				},

				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'uadexport',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._exportProcessesResourceURL = config.exportProcessesResourceURL;

						instance._renderTimer = A.later(RENDER_INTERVAL_IN_PROGRESS, instance, instance._renderExportProcesses);
					},

					destructor: function() {
						var instance = this;

						if (instance._renderTimer) {
							instance._renderTimer.cancel();
						}
					},

					_isBackgroundTaskInProgress: function() {
						var instance = this;

						var exportProcessesNode = instance.get('exportProcessesNode');

						return !!exportProcessesNode.one('.export-process-status-in-progress');
					},

					_renderExportProcesses: function() {
						var instance = this;

						var exportProcessesNode = instance.get('exportProcessesNode');

						if (exportProcessesNode && instance._exportProcessesResourceURL) {
							A.io.request(
								instance._exportProcessesResourceURL,
								{
									method: 'GET',
									on: {
										success: function(event, id, obj) {
											exportProcessesNode.plug(A.Plugin.ParseContent);

											exportProcessesNode.empty();

											exportProcessesNode.setContent(this.get('responseData'));

											instance._scheduleRenderProcess();
										}
									}
								}
							);
						}
					},

					_scheduleRenderProcess: function() {
						var instance = this;

						var renderInterval = RENDER_INTERVAL_IDLE;

						if (instance._isBackgroundTaskInProgress()) {
							renderInterval = RENDER_INTERVAL_IN_PROGRESS;
						}

						instance._renderTimer = A.later(renderInterval, instance, instance._renderExportProcesses);
					},

					_setNode: function(val) {
						var instance = this;

						if (Lang.isString(val)) {
							val = instance.one(val);
						}
						else {
							val = A.one(val);
						}

						return val;
					}
				}
			}
		);

		Liferay.UADExport = UADExport;
	},
	'',
	{
		requires: ['aui-io-request', 'aui-parse-content', 'liferay-portlet-base']
	}
);