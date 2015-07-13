;(function() {
	var PATH_DOCKBAR_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/comliferaycontrolmenuweb';

	AUI().applyConfig(
		{
			groups: {
				controlmenu: {
					base: PATH_DOCKBAR_WEB + '/js/',
					modules: {
						'liferay-dockbar': {
							path: 'dockbar.js',
							requires: [
								'aui-node',
								'event-touch'
							]
						},
						'liferay-dockbar-add-application': {
							path: 'dockbar_add_application.js',
							requires: [
								'aui-io-request',
								'event-key',
								'event-mouseenter',
								'liferay-dockbar',
								'liferay-dockbar-add-base',
								'liferay-panel-search',
								'liferay-portlet-base',
								'liferay-toggler-interaction'
							]
						},
						'liferay-dockbar-add-base': {
							path: 'dockbar_add_base.js',
							requires: [
								'anim',
								'aui-base',
								'liferay-dockbar',
								'liferay-layout',
								'transition'
							]
						},
						'liferay-dockbar-add-content': {
							path: 'dockbar_add_content.js',
							requires: [
								'aui-io-request',
								'event-mouseenter',
								'liferay-dockbar',
								'liferay-dockbar-add-content-preview',
								'liferay-dockbar-add-content-search',
								'liferay-portlet-base'
							]
						},
						'liferay-dockbar-add-content-preview': {
							path: 'dockbar_add_content_preview.js',
							requires: [
								'aui-debounce',
								'aui-io-request',
								'event-mouseenter'
							]
						},
						'liferay-dockbar-add-content-search': {
							path: 'dockbar_add_content_search.js',
							requires: [
								'aui-base',
								'liferay-dockbar',
								'liferay-search-filter'
							]
						},
						'liferay-dockbar-device-preview': {
							path: 'dockbar_device_preview.js',
							requires: [
								'aui-dialog-iframe-deprecated',
								'aui-event-input',
								'aui-modal',
								'liferay-portlet-base',
								'liferay-util-window',
								'liferay-widget-size-animation-plugin'
							]
						},
						'liferay-dockbar-portlet-dd': {
							condition: {
								name: 'liferay-dockbar-portlet-dd',
								test: function(A) {
									return !A.UA.mobile;
								},
								trigger: ['liferay-dockbar-add-application', 'liferay-dockbar-add-content']
							},
							path: 'dockbar_portlet_dd.js',
							requires: [
								'aui-base',
								'dd',
								'liferay-dockbar',
								'liferay-layout',
								'liferay-layout-column',
								'liferay-layout-freeform',
								'liferay-portlet-base'
							]
						}
					},
					root: PATH_DOCKBAR_WEB + '/js/'
				}
			}
		}
	);
})();