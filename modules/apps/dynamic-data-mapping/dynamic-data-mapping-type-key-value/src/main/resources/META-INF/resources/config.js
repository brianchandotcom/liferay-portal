;(function() {
	var LiferayAUI = Liferay.AUI;

	var PATH_DDM_TYPE_KEYVALUE = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-type-key-value';

	AUI().applyConfig(
		{
			groups: {
				'field-key-value': {
					base: PATH_DDM_TYPE_KEYVALUE + '/',
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-key-value': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'key_value_field.js',
							requires: [
								'aui-text-unicode',
								'liferay-ddm-form-field-text',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-key-value-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'key_value.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_KEYVALUE + '/'
				}
			}
		}
	);
})();