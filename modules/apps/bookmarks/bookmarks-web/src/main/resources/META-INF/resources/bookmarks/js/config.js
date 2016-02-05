;(function() {
	var PATH_BOOKMARKS_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/bookmarks-web';

	AUI().applyConfig(
		{
			groups: {
				bookmarks: {
					base: PATH_BOOKMARKS_WEB + '/bookmarks/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-bookmarks': {
							path: 'main.js',
							requires: [
								'liferay-portlet-base'
							]
						}
					},
					root: PATH_BOOKMARKS_WEB + '/bookmarks/js/'
				}
			}
		}
	);
})();