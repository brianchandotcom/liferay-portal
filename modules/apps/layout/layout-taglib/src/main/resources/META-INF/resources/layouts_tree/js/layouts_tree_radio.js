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
	'liferay-layouts-tree-radio',
	(A) => {
		const Lang = A.Lang;

		const LABEL_TPL =
			'<span class="{cssClass}" title="{title}">{label}</span>';

		const STR_HOST = 'host';

		const LayoutsTreeRadio = A.Component.create({
			ATTRS: {
				showRootNode: {
					value: false,
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'layoutstreeradio',

			NS: 'radio',

			prototype: {
				_formatNode() {
					const currentRetVal = A.Do.currentRetVal;

					return new A.Do.AlterReturn('Modified type attribute', {
						...currentRetVal,
						type: 'liferay-radio',
					});
				},

				_formatNodeLabel(node, cssClass, label, title) {
					return new A.Do.AlterReturn(
						'Modified node label',
						Lang.sub(LABEL_TPL, {
							cssClass,
							label,
							title,
						})
					);
				},

				_formatRootNode(rootConfig) {
					const instance = this;

					const host = instance.get(STR_HOST);

					let labelEl = rootConfig.label;

					if (!instance.get('showRootNode')) {
						labelEl = A.Node.create('<span class="hide"></span>');

						host.get('boundingBox').addClass(
							'lfr-tree-root-node-hidden'
						);
					}

					return new A.Do.AlterReturn(
						'Modified cssClass, label and type attributes',
						{...A.Do.currentRetVal, labelEl, type: 'liferay-radio'}
					);
				},

				_onNodeCheckedChange(event) {
					const instance = this;

					if (event.newVal) {
						instance.get(STR_HOST).fire('radioNodeCheckedChange', {
							node: event.target,
						});
					}
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
							'*:checkedChange',
							instance._onNodeCheckedChange,
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
						),
					];

					host.get('boundingBox').addClass('lfr-tree-radio');
				},
			},
		});

		A.Plugin.LayoutsTreeRadio = LayoutsTreeRadio;
	},
	'',
	{
		requires: ['aui-tree-node', 'liferay-layouts-tree-node-radio'],
	}
);
