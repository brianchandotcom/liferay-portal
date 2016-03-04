;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				'field-options': {
					base: MODULE_PATH + '/',
					filter: LiferayAUI.getFilterConfig(),
					combine: Liferay.AUI.getCombine(),
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
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();