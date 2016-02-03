AUI.add(
	'liferay-control-menu-add-content-drag-drop',
	function(A) {
		var DDM = A.DD.DDM;

		var ControlMenu = Liferay.ControlMenu;
		var Layout = Liferay.Layout;

		var PROXY_NODE_ITEM = Layout.PROXY_NODE_ITEM;

		var STR_NODE = 'node';

		var AddContentDragDrop = function() {
		};

		AddContentDragDrop.prototype = {
			initializer: function() {
				var instance = this;

				instance._bindUIDragDrop();
			},

			_bindUIDragDrop: function() {
				var instance = this;

				var portletItemOptions = {
					delegateConfig: {
						container: instance._entriesPanel,
						dragConfig: {
							clickPixelThresh: 0,
							clickTimeThresh: 0
						},
						invalid: '.lfr-portlet-used',
						target: false
					},
					dragNodes: '[data-draggable]',
					dropContainer: function(dropNode) {
						return dropNode.one(Layout.options.dropContainer);
					}
				};

				var defaultLayoutOptions = Layout.DEFAULT_LAYOUT_OPTIONS;

				if (defaultLayoutOptions) {
					portletItemOptions.on = defaultLayoutOptions.on;
				}

				var portletItemClass = 'PortletItem';

				if (themeDisplay.isFreeformLayout()) {
					portletItemClass = 'FreeFormPortletItem';
				}

				var portletItem = new ControlMenu[portletItemClass](portletItemOptions);

				portletItem.on('drag:end', instance._onDragEnd, instance);

				instance._portletItem = portletItem;

				Liferay.fire('initLayout');
			},

			_onDragEnd: function(event) {
				var instance = this;

				var portletItem = event.currentTarget;

				var appendNode = portletItem.appendNode;

				if (appendNode && appendNode.inDoc()) {
					var portletNode = event.target.get(STR_NODE);

					instance.addPortlet(
						portletNode,
						{
							item: appendNode
						}
					);
				}
			}
		};

		var PortletItem = A.Component.create(
			{
				ATTRS: {
					lazyStart: {
						value: true
					},

					proxyNode: {
						value: PROXY_NODE_ITEM
					}
				},

				EXTENDS: Layout.ColumnLayout,

				NAME: 'PortletItem',

				prototype: {
					PROXY_TITLE: PROXY_NODE_ITEM.one('.portlet-title'),

					bindUI: function() {
						var instance = this;

						PortletItem.superclass.bindUI.apply(this, arguments);

						instance.on('placeholderAlign', instance._onPlaceholderAlign);
					},

					_getAppendNode: function() {
						var instance = this;

						instance.appendNode = DDM.activeDrag.get(STR_NODE).clone();

						return instance.appendNode;
					},

					_onDragStart: function() {
						var instance = this;

						PortletItem.superclass._onDragStart.apply(this, arguments);

						instance._syncProxyTitle();

						instance.lazyEvents = false;
					},

					_onPlaceholderAlign: function(event) {
						var instance = this;

						var drop = event.drop;
						var portletItem = event.currentTarget;

						if (drop && portletItem) {
							var dropNodeId = drop.get(STR_NODE).get('id');

							if (Layout.EMPTY_COLUMNS[dropNodeId]) {
								portletItem.activeDrop = drop;
								portletItem.lazyEvents = false;
								portletItem.quadrant = 1;
							}
						}
					},

					_positionNode: function(event) {
						var instance = this;

						var portalLayout = event.currentTarget;

						var activeDrop = portalLayout.lastAlignDrop || portalLayout.activeDrop;

						if (activeDrop) {
							var dropNode = activeDrop.get(STR_NODE);

							if (dropNode.isStatic) {
								var dropColumn = dropNode.ancestor(Layout.options.dropContainer);
								var foundReferencePortlet = Layout.findReferencePortlet(dropColumn);

								if (!foundReferencePortlet) {
									foundReferencePortlet = Layout.getLastPortletNode(dropColumn);
								}

								if (foundReferencePortlet) {
									var drop = DDM.getDrop(foundReferencePortlet);

									if (drop) {
										portalLayout.quadrant = 4;
										portalLayout.activeDrop = drop;
										portalLayout.lastAlignDrop = drop;
									}
								}
							}

							PortletItem.superclass._positionNode.apply(this, arguments);
						}
					},

					_syncProxyTitle: function() {
						var instance = this;

						var node = DDM.activeDrag.get(STR_NODE);
						var title = node.attr('data-title');

						instance.PROXY_TITLE.html(title);
					}
				}
			}
		);

		var FreeFormPortletItem = A.Component.create(
			{
				ATTRS: {
					lazyStart: {
						value: false
					}
				},

				EXTENDS: PortletItem,

				NAME: 'FreeFormPortletItem',

				prototype: {
					initializer: function() {
						var instance = this;

						var placeholder = instance.get('placeholder');

						if (placeholder) {
							placeholder.addClass(Layout.options.freeformPlaceholderClass);
						}
					}
				}
			}
		);

		ControlMenu.AddContentDragDrop = AddContentDragDrop;
		ControlMenu.FreeFormPortletItem = FreeFormPortletItem;
		ControlMenu.PortletItem = PortletItem;
	},
	'',
	{
		requires: ['aui-base', 'dd', 'liferay-control-menu', 'liferay-layout', 'liferay-layout-column', 'liferay-layout-freeform', 'liferay-portlet-base']
	}
);