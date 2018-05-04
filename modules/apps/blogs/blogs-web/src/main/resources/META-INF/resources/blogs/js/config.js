;(function() {
	AUI().applyConfig(
		{
			groups: {
				blogs: {
					base: MODULE_PATH + '/blogs/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-blogs': {
							path: 'blogs.js',
							requires: [
								'aui-base',
								'aui-io-request',
								'liferay-portlet-base'
							]
						}
					},
					root: MODULE_PATH + '/blogs/js/'
				}
			}
		}
	);
})();