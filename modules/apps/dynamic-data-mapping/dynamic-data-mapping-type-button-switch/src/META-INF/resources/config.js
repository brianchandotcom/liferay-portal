;(function() {
	var PATH_DDM_TYPE_BUTTON_SWITCH = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-type-button-switch';

	AUI().applyConfig(
		{
			groups: {
				'field-button-switch': {
					base: PATH_DDM_TYPE_BUTTON_SWITCH + '/',
					modules: {
						'liferay-ddm-form-field-button-switch': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'button-switch_field.js',
							requires: [
								'liferay-ddm-form-renderer-field',
								'aui-button-switch'
							]
						},
						'liferay-ddm-form-field-button-switch-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'button-switch.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_BUTTON_SWITCH + '/'
				}
			}
		}
	);
})();