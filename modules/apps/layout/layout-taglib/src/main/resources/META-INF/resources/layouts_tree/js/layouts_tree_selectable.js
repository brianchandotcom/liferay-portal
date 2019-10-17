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
	'liferay-layouts-tree-selectable',
	A => {
		var Lang = A.Lang;

		var LABEL_TPL =
			'<span class="{cssClass}" title="{title}">{label}</span>';

		var STR_DEFAULT_STATE = 'defaultState';

		var STR_HOST = 'host';

		var LayoutsTreeSelectable = A.Component.create({
			ATTRS: {
				defaultState: {
					validator: Lang.isBoolean,
					value: false
				}
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'layoutstreeselectable',

			NS: 'selectable',

			prototype: {
				_formatNode() {
					var instance = this;

					var currentRetVal = A.Do.currentRetVal;

					return new A.Do.AlterReturn(
						'Modified checked and type attributes',
						A.merge(currentRetVal, {
							checked: instance.get(STR_DEFAULT_STATE),
							type: 'liferay-task'
						})
					);
				},

				_formatNodeLabel(node, cssClass, label, title) {
					return new A.Do.AlterReturn(
						'Modified node label',
						Lang.sub(LABEL_TPL, {
							cssClass,
							label,
							title
						})
					);
				},

				_formatRootNode(rootConfig) {
					var instance = this;

					return new A.Do.AlterReturn(
						'Modified checked, label and type attributes',
						A.merge(A.Do.currentRetVal, {
							checked: instance.get(STR_DEFAULT_STATE),
							label: rootConfig.label,
							type: 'liferay-task'
						})
					);
				},

				_onNodeCheckedChange(event) {
					var instance = this;

					var host = instance.get(STR_HOST);

					if (event.target === event.originalTarget) {
						host.fire('selectableNodeCheckedChange', {
							checked: event.newVal,
							node: event.target
						});
					}
				},

				_onNodeChildrenChange(event) {
					var instance = this;

					var host = instance.get(STR_HOST);

					if (event.src !== A.Widget.UI_SRC) {
						host.fire('selectableNodeChildrenChange', {
							node: event.target
						});
					}
				},

				_onTreeAppend(event) {
					var instance = this;

					var host = instance.get(STR_HOST);

					host.fire('selectableTreeAppend', {
						node: event.tree.node
					});
				},

				_onTreeRender() {
					var instance = this;

					var host = instance.get(STR_HOST);

					host.fire('selectableTreeRender');
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._eventHandles = [
						instance.afterHostEvent(
							'*:checkedChange',
							instance._onNodeCheckedChange,
							instance
						),
						instance.afterHostEvent(
							'*:childrenChange',
							instance._onNodeChildrenChange,
							instance
						),
						instance.afterHostEvent(
							'append',
							instance._onTreeAppend,
							instance
						),
						instance.afterHostEvent(
							'render',
							instance._onTreeRender,
							instance
						),
						instance.doAfter(
							'_formatNode',
							instance._formatNode,
							instance
						),
						instance.doAfter(
							'_formatNodeLabel',
							instance._formatNodeLabel,
							instance
						),
						instance.doAfter(
							'_formatRootNode',
							instance._formatRootNode,
							instance
						)
					];
				}
			}
		});

		A.Plugin.LayoutsTreeSelectable = LayoutsTreeSelectable;
	},
	'',
	{
		requires: ['liferay-layouts-tree-node-task']
	}
);
