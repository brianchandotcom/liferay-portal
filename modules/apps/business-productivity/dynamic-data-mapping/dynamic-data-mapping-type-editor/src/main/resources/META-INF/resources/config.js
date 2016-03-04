;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-editor': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-editor': {
							path: 'editor_field.js',
							requires: [
								'liferay-alloy-editor',
								'liferay-ddm-form-field-text',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-editor-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'editor.soy.js',
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