;(function() {
	var PATH_ITEM_SELECTOR_TAGLIB = Liferay.ThemeDisplay.getPathContext() + '/o/item-selector-taglib';

	AUI().applyConfig(
		{
			groups: {
				'item-selector-taglib': {
					base: PATH_ITEM_SELECTOR_TAGLIB + '/',
					modules: {
						'liferay-image-selector': {
							path: 'image_selector/js/image_selector.js',
							requires: [
								'aui-base',
								'liferay-item-selector-dialog',
								'liferay-portlet-base',
								'uploader'
							]
						}
					},
					root: PATH_ITEM_SELECTOR_TAGLIB + '/'
				}
			}
		}
	);
})();