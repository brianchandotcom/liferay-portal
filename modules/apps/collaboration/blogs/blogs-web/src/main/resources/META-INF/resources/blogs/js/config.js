;(function() {
	AUI().applyConfig(
		{
			groups: {
				blogs: {
					base: MODULE_PATH + '/blogs/js/',
					combine: Liferay.AUI.getCombine(),
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