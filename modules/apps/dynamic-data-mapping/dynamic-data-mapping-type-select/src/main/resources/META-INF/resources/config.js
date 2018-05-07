;(function() {
	var LiferayAUI = Liferay.AUI;

	AUI().applyConfig(
		{
			groups: {
				'field-select': {
					base: MODULE_PATH + '/',
					combine: Liferay.AUI.getCombine(),
					filter: LiferayAUI.getFilterConfig(),
					modules: {
						'liferay-ddm-form-field-select': {
							condition: {
								trigger: 'liferay-ddm-form-renderer'
							},
							path: 'select_field.js',
							requires: [
								'aui-tooltip',
								'liferay-ddm-form-field-select-search-support',
								'liferay-ddm-form-renderer-field',
								'liferay-ddm-soy-template-util'
							]
						},
						'liferay-ddm-form-field-select-search-support': {
							path: 'select_search_support.js',
							requires: [
								'highlight',
								'liferay-ddm-soy-template-util'
							]
						}
					},
					root: MODULE_PATH + '/'
				}
			}
		}
	);
})();