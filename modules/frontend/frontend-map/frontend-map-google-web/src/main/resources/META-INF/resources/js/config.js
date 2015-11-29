;(function() {
	var PATH_FRONTEND_MAP_GOOGLE_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/frontend-map-google-web';

	AUI().applyConfig(
		{
			groups: {
				mapgoogle: {
					base: PATH_FRONTEND_MAP_GOOGLE_WEB + '/js/',
					modules: {
						'liferay-map-google': {
							path: 'map.js',
							requires: [
								'liferay-map-base'
							]
						}
					},
					root: PATH_FRONTEND_MAP_GOOGLE_WEB + '/js/'
				}
			}
		}
	);
})();