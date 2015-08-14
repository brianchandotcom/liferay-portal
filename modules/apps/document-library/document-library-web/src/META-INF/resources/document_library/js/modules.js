;(function() {
	var PATH_DDM_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/ddm-web';

	var PATH_DL_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/document-library-web';

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
				},
				dl: {
					base: PATH_DL_WEB + '/document_library/js/',
					modules: {
						'document-library-upload': {
							path: 'upload.js',
							requires: [
								'aui-component',
								'aui-data-set-deprecated',
								'aui-overlay-manager-deprecated',
								'aui-overlay-mask-deprecated',
								'aui-parse-content',
								'aui-progressbar',
								'aui-template-deprecated',
								'aui-tooltip',
								'liferay-app-view-move',
								'liferay-app-view-select',
								'liferay-search-container',
								'liferay-storage-formatter',
								'querystring-parse-simple',
								'uploader'
							]
						},
						'liferay-document-library': {
							path: 'main.js',
							requires: [
								'document-library-upload',
								'liferay-app-view-move',
								'liferay-message',
								'liferay-portlet-base'
							]
						}
					},
					root: PATH_DL_WEB + '/document_library/js/'
				}
			}
		}
	);
})();