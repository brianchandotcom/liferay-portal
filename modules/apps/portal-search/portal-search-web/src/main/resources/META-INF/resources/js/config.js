;(function() {
	AUI().applyConfig(
		{
			groups: {
				modified_facet: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-search-modified-facet': {
							path: 'modified_facet.js',
							requires: ['liferay-search-facet-util']
						}
					},
					root: MODULE_PATH + '/js/'
				},
				search: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-search-bar': {
							path: 'search_bar.js',
							requires: []
						},
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