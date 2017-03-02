;(function() {
	AUI().applyConfig(
		{
			groups: {
				stagingTaglib: {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-export-import-management-bar-button': {
							path: 'export_import_entity_management_bar_button/js/main.js',
							requires: [
								'aui-component',
								'liferay-search-container',
								'liferay-search-container-select'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();