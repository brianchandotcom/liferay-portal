;(function() {
	AUI().applyConfig(
		{
			groups: {
				bookmarks: {
					base: MODULE_PATH + '/bookmarks/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-bookmarks': {
							path: 'main.js',
							requires: [
								'liferay-portlet-base'
							]
						}
					},
					root: MODULE_PATH + '/bookmarks/js/'
				}
			}
		}
	);
})();