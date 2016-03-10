;(function() {
	AUI().applyConfig(
		{
			groups: {
				alloyeditor: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-alloy-editor': {
							path: 'alloyeditor.js',
							requires: [
								'aui-component',
								'liferay-portlet-base'
							]
						},
						'liferay-alloy-editor-source': {
							path: 'alloyeditor_source.js',
							requires: [
								'aui-debounce',
								'liferay-fullscreen-source-editor',
								'liferay-source-editor',
								'plugin'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();