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
	'liferay-layouts-tree-check-content-display-page',
	(A) => {
		const CSS_LAYOUT_INVALID = 'layout-page-invalid';

		const CSS_TREE_HITAREA = A.getClassName('tree', 'hitarea');

		const STR_HOST = 'host';

		const LayoutsTreeCheckContentDisplayPage = A.Component.create({
			EXTENDS: A.Plugin.Base,

			NAME: 'layoutstreecheckcontentdisplaypage',

			NS: 'checkContentDisplayPage',

			prototype: {
				_beforeClickNodeEl(event) {
					let result;

					if (!event.target.test('.' + CSS_TREE_HITAREA)) {
						const link = event.currentTarget.one('a');

						if (!link || link.hasClass(CSS_LAYOUT_INVALID)) {
							result = new A.Do.Halt();
						}
					}

					return result;
				},

				_beforeFormatNodeLabel(node, cssClass, label) {
					let result;

					if (!node.contentDisplayPage) {
						cssClass = cssClass + ' ' + CSS_LAYOUT_INVALID;

						result = new A.Do.AlterArgs(
							'Added layout-page-invalid CSS class',
							[
								node,
								cssClass,
								label,
								Liferay.Language.get(
									'this-page-is-not-a-content-display-page-template'
								),
							]
						);
					}

					return result;
				},

				_formatRootNode() {
					const instance = this;

					return new A.Do.AlterReturn('Modified label attribute', {
						...A.Do.currentRetVal,
						label: instance.get(STR_HOST).get('root').label,
					});
				},

				_onTreeAppend(event) {
					const instance = this;

					const host = instance.get(STR_HOST);

					host.fire('checkContentDisplayTreeAppend', {
						node: event.tree.node,
					});
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					const host = instance.get(STR_HOST);

					instance._eventHandles = [
						instance.afterHostEvent(
							'append',
							instance._onTreeAppend,
							instance
						),
						instance.doAfter(
							'_formatRootNode',
							instance._formatRootNode,
							instance
						),
						instance.doBefore(
							'_formatNodeLabel',
							instance._beforeFormatNodeLabel,
							instance
						),
						instance.doBefore(
							'_onClickNodeEl',
							instance._beforeClickNodeEl,
							instance
						),
					];

					host.get('boundingBox').addClass('lfr-tree-display-page');
				},
			},
		});

		A.Plugin.LayoutsTreeCheckContentDisplayPage = LayoutsTreeCheckContentDisplayPage;
	},
	'',
	{
		requires: ['aui-component', 'plugin'],
	}
);
