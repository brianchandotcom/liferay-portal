;(function() {
	var PATH_DDM_TYPE_CHECKBOX = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-type-checkbox';

	AUI().applyConfig(
		{
			groups: {
				'field-checkbox': {
					base: PATH_DDM_TYPE_CHECKBOX + '/',
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
					root: PATH_DDM_TYPE_CHECKBOX + '/'
				}
			}
		}
	);
})();