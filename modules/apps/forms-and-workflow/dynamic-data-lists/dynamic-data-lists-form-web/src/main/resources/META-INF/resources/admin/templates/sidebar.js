Liferay.Loader.require(
	'frontend-js-metal-web/metal-soy-bundle/build/bundle',
	'dynamic-data-lists-form-web/admin/templates/sidebar.es',
	function(MetalSoyBundle, SideBar) {
		if (!window.DDLSidebar) {
			window.DDLSidebar = {};
		}

		SideBar.default.forEach(function(item) {
			window.DDLSidebar[item.key] = item.component;
		});

		AUI.add('liferay-ddl-form-builder-sidebar-template');
	}
);