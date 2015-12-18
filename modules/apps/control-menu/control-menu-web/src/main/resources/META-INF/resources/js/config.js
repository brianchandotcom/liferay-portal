;(function() {
	var PATH_CONTROL_MENU_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/control-menu-web';

	AUI().applyConfig(
		{
			groups: {
				controlmenu: {
					base: PATH_CONTROL_MENU_WEB + '/js/',
					modules: {
						'liferay-control-menu': {
							path: 'control_menu.js',
							requires: [
								'aui-node',
								'event-touch'
							]
						},
						'liferay-control-menu-add-application': {
							path: 'control_menu_add_application.js',
							requires: [
								'aui-io-request',
								'event-key',
								'event-mouseenter',
								'liferay-control-menu',
								'liferay-control-menu-add-base',
								'liferay-panel-search',
								'liferay-portlet-base',
								'liferay-toggler-interaction'
							]
						},
						'liferay-control-menu-add-base': {
							path: 'control_menu_add_base.js',
							requires: [
								'anim',
								'aui-base',
								'liferay-control-menu',
								'liferay-layout',
								'liferay-layout-column',
								'liferay-layout-freeform',
								'transition'
							]
						},
						'liferay-control-menu-add-content': {
							path: 'control_menu_add_content.js',
							requires: [
								'aui-io-request',
								'event-mouseenter',
								'liferay-control-menu',
								'liferay-control-menu-add-content-preview',
								'liferay-control-menu-add-content-search',
								'liferay-portlet-base'
							]
						},
						'liferay-control-menu-add-content-drag-drop': {
							path: 'control_menu_add_content_drag_drop.js',
							requires: [
								'aui-base',
								'dd',
								'liferay-control-menu',
								'liferay-layout',
								'liferay-layout-column',
								'liferay-layout-freeform',
								'liferay-portlet-base'
							]
						},
						'liferay-control-menu-add-content-preview': {
							path: 'control_menu_add_content_preview.js',
							requires: [
								'aui-debounce',
								'aui-io-request',
								'event-mouseenter'
							]
						},
						'liferay-control-menu-add-content-search': {
							path: 'control_menu_add_content_search.js',
							requires: [
								'aui-base',
								'liferay-control-menu',
								'liferay-search-filter'
							]
						},
						'liferay-control-menu-portlet-dd': {
							condition: {
								name: 'liferay-control-menu-portlet-dd',
								test: function(A) {
									return !A.UA.mobile;
								},
								trigger: ['liferay-control-menu-add-application', 'liferay-control-menu-add-content']
							},
							path: 'control_menu_portlet_dd.js',
							requires: [
								'aui-base',
								'dd',
								'liferay-control-menu',
								'liferay-layout',
								'liferay-layout-column',
								'liferay-layout-freeform',
								'liferay-portlet-base'
							]
						}
					},
					root: PATH_CONTROL_MENU_WEB + '/js/'
				}
			}
		}
	);
})();