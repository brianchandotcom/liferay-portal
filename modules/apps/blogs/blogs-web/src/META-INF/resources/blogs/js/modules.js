;(function() {
	var PATH_PORTLET = Liferay.AUI.getPortletRootPath();

	AUI().applyConfig(
		{
			groups: {
				blogs: {
					base: PATH_PORTLET + '/blogs/js/',
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
					root: PATH_PORTLET + '/blogs/js/'
				}
			}
		}
	);
})();