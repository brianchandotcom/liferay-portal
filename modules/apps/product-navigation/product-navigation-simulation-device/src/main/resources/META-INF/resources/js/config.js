;(function() {
	AUI().applyConfig(
		{
			groups: {
				'navigation-simulation-device': {
					base: MODULE_PATH + '/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-product-navigation-simulation-device': {
							path: 'product_navigation_simulation_device.js',
							requires: [
								'aui-dialog-iframe-deprecated',
								'aui-event-input',
								'aui-modal',
								'liferay-portlet-base',
								'liferay-product-navigation-control-menu',
								'liferay-util-window',
								'liferay-widget-size-animation-plugin'
							]
						}
					},
					root: MODULE_PATH + '/js/'
				}
			}
		}
	);
})();