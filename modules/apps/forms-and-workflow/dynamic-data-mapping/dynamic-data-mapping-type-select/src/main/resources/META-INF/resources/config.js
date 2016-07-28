;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				'field-select': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-select': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-select-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select.soy.js',
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