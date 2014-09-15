;(function() {

	var LiferayAUI = Liferay.AUI;

	var PATH_PORTLET = LiferayAUI.getPortletRootPath();

	AUI().applyConfig(
		{
			groups: {
				blogs: {
					base: PATH_PORTLET + '/blogs/js/',
					modules: {
						'liferay-blogs': {
							path: 'blog.js',
							requires: [
								'aui-base',
								'aui-toggler',
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