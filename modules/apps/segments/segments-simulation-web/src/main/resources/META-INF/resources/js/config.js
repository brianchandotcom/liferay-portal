;(function() {
	AUI().applyConfig(
		{
			groups: {
				segmentssimulation: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-portlet-segments-simulation': {
							path: 'main.js',
							requires: [
								'aui-base',
								'aui-io-request',
								'liferay-portlet-base',
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();