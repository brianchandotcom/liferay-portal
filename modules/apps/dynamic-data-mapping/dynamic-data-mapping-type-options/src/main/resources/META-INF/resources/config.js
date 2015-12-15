;(function() {
	var LiferayAUI = Liferay.AUI;

	var PATH_DDM_TYPE_OPTIONS = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-type-options';

	AUI().applyConfig(
		{
			groups: {
				'field-options': {
					base: PATH_DDM_TYPE_OPTIONS + '/',
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-options': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'options_field.js',
							requires: [
								'aui-sortable-list',
								'liferay-ddm-form-field-key-value',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-options-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'options.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_OPTIONS + '/'
				}
			}
		}
	);
})();