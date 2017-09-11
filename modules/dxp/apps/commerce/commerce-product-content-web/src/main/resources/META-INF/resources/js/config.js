;(function() {
	AUI().applyConfig(
		{
			groups: {
				productcontent: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-commerce-product-content': {
							path: 'product_content.js',
							requires: [
								'aui-base',
								'aui-io-request',
								'aui-parse-content',
								'liferay-portlet-base',
								'liferay-portlet-url'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();