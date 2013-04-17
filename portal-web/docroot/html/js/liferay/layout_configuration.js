;(function(A, Liferay) {
	var Browser = Liferay.Browser;
	var Util = Liferay.Util;

	var LayoutConfiguration = {};

	A.add(
		'liferay-layout-configuration',
		function(A) {
			var DDM = A.DD.DDM;
			var Layout = Liferay.Layout;

			A.mix(
				LayoutConfiguration,
				{
					categories: [],
					portlets: [],
					showTimer: 0,

					init: function() {
						var instance = this;

					},

					_loadContent: function() {
						var instance = this;

						Liferay.fire('initLayout');

						Util.addInputType();

						instance._dialogBody = A.one('#portal_add_panel');

						if (instance._dialogBody) {
							instance._portletItems = instance._dialogBody.all('div.lfr-portlet-item');

							var portlets = instance._portletItems;

							var dialogBody = instance._dialogBody;

							var portletItem = null;
							var layoutOptions = Layout.options;

							var portletItemOptions = {
								delegateConfig: {
									container: '#portal_add_panel',
									dragConfig: {
										clickPixelThresh: 0,
										clickTimeThresh: 0
									},
									invalid: '.lfr-portlet-used',
									target: false
								},
								dragNodes: '.lfr-portlet-item',
								dropContainer: function(dropNode) {
									return dropNode.one(layoutOptions.dropContainer);
								},
								on: Layout.DEFAULT_LAYOUT_OPTIONS.on
							};

							if (themeDisplay.isFreeformLayout()) {
								portletItem = new Layout.FreeFormPortletItem(portletItemOptions);
							}
							else {
								portletItem = new Layout.PortletItem(portletItemOptions);
							}

							if (Browser.isIe()) {
								dialogBody.delegate(
									'mouseenter',
									function(event) {
										event.currentTarget.addClass('over');
									},
									'.lfr-portlet-item'
								);

								dialogBody.delegate(
									'mouseleave',
									function(event) {
										event.currentTarget.removeClass('over');
									},
									'.lfr-portlet-item'
								);
							}
						}
					}
				}
			);

			var PROXY_NODE_ITEM = Layout.PROXY_NODE_ITEM;

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

							LayoutConfiguration._addPortlet(portletNode, options);
						},

						_getAppendNode: function() {
							var instance = this;

							instance.appendNode = DDM.activeDrag.get('node').clone();

							return instance.appendNode;
						},

						_onDragEnd: function(event) {
							var instance = this;

							PortletItem.superclass._onDragEnd.apply(this, arguments);

							var appendNode = instance.appendNode;

							if (appendNode && appendNode.inDoc()) {
								var portletNode = event.target.get('node');

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
								var dropNodeId = drop.get('node').get('id');

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
								var dropNode = activeDrop.get('node');

								if (dropNode.isStatic) {
									var options = Layout.options;
									var dropColumn = dropNode.ancestor(options.dropContainer);
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

							var node = DDM.activeDrag.get('node');
							var title = node.attr('title');

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

			Layout.FreeFormPortletItem = FreeFormPortletItem;
			Layout.PortletItem = PortletItem;
		},
		'',
		{
			requires: ['dd', 'liferay-layout']
		}
	);

	Liferay.LayoutConfiguration = LayoutConfiguration;
})(AUI(), Liferay);