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

/**
 * @deprecated As of Athanasius (7.3.x), replaced by ItemSelectorUrl.es.js
 * @module liferay-item-selector-url
 */

AUI.add(
	'liferay-item-selector-url',
	(A) => {
		const Lang = A.Lang;

		const ITEM_LINK_TPL =
			'<a data-returnType="URL" data-value="{value}" href="{preview}"></a>';

		const STR_LINKS = 'links';

		const STR_SELECTED_ITEM = 'selectedItem';

		const STR_VISIBLE_CHANGE = 'visibleChange';

		const ItemSelectorUrl = A.Component.create({
			ATTRS: {
				closeCaption: {
					validator: Lang.isString,
					value: '',
				},
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'itemselectorurl',

			prototype: {
				_afterVisibleChange(event) {
					const instance = this;

					if (!event.newVal) {
						instance.fire(STR_SELECTED_ITEM);
					}
				},

				_bindUI() {
					const instance = this;

					const itemViewer = instance._itemViewer;

					instance._eventHandles = [
						itemViewer.after(
							STR_VISIBLE_CHANGE,
							instance._afterVisibleChange,
							instance
						),
						itemViewer.on(
							'animate',
							instance._onItemSelected,
							instance
						),
						instance._inputNode.on(
							'input',
							instance._onInput,
							instance
						),
						instance._buttonNode.on(
							'click',
							instance._previewItem,
							instance
						),
					];
				},

				_onInput(event) {
					const instance = this;

					Liferay.Util.toggleDisabled(
						instance._buttonNode,
						!event.currentTarget.val()
					);
				},

				_onItemSelected() {
					const instance = this;

					const itemViewer = instance._itemViewer;

					const link = itemViewer
						.get(STR_LINKS)
						.item(itemViewer.get('currentIndex'));

					instance.fire(STR_SELECTED_ITEM, {
						data: {
							returnType: link.getData('returnType'),
							value: link.getData('value'),
						},
					});
				},

				_previewItem() {
					const instance = this;

					const url = instance._inputNode.val();

					if (url) {
						const linkNode = A.Node.create(
							Lang.sub(ITEM_LINK_TPL, {
								preview: url,
								value: url,
							})
						);

						const itemViewer = instance._itemViewer;

						itemViewer.set(STR_LINKS, new A.NodeList(linkNode));

						itemViewer.show();
					}
				},

				_renderUI() {
					const instance = this;

					const rootNode = instance.rootNode;

					instance._itemViewer.render(rootNode);
				},

				destructor() {
					const instance = this;

					instance._itemViewer.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					instance._itemViewer = new A.LiferayItemViewer({
						btnCloseCaption: instance.get('closeCaption'),
						caption: '',
						links: '',
						renderControls: false,
						renderSidebar: false,
					});

					instance._inputNode = instance.one('#urlInput');
					instance._buttonNode = instance.one('#previewBtn');

					instance._bindUI();
					instance._renderUI();
				},
			},
		});

		Liferay.ItemSelectorUrl = ItemSelectorUrl;
	},
	'',
	{
		requires: [
			'aui-event-input',
			'liferay-item-viewer',
			'liferay-portlet-base',
		],
	}
);
