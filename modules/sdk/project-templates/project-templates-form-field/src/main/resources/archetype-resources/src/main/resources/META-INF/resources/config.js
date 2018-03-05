;(function() {
	AUI().applyConfig(
		{
			groups: {
				'${artifactId}-group': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'${artifactId}-form-field': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: '${artifactId}_field.js',
							requires: [
								'liferay-ddm-form-renderer-field'
							]
						},
						'${artifactId}-form-field-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: '${artifactId}.soy.js',
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