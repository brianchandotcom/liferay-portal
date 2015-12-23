;(function() {
	var PATH_FRONTEND_MAP_COMMON_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/map-common-web';

	AUI().applyConfig(
		{
			groups: {
				mapbase: {
					base: PATH_FRONTEND_MAP_COMMON_WEB + '/js/',
					modules: {
						'liferay-map-base': {
							path: 'map.js',
							requires: [
								'aui-base'
							]
						}
					},
					root: PATH_FRONTEND_MAP_COMMON_WEB + '/js/'
				}
			}
		}
	);
})();