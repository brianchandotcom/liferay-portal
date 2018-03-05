;(function() {
	AUI().applyConfig(
		{
			groups: {
				dl: {
					base: MODULE_PATH + '/document_library/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'document-library-checkin': {
							path: 'checkin.js',
							requires: [
								'liferay-document-library',
								'liferay-util-window'
							]
						},
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
								'liferay-history-manager',
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
								'liferay-message',
								'liferay-portlet-base'
							]
						}
					},
					root: MODULE_PATH + '/document_library/js/'
				}
			}
		}
	);
})();