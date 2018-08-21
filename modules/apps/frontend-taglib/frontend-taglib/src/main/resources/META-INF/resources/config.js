;(function() {
	AUI().applyConfig(
		{
			groups: {
				'frontend-taglib': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-diff-version-comparator': {
							path: 'diff_version_comparator/js/diff_version_comparator.js',
							requires: [
								'aui-io-request',
								'autocomplete-base',
								'autocomplete-filters',
								'liferay-portlet-base'
							]
						},
						'liferay-management-bar': {
							path: 'management_bar/js/management_bar.js',
							requires: [
								'aui-component',
								'liferay-portlet-base'
							]
						},
						'liferay-sidebar-panel': {
							path: 'sidebar_panel/js/sidebar_panel.js',
							requires: [
								'aui-base',
								'aui-debounce',
								'aui-io-request',
								'aui-parse-content',
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