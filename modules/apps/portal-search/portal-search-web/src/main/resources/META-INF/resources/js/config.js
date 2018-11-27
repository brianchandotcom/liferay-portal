;(function() {
	AUI().applyConfig(
		{
			groups: {
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
						},
						'liferay-search-modified-facet': {
							path: 'modified_facet.js',
							requires: ['liferay-search-facet-util']
						},
						'liferay-search-modified-facet-configuration': {
							path: 'modified_facet_configuration.js',
							requires: ['aui-node']
						},
						'liferay-search-sort-configuration': {
							path: 'sort_configuration.js',
							requires: ['aui-node']
						},
						'liferay-search-sort-util': {
							path: 'sort_util.js',
							requires: []
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();