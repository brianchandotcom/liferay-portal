AUI.add(
	'liferay-dockbar-add-content-drag-drop',
	function(A) {
		var DDM = A.DD.DDM;
		var Dockbar = Liferay.Dockbar;
		var Layout = Liferay.Layout;

		var PROXY_NODE_ITEM = Layout.PROXY_NODE_ITEM;

		var STR_NODE = 'node';

		var AddContentDragDrop = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'addcontentdragdrop',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._addPanel = instance.byId('addPanelContainer');

						instance._bindUI();
					},

					_bindUI: function() {
						var instance = this;

						var portletItemOptions = {
							delegateConfig: {
								container: instance._addPanel,
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
							},
							on: Layout.DEFAULT_LAYOUT_OPTIONS.on
						};

						if (themeDisplay.isFreeformLayout()) {
							instance._portletItem = new Dockbar.FreeFormPortletItem(portletItemOptions);
						}
						else {
							instance._portletItem = new Dockbar.PortletItem(portletItemOptions);
						}

						Liferay.fire('initLayout');
					}
				}
			}
		);

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

					_addPortlet: function(portletNode, options) {
						var instance = this;

						Liferay.fire(
							'AddContent:addPortlet',
							{
								node: portletNode,
								options: options
							}
						);
					},

					_getAppendNode: function() {
						var instance = this;

						instance.appendNode = DDM.activeDrag.get(STR_NODE).clone();

						return instance.appendNode;
					},

					_onDragEnd: function(event) {
						var instance = this;

						PortletItem.superclass._onDragEnd.apply(this, arguments);

						var appendNode = instance.appendNode;

						if (appendNode && appendNode.inDoc()) {
							var portletNode = event.target.get(STR_NODE);

							instance._addPortlet(
								portletNode,
								{
									item: instance.appendNode
								}
							);
						}
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

		Dockbar.AddContentDragDrop = AddContentDragDrop;
		Dockbar.FreeFormPortletItem = FreeFormPortletItem;
		Dockbar.PortletItem = PortletItem;
	},
	'',
	{
		requires: ['aui-base', 'dd', 'liferay-dockbar', 'liferay-layout', 'liferay-portlet-base']
	}
);