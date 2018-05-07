;(function() {
	AUI().applyConfig(
		{
			groups: {
				'field-password': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-password': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'password_field.js',
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