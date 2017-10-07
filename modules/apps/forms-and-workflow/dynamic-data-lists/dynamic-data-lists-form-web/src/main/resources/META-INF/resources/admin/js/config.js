;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				ddl: {
					base: MODULE_PATH + '/admin/js/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddl-portlet': {
							path: 'form_portlet.js',
							requires: [
								'liferay-ddm-form-builder',
								'liferay-ddm-form-builder-definition-serializer',
								'liferay-ddm-form-builder-layout-serializer',
								'liferay-ddm-form-builder-rule-builder',
								'liferay-portlet-base',
								'liferay-util-window'
							]
						},
						'liferay-ddl-form-builder-copy-publish-form-url-popover': {
							path: 'form_builder_copy_publish_form_url_popover.js',
							requires: [
								'aui-popover'
							]
						}
					},
					root: MODULE_PATH + '/admin/js/'
				}
			}
		}
	);
})();