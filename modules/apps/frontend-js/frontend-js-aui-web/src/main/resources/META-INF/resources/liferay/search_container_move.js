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
	'liferay-search-container-move',
	A => {
		var AUA = A.UA;

		var Lang = A.Lang;

		var STR_BLANK = '';

		var STR_CONTENT_BOX = 'contentBox';

		var STR_DATA = 'data';

		var STR_DRAG_NODE = 'dragNode';

		var STR_HOST = 'host';

		var STR_NODE = 'node';

		var TOUCH_ENABLED = AUA.mobile && AUA.touchEnabled;

		var SearchContainerMove = A.Component.create({
			ATTRS: {
				ddConfig: {
					valueFn: '_valueDDConfig'
				},

				dropTargets: {
					validator: Lang.isArray
				},

				rowSelector: {
					validator: Lang.isString,
					value: 'li,tr'
				},

				tooltipClass: {
					validator: Lang.isString,
					value: 'btn btn-group btn-secondary'
				}
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'searchcontainermove',

			NS: 'move',

			prototype: {
				_getMoveText(selectedItemsCount, targetAvailable) {
					var moveText = STR_BLANK;

					if (targetAvailable) {
						moveText = Liferay.Language.get(
							'x-item-is-ready-to-be-moved-to-x'
						);

						if (selectedItemsCount > 1) {
							moveText = Liferay.Language.get(
								'x-items-are-ready-to-be-moved-to-x'
							);
						}
					}
					else {
						moveText = Liferay.Language.get(
							'x-item-is-ready-to-be-moved'
						);

						if (selectedItemsCount > 1) {
							moveText = Liferay.Language.get(
								'x-items-are-ready-to-be-moved'
							);
						}
					}

					return moveText;
				},

				_initDragAndDrop() {
					var instance = this;

					var host = instance.get(STR_HOST);

					instance._ddHandler = new A.DD.Delegate({
						container: host.get(STR_CONTENT_BOX),
						dragConfig: instance.get('ddConfig'),
						nodes: instance.get('rowSelector'),
						on: {
							'drag:drophit': A.bind('_onDragDropHit', instance),
							'drag:enter': A.bind('_onDragEnter', instance),
							'drag:exit': A.bind('_onDragExit', instance),
							'drag:start': A.bind('_onDragStart', instance)
						}
					});

					instance._ddHandler.dd.plug([
						{
							cfg: {
								moveOnEnd: false
							},
							fn: A.Plugin.DDProxy
						},
						{
							cfg: {
								horizontal: false,
								scrollDelay: 100,
								vertical: true
							},
							fn: A.Plugin.DDWinScroll
						}
					]);
				},

				_initDropTargets() {
					var instance = this;

					var dropTargets = instance.get('dropTargets');

					if (dropTargets && themeDisplay.isSignedIn()) {
						var host = instance.get(STR_HOST);

						dropTargets.forEach(target => {
							var container =
								A.one(target.container) ||
								host.get(STR_CONTENT_BOX);

							var targetNodes = container.all(target.selector);

							targetNodes.each(item => {
								item.plug(A.Plugin.Drop, {
									groups: [host.get('id')]
								}).drop.on({
									'drop:enter'() {
										item.addClass(target.activeCssClass);
									},

									'drop:exit'() {
										item.removeClass(target.activeCssClass);
									},

									'drop:hit'(event) {
										item.removeClass(target.activeCssClass);

										var selectedItems = instance._ddHandler.dd.get(
											STR_DATA
										).selectedItems;

										var dropTarget = event.drop.get(
											STR_NODE
										);

										host.executeAction(target.action, {
											selectedItems,
											targetItem: dropTarget
										});
									}
								});
							});

							if (target.infoCssClass) {
								instance._ddHandler.on(
									['drag:start', 'drag:end'],
									event => {
										targetNodes.toggleClass(
											target.infoCssClass,
											event.type == 'drag:start'
										);
									}
								);
							}
						});
					}
				},

				_onDragDropHit(event) {
					var instance = this;

					var proxyNode = event.target.get(STR_DRAG_NODE);

					proxyNode.removeClass(instance.get('tooltipClass'));

					proxyNode.empty();
				},

				_onDragEnter(event) {
					var instance = this;

					var dragNode = event.drag.get(STR_NODE);

					var dropTarget = event.drop.get(STR_NODE);

					if (!dragNode.compareTo(dropTarget)) {
						var proxyNode = event.target.get(STR_DRAG_NODE);

						var dd = instance._ddHandler.dd;

						var selectedItemsCount = dd.get(STR_DATA)
							.selectedItemsCount;

						var moveText = instance._getMoveText(
							selectedItemsCount,
							true
						);

						var itemTitle = dropTarget.attr('data-title').trim();

						proxyNode.html(
							Lang.sub(moveText, [
								selectedItemsCount,
								A.Lang.String.escapeHTML(itemTitle)
							])
						);
					}
				},

				_onDragExit(event) {
					var instance = this;

					var proxyNode = event.target.get(STR_DRAG_NODE);

					var selectedItemsCount = instance._ddHandler.dd.get(
						STR_DATA
					).selectedItemsCount;

					var moveText = instance._getMoveText(selectedItemsCount);

					proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));
				},

				_onDragStart(event) {
					var instance = this;

					var target = event.target;

					var node = target.get(STR_NODE);

					var selectedItems = new A.NodeList(node);

					var host = instance.get(STR_HOST);

					if (host.hasPlugin('select')) {
						var searchContainerSelect = host.select;

						var selected = searchContainerSelect.isSelected(node);

						if (!selected) {
							searchContainerSelect.toggleAllRows(false);
							searchContainerSelect.toggleRow(
								{
									toggleCheckbox: true
								},
								node
							);
						}
						else {
							selectedItems = searchContainerSelect.getCurrentPageSelectedElements();
						}
					}

					var selectedItemsCount = selectedItems.size();

					var dd = instance._ddHandler.dd;

					dd.set(STR_DATA, {
						selectedItems,
						selectedItemsCount
					});

					var proxyNode = target.get(STR_DRAG_NODE);

					proxyNode.setStyles({
						height: STR_BLANK,
						width: STR_BLANK
					});

					var moveText = instance._getMoveText(selectedItemsCount);

					proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

					proxyNode.addClass(instance.get('tooltipClass'));
				},

				_valueDDConfig() {
					var instance = this;

					var host = instance.get(STR_HOST);

					return {
						clickPixelThresh: TOUCH_ENABLED ? 100000 : 50,
						clickTimeThresh: TOUCH_ENABLED ? 150000 : 1000,
						groups: [host.get('id')],
						offsetNode: false
					};
				},

				destructor() {
					var instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					var instance = this;

					instance._initDragAndDrop();

					instance._initDropTargets();
				}
			}
		});

		A.Plugin.SearchContainerMove = SearchContainerMove;
	},
	'',
	{
		requires: [
			'aui-component',
			'dd-constrain',
			'dd-delegate',
			'dd-drag',
			'dd-drop',
			'dd-proxy',
			'plugin'
		]
	}
);
