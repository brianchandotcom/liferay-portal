;(function() {
	var PATH_DDM_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-web';

	AUI().applyConfig(
		{
			groups: {
				ddm: {
					base: PATH_DDM_WEB + '/js/',
					modules: {
						'liferay-portlet-dynamic-data-mapping': {
							condition: {
								trigger: 'liferay-document-library'
							},
							path: 'main.js',
							requires: [
								'arraysort',
								'aui-form-builder-deprecated',
								'aui-form-validator',
								'aui-map', 'aui-text-unicode',
								'json',
								'liferay-menu',
								'liferay-translation-manager',
								'liferay-util-window',
								'text'
							]
						},
						'liferay-portlet-dynamic-data-mapping-custom-fields': {
							condition: {
								trigger: 'liferay-document-library'
							},
							path: 'custom_fields.js',
							requires: [
								'liferay-portlet-dynamic-data-mapping'
							]
						}
					},
					root: PATH_DDM_WEB + '/js/'
				}
			}
		}
	);
})();