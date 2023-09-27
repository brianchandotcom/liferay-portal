/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-container-move',
	(A) => {
		const AUA = A.UA;

		const Lang = A.Lang;

		const STR_BLANK = '';

		const STR_CONTENT_BOX = 'contentBox';

		const STR_DATA = 'data';

		const STR_DRAG_NODE = 'dragNode';

		const STR_HOST = 'host';

		const STR_NODE = 'node';

		const TOUCH_ENABLED = AUA.mobile && AUA.touchEnabled;

		const SearchContainerMove = A.Component.create({
			ATTRS: {
				ddConfig: {
					valueFn: '_valueDDConfig',
				},

				dropTargets: {
					validator: Lang.isArray,
				},

				rowSelector: {
					validator: Lang.isString,
					value: 'dd,li,tr',
				},

				tooltipClass: {
					validator: Lang.isString,
					value: 'btn btn-group btn-secondary',
				},
			},

			EXTENDS: A.Plugin.Base,

			NAME: 'searchcontainermove',

			NS: 'move',

			prototype: {
				_getMoveText(selectedItemsCount, targetAvailable) {
					let moveText = STR_BLANK;

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
					const instance = this;

					const host = instance.get(STR_HOST);

					instance._ddHandler = new A.DD.Delegate({
						container: host.get(STR_CONTENT_BOX),
						dragConfig: instance.get('ddConfig'),
						nodes: instance.get('rowSelector'),
						on: {
							'drag:drophit': A.bind('_onDragDropHit', instance),
							'drag:enter': A.bind('_onDragEnter', instance),
							'drag:exit': A.bind('_onDragExit', instance),
							'drag:start': A.bind('_onDragStart', instance),
						},
					});

					instance._ddHandler.dd.plug([
						{
							cfg: {
								moveOnEnd: false,
							},
							fn: A.Plugin.DDProxy,
						},
						{
							cfg: {
								horizontal: false,
								scrollDelay: 100,
								vertical: true,
							},
							fn: A.Plugin.DDWinScroll,
						},
					]);
				},

				_initDropTargets() {
					const instance = this;

					const dropTargets = instance.get('dropTargets');

					if (dropTargets && themeDisplay.isSignedIn()) {
						const host = instance.get(STR_HOST);

						dropTargets.forEach((target) => {
							const container =
								A.one(target.container) ||
								host.get(STR_CONTENT_BOX);

							const targetNodes = container.all(target.selector);

							targetNodes.each((item) => {
								item.plug(A.Plugin.Drop, {
									groups: [host.get('id')],
								}).drop.on({
									'drop:enter'() {
										item.addClass(target.activeCssClass);
									},

									'drop:exit'() {
										item.removeClass(target.activeCssClass);
									},

									'drop:hit'(event) {
										item.removeClass(target.activeCssClass);

										const selectedItems = instance._ddHandler.dd.get(
											STR_DATA
										).selectedItems;

										const dropTarget = event.drop.get(
											STR_NODE
										);

										host.executeAction(target.action, {
											selectedItems,
											targetItem: dropTarget,
										});
									},
								});
							});

							if (target.infoCssClass) {
								instance._ddHandler.on(
									['drag:start', 'drag:end'],
									(event) => {
										targetNodes.toggleClass(
											target.infoCssClass,
											event.type === 'drag:start'
										);
									}
								);
							}
						});
					}
				},

				_onDragDropHit(event) {
					const instance = this;

					const proxyNode = event.target.get(STR_DRAG_NODE);

					proxyNode.removeClass(instance.get('tooltipClass'));

					proxyNode.empty();
				},

				_onDragEnter(event) {
					const instance = this;

					const dragNode = event.drag.get(STR_NODE);

					const dropTarget = event.drop.get(STR_NODE);

					if (!dragNode.compareTo(dropTarget)) {
						const proxyNode = event.target.get(STR_DRAG_NODE);

						const dd = instance._ddHandler.dd;

						const selectedItemsCount = dd.get(STR_DATA)
							.selectedItemsCount;

						const moveText = instance._getMoveText(
							selectedItemsCount,
							true
						);

						const itemTitle = dropTarget.attr('data-title').trim();

						proxyNode.html(
							Lang.sub(moveText, [
								selectedItemsCount,
								A.Lang.String.escapeHTML(itemTitle),
							])
						);
					}
				},

				_onDragExit(event) {
					const instance = this;

					const proxyNode = event.target.get(STR_DRAG_NODE);

					const selectedItemsCount = instance._ddHandler.dd.get(
						STR_DATA
					).selectedItemsCount;

					const moveText = instance._getMoveText(selectedItemsCount);

					proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));
				},

				_onDragStart(event) {
					const instance = this;

					const target = event.target;

					const node = target.get(STR_NODE);

					let selectedItems = new A.NodeList(node);

					const host = instance.get(STR_HOST);

					if (host.hasPlugin('select')) {
						const searchContainerSelect = host.select;

						const selected = searchContainerSelect.isSelected(node);

						if (!selected) {
							searchContainerSelect.toggleAllRows(false);
							searchContainerSelect.toggleRow(
								{
									toggleCheckbox: true,
								},
								node
							);
						}
						else {
							selectedItems = searchContainerSelect.getCurrentPageSelectedElements();
						}
					}

					const selectedItemsCount = selectedItems.size();

					const dd = instance._ddHandler.dd;

					dd.set(STR_DATA, {
						selectedItems,
						selectedItemsCount,
					});

					const proxyNode = target.get(STR_DRAG_NODE);

					proxyNode.setStyles({
						height: STR_BLANK,
						width: STR_BLANK,
					});

					const moveText = instance._getMoveText(selectedItemsCount);

					proxyNode.html(Lang.sub(moveText, [selectedItemsCount]));

					proxyNode.addClass(instance.get('tooltipClass'));
				},

				_valueDDConfig() {
					const instance = this;

					const host = instance.get(STR_HOST);

					return {
						clickPixelThresh: TOUCH_ENABLED ? 100000 : 50,
						clickTimeThresh: TOUCH_ENABLED ? 150000 : 1000,
						groups: [host.get('id')],
						offsetNode: false,
					};
				},

				destructor() {
					const instance = this;

					new A.EventHandle(instance._eventHandles).detach();
				},

				initializer() {
					const instance = this;

					instance._initDragAndDrop();

					instance._initDropTargets();
				},
			},
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
			'plugin',
		],
	}
);
