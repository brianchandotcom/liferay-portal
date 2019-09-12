/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

(function() {
	AUI().applyConfig({
		groups: {
			'layout-taglib': {
				base: MODULE_PATH + '/',
				combine: Liferay.AUI.getCombine(),
				filter: Liferay.AUI.getFilterConfig(),
				modules: {
					'liferay-layouts-tree': {
						path: 'layouts_tree/js/layouts_tree.js',
						requires: ['aui-tree-view']
					},
					'liferay-layouts-tree-check-content-display-page': {
						path:
							'layouts_tree/js/layouts_tree_check_content_display_page.js',
						requires: ['aui-component', 'plugin']
					},
					'liferay-layouts-tree-node-radio': {
						path: 'layouts_tree/js/layouts_tree_node_radio.js',
						requires: ['aui-tree-node']
					},
					'liferay-layouts-tree-node-task': {
						path: 'layouts_tree/js/layouts_tree_node_task.js',
						requires: ['aui-tree-node']
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
						requires: ['liferay-layouts-tree-node-task']
					},
					'liferay-layouts-tree-state': {
						path: 'layouts_tree/js/layouts_tree_state.js',
						requires: ['aui-base']
					}
				},
				root: MODULE_PATH + '/'
			}
		}
	});
})();
