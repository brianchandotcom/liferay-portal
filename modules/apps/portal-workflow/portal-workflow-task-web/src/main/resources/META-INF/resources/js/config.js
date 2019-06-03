(function() {
	AUI().applyConfig({
		groups: {
			workflowtasks: {
				base: MODULE_PATH + '/js/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-workflow-tasks': {
						path: 'main.js',
						requires: ['liferay-util-window']
					}
				},
				root: MODULE_PATH + '/js/'
			}
		}
	});
})();
