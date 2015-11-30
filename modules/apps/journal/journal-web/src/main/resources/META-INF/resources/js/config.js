;(function() {
	var PATH_JOURNAL_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/journal-web';

	AUI().applyConfig(
		{
			groups: {
				journal: {
					base: PATH_JOURNAL_WEB + '/js/',
					modules: {
						'liferay-journal-content': {
							path: 'content.js',
							requires: [
								'aui-base',
								'liferay-portlet-base'
							]
						},
						'liferay-journal-navigation': {
							path: 'navigation.js',
							requires: [
								'liferay-portlet-base'
							]
						},
						'liferay-portlet-journal': {
							path: 'main.js',
							requires: [
								'aui-base',
								'aui-dialog-iframe-deprecated',
								'aui-tooltip',
								'liferay-portlet-base',
								'liferay-util-window'
							]
						}
					},
					root: PATH_JOURNAL_WEB + '/js/'
				}
			}
		}
	);
})();