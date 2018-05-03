;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-paragraph': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-paragraph': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'paragraph_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();