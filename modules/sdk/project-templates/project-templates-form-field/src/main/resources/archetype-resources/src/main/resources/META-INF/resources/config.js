;(function() {
	AUI().applyConfig(
		{
			groups: {
#if (${liferayVersion.startsWith("7.0")})
				'${artifactId}-group': {
#elseif (${liferayVersion.startsWith("7.1")})
				'field-${artifactId}': {
#end
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
#if (${liferayVersion.startsWith("7.0")})
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
#elseif (${liferayVersion.startsWith("7.1")})
						}
#end
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();