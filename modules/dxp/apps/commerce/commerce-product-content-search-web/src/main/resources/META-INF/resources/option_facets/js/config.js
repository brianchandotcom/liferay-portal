;(function() {
	AUI().applyConfig(
		{
			groups: {
				search: {
					base: MODULE_PATH + '/option_facets/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-cp-option-facets-util': {
							path: 'facet_util.js',
							requires: []
						}
					},
					root: MODULE_PATH + '/option_facets/js/'
				}
			}
		}
	);
})();