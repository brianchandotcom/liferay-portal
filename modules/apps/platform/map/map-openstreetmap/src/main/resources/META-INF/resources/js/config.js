;(function() {
	AUI().applyConfig(
		{
			groups: {
				mapopenstreet: {
					base: MODULE_PATH + '/js/',
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
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();