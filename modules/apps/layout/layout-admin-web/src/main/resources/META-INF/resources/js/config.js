;(function() {
	var PATH_LAYOUT_ADMIN_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/layout-admin-web';

	AUI().applyConfig(
		{
			groups: {
				'layout-admin-web': {
					base: PATH_LAYOUT_ADMIN_WEB + '/js/',
					modules: {
						'liferay-layout-tree-fullscreen': {
							path: 'layout-tree-fullscreen.js',
							requires: [
								'aui-component',
								'liferay-portlet-base'
							]
						}
					},
					root: PATH_LAYOUT_ADMIN_WEB + '/js/'
				}
			}
		}
	);
})();