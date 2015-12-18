;(function() {
	var PATH_DDM_TYPE_PARAGRAPH = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-type-paragraph';

	AUI().applyConfig(
		{
			groups: {
				'field-paragraph': {
					base: PATH_DDM_TYPE_PARAGRAPH + '/',
					modules: {
						'liferay-ddm-form-field-paragraph': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'paragraph_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-paragraph-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'paragraph.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_PARAGRAPH + '/'
				}
			}
		}
	);
})();