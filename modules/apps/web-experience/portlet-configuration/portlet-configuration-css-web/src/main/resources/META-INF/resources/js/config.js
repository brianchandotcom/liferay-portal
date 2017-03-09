;(function() {
	AUI().applyConfig(
		{
			groups: {
				lookandfeel: {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-portlet-look-and-feel': {
							path: 'main.js',
							requires: [
								'aui-base',
								'aui-color-picker-popover',
								'liferay-portlet-base'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();