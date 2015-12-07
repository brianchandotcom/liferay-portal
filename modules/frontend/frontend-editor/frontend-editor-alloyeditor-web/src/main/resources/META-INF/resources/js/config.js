;(function() {
	var PATH_FRONTEND_EDITOR_ALLOYEDITOR_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/frontend-editor-alloyeditor-web';

	AUI().applyConfig(
		{
			groups: {
				alloyeditor: {
					base: PATH_FRONTEND_EDITOR_ALLOYEDITOR_WEB + '/js/',
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
					root: PATH_FRONTEND_EDITOR_ALLOYEDITOR_WEB + '/js/'
				}
			}
		}
	);
})();