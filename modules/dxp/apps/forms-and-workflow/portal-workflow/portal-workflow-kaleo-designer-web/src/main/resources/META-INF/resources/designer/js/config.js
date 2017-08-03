;(function() {
	AUI().applyConfig(
		{
			groups: {
				'kaleo-designer': {
					base: MODULE_PATH + '/designer/js/',
					combine: Liferay.AUI.getCombine(),
					filter: Liferay.AUI.getFilterConfig(),
					modules: {
						'liferay-kaleo-designer-autocomplete-util': {
							path: 'autocomplete_util.js',
							requires: [
								'autocomplete',
								'autocomplete-highlighters'
							]
						},
						'liferay-kaleo-designer-editors': {
							path: 'editors.js',
							requires: [
								'aui-ace-editor',
								'aui-ace-editor-mode-xml',
								'aui-base',
								'aui-datatype',
								'aui-node',
								'liferay-kaleo-designer-autocomplete-util',
								'liferay-kaleo-designer-utils'
							]
						},
						'liferay-kaleo-designer-nodes': {
							path: 'nodes.js',
							requires: [
								'aui-datatable',
								'aui-datatype', 'aui-diagram-builder',
								'liferay-kaleo-designer-editors',
								'liferay-kaleo-designer-utils'
							]
						},
						'liferay-kaleo-designer-utils': {
							path: 'utils.js',
							requires: []
						},
						'liferay-kaleo-designer-xml-util': {
							path: 'xml_util.js',
							requires: [
								'aui-base'
							]
						},
						'liferay-portlet-kaleo-designer': {
							path: 'main.js',
							requires: [
								'aui-ace-editor',
								'aui-ace-editor-mode-xml',
								'aui-tpl-snippets-deprecated',
								'dataschema-xml',
								'datasource',
								'datatype-xml',
								'event-valuechange',
								'io-form',
								'liferay-kaleo-designer-autocomplete-util',
								'liferay-kaleo-designer-editors',
								'liferay-kaleo-designer-nodes',
								'liferay-kaleo-designer-utils',
								'liferay-kaleo-designer-xml-util',
								'liferay-util-window',
								'liferay-xml-formatter'
							]
						}

					},
					root: MODULE_PATH + '/designer/js/'
				}
			}
		}
	);
})();