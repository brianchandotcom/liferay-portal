;(function() {
	AUI().applyConfig(
		{
			groups: {
				components: {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-calendar-recurrence-converter': {
							path: 'js/recurrence_converter.js',
							requires: []
						},
						'liferay-calendar-recurrence-dialog': {
							path: 'js/recurrence.js',
							requires: [
								'aui-base',
								'liferay-calendar-recurrence-util'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();