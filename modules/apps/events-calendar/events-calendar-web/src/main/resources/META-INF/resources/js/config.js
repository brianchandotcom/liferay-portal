;(function() {
	AUI().applyConfig(
		{
			groups: {
				'events-calendar': {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-calendar-container': {
							path: 'calendar_container.js',
							requires: [
								'aui-alert',
								'aui-base',
								'aui-component',
								'liferay-portlet-base'
							]
						},
						'liferay-calendar-remote-services': {
							path: 'remote_services.js',
							requires: [
								'aui-base',
								'aui-component',
								'aui-io',
								'liferay-calendar-util',
								'liferay-portlet-base',
								'liferay-portlet-url'
							]
						},
						'liferay-calendar-util-override': {
							path: 'calendar_util.js',
							condition: {
							   name: 'liferay-calendar-util-override',
							   trigger: 'liferay-calendar-util',
							   when: 'instead'
							},
							requires: [
								'aui-datatype',
								'aui-io',
								'aui-scheduler',
								'aui-toolbar',
								'autocomplete',
								'autocomplete-highlighters',
								'liferay-portlet-url'
							]
						},
						'liferay-scheduler-override': {
							path: 'scheduler.js',
							condition: {
								name: 'liferay-scheduler-override',
								trigger: 'liferay-scheduler',
								when: 'instead'
							},
							requires: [
								'async-queue',
								'aui-datatype',
								'aui-scheduler',
								'dd-plugin',
								'liferay-calendar-message-util',
								'liferay-calendar-recurrence-converter',
								'liferay-calendar-recurrence-util',
								'liferay-calendar-util',
								'liferay-node',
								'liferay-scheduler-event-recorder',
								'liferay-scheduler-models',
								'liferay-store',
								'promise',
								'resize-plugin'
							]

						},
						'liferay-scheduler-event-recorder': {
							path: 'scheduler_event_recorder.js',
							requires: [
								'dd-plugin',
								'liferay-calendar-util',
								'liferay-node',
								'resize-plugin'
							]
						},
						'liferay-scheduler-models': {
							path: 'scheduler_models.js',
							requires: [
								'aui-datatype',
								'dd-plugin',
								'liferay-calendar-util',
								'liferay-store'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();