;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-paragraph': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
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
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();