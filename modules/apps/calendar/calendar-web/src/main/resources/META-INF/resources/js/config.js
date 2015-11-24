;(function() {
	var PATH_CALENDAR_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/calendar-web';

	AUI().applyConfig(
		{
			groups: {
				components: {
					base: PATH_CALENDAR_WEB + '/',
					modules: {
						'liferay-calendar-recurrence-dialog': {
							path: 'js/recurrence.js',
							requires: [
								'aui-base',
								'liferay-calendar-recurrence-util'
							]
						},
						'liferay-calendar-recurrence-converter': {
							path: 'js/recurrence_converter.js',
							requires: []
						}
					},
					root: PATH_CALENDAR_WEB + '/'
				}
			}
		}
	);
})();