/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-layout-column',
	(A) => {
		const DDM = A.DD.DDM;

		const Layout = Liferay.Layout;

		const CSS_DRAGGING = 'dragging';

		Layout.getLastPortletNode = function (column) {
			const portlets = column.all(Layout.options.portletBoundary);

			const lastIndex = portlets.size() - 1;

			return portlets.item(lastIndex);
		};

		Layout.findSiblingPortlet = function (portletNode, siblingPos) {
			const dragNodes = Layout.options.dragNodes;
			let sibling = portletNode.get(siblingPos);

			while (sibling && !sibling.test(dragNodes)) {
				sibling = sibling.get(siblingPos);
			}

			return sibling;
		};

		const ColumnLayout = A.Component.create({
			ATTRS: {
				proxyNode: {
					value: Layout.PROXY_NODE,
				},
			},

			EXTENDS: A.SortableLayout,

			NAME: 'ColumnLayout',

			prototype: {
				_positionNode(event) {
					const portalLayout = event.currentTarget;

					const activeDrop =
						portalLayout.lastAlignDrop || portalLayout.activeDrop;

					if (activeDrop) {
						const dropNode = activeDrop.get('node');
						const isStatic = dropNode.isStatic;

						if (isStatic) {
							const start = isStatic === 'start';

							portalLayout.quadrant = start ? 4 : 1;
						}

						ColumnLayout.superclass._positionNode.apply(
							this,
							arguments
						);
					}
				},

				_syncProxyNodeSize() {
					const instance = this;

					const dragNode = DDM.activeDrag.get('dragNode');
					const proxyNode = instance.get('proxyNode');

					if (proxyNode && dragNode) {
						dragNode.set('offsetHeight', 30);
						dragNode.set('offsetWidth', 200);

						proxyNode.set('offsetHeight', 30);
						proxyNode.set('offsetWidth', 200);
					}
				},

				dragItem: 0,
			},

			register() {
				const columnLayoutDefaults = {
					...Layout.DEFAULT_LAYOUT_OPTIONS,
					after: {
						'drag:end'() {
							Layout._columnContainer.removeClass(CSS_DRAGGING);
						},

						'drag:start'() {
							const node = DDM.activeDrag.get('node');
							const nodeId = node.get('id');

							Layout.PORTLET_TOPPER.html(
								Layout._getPortletTitle(nodeId)
							);

							if (Liferay.Data.isCustomizationView()) {
								Layout.DEFAULT_LAYOUT_OPTIONS.dropNodes.addClass(
									'customizable'
								);
							}

							Layout._columnContainer.addClass(CSS_DRAGGING);
						},
					},
					on: {
						'drag:start'() {
							Liferay.fire('portletDragStart');
						},

						'drop:enter'() {
							Liferay.Layout.updateOverNestedPortletInfo();
						},

						'drop:exit'() {
							Liferay.Layout.updateOverNestedPortletInfo();
						},
						'placeholderAlign'(event) {
							const portalLayout = event.currentTarget;

							const activeDrop = portalLayout.activeDrop;
							const lastActiveDrop = portalLayout.lastActiveDrop;

							if (lastActiveDrop) {
								const activeDropNode = activeDrop.get('node');
								const lastActiveDropNode = lastActiveDrop.get(
									'node'
								);

								const isStatic = activeDropNode.isStatic;
								const quadrant = portalLayout.quadrant;

								if (isStatic) {
									const start = isStatic === 'start';

									const siblingPos = start
										? 'nextSibling'
										: 'previousSibling';

									const siblingPortlet = Layout.findSiblingPortlet(
										activeDropNode,
										siblingPos
									);
									const staticSibling =
										siblingPortlet &&
										siblingPortlet.isStatic === isStatic;

									if (
										staticSibling ||
										(start && quadrant <= 2) ||
										(!start && quadrant >= 3)
									) {
										event.halt();
									}
								}

								const overColumn = !activeDropNode.drop;

								if (!Layout.OVER_NESTED_PORTLET && overColumn) {
									const activeDropNodeId = activeDropNode.get(
										'id'
									);
									const emptyColumn =
										Layout.EMPTY_COLUMNS[activeDropNodeId];

									if (!emptyColumn) {
										if (
											activeDropNode !==
											lastActiveDropNode
										) {
											let referencePortlet = Layout.getLastPortletNode(
												activeDropNode
											);

											if (
												referencePortlet &&
												referencePortlet.isStatic
											) {
												const options = Layout.options;

												const dropColumn = activeDropNode.one(
													options.dropContainer
												);
												const foundReferencePortlet = Layout.findReferencePortlet(
													dropColumn
												);

												if (foundReferencePortlet) {
													referencePortlet = foundReferencePortlet;
												}
											}

											const drop = A.DD.DDM.getDrop(
												referencePortlet
											);

											if (drop) {
												portalLayout.quadrant = 4;
												portalLayout.activeDrop = drop;
												portalLayout.lastAlignDrop = drop;
											}

											portalLayout._syncPlaceholderUI();
										}

										event.halt();
									}
								}

								if (
									Layout.OVER_NESTED_PORTLET &&
									activeDropNode === lastActiveDropNode
								) {
									event.halt();
								}
							}
						},
					},
				};

				Layout._columnContainer = A.all(Layout._layoutContainer);

				Layout.layoutHandler = new Layout.ColumnLayout(
					columnLayoutDefaults
				);

				Layout.syncDraggableClassUI();
			},
		});

		Layout.ColumnLayout = ColumnLayout;
	},
	'',
	{
		requires: ['aui-sortable-layout', 'dd'],
	}
);
