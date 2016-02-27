;(function() {
	var PATH_FRONTEND_MAP_OPEN_STREET_MAP_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/map-openstreetmap';

	AUI().applyConfig(
		{
			groups: {
				mapopenstreet: {
					base: PATH_FRONTEND_MAP_OPEN_STREET_MAP_WEB + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-map-openstreetmap': {
							path: 'map.js',
							requires: [
								'jsonp',
								'liferay-map-common',
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