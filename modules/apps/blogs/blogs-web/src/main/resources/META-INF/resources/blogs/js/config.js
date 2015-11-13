;(function() {
	var PATH_BLOGS_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/blogs-web';

	AUI().applyConfig(
		{
			groups: {
				blogs: {
					base: PATH_BLOGS_WEB + '/blogs/js/',
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
					root: PATH_BLOGS_WEB + '/blogs/js/'
				}
			}
		}
	);
})();