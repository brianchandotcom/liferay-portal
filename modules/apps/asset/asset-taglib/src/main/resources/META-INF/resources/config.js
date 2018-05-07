;(function() {
	AUI().applyConfig(
		{
			groups: {
				'asset-taglib': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-asset-taglib-categories-selector': {
							path: 'asset_categories_selector/js/asset_taglib_categories_selector.js',
							requires: [
								'aui-tree',
								'liferay-asset-taglib-tags-selector'
							]
						},
						'liferay-asset-taglib-tags-selector': {
							path: 'asset_tags_selector/js/asset_taglib_tags_selector.js',
							requires: [
								'aui-io-plugin-deprecated',
								'aui-live-search-deprecated',
								'aui-template-deprecated',
								'aui-textboxlist-deprecated',
								'datasource-cache',
								'liferay-item-selector-dialog',
								'liferay-service-datasource'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();