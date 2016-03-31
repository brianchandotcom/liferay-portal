;(function() {
	AUI().applyConfig(
		{
			groups: {
				'invite-members': {
					base: MODULE_PATH + '/invite_members/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-portlet-invite-members': {
							path: 'main.js',
							requires: [
								'aui-base',
								'autocomplete-base',
								'datasource-io',
								'datatype-number',
								'liferay-portlet-base',
								'liferay-util-window',
								'node-core'
							]
						}
					},
					root: MODULE_PATH + '/invite_members/js/'
				}
			}
		}
	);
})();