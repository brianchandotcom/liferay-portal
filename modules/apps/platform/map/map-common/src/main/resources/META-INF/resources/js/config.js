;(function() {
	var PATH_FRONTEND_MAP_COMMON_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/map-common';

	AUI().applyConfig(
		{
			groups: {
				mapbase: {
					base: PATH_FRONTEND_MAP_COMMON_WEB + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-map-common': {
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