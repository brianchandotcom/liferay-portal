;(function() {
	var PATH_LAYOUT_TAGLIB = Liferay.ThemeDisplay.getPathContext() + '/o/layout-taglib';

	AUI().applyConfig(
		{
			groups: {
				'layout-taglib': {
					base: PATH_LAYOUT_TAGLIB + '/',
					combine: Liferay.AUI.getCombine(),
					modules: {
						'liferay-layouts-tree': {
							path: 'layouts_tree/js/layouts_tree.js',
							requires: [
								'aui-tree-view'
							]
						},
						'liferay-layouts-tree-check-content-display-page': {
							path: 'layouts_tree/js/layouts_tree_check_content_display_page.js',
							requires: [
								'aui-component',
								'plugin'
							]
						},
						'liferay-layouts-tree-node-radio': {
							path: 'layouts_tree/js/layouts_tree_node_radio.js',
							requires: [
								'aui-tree-node'
							]
						},
						'liferay-layouts-tree-node-task': {
							path: 'layouts_tree/js/layouts_tree_node_task.js',
							requires: [
								'aui-tree-node'
							]
						},
						'liferay-layouts-tree-radio': {
							path: 'layouts_tree/js/layouts_tree_radio.js',
							requires: [
								'aui-tree-node',
								'liferay-layouts-tree-node-radio'
							]
						},
						'liferay-layouts-tree-selectable': {
							path: 'layouts_tree/js/layouts_tree_selectable.js',
							requires: [
								'liferay-layouts-tree-node-task'
							]
						},
						'liferay-layouts-tree-state': {
							path: 'layouts_tree/js/layouts_tree_state.js',
							requires: [
								'aui-base',
								'aui-io-request',
								'liferay-store'
							]
						}
					},
					root: PATH_LAYOUT_TAGLIB + '/'
				}
			}
		}
	);
})();