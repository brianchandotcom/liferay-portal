;(function() {
	AUI().applyConfig(
		{
			groups: {
				calendar: {
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
						'liferay-calendar-date-picker-sanitizer': {
							path: 'date_picker_sanitizer.js',
							requires: [
								'aui-base'
							]
						},
						'liferay-calendar-interval-selector': {
							path: 'interval_selector.js',
							requires: [
								'aui-base',
								'liferay-portlet-base'
							]
						},
						'liferay-calendar-interval-selector-scheduler-event-link': {
							path: 'interval_selector_scheduler_event_link.js',
							requires: [
								'aui-base',
								'liferay-portlet-base'
							]
						},
						'liferay-calendar-list': {
							path: 'calendar_list.js',
							requires: [
								'aui-template-deprecated',
								'liferay-scheduler'
							]
						},
						'liferay-calendar-message-util': {
							path: 'message_util.js',
							requires: [
								'aui-alert',
								'liferay-util-window'
							]
						},
						'liferay-calendar-recurrence-converter': {
							path: 'recurrence_converter.js',
							requires: []
						},
						'liferay-calendar-recurrence-dialog': {
							path: 'recurrence.js',
							requires: [
								'aui-base',
								'liferay-calendar-recurrence-util'
							]
						},
						'liferay-calendar-recurrence-util': {
							path: 'recurrence_util.js',
							requires: [
								'aui-base',
								'liferay-util-window'
							]
						},
						'liferay-calendar-reminders': {
							path: 'calendar_reminders.js',
							requires: [
								'aui-base'
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
						'liferay-calendar-session-listener': {
							path: 'session_listener.js',
							requires: [
								'aui-base',
								'liferay-scheduler'
							]
						},
						'liferay-calendar-simple-color-picker': {
							path: 'simple_color_picker.js',
							requires: [
								'aui-base',
								'aui-template-deprecated'
							]
						},
						'liferay-calendar-simple-menu': {
							path: 'simple_menu.js',
							requires: [
								'aui-base',
								'aui-template-deprecated',
								'event-outside',
								'event-touch',
								'widget-modality',
								'widget-position',
								'widget-position-align',
								'widget-position-constrain',
								'widget-stack',
								'widget-stdmod'
							]
						},
						'liferay-calendar-util': {
							path: 'calendar_util.js',
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
						'liferay-scheduler': {
							path: 'scheduler.js',
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