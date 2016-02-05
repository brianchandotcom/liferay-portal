;(function() {
	var PATH_DDM_TYPE_CAPTCHA = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-type-captcha';

	AUI().applyConfig(
		{
			groups: {
				'field-captcha': {
					base: PATH_DDM_TYPE_CAPTCHA + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-ddm-form-field-captcha': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'captcha_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						}
					},
					root: PATH_DDM_TYPE_CAPTCHA + '/'
				}
			}
		}
	);
})();