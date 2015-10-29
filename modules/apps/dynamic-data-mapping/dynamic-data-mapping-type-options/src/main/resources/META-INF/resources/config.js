;(function() {
	var PATH_DDM_TYPE_OPTIONS = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-type-options';

	AUI().applyConfig(
		{
			groups: {
				'field-options': {
					base: PATH_DDM_TYPE_OPTIONS + '/',
					modules: {
						'liferay-ddm-form-field-options': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'options_field.js',
							requires: [
								'liferay-auto-fields',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-options-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'options.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_OPTIONS + '/'
				}
			}
		}
	);
})();