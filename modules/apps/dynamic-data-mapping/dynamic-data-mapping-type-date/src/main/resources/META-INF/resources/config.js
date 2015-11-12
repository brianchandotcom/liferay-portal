;(function() {
	var PATH_DDM_TYPE_DATE = Liferay.ThemeDisplay.getPathContext() + '/o/dynamic-data-mapping-type-date';

	AUI().applyConfig(
		{
			groups: {
				'field-date': {
					base: PATH_DDM_TYPE_DATE + '/',
					modules: {
						'liferay-ddm-form-field-date': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'date_field.js',
							requires: [
								'aui-datepicker',
								'liferay-ddm-form-renderer-field'
							]
						},
						'liferay-ddm-form-field-date-template': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'date.soy.js',
							requires: [
								'soyutils'
							]
						}
					},
					root: PATH_DDM_TYPE_DATE + '/'
				}
			}
		}
	);
})();