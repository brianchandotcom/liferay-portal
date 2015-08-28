;(function() {
	var PATH_DDM_TYPE_RADIO = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-type-radio';

	AUI().applyConfig(
		{
			groups: {
				'field-radio': {
					base: PATH_DDM_TYPE_RADIO + '/',
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
					root: PATH_DDM_TYPE_RADIO + '/'
				}
			}
		}
	);
})();