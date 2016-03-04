;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				'field-validation': {
					base: MODULE_PATH + '/',
					filter: LiferayAUI.getFilterConfig(),
					combine: Liferay.AUI.getCombine(),
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
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();