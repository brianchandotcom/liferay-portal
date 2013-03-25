;(function(A, Liferay) {
	var Browser = Liferay.Browser;
	var Util = Liferay.Util;

	var LayoutConfiguration = {};

	Liferay.provide(
		LayoutConfiguration,
		'toggle',
		function(ppid) {
			var instance = this;

			var dialog = instance._applicationsDialog;

			if (!dialog) {
				var body = A.getBody();

				var url = themeDisplay.getPathMain() + '/portal/render_portlet';

				dialog = new A.Dialog(
					{
						on: {
							visibleChange: function(event) {
								body.toggleClass('lfr-has-sidebar', event.newVal);
							}
						},
						title: Liferay.Language.get('add-application'),
						width: 250
					}
				).render();

				var contentBox = dialog.get('contentBox');

				dialog.plug(
					A.Plugin.IO,
					{
						data: {
							doAsUserId: themeDisplay.getDoAsUserIdEncoded(),
							p_l_id: themeDisplay.getPlid(),
							p_p_id: ppid,
							p_p_state: 'exclusive'
						},
						after: {
							success: function(event) {
								instance._dialogBody = contentBox;

								instance._loadContent();
							}
						},
						uri: url
					}
				);

				instance._applicationsDialog = dialog;
			}

			dialog.show();
		},
		['aui-dialog', 'liferay-layout-configuration']
	);

	A.add(
		'liferay-layout-configuration',
		function(A) {
			var DDM = A.DD.DDM;
			var Layout = Liferay.Layout;

			var CSS_REPAINT = 'lfr-helper-repaint';

			var FORCE_REPAINT = (Liferay.Browser.isIe() && Liferay.Browser.getMajorVersion() === 9);

			A.mix(
				LayoutConfiguration,
				{
					categories: [],
					portlets: [],
					showTimer: 0,

					init: function() {
						var instance = this;

						var menu = A.one('#portal_add_content');

						instance.menu = menu;

						var searchInput = A.one('#layout_configuration_content');

						instance._searchInput = searchInput;

						if (menu) {
							instance.portlets = menu.all('.lfr-portlet-item');
							instance.categories = menu.all('.lfr-content-category');
							instance.categoryContainers = menu.all('.lfr-add-content');

							var data = function(node) {
								var id = node.attr('id');

								var dataNode = A.one('#' + id + 'CategoryPath');

								var value = (dataNode && dataNode.val()) || '';

								return [Util.uncamelize(value), value].join(' ').toLowerCase();
							};

							var isVisible = function(item, index, collection) {
								return !item.hasClass('aui-helper-hidden');
							};

							new A.LiveSearch(
								{
									data: data,
									hide: function(node) {
										node.hide();
									},
									input: searchInput,
									nodes: '#portal_add_content .lfr-portlet-item',
									show: function(node) {
										node.show();

										var categoryParent = node.ancestorsByClassName('lfr-content-category');
										var contentParent = node.ancestorsByClassName('lfr-add-content');

										if (categoryParent) {
											categoryParent.show();
										}

										if (contentParent) {
											contentParent.replaceClass('collapsed', 'expanded');
											contentParent.show();
										}
									}
								}
							);

							var body = A.getBody();

							var repaintTask = A.debounce(body.toggleClass, 10, body, CSS_REPAINT);

							new A.LiveSearch(
								{
									after: {
										search: function(event) {
											if (!this.query) {
												instance.categories.hide();
												instance.categoryContainers.replaceClass('expanded', 'collapsed');
												instance.categoryContainers.show();
											}

											if (this.query == '*') {
												instance.categories.show();
												instance.categoryContainers.replaceClass('collapsed', 'expanded');
												instance.categoryContainers.show();

												instance.portlets.show();
											}

											if (FORCE_REPAINT) {
												repaintTask();
											}
										}
									},
									data: data,
									hide: function(node) {
										var children = node.all('.lfr-content-category > li');

										var action = 'hide';

										if (children.some(isVisible)) {
											action = 'show';
										}

										node[action]();
									},
									input: searchInput,
									nodes: '#portal_add_content .lfr-add-content'
								}
							);
						}
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

						var portlets = A.all('[portletid=' + portletId + ']');

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
							var instanceable = (portlet.attr('instanceable') == 'true');
							var plid = portlet.attr('plid');
							var portletId = portlet.attr('portletId');
							var portletItemId = portlet.attr('portletItemId');
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

						Liferay.on('closePortlet', instance._onPortletClose, instance);

						instance._portletItems = instance._dialogBody.all('div.lfr-portlet-item');

						var portlets = instance._portletItems;

						var dialogBody = instance._dialogBody;

						dialogBody.delegate(
							'click',
							function(event) {
								var link = event.currentTarget;
								var portlet = link.ancestor('.lfr-portlet-item');

								instance._addPortlet(portlet);
							},
							'.lfr-portlet-item a'
						);

						var portletItem = null;
						var layoutOptions = Layout.options;

						var portletItemOptions = {
							delegateConfig: {
								container: '#portal_add_content',
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

						dialogBody.delegate(
							'click',
							function(event) {
								var heading = event.currentTarget.ancestor('.lfr-add-content');

								var category = heading.one('> .lfr-content-category');

								if (category) {
									category.toggle();
								}

								if (heading) {
									heading.toggleClass('collapsed').toggleClass('expanded');
								}
							},
							'.lfr-add-content > h2'
						);

						Util.focusFormField(instance._searchInput);
					},

					_onPortletClose: function(event) {
						var instance = this;

						var popup = A.one('#portal_add_content');

						if (popup) {
							var item = popup.one('.lfr-portlet-item[plid=' + event.plid + '][portletId=' + event.portletId + '][instanceable=false]');

							if (item && item.hasClass('lfr-portlet-used')) {
								var portletId = item.attr('portletId');

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
			requires: ['aui-live-search', 'dd', 'liferay-layout']
		}
	);

	Liferay.LayoutConfiguration = LayoutConfiguration;
})(AUI(), Liferay);