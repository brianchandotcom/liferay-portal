;(function() {
	AUI().applyConfig(
		{
			groups: {
				'soyutils': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'soyutils': {
							path: 'soyutils.js'
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);

	Loader.addModule(
		{
			dependencies: [],
			exports: 'soy',
			name: 'soyutils',
			path: MODULE_PATH + '/soyutils.js'
		}
	);
})();