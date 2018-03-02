;(function() {
	AUI().applyConfig(
		{
			groups: {
				search: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-search-facet-util': {
							path: 'facet_util.js',
							requires: []
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();