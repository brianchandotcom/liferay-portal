;(function() {
	var PATH_DDM_TYPE_EDITOR = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-type-editor';

	AUI().applyConfig(
		{
			groups: {
				'field-editor': {
					base: PATH_DDM_TYPE_EDITOR + '/',
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
					root: PATH_DDM_TYPE_EDITOR + '/'
				}
			}
		}
	);
})();