;(function() {
	var LiferayAUI = Liferay.AUI;

	var PATH_DDM_TYPE_VALIDATION = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-type-validation';

	AUI().applyConfig(
		{
			groups: {
				'field-validation': {
					base: PATH_DDM_TYPE_VALIDATION + '/',
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-validation': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'validation_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-validation-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'validation.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_VALIDATION + '/'
				}
			}
		}
	);
})();