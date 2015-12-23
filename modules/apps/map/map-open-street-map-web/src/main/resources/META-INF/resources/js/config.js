;(function() {
	var PATH_FRONTEND_MAP_OPEN_STREET_MAP_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/map-open-street-map-web';

	AUI().applyConfig(
		{
			groups: {
				mapopenstreet: {
					base: PATH_FRONTEND_MAP_OPEN_STREET_MAP_WEB + '/js/',
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
					root: PATH_FRONTEND_MAP_OPEN_STREET_MAP_WEB + '/js/'
				}
			}
		}
	);
})();