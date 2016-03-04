;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-checkbox': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-ddm-form-field-checkbox': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'checkbox_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-checkbox-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'checkbox.soy.js',
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