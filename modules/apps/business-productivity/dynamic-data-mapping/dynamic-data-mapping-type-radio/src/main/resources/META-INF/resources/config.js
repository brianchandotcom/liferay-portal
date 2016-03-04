;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-radio': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-ddm-form-field-radio': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'radio_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-radio-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'radio.soy.js',
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