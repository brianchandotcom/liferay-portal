;(function() {
	var PATH_FRONTEND_MAP_OPENSTREET_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/map-openstreet-web';

	AUI().applyConfig(
		{
			groups: {
				mapopenstreet: {
					base: PATH_FRONTEND_MAP_OPENSTREET_WEB + '/js/',
					modules: {
						'liferay-map-openstreet': {
							path: 'map.js',
							requires: [
								'jsonp',
								'liferay-map-base',
								'timers'
							]
						}
					},
					root: PATH_FRONTEND_MAP_OPENSTREET_WEB + '/js/'
				}
			}
		}
	);
})();