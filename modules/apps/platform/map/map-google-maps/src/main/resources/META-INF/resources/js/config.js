;(function() {
	AUI().applyConfig(
		{
			groups: {
				mapgoogle: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-map-google-maps': {
							path: 'map.js',
							requires: [
								'liferay-map-common'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();