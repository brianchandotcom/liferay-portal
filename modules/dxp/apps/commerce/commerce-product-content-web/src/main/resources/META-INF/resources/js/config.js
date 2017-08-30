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
								'liferay-portlet-base',
								'aui-io-request',
								'aui-parse-content'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();