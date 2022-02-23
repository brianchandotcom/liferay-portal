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
	'liferay-layout',
	(A) => {
		var Util = Liferay.Util;

		var CSS_DRAGGABLE = 'portlet-draggable';

		var LAYOUT_CONFIG = Liferay.Data.layoutConfig;

		var Layout = {
			_afterPortletClose(event) {
				var column = event.column;

				if (column) {
					Layout.syncEmptyColumnClassUI(column);
				}
			},

			_getPortletTitle: A.cached((id) => {
				var portletBoundary = A.one('#' + id);

				var portletTitle = portletBoundary.one('.portlet-title');

				if (!portletTitle) {
					portletTitle = Layout.PROXY_NODE_ITEM.one('.portlet-title');

					var title = portletBoundary.one('.portlet-title-default');

					var titleText = '';

					if (title) {
						titleText = title.html();
					}

					portletTitle.html(titleText);
				}

				return portletTitle.outerHTML();
			}),

			_onPortletClose(event) {
				var portlet = event.portlet;

				var column = portlet.ancestor(Layout.options.dropContainer);

				Layout.updateCurrentPortletInfo(portlet);

				if (portlet.test('.portlet-nested-portlets')) {
					Layout.closeNestedPortlets(portlet);
				}

				event.column = column;
			},

			_onPortletDragEnd(event) {
				var dragNode = event.target.get('node');

				var columnNode = dragNode.get('parentNode');

				Layout.saveIndex(dragNode, columnNode);

				Layout.syncEmptyColumnClassUI(columnNode);
			},

			_onPortletDragStart(event) {
				var dragNode = event.target.get('node');

				Layout.updateCurrentPortletInfo(dragNode);
			},

			EMPTY_COLUMNS: {},

			INITIALIZED: false,

			OVER_NESTED_PORTLET: false,

			PORTLET_TOPPER: A.Node.create('<div class="portlet-topper"></div>'),

			PROXY_NODE: A.Node.create(
				'<div class="lfr-portlet-proxy sortable-layout-proxy"></div>'
			),

			PROXY_NODE_ITEM: A.Node.create(
				'<div class="lfr-portlet-proxy sortable-layout-proxy">' +
					'<div class="portlet-topper">' +
					'<span class="portlet-title"></span>' +
					'</div>' +
					'</div>'
			),

			bindDragDropListeners() {
				var layoutHandler = Layout.getLayoutHandler();

				layoutHandler.on(
					'drag:end',
					A.bind('_onPortletDragEnd', Layout)
				);
				layoutHandler.on(
					'drag:start',
					A.bind('_onPortletDragStart', Layout)
				);

				layoutHandler.delegate.dd.plug({
					cfg: {
						horizontal: false,
						scrollDelay: 30,
						vertical: true,
					},
					fn: A.Plugin.DDWinScroll,
				});
			},

			closeNestedPortlets(portlet) {
				var nestedPortlets = portlet.all('.portlet-boundary');

				nestedPortlets.each((portlet) => {
					Liferay.Portlet.close(portlet, true, {
						nestedPortlet: true,
					});
				});
			},

			findIndex(node) {
				var options = Layout.options;
				var parentNode = node.get('parentNode');

				return parentNode
					.all('> ' + options.portletBoundary)
					.indexOf(node);
			},

			findReferencePortlet(dropColumn) {
				var portletBoundary = Layout.options.portletBoundary;
				var portlets = dropColumn.all('>' + portletBoundary);

				var firstPortlet = portlets.item(0);
				var referencePortlet = null;

				if (firstPortlet) {
					var firstPortletStatic = firstPortlet.isStatic;
					var lastStatic = null;

					if (!firstPortletStatic || firstPortletStatic === 'end') {
						referencePortlet = firstPortlet;
					}
					else {
						portlets.each((item) => {
							var isStatic = item.isStatic;

							if (
								!isStatic ||
								(lastStatic &&
									isStatic &&
									isStatic !== lastStatic)
							) {
								referencePortlet = item;
							}

							lastStatic = isStatic;
						});
					}
				}

				return referencePortlet;
			},

			fire() {
				var layoutHandler = Layout.getLayoutHandler();

				var retVal;

				if (layoutHandler) {
					retVal = layoutHandler.fire.apply(layoutHandler, arguments);
				}

				return retVal;
			},

			getActiveDropContainer() {
				var options = Layout.options;

				return A.all(
					options.dropContainer +
						':not(.' +
						options.disabledDropContainerClass +
						')'
				).item(0);
			},

			getActiveDropNodes() {
				var options = Layout.options;

				var dropNodes = [];

				A.all(options.dropContainer).each((dropContainer) => {
					if (
						!dropContainer.hasClass(
							options.disabledDropContainerClass
						)
					) {
						dropNodes.push(dropContainer.get('parentNode'));
					}
				});

				return A.all(dropNodes);
			},

			getLayoutHandler() {
				if (!Layout.layoutHandler) {
					Liferay.fire('initLayout');
				}

				return Layout.layoutHandler;
			},

			getPortlets() {
				var options = Layout.options;

				return A.all(options.dragNodes);
			},

			hasMoved(dragNode) {
				var curPortletInfo = Layout.curPortletInfo;
				var moved = false;

				if (curPortletInfo) {
					var currentIndex = Layout.findIndex(dragNode);
					var currentParent = dragNode.get('parentNode');

					if (
						curPortletInfo.originalParent !== currentParent ||
						curPortletInfo.originalIndex !== currentIndex
					) {
						moved = true;
					}
				}

				return moved;
			},

			hasPortlets(columnNode) {
				var options = Layout.options;

				return !!columnNode.one(options.portletBoundary);
			},

			on() {
				var layoutHandler = Layout.getLayoutHandler();

				var retVal;

				if (layoutHandler) {
					retVal = layoutHandler.on.apply(layoutHandler, arguments);
				}

				return retVal;
			},

			options: LAYOUT_CONFIG,

			refresh(portlet) {
				var layoutHandler = Layout.getLayoutHandler();

				portlet = A.one(portlet);

				if (portlet && layoutHandler) {
					layoutHandler.delegate.syncTargets();

					Layout.updatePortletDropZones(portlet);
				}
			},

			saveIndex(portletNode, columnNode) {
				var columnNodeId = columnNode.get('id');

				var currentColumnId = columnNodeId.replace(
					/layout-column_/,
					''
				);
				var portletId = Util.getPortletId(portletNode.get('id'));
				var position = Layout.findIndex(portletNode);

				if (Layout.hasMoved(portletNode)) {
					Liferay.fire('portletMoved', {
						portlet: portletNode,
						portletId,
						position,
					});

					Layout.saveLayout({
						cmd: 'move',
						p_p_col_id: currentColumnId,
						p_p_col_pos: position,
						p_p_id: portletId,
					});
				}
			},

			syncDraggableClassUI() {
				var options = Layout.options;

				if (options.dragNodes) {
					var dragNodes = A.all(options.dragNodes);

					if (options.invalid) {
						dragNodes = dragNodes.filter(
							':not(' + options.invalid + ')'
						);
					}

					dragNodes.addClass(CSS_DRAGGABLE);
				}
			},

			syncEmptyColumnClassUI(columnNode) {
				var curPortletInfo = Layout.curPortletInfo;
				var options = Layout.options;

				if (curPortletInfo) {
					var columnHasPortlets = Layout.hasPortlets(columnNode);
					var emptyColumnClass = options.emptyColumnClass;
					var originalParent = curPortletInfo.originalParent;

					var originalColumnHasPortlets = Layout.hasPortlets(
						originalParent
					);

					var currentColumn = columnNode.ancestor(options.dropNodes);
					var originalColumn = originalParent.ancestor(
						options.dropNodes
					);

					if (currentColumn) {
						var dropZoneId = currentColumn.get('id');

						Layout.EMPTY_COLUMNS[dropZoneId] = !columnHasPortlets;
					}

					if (originalColumn) {
						var originalDropZoneId = originalColumn.get('id');

						Layout.EMPTY_COLUMNS[
							originalDropZoneId
						] = !originalColumnHasPortlets;
					}

					columnNode.toggleClass(
						emptyColumnClass,
						!columnHasPortlets
					);
					originalParent.toggleClass(
						emptyColumnClass,
						!originalColumnHasPortlets
					);
				}
			},

			updateCurrentPortletInfo(dragNode) {
				var options = Layout.options;

				Layout.curPortletInfo = {
					node: dragNode,
					originalIndex: Layout.findIndex(dragNode),
					originalParent: dragNode.get('parentNode'),
					siblingsPortlets: dragNode.siblings(
						options.portletBoundary
					),
				};
			},

			updateEmptyColumnsInfo() {
				var options = Layout.options;

				A.all(options.dropNodes).each((item) => {
					var columnId = item.get('id');

					Layout.EMPTY_COLUMNS[columnId] = !Layout.hasPortlets(item);
				});
			},

			updatePortletDropZones(portletBoundary) {
				var options = Layout.options;
				var portletDropNodes = portletBoundary.all(options.dropNodes);

				var layoutHandler = Layout.getLayoutHandler();

				portletDropNodes.each((item) => {
					layoutHandler.addDropNode(item);
				});
			},
		};

		Layout.init = function (options) {
			options = options || Layout.options;

			options.handles = A.Array(options.handles);

			Layout.PROXY_NODE.append(Layout.PORTLET_TOPPER);

			var layoutContainer = options.container;

			Layout._layoutContainer = A.one(layoutContainer);

			Layout.DEFAULT_LAYOUT_OPTIONS = {
				columnContainer: layoutContainer,
				delegateConfig: {
					container: layoutContainer,
					dragConfig: {
						clickPixelThresh: 0,
						clickTimeThresh: 250,
					},
					handles: options.handles,
					invalid: options.invalid,
				},
				dragNodes: options.dragNodes,
				dropContainer(dropNode) {
					return dropNode.one(options.dropContainer);
				},
				dropNodes: Layout.getActiveDropNodes(),
				lazyStart: true,
				proxy: {
					resizeFrame: false,
				},
			};

			var eventHandles = [];

			A.use('liferay-layout-column', () => {
				Layout.ColumnLayout.register();

				Layout.bindDragDropListeners();

				Layout.updateEmptyColumnsInfo();

				Liferay.after('closePortlet', Layout._afterPortletClose);
				Liferay.on('closePortlet', Layout._onPortletClose);

				Liferay.on('screenFlip', () => {
					if (eventHandles) {
						new A.EventHandle(eventHandles).detach();
					}

					Layout.getLayoutHandler().destroy();
				});

				Layout.INITIALIZED = true;
			});
		};

		Liferay.provide(
			Layout,
			'saveLayout',
			(options) => {
				var data = {
					doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
					p_auth: Liferay.authToken,
					p_l_id: themeDisplay.getPlid(),
				};

				A.mix(data, options);

				Liferay.Util.fetch(
					themeDisplay.getPathMain() + '/portal/update_layout',
					{
						body: Liferay.Util.objectToFormData(data),
						method: 'POST',
					}
				).then((response) => {
					if (response.ok) {
						Liferay.fire('updatedLayout');
					}
				});
			},
			['aui-base']
		);

		Liferay.provide(
			Layout,
			'updateOverNestedPortletInfo',
			() => {
				var activeDrop = A.DD.DDM.activeDrop;
				var nestedPortletId = Layout.options.nestedPortletId;

				if (activeDrop) {
					var activeDropNode = activeDrop.get('node');
					var activeDropNodeId = activeDropNode.get('id');

					Layout.OVER_NESTED_PORTLET =
						activeDropNodeId.indexOf(nestedPortletId) > -1;
				}
			},
			['dd-ddm']
		);

		if (LAYOUT_CONFIG) {
			var layoutContainer = A.one(LAYOUT_CONFIG.container);

			Liferay.once('initLayout', () => {
				Layout.init();
			});

			if (layoutContainer) {
				if (!A.UA.touchEnabled) {
					layoutContainer.once('mousemove', () => {
						Liferay.fire('initLayout');
					});
				}
				else {
					Liferay.fire('initLayout');
				}
			}
		}

		Liferay.Layout = Layout;
	},
	'',
	{
		requires: [],
	}
);
