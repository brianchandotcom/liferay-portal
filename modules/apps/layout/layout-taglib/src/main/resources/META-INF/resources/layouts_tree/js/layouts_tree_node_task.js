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

AUI.add(
	'liferay-layouts-tree-node-task',
	A => {
		var LayoutsTreeNodeTask = A.Component.create({
			EXTENDS: A.TreeNodeTask,

			NAME: 'layoutstreenodetask',

			prototype: {
				_uiSetChecked(val) {
					var instance = this;

					instance._syncIconCheckUI();

					instance
						.get('contentBox')
						.toggleClass(
							A.getClassName('tree', 'node', 'checked'),
							val
						);
				},

				renderUI() {
					var instance = this;

					LayoutsTreeNodeTask.superclass.renderUI.apply(
						instance,
						arguments
					);

					var checkEl = instance.get('checkEl');

					if (checkEl) {
						checkEl.remove();
					}
				},

				toggleCheck() {
					var instance = this;

					var checked = instance.get('checked');

					if (checked) {
						instance.uncheck();
					}
					else {
						instance.check();
					}
				}
			}
		});

		A.LayoutsTreeNodeTask = LayoutsTreeNodeTask;

		A.TreeNode.nodeTypes['liferay-task'] = LayoutsTreeNodeTask;
	},
	'',
	{
		requires: ['aui-tree-node']
	}
);
