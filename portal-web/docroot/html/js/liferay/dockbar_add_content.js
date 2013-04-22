AUI.add(
	'liferay-dockbar-add-content',
	function(A) {
		var DDM = A.DD.DDM;
		var Lang = A.Lang;
		var Layout = Liferay.Layout;
		var LayoutConfiguration = Liferay.LayoutConfiguration;
		var Dockbar = Liferay.Dockbar;
		var Portlet = Liferay.Portlet;

		var BODY_CONTENT = 'bodyContent';

		var CSS_CLASS_OVER = 'over';

		var NODE = 'node'

		var RESPONSE_DATA = 'responseData';

		var STR_ACTION = 'action';

		var STR_CLICK = 'click';

		var STR_LFR_COLLAPSED = 'lfr-collapsed';

		var STR_LFR_PORTLET_USED = 'lfr-portlet-used';

		var TPL_ERROR = '<div class="portlet-msg-error">{0}</div>';

		var TPL_LOADING = '<div class="loading-animation" />';

		var AddApplicationSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],
				EXTENDS: A.Base,
				NAME: 'addapplicationsearch',
				prototype: {
					initializer: function() {
						this._bindUIACBase();
						this._syncUIACBase();
					}
				}
			}
		);

		var AddContent = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'addcontent',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._addPanel = instance.byId('addApplicationAndContentPanel');
						instance._addApplicationForm = instance.byId('addApplicationForm');
						instance._addContentForm = instance.byId('addContentForm');
						instance._closePanel = instance.byId('closePanel');
						instance._entriesContainer = instance.byId('entriesContainer');
						instance._numItems = instance.byId('numItems');
						instance._searchContentInput = instance.byId('searchContentInput');
						instance._searchApplicationInput = instance.byId('searchApplication');
						instance._styleButtonsList = instance.byId('styleButtons');

						instance._portlets = instance._addPanel.all('.content-item');
						instance._categories = instance._addPanel.all('.lfr-content-category');
						instance._categoryContainers = instance._addPanel.all('.lfr-add-content');

						instance._styleButtons = instance._styleButtonsList.all('.button');

						var results = [];

						instance._portlets.each(
							function(node) {
								results.push(
									{
										node: node,
										search: node.attr('data-search')
									}
								);
							}
						);

						var addApplicationSearch = new AddApplicationSearch(
							{
								inputNode: instance._searchApplicationInput,
								minQueryLength: 0,
								queryDelay: 300,
								source: results,
								resultFilters: 'phraseMatch',
								resultTextLocator: 'search'
							}
						);

						instance._addApplicationSearch = addApplicationSearch;

						var addContentSearch = new AddContentSearch(
							{
								inputNode: instance._searchContentInput,
								minQueryLength: 0,
								queryDelay: 300
							}
						);

						instance._addContentSearch = addContentSearch;

						instance._createToolTip();

						instance._loadPreviewTask = A.debounce('_loadPreviewFn', 200, instance);

						Liferay.on(
							'AddContent:addPortlet',
							function(event) {
								var portlet = event.node;
								var options = event.options;

								instance._addPortlet(portlet, options);
							}
						);

						instance._bindUI();
					},

					_addApplication: function(event) {
						var instance = this;

						var portlet = event.currentTarget;

						instance._addPortlet(portlet);
					},

					_addPortlet: function(portlet, options) {
						var instance = this;

						var portletMetaData = instance._getPortletMetaData(portlet);

						if (!portletMetaData.portletUsed) {
							var plid = portletMetaData.plid;
							var portletData = portletMetaData.portletData;
							var portletId = portletMetaData.portletId;
							var portletItemId = portletMetaData.portletItemId;
							var isInstanceable = portletMetaData.instanceable;

							if (!isInstanceable) {
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
								portletData: portletData,
								portletId: portletId,
								portletItemId: portletItemId
							};

							Portlet.add(portletOptions);
						}
					},

					_afterPreviewFailure: function(event) {
						var instance = this;

						var errorMsg = Lang.sub(
							TPL_ERROR,
							[Liferay.Language.get('unable-to-load-content')]
						);

						instance._tooltip.set(BODY_CONTENT, errorMsg);
					},

					_afterPreviewSuccess: function(event) {
						var instance = this;

						var tooltip = instance._tooltip;

						tooltip.set(BODY_CONTENT, event.currentTarget.get(RESPONSE_DATA));

						tooltip.get('boundingBox').one('.add-button-preview input').on(STR_CLICK, instance._addApplication, instance);
					},

					_afterSuccess: function(event) {
						var instance = this;

						instance._entriesContainer.setContent(event.currentTarget.get(RESPONSE_DATA));

						instance._createToolTip();
					},

					_bindUI: function() {
						var instance = this;

						instance._closePanel.on(STR_CLICK, Dockbar.loadPanel, Dockbar);

						instance._numItems.on('change', instance._onChangeNumItems, instance);

						instance._addPanel.delegate(STR_CLICK, instance._addApplication, '.add-content-item', instance);

						instance._styleButtonsList.delegate(STR_CLICK, instance._onChangeDisplayStyle, '.button', instance);

						instance._addApplicationSearch.on(
							'results',
							function(event) {
								instance._restartSearch = true;

								instance._refreshApplicationList(event);
							}
						);

						instance._addContentSearch.after(
							'query',
							function(event) {
								instance._restartSearch = true;

								instance._refreshContentList(event);
							}
						);

						var layoutOptions = Layout.options;

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
								return dropNode.one(layoutOptions.dropContainer);
							},
							on: Layout.DEFAULT_LAYOUT_OPTIONS.on
						};

						if (themeDisplay.isFreeformLayout()) {
							instance._portletItem = new Dockbar.FreeFormPortletItem(portletItemOptions);
						}
						else {
							instance._portletItem = new Dockbar.PortletItem(portletItemOptions);
						}

						instance._searchContentInput.on('keydown', instance._onSearchInputKeyDown, instance);

						instance._searchApplicationInput.on('keydown', instance._onSearchInputKeyDown, instance);

						Liferay.on('closePortlet', instance._onPortletClose, instance);

						Liferay.on('showTab', instance._onShowTab, instance);

						Liferay.fire('initLayout');
					},

					_createToolTip: function() {
						var instance = this;

						if (instance._tooltip) {
							instance._tooltip.destroy();
						}

						instance._tooltip = new A.Tooltip(
							{
								align: {
									points: ['lc', 'rc']
								},
								cssClass: 'lfr-content-preview-popup',
								constrain: true,
								hideOn: 'mouseleave',
								on: {
									show: A.bind('_onTooltipShow', instance),
									hide: function() {
										var currentNode = this.get('currentNode');

										currentNode.removeClass(CSS_CLASS_OVER);
									}
								},
								showArrow: false,
								showOn: 'mouseenter',
								trigger: '.has-preview'
							}
						).render();
					},

					_disablePortletEntry: function(portletId) {
						var instance = this;

						instance._eachPortletEntry(
							portletId,
							function(item, index) {
								item.addClass(STR_LFR_PORTLET_USED);
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
								item.removeClass(STR_LFR_PORTLET_USED);
							}
						);
					},

					_getIOPreview: function() {
						var instance = this;

						var ioPreview = instance._ioPreview;

						if (!ioPreview) {
							ioPreview = A.io.request(
								instance._addContentForm.getAttribute(STR_ACTION),
								{
									after: {
										failure: A.bind('_afterPreviewFailure', instance),
										success: A.bind('_afterPreviewSuccess', instance)
									},
									autoLoad: false,
									data: {
										viewEntries: false,
										viewPreview: true
									}
								}
							);

							instance._ioPreview = ioPreview;
						}

						return ioPreview;
					},

					_getPortletMetaData: function(portlet) {
						var instance = this;

						var portletMetaData = portlet._LFR_portletMetaData;

						if (!portletMetaData) {
							var classPK = portlet.attr('data-class-pk');
							var className = portlet.attr('data-class-name');

							var instanceable = (portlet.attr('data-instanceable') == 'true');
							var plid = portlet.attr('data-plid');

							var portletData = '';

							if ((className != '') && (classPK != '')) {
								portletData = classPK + ',' + className;
							}

							var portletId = portlet.attr('data-portlet-id');
							var portletItemId = portlet.attr('data-portlet-item-id');
							var portletUsed = portlet.hasClass(STR_LFR_PORTLET_USED);

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

					_loadPreviewFn: function(className, classPK) {
						var instance = this;

						var ioPreview = instance._getIOPreview();

						ioPreview.stop();

						ioPreview.set('data.classPK', classPK);
						ioPreview.set('data.className', className);

						ioPreview.start();
					},

					_onChangeDisplayStyle: function(event) {
						var instance = this;

						var currentTarget = event.currentTarget;

						currentTarget.radioClass('selected');

						var displayStyle = currentTarget.attr('data-style');

						Liferay.Store('liferay_addpanel_displaystyle', displayStyle);

						instance._refreshContentList(event);
					},

					_onChangeNumItems: function(event) {
						var instance = this;

						Liferay.Store('liferay_addpanel_numitems', instance._numItems.val());

						instance._refreshContentList(event);
					},

					_onSearchInputKeyDown: function(event) {
						if (event.isKey('ENTER')) {
							event.halt();
						}
					},

					_onShowTab: function(event) {
						var instance = this;

						if (event.namespace.indexOf(instance.get('namespace')) === 0) {
							var index = event.selectedIndex;

							Liferay.Store('liferay_addpanel_tab', event.names[index]);
						}
					},

					_onTooltipShow: function(event) {
						var instance = this;

						var tooltip = instance._tooltip;

						tooltip.set(BODY_CONTENT, TPL_LOADING);

						var currentNode = tooltip.get('currentNode');

						if (instance._previousNode && (instance._previousNode != currentNode)) {
							currentNode.addClass(CSS_CLASS_OVER);

							instance._previousNode.removeClass(CSS_CLASS_OVER);
						}

						instance._previousNode = currentNode;

						instance._loadPreviewTask(currentNode.attr('data-class-name'), currentNode.attr('data-class-pk'));
					},

					_onPortletClose: function(event) {
						var instance = this;

						var item = instance._addPanel.one('.lfr-portlet-item[data-plid=' + event.plid + '][data-portlet-id=' + event.portletId + '][data-instanceable=false]');

						if (item && item.hasClass(STR_LFR_PORTLET_USED)) {
							var portletId = item.attr('data-portlet-id');

							instance._enablePortletEntry(portletId);
						}
					},

					_refreshApplicationList: function(event) {
						var instance = this;

						var query = event.query;

						if (!query) {
							instance._categories.addClass(STR_LFR_COLLAPSED);

							instance._categoryContainers.show();
							instance._portlets.show();
						}
						else if (query == '*') {
							instance._categories.removeClass(STR_LFR_COLLAPSED);

							instance._categoryContainers.show();
							instance._portlets.show();
						}
						else {
							instance._categories.addClass(STR_LFR_COLLAPSED);

							instance._categoryContainers.hide();
							instance._portlets.hide();

							A.each(
								event.results,
								function(result) {
									var node = result.raw.node;

									node.show();

									var categoryParent = node.ancestorsByClassName('lfr-content-category');

									if (categoryParent) {
										categoryParent.removeClass(STR_LFR_COLLAPSED);
									}

									var contentParent = node.ancestorsByClassName('lfr-add-content');

									if (contentParent) {
										contentParent.show();
									}
								}
							);
						}
					},

					_refreshContentList: function(event) {
						var instance = this;

						var styleButton = instance._styleButtonsList.one('.selected');

						var displayStyle = styleButton.attr('data-style');

						A.io.request(
							instance._addContentForm.getAttribute(STR_ACTION),
							{
								after: {
									success: A.bind('_afterSuccess', instance)
								},
								data: {
									delta: instance._numItems.val(),
									displayStyle: displayStyle,
									keywords: instance._searchContentInput.val(),
									viewEntries: true,
									viewPreview: false
								}
							}
						);
					}
				}
			}
		);

		var AddContentSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],
				EXTENDS: A.Base,
				NAME: 'addcontentsearch',
				prototype: {
					initializer: function() {
						this._bindUIACBase();
						this._syncUIACBase();
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

						instance.appendNode = DDM.activeDrag.get(NODE).clone();

						return instance.appendNode;
					},

					_onDragEnd: function(event) {
						var instance = this;

						PortletItem.superclass._onDragEnd.apply(this, arguments);

						var appendNode = instance.appendNode;

						if (appendNode && appendNode.inDoc()) {
							var portletNode = event.target.get(NODE);

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
							var dropNodeId = drop.get(NODE).get('id');

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
							var dropNode = activeDrop.get(NODE);

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

						var node = DDM.activeDrag.get(NODE);
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

		Dockbar.AddApplicationSearch = AddApplicationSearch;
		Dockbar.AddContent = AddContent;
		Dockbar.AddContentSearch = AddContentSearch;
		Dockbar.FreeFormPortletItem = FreeFormPortletItem;
		Dockbar.PortletItem = PortletItem;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-request', 'aui-tooltip', 'autocomplete-base', 'autocomplete-filters', 'dd', 'event-mouseenter', 'liferay-dockbar', 'liferay-layout', 'liferay-portlet-base']
	}
);