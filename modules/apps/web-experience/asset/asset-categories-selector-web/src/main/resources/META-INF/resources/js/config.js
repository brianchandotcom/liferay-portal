;(function() {
	AUI().applyConfig(
		{
			groups: {
				'liferay-asset-portlet-category-selector-group': {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-asset-portlet-category-selector': {
							path: 'liferay_asset_portlet_category_selector.js',
							requires: [
								'aui-tree-view'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();