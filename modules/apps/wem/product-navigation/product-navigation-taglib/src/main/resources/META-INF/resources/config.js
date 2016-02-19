;(function() {
	var PATH_CONTROL_MENU_WEB = Liferay.ThemeDisplay.getPathContext() + '/o/product-navigation-taglib/';

	AUI().applyConfig(
		{
			groups: {
				controlmenu: {
					base: PATH_CONTROL_MENU_WEB,
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-product-navigation-control-menu': {
							path: 'control_menu/js/product_navigation_control_menu.js',
							requires: [
								'aui-node',
								'event-touch'
							]
						},
						'liferay-product-navigation-control-menu-add-application': {
							path: 'control_menu/js/product_navigation_control_menu_add_application.js',
							requires: [
								'aui-io-request',
								'event-key',
								'event-mouseenter',
								'liferay-panel-search',
								'liferay-portlet-base',
								'liferay-product-navigation-control-menu',
								'liferay-product-navigation-control-menu-add-base',
								'liferay-toggler-interaction'
							]
						},
						'liferay-product-navigation-control-menu-add-base': {
							path: 'control_menu/js/product_navigation_control_menu_add_base.js',
							requires: [
								'anim',
								'aui-base',
								'liferay-layout',
								'liferay-layout-column',
								'liferay-product-navigation-control-menu',
								'transition'
							]
						},
						'liferay-product-navigation-control-menu-add-content': {
							path: 'control_menu/js/product_navigation_control_menu_add_content.js',
							requires: [
								'aui-io-request',
								'aui-parse-content',
								'event-mouseenter',
								'liferay-portlet-base',
								'liferay-product-navigation-control-menu',
								'liferay-product-navigation-control-menu-add-content-search'
							]
						},
						'liferay-product-navigation-control-menu-add-content-drag-drop': {
							path: 'control_menu/js/product_navigation_control_menu_add_content_drag_drop.js',
							requires: [
								'aui-base',
								'dd',
								'liferay-layout',
								'liferay-layout-column',
								'liferay-portlet-base',
								'liferay-product-navigation-control-menu'
							]
						},
						'liferay-product-navigation-control-menu-add-content-search': {
							path: 'control_menu/js/product_navigation_control_menu_add_content_search.js',
							requires: [
								'aui-base',
								'liferay-product-navigation-control-menu',
								'liferay-search-filter'
							]
						},
						'liferay-product-navigation-control-menu-portlet-dd': {
							condition: {
								name: 'liferay-product-navigation-control-menu-portlet-dd',
								test: function(A) {
									return !A.UA.mobile;
								},
								trigger: ['liferay-product-navigation-control-menu-add-application', 'liferay-product-navigation-control-menu-add-content']
							},
							path: 'control_menu/js/product_navigation_control_menu_portlet_dd.js',
							requires: [
								'aui-base',
								'dd',
								'liferay-layout',
								'liferay-layout-column',
								'liferay-portlet-base',
								'liferay-product-navigation-control-menu'
							]
						}
					},
					root: PATH_CONTROL_MENU_WEB
				}
			}
		}
	);
})();