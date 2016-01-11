AUI.add(
	'liferay-control-menu-add-base',
	function(A) {
		var DDM = A.DD.DDM;
		var Lang = A.Lang;

		var ControlMenu = Liferay.ControlMenu;
		var Layout = Liferay.Layout;
		var Portlet = Liferay.Portlet;
		var Util = Liferay.Util;

		var CSS_LFR_PORTLET_USED = 'lfr-portlet-used';

		var DATA_CLASS_NAME = 'data-class-name';

		var DATA_CLASS_PK = 'data-class-pk';

		var DATA_PORTLET_ID = 'data-portlet-id';

		var PROXY_NODE_ITEM = Layout.PROXY_NODE_ITEM;

		var STR_EMPTY = '';

		var STR_NODE = 'node';

		var TPL_LOADING = '<div class="loading-animation" />';

		var AddBase = A.Component.create(
			{
				ATTRS: {
					focusItem: {
						setter: A.one
					},

					id: {
						validator: Lang.isString
					},

					inputNode: {
						setter: A.one
					},

					nodeList: {
						setter: A.one
					},

					nodeSelector: {
						validator: Lang.isString
					},

					selected: {
						validator: Lang.isBoolean
					}
				},

				EXTENDS: A.Base,

				NAME: 'addbase',

				prototype: {
					initializer: function(config) {
						var instance = this;

						if (instance.get('selected')) {
							var focusItem = instance.get('focusItem');

							if (focusItem) {
								focusItem.focus();
							}
						}

						instance._addedMessage = instance.byId('addedMessage');

						instance._hideAddedMessageTask = A.debounce(A.bind('_hideAddedMessage', instance), 2000);

						instance._eventHandles = [];

						instance._bindUIDABase();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					addPortlet: function(portlet, options) {
						var instance = this;

						var portletMetaData = instance._getPortletMetaData(portlet);

						if (!portletMetaData.portletUsed) {
							var portletId = portletMetaData.portletId;

							if (!portletMetaData.instanceable) {
								instance._disablePortletEntry(portletId);
							}

							var beforePortletLoaded = null;
							var placeHolder = A.Node.create(TPL_LOADING);

							if (options) {
								var item = options.item;

								item.placeAfter(placeHolder);
								item.remove(true);

								beforePortletLoaded = options.beforePortletLoaded;
							}
							else {
								var firstColumn = Layout.getActiveDropNodes().item(0);

								if (firstColumn) {
									var dropColumn = firstColumn.one(Layout.options.dropContainer);
									var referencePortlet = Layout.findReferencePortlet(dropColumn);

									if (referencePortlet) {
										referencePortlet.placeBefore(placeHolder);
									}
									else if (dropColumn) {
										dropColumn.append(placeHolder);
									}
								}
							}

							if (Util.isPhone() || Util.isTablet()) {
								placeHolder.guid();

								instance._syncContentLink(placeHolder);

								instance._portletFeedback(portletId, portlet);

								Liferay.once(
									'addPortlet',
									function(event) {
										instance._syncContentLink(event.portlet);
									}
								);
							}

							Portlet.add(
								{
									beforePortletLoaded: beforePortletLoaded,
									placeHolder: placeHolder,
									plid: portletMetaData.plid,
									portletData: portletMetaData.portletData,
									portletId: portletId,
									portletItemId: portletMetaData.portletItemId
								}
							);
						}
					},

					_bindUIDABase: function() {
						var instance = this;

						instance._eventHandles.push(Liferay.after('showTab', instance._showTab, instance));
					},

					_disablePortletEntry: function(portletId) {
						var instance = this;

						instance._eachPortletEntry(
							portletId,
							function(item, index) {
								item.addClass(CSS_LFR_PORTLET_USED);
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
								item.removeClass(CSS_LFR_PORTLET_USED);
							}
						);
					},

					_getPortletMetaData: function(portlet) {
						var instance = this;

						var portletMetaData = portlet._LFR_portletMetaData;

						if (!portletMetaData) {
							var className = portlet.attr(DATA_CLASS_NAME);
							var classPK = portlet.attr(DATA_CLASS_PK);

							var instanceable = (portlet.attr('data-instanceable') == 'true');
							var plid = portlet.attr('data-plid');

							var portletData = STR_EMPTY;

							if ((className != STR_EMPTY) && (classPK != STR_EMPTY)) {
								portletData = classPK + ',' + className;
							}

							var portletId = portlet.attr(DATA_PORTLET_ID);
							var portletItemId = portlet.attr('data-portlet-item-id');
							var portletUsed = portlet.hasClass(CSS_LFR_PORTLET_USED);

							portletMetaData = {
								instanceable: instanceable,
								plid: plid,
								portletData: portletData,
								portletId: portletId,
								portletItemId: portletItemId,
								portletUsed: portletUsed
							};

							portlet._LFR_portletMetaData = portletMetaData;
						}

						return portletMetaData;
					},

					_hideAddedMessage: function() {
						var instance = this;

						instance._addedMessage.hide(true);

						instance._skipToContentHandle.detach();
					},

					_portletFeedback: function(portletId, portlet) {
						var instance = this;

						var addedMessagePortlet = instance.byId('portletName');

						var portletNameNode = portlet.ancestor('[data-portlet-id=' + portletId + ']', true);

						if (portletNameNode) {
							var portletName = portletNameNode.attr('data-title');

							addedMessagePortlet.setHTML(portletName);

							instance._skipToContentHandle = instance._contentLink.on('tap', A.bind('_skipToContent', instance));

							instance._addedMessage.show(
								true,
								function() {
									instance._hideAddedMessageTask();
								}
							);
						}
					},

					_showTab: function(event) {
						var instance = this;

						var focusItem = instance.get('focusItem');

						if (focusItem && event.tabSection && event.tabSection.contains(focusItem)) {
							focusItem.focus();
						}
					},

					_skipToContent: function(event) {
						var instance = this;

						event.preventDefault();

						var portletXY = instance._lastAddedPortlet.getXY();
						var scrollAnim = instance._scrollAnim;

						if (!scrollAnim) {
							scrollAnim = new A.Anim(
								{
									duration: 0.3,
									easing: 'easeOut',
									node: 'win'
								}
							);

							instance._scrollAnim = scrollAnim;
						}

						scrollAnim.set(
							'to',
							{
								scroll: [portletXY[0], (portletXY[1] - 40)]
							}
						).run();

						instance._hideAddedMessage();
					},

					_syncContentLink: function(node) {
						var instance = this;

						var href = '#' + node.attr('id');

						var contentLink = instance._contentLink;

						if (!contentLink) {
							contentLink = instance.byId('contentLink');

							contentLink.swallowEvent('click', true);

							instance._contentLink = contentLink;
						}

						contentLink.attr('href', href);

						instance._lastAddedPortlet = node;
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

		ControlMenu.AddBase = AddBase;
		ControlMenu.FreeFormPortletItem = FreeFormPortletItem;
		ControlMenu.PortletItem = PortletItem;
	},
	'',
	{
		requires: ['anim', 'aui-base', 'liferay-control-menu', 'liferay-layout', 'liferay-layout-column', 'liferay-layout-freeform', 'transition']
	}
);