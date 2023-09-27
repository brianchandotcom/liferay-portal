/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-layout',
	(A) => {
		const Util = Liferay.Util;

		const CSS_DRAGGABLE = 'portlet-draggable';

		const LAYOUT_CONFIG = Liferay.Data.layoutConfig;

		const Layout = {
			_afterPortletClose(event) {
				const column = event.column;

				if (column) {
					Layout.syncEmptyColumnClassUI(column);
				}
			},

			_getPortletTitle: A.cached((id) => {
				const portletBoundary = A.one('#' + id);

				let portletTitle = portletBoundary.one('.portlet-title');

				if (!portletTitle) {
					portletTitle = Layout.PROXY_NODE_ITEM.one('.portlet-title');

					const title = portletBoundary.one('.portlet-title-default');

					let titleText = '';

					if (title) {
						titleText = title.html();
					}

					portletTitle.html(titleText);
				}

				return portletTitle.outerHTML();
			}),

			_onPortletClose(event) {
				const portlet = event.portlet;

				const column = portlet.ancestor(Layout.options.dropContainer);

				Layout.updateCurrentPortletInfo(portlet);

				if (portlet.test('.portlet-nested-portlets')) {
					Layout.closeNestedPortlets(portlet);
				}

				event.column = column;
			},

			_onPortletDragEnd(event) {
				const dragNode = event.target.get('node');

				const columnNode = dragNode.get('parentNode');

				Layout.saveIndex(dragNode, columnNode);

				Layout.syncEmptyColumnClassUI(columnNode);
			},

			_onPortletDragStart(event) {
				const dragNode = event.target.get('node');

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
				const layoutHandler = Layout.getLayoutHandler();

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
				const nestedPortlets = portlet.all('.portlet-boundary');

				nestedPortlets.each((portlet) => {
					Liferay.Portlet.close(portlet, true, {
						nestedPortlet: true,
					});
				});
			},

			findIndex(node) {
				const options = Layout.options;
				const parentNode = node.get('parentNode');

				return parentNode
					.all('> ' + options.portletBoundary)
					.indexOf(node);
			},

			findReferencePortlet(dropColumn) {
				const portletBoundary = Layout.options.portletBoundary;
				const portlets = dropColumn.all('>' + portletBoundary);

				const firstPortlet = portlets.item(0);
				let referencePortlet = null;

				if (firstPortlet) {
					const firstPortletStatic = firstPortlet.isStatic;
					let lastStatic = null;

					if (!firstPortletStatic || firstPortletStatic === 'end') {
						referencePortlet = firstPortlet;
					}
					else {
						portlets.each((item) => {
							const isStatic = item.isStatic;

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
				const layoutHandler = Layout.getLayoutHandler();

				let retVal;

				if (layoutHandler) {
					retVal = layoutHandler.fire.apply(layoutHandler, arguments);
				}

				return retVal;
			},

			getActiveDropContainer() {
				const options = Layout.options;

				return A.all(
					options.dropContainer +
						':not(.' +
						options.disabledDropContainerClass +
						')'
				).item(0);
			},

			getActiveDropNodes() {
				const options = Layout.options;

				const dropNodes = [];

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
				const options = Layout.options;

				return A.all(options.dragNodes);
			},

			hasMoved(dragNode) {
				const curPortletInfo = Layout.curPortletInfo;
				let moved = false;

				if (curPortletInfo) {
					const currentIndex = Layout.findIndex(dragNode);
					const currentParent = dragNode.get('parentNode');

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
				const options = Layout.options;

				return !!columnNode.one(options.portletBoundary);
			},

			on() {
				const layoutHandler = Layout.getLayoutHandler();

				let retVal;

				if (layoutHandler) {
					retVal = layoutHandler.on.apply(layoutHandler, arguments);
				}

				return retVal;
			},

			options: LAYOUT_CONFIG,

			refresh(portlet) {
				const layoutHandler = Layout.getLayoutHandler();

				portlet = A.one(portlet);

				if (portlet && layoutHandler) {
					layoutHandler.delegate.syncTargets();

					Layout.updatePortletDropZones(portlet);
				}
			},

			saveIndex(portletNode, columnNode) {
				const columnNodeId = columnNode.get('id');

				const currentColumnId = columnNodeId.replace(
					/layout-column_/,
					''
				);
				const portletId = Util.getPortletId(portletNode.get('id'));
				const position = Layout.findIndex(portletNode);

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
				const options = Layout.options;

				if (options.dragNodes) {
					let dragNodes = A.all(options.dragNodes);

					if (options.invalid) {
						dragNodes = dragNodes.filter(
							':not(' + options.invalid + ')'
						);
					}

					dragNodes.addClass(CSS_DRAGGABLE);
				}
			},

			syncEmptyColumnClassUI(columnNode) {
				const curPortletInfo = Layout.curPortletInfo;
				const options = Layout.options;

				if (curPortletInfo) {
					const columnHasPortlets = Layout.hasPortlets(columnNode);
					const emptyColumnClass = options.emptyColumnClass;
					const originalParent = curPortletInfo.originalParent;

					const originalColumnHasPortlets = Layout.hasPortlets(
						originalParent
					);

					const currentColumn = columnNode.ancestor(
						options.dropNodes
					);
					const originalColumn = originalParent.ancestor(
						options.dropNodes
					);

					if (currentColumn) {
						const dropZoneId = currentColumn.get('id');

						Layout.EMPTY_COLUMNS[dropZoneId] = !columnHasPortlets;
					}

					if (originalColumn) {
						const originalDropZoneId = originalColumn.get('id');

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
				const options = Layout.options;

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
				const options = Layout.options;

				A.all(options.dropNodes).each((item) => {
					const columnId = item.get('id');

					Layout.EMPTY_COLUMNS[columnId] = !Layout.hasPortlets(item);
				});
			},

			updatePortletDropZones(portletBoundary) {
				const options = Layout.options;
				const portletDropNodes = portletBoundary.all(options.dropNodes);

				const layoutHandler = Layout.getLayoutHandler();

				portletDropNodes.each((item) => {
					layoutHandler.addDropNode(item);
				});
			},
		};

		Layout.init = function (options) {
			options = options || Layout.options;

			options.handles = A.Array(options.handles);

			Layout.PROXY_NODE.append(Layout.PORTLET_TOPPER);

			const layoutContainer = options.container;

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

			const eventHandles = [];

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
				const data = {
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
				const activeDrop = A.DD.DDM.activeDrop;
				const nestedPortletId = Layout.options.nestedPortletId;

				if (activeDrop) {
					const activeDropNode = activeDrop.get('node');
					const activeDropNodeId = activeDropNode.get('id');

					Layout.OVER_NESTED_PORTLET =
						activeDropNodeId.indexOf(nestedPortletId) > -1;
				}
			},
			['dd-ddm']
		);

		if (LAYOUT_CONFIG) {
			const layoutContainer = A.one(LAYOUT_CONFIG.container);

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
