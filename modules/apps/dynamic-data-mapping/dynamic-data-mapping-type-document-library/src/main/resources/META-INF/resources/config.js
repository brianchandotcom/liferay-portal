;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-document-library': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-document-library': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'document_library_field.js',
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