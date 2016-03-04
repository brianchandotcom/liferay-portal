;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				'field-key-value': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-key-value': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'key_value_field.js',
							requires: [
								'aui-text-unicode',
								'liferay-ddm-form-field-text',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-key-value-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'key_value.soy.js',
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