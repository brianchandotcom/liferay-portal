;(function() {
	var PATH_DDM_TYPE_KEYVALUE = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-type-keyvalue';

	AUI().applyConfig(
		{
			groups: {
				'field-keyvalue': {
					base: PATH_DDM_TYPE_KEYVALUE + '/',
					modules: {
						'liferay-ddm-form-field-keyvalue': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'keyvalue_field.js',
							requires: [
								'aui-text-unicode',
								'liferay-ddm-form-field-text',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-keyvalue-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'keyvalue.soy.js',
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