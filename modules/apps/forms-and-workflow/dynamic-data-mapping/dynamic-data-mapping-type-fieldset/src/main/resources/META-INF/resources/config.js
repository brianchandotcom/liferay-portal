;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-fieldset': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-ddm-form-field-fieldset': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'fieldset_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-fieldset-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'fieldset.soy.js',
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