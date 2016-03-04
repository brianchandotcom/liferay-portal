;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-date': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-ddm-form-field-date': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'date_field.js',
							requires: [
								'aui-datepicker',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-date-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'date.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();