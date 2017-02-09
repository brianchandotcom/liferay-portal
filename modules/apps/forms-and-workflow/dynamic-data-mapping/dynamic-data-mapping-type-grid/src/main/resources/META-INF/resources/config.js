;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				'field-grid': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-grid': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'grid_field.js',
							requires: [
								'liferay-ddm-form-field-grid-template',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-grid-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'grid.soy.js',
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