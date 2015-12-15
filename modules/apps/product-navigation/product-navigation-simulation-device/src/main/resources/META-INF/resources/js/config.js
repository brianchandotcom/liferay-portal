;(function() {
	var PATH_NAVIGATION_SIMULATION_DEVICE = Liferay.ThemeDisplay.getPathContext() + '/o/product-navigation-simulation-device';

	AUI().applyConfig(
		{
			groups: {
				'navigation-simulation-device': {
					base: PATH_NAVIGATION_SIMULATION_DEVICE + '/js/',
					modules: {
						'liferay-product-navigation-simulation-device': {
							path: 'product_navigation_simulation_device.js',
							requires: [
								'aui-dialog-iframe-deprecated',
								'aui-event-input',
								'aui-modal',
								'liferay-control-menu',
								'liferay-portlet-base',
								'liferay-util-window',
								'liferay-widget-size-animation-plugin'
							]
						}
					},
					root: PATH_NAVIGATION_SIMULATION_DEVICE + '/js/'
				}
			}
		}
	);
})();