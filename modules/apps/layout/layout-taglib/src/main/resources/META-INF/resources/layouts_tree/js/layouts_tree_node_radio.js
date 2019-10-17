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
	'liferay-layouts-tree-node-radio',
	A => {
		var TPL_RADIO = '<label><input type="radio"></label>';

		var LayoutsTreeNodeRadio = A.Component.create({
			ATTRS: {
				checkEl: {
					setter: A.one,
					valueFn: '_valueCheckEl'
				}
			},

			EXTENDS: A.TreeNodeRadio,

			NAME: 'layoutstreenoderadio',

			prototype: {
				_uiSetChecked(val) {
					var instance = this;

					instance
						.get('checkEl')
						.one('input')
						.attr('checked', val ? 'checked' : '');
				},

				_valueCheckEl() {
					var instance = this;

					var checkName = instance.get('checkName');

					return A.Node.create(TPL_RADIO).attr('name', checkName);
				},

				renderUI() {
					var instance = this;

					LayoutsTreeNodeRadio.superclass.renderUI.apply(
						instance,
						arguments
					);

					var checkEl = instance.get('checkEl');

					checkEl.append(instance.get('label'));

					checkEl.show();

					var labelEl = instance.get('labelEl');

					labelEl.empty();

					labelEl.addClass('radio');

					var checkContainerEl = instance.get('checkContainerEl');

					checkContainerEl.removeClass(
						'tree-node-checkbox-container'
					);

					labelEl.append(checkContainerEl);

					instance.get('hitAreaEl').remove();
					instance.get('iconEl').remove();
				}
			}
		});

		A.LayoutsTreeNodeRadio = LayoutsTreeNodeRadio;

		A.TreeNode.nodeTypes['liferay-radio'] = LayoutsTreeNodeRadio;
	},
	'',
	{
		requires: ['aui-tree-node']
	}
);
