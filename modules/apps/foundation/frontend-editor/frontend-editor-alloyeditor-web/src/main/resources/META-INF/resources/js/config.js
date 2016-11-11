;(function() {
	AUI().applyConfig(
		{
			groups: {
				alloyeditor: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
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
						},
						'liferay-editor-image-uploader': {
							path: 'editor_image_uploader.js',
							requires: [
								'aui-alert',
								'aui-base',
								'aui-progressbar',
								'uploader'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();