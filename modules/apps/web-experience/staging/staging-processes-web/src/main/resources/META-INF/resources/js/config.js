;(function() {
	AUI().applyConfig(
		{
			groups: {
				stagingprocessesweb: {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-staging-processes-export-import': {
							path: 'js/main.js',
							requires: [
								'aui-datatype',
								'aui-dialog-iframe-deprecated',
								'aui-io-request',
								'aui-modal',
								'aui-parse-content',
								'aui-toggler',
								'aui-tree-view',
								'liferay-notice',
								'liferay-portlet-base',
								'liferay-portlet-url',
								'liferay-store',
								'liferay-util-window'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();