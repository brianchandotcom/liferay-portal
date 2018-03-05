;(function() {
	AUI().applyConfig(
		{
			groups: {
				layoutcustomizationsettings: {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-layout-customization-settings': {
							path: 'js/layout_customization_settings.js',
							requires: [
								'aui-base',
								'aui-io-request',
								'aui-overlay-mask-deprecated',
								'liferay-portlet-base'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();