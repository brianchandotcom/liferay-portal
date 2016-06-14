;(function() {
	AUI().applyConfig(
		{
			groups: {
				'asset-taglib': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-asset-taglib-tags-selector': {
							path: 'asset_tags_selector/js/asset_tags_selector.js',
							requires: [
								'aui-io-plugin-deprecated',
								'aui-live-search-deprecated',
								'aui-modal',
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