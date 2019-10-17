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
	'liferay-layout-column',
	A => {
		var DDM = A.DD.DDM;

		var Layout = Liferay.Layout;

		var CSS_DRAGGING = 'dragging';

		Layout.getLastPortletNode = function(column) {
			var portlets = column.all(Layout.options.portletBoundary);

			var lastIndex = portlets.size() - 1;

			return portlets.item(lastIndex);
		};

		Layout.findSiblingPortlet = function(portletNode, siblingPos) {
			var dragNodes = Layout.options.dragNodes;
			var sibling = portletNode.get(siblingPos);

			while (sibling && !sibling.test(dragNodes)) {
				sibling = sibling.get(siblingPos);
			}

			return sibling;
		};

		var ColumnLayout = A.Component.create({
			ATTRS: {
				proxyNode: {
					value: Layout.PROXY_NODE
				}
			},

			EXTENDS: A.SortableLayout,

			NAME: 'ColumnLayout',

			prototype: {
				_positionNode(event) {
					var portalLayout = event.currentTarget;

					var activeDrop =
						portalLayout.lastAlignDrop || portalLayout.activeDrop;

					if (activeDrop) {
						var dropNode = activeDrop.get('node');
						var isStatic = dropNode.isStatic;

						if (isStatic) {
							var start = isStatic == 'start';

							portalLayout.quadrant = start ? 4 : 1;
						}

						ColumnLayout.superclass._positionNode.apply(
							this,
							arguments
						);
					}
				},

				_syncProxyNodeSize() {
					var instance = this;

					var dragNode = DDM.activeDrag.get('dragNode');
					var proxyNode = instance.get('proxyNode');

					if (proxyNode && dragNode) {
						dragNode.set('offsetHeight', 30);
						dragNode.set('offsetWidth', 200);

						proxyNode.set('offsetHeight', 30);
						proxyNode.set('offsetWidth', 200);
					}
				},

				dragItem: 0
			},

			register() {
				var columnLayoutDefaults = A.merge(
					Layout.DEFAULT_LAYOUT_OPTIONS,
					{
						after: {
							'drag:end'() {
								Layout._columnContainer.removeClass(
									CSS_DRAGGING
								);
							},

							'drag:start'() {
								var node = DDM.activeDrag.get('node');
								var nodeId = node.get('id');

								Layout.PORTLET_TOPPER.html(
									Layout._getPortletTitle(nodeId)
								);

								if (Liferay.Data.isCustomizationView()) {
									Layout.DEFAULT_LAYOUT_OPTIONS.dropNodes.addClass(
										'customizable'
									);
								}

								Layout._columnContainer.addClass(CSS_DRAGGING);
							}
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
							placeholderAlign(event) {
								var portalLayout = event.currentTarget;

								var activeDrop = portalLayout.activeDrop;
								var lastActiveDrop =
									portalLayout.lastActiveDrop;

								if (lastActiveDrop) {
									var activeDropNode = activeDrop.get('node');
									var lastActiveDropNode = lastActiveDrop.get(
										'node'
									);

									var isStatic = activeDropNode.isStatic;
									var quadrant = portalLayout.quadrant;

									if (isStatic) {
										var start = isStatic == 'start';

										var siblingPos = start
											? 'nextSibling'
											: 'previousSibling';

										var siblingPortlet = Layout.findSiblingPortlet(
											activeDropNode,
											siblingPos
										);
										var staticSibling =
											siblingPortlet &&
											siblingPortlet.isStatic == isStatic;

										if (
											staticSibling ||
											(start && quadrant <= 2) ||
											(!start && quadrant >= 3)
										) {
											event.halt();
										}
									}

									var overColumn = !activeDropNode.drop;

									if (
										!Layout.OVER_NESTED_PORTLET &&
										overColumn
									) {
										var activeDropNodeId = activeDropNode.get(
											'id'
										);
										var emptyColumn =
											Layout.EMPTY_COLUMNS[
												activeDropNodeId
											];

										if (!emptyColumn) {
											if (
												activeDropNode !=
												lastActiveDropNode
											) {
												var referencePortlet = Layout.getLastPortletNode(
													activeDropNode
												);

												if (
													referencePortlet &&
													referencePortlet.isStatic
												) {
													var options =
														Layout.options;

													var dropColumn = activeDropNode.one(
														options.dropContainer
													);
													var foundReferencePortlet = Layout.findReferencePortlet(
														dropColumn
													);

													if (foundReferencePortlet) {
														referencePortlet = foundReferencePortlet;
													}
												}

												var drop = A.DD.DDM.getDrop(
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
										activeDropNode == lastActiveDropNode
									) {
										event.halt();
									}
								}
							}
						}
					}
				);

				Layout._columnContainer = A.all(Layout._layoutContainer);

				Layout.layoutHandler = new Layout.ColumnLayout(
					columnLayoutDefaults
				);

				Layout.syncDraggableClassUI();
			}
		});

		Layout.ColumnLayout = ColumnLayout;
	},
	'',
	{
		requires: ['aui-sortable-layout', 'dd']
	}
);
