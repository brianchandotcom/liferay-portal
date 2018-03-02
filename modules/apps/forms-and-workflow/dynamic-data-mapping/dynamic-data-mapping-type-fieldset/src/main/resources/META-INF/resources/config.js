;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-fieldset': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-fieldset': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'fieldset_field.js',
							requires: [
								'liferay-ddm-form-field-fieldset-util',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-fieldset-util': {
							path: 'fieldset_field_util.js',
							requires: [
								'aui-component',
								'liferay-ddm-form-renderer-util'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();