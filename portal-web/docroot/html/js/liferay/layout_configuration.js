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

					_addPortlet: function(portlet, options) {
						var instance = this;

						var portletMetaData = instance._getPortletMetaData(portlet);

						if (!portletMetaData.portletUsed) {
							var plid = portletMetaData.plid;
							var portletId = portletMetaData.portletId;
							var portletItemId = portletMetaData.portletItemId;
							var isInstanceable = portletMetaData.instanceable;

							if (!isInstanceable) {
								instance._disablePortletEntry(portletId);
							}

							var beforePortletLoaded = null;
							var placeHolder = A.Node.create('<div class="loading-animation" />');

							if (options) {
								var item = options.item;

								item.placeAfter(placeHolder);
								item.remove(true);

								beforePortletLoaded = options.beforePortletLoaded;
							}
							else {
								var layoutOptions = Layout.options;

								var firstColumn = Layout.getActiveDropNodes().item(0);

								if (firstColumn) {
									var dropColumn = firstColumn.one(layoutOptions.dropContainer);
									var referencePortlet = Layout.findReferencePortlet(dropColumn);

									if (referencePortlet) {
										referencePortlet.placeBefore(placeHolder);
									}
									else {
										if (dropColumn) {
											dropColumn.append(placeHolder);
										}
									}
								}
							}

							var portletOptions = {
								beforePortletLoaded: beforePortletLoaded,
								plid: plid,
								placeHolder: placeHolder,
								portletId: portletId,
								portletItemId: portletItemId
							};

							Liferay.Portlet.add(portletOptions);
						}
					},

					_disablePortletEntry: function(portletId) {
						var instance = this;

						instance._eachPortletEntry(
							portletId,
							function(item, index) {
								item.addClass('lfr-portlet-used');
							}
						);
					},

					_eachPortletEntry: function(portletId, callback) {
						var instance = this;

						var portlets = A.all('[data-portlet-id=' + portletId + ']');

						portlets.each(callback);
					},

					_enablePortletEntry: function(portletId) {
						var instance = this;

						instance._eachPortletEntry(
							portletId,
							function(item, index) {
								item.removeClass('lfr-portlet-used');
							}
						);
					},

					_getPortletMetaData: function(portlet) {
						var instance = this;

						var portletMetaData = portlet._LFR_portletMetaData;

						if (!portletMetaData) {
							var instanceable = (portlet.attr('data-instanceable') == 'true');
							var plid = portlet.attr('data-plid');
							var portletId = portlet.attr('data-portlet-id');
							var portletItemId = portlet.attr('data-portlet-item-id');
							var portletUsed = portlet.hasClass('lfr-portlet-used');

							portletMetaData = {
								instanceable: instanceable,
								plid: plid,
								portletId: portletId,
								portletItemId: portletItemId,
								portletUsed: portletUsed
							};

							portlet._LFR_portletMetaData = portletMetaData;
						}

						return portletMetaData;
					},

					_loadContent: function() {
						var instance = this;

						Liferay.fire('initLayout');

						instance.init();

						Util.addInputType();

						instance._dialogBody = A.one('#portal_add_panel');

						if (instance._dialogBody) {
							Liferay.on('closePortlet', instance._onPortletClose, instance);

							instance._portletItems = instance._dialogBody.all('div.lfr-portlet-item');

							var portlets = instance._portletItems;

							var dialogBody = instance._dialogBody;

							dialogBody.delegate(
								'click',
								function(event) {
									var link = event.currentTarget;
									var portlet = link.ancestor('.lfr-portlet-item');

									if (!portlet.hasClass('lfr-portlet-used')) {
										instance._addPortlet(portlet);
									}
								},
								'.lfr-portlet-item a'
							);

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
					},

					_onPortletClose: function(event) {
						var instance = this;

						var panel = instance._dialogBody;

						if (panel) {
							var item = panel.one('.lfr-portlet-item[data-plid=' + event.plid + '][data-portlet-id=' + event.portletId + '][data-instanceable=false]');

							if (item && item.hasClass('lfr-portlet-used')) {
								var portletId = item.attr('data-portlet-id');

								instance._enablePortletEntry(portletId);
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