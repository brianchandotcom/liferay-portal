;(function() {
	var PATH_FRONTEND_MAP_BASE_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/map-base-web';

	AUI().applyConfig(
		{
			groups: {
				mapbase: {
					base: PATH_FRONTEND_MAP_BASE_WEB + '/js/',
					modules: {
						'liferay-map-base': {
							path: 'map.js',
							requires: [
								'aui-base'
							]
						}
					},
					root: PATH_FRONTEND_MAP_BASE_WEB + '/js/'
				}
			}
		}
	);
})();