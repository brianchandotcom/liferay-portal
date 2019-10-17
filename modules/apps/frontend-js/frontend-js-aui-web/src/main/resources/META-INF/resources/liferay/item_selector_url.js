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
	'liferay-item-selector-url',
	A => {
		var Lang = A.Lang;

		var ITEM_LINK_TPL =
			'<a data-returnType="URL" data-value="{value}" href="{preview}"></a>';

		var STR_LINKS = 'links';

		var STR_SELECTED_ITEM = 'selectedItem';

		var STR_VISIBLE_CHANGE = 'visibleChange';

		var ItemSelectorUrl = A.Component.create({
			ATTRS: {
				closeCaption: {
					validator: Lang.isString,
					value: ''
				}
			},

			AUGMENTS: [Liferay.PortletBase],

			EXTENDS: A.Base,

			NAME: 'itemselectorurl',

			prototype: {
				_afterVisibleChange(event) {
					var instance = this;

					if (!event.newVal) {
						instance.fire(STR_SELECTED_ITEM);
					}
				},

				_bindUI() {
					var instance = this;

					var itemViewer = instance._itemViewer;

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
						)
					];
				},

				_onInput(event) {
					var instance = this;

					Liferay.Util.toggleDisabled(
						instance._buttonNode,
						!event.currentTarget.val()
					);
				},

				_onItemSelected() {
					var instance = this;

					var itemViewer = instance._itemViewer;

					var link = itemViewer
						.get(STR_LINKS)
						.item(itemViewer.get('currentIndex'));

					instance.fire(STR_SELECTED_ITEM, {
						data: {
							returnType: link.getData('returnType'),
							value: link.getData('value')
						}
					});
				},

				_previewItem() {
					var instance = this;

					var url = instance._inputNode.val();

					if (url) {
						var linkNode = A.Node.create(
							Lang.sub(ITEM_LINK_TPL, {
								preview: url,
								value: url
							})
						);

						var itemViewer = instance._itemViewer;

						itemViewer.set(STR_LINKS, new A.NodeList(linkNode));

						itemViewer.show();
					}
				},

				_renderUI() {
					var instance = this;

					var rootNode = instance.rootNode;

					instance._itemViewer.render(rootNode);
				},

				destructor() {
					var instance = this;

					instance._itemViewer.destroy();

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._itemViewer = new A.LiferayItemViewer({
						btnCloseCaption: instance.get('closeCaption'),
						caption: '',
						links: '',
						renderControls: false,
						renderSidebar: false
					});

					instance._inputNode = instance.one('#urlInput');
					instance._buttonNode = instance.one('#previewBtn');

					instance._bindUI();
					instance._renderUI();
				}
			}
		});

		Liferay.ItemSelectorUrl = ItemSelectorUrl;
	},
	'',
	{
		requires: [
			'aui-event-input',
			'liferay-item-viewer',
			'liferay-portlet-base'
		]
	}
);
