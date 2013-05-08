AUI.add(
	'liferay-layout-column',
	function(A) {
		var DDM = A.DD.DDM;

		var Layout = Liferay.Layout;

		var CSS_DRAGGING = 'dragging';

		Layout.getLastPortletNode = function(column) {
			var instance = this;

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

		Layout.register = function() {
			var columnLayoutDefaults = A.merge(
				Layout.DEFAULT_LAYOUT_OPTIONS,
				{
					after: {
						'drag:start': function(event) {
							var node = DDM.activeDrag.get('node');
							var nodeId = node.get('id');

							Layout.PORTLET_TOPPER.html(Layout._getPortletTitle(nodeId));

							if (Liferay.Data.isCustomizationView()) {
								Layout.DEFAULT_LAYOUT_OPTIONS.dropNodes.addClass('customizable');
							}

							Layout._columnContainer.addClass(CSS_DRAGGING);
						},

						'drag:end': function(event) {
							Layout._columnContainer.removeClass(CSS_DRAGGING);
						}
					},
					on: {
						'drag:start': function(event) {
							Liferay.fire('portletDragStart');
						},

						'drop:enter': function(event) {
							Liferay.Layout.updateOverNestedPortletInfo();
						},

						'drop:exit': function(event) {
							Liferay.Layout.updateOverNestedPortletInfo();
						},
						placeholderAlign: function(event) {
							var portalLayout = event.currentTarget;
							var activeDrop = portalLayout.activeDrop;
							var lastActiveDrop = portalLayout.lastActiveDrop;

							if (lastActiveDrop) {
								var activeDropNode = activeDrop.get('node');
								var lastActiveDropNode = lastActiveDrop.get('node');

								var quadrant = portalLayout.quadrant;
								var isStatic = activeDropNode.isStatic;

								if (isStatic) {
									var start = (isStatic == 'start');
									var siblingPos = (start ? 'nextSibling' : 'previousSibling');

									var siblingPortlet = Layout.findSiblingPortlet(activeDropNode, siblingPos);
									var staticSibling = (siblingPortlet && (siblingPortlet.isStatic == isStatic));

									if (staticSibling ||
										(start && (quadrant <= 2)) ||
										(!start && (quadrant >= 3))) {

										event.halt();
									}
								}

								var isOverColumn = !activeDropNode.drop;

								if (!Layout.OVER_NESTED_PORTLET && isOverColumn) {
									var activeDropNodeId = activeDropNode.get('id');
									var emptyColumn = Layout.EMPTY_COLUMNS[activeDropNodeId];

									if (!emptyColumn) {
										if (activeDropNode != lastActiveDropNode) {
											var referencePortlet = Layout.getLastPortletNode(activeDropNode);

											if (referencePortlet && referencePortlet.isStatic) {
												var options = Layout.options;
												var dropColumn = activeDropNode.one(options.dropContainer);
												var foundReferencePortlet = Layout.findReferencePortlet(dropColumn);

												if (foundReferencePortlet) {
													referencePortlet = foundReferencePortlet;
												}
											}

											var drop = A.DD.DDM.getDrop(referencePortlet);

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

								if (Layout.OVER_NESTED_PORTLET && (activeDropNode == lastActiveDropNode)) {
									event.halt();
								}
							}
						}
					}
				}
			);

			Layout._columnContainer = A.all(Layout._layoutContainer);

			Layout.layoutHandler = new Layout.ColumnLayout(columnLayoutDefaults);

			Layout.syncDraggableClassUI();
		};

		var ColumnLayout = A.Component.create(
			{
				ATTRS: {
					proxyNode: {
						value: Layout.PROXY_NODE
					}
				},

				NAME: 'ColumnLayout',

				EXTENDS: A.SortableLayout,

				prototype: {
					dragItem: 0,

					_positionNode: function(event) {
						var instance = this;

						var portalLayout = event.currentTarget;
						var activeDrop = portalLayout.lastAlignDrop || portalLayout.activeDrop;

						if (activeDrop) {
							var dropNode = activeDrop.get('node');
							var isStatic = dropNode.isStatic;

							if (isStatic) {
								var start = (isStatic == 'start');

								portalLayout.quadrant = (start ? 4 : 1);
							}

							ColumnLayout.superclass._positionNode.apply(this, arguments);
						}
					},

					_syncProxyNodeSize: function() {
						var instance = this;

						var dragNode = DDM.activeDrag.get('dragNode');
						var proxyNode = instance.get('proxyNode');

						if (proxyNode && dragNode) {
							dragNode.set('offsetHeight', 30);
							dragNode.set('offsetWidth', 200);

							proxyNode.set('offsetHeight', 30);
							proxyNode.set('offsetWidth', 200);
						}
					}
				}
			}
		);

		Layout.ColumnLayout = ColumnLayout;
	},
	'',
	{
		requires: ['aui-sortable-layout', 'dd']
	}
);