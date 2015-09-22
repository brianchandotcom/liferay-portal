;(function() {
	var PATH_SOYUTILS = Liferay.ThemeDisplay.getPathContext() + '/o/frontend-js-soyutils-web';

	AUI().applyConfig(
		{
			groups: {
				'soyutils': {
					base: PATH_SOYUTILS + '/',
					combine: LiferayAUI.getCombine(),
					modules: {
						'soyutils': {
							path: 'soyutils.js'
						}
					},
					root: PATH_SOYUTILS + '/'
				}
			}
		}
	);

	Loader.addModule(
		{
			dependencies: [],
			exports: 'soy',
			name: 'soyutils',
			path: PATH_SOYUTILS + '/soyutils.js'
		}
	);
})();