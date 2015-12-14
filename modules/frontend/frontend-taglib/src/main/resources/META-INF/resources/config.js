;(function() {
	var PATH_FRONTEND_TAGLIB = Liferay.ThemeDisplay.getPathContext() + '/o/frontend-taglib';

	AUI().applyConfig(
		{
			groups: {
				'frontend-taglib': {
					base: PATH_FRONTEND_TAGLIB + '/',
					modules: {
						'liferay-sidebar-panel': {
							path: 'sidebar_panel/js/sidebar_panel.js',
							requires: [
								'aui-base',
								'aui-io-request',
								'liferay-portlet-base'
							]
						}
					},
					root: PATH_FRONTEND_TAGLIB + '/'
				}
			}
		}
	);
})();