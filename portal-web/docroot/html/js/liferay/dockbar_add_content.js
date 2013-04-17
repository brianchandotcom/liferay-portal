AUI.add(
	'liferay-dockbar-add-content',
	function(A) {
		var Lang = A.Lang;
		var Layout = Liferay.Layout;
		var LayoutConfiguration = Liferay.LayoutConfiguration;
		var Dockbar = Liferay.Dockbar;
		var Portlet = Liferay.Portlet;

		var STR_CLICK = 'click';

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

						instance._addApplicationPanel = instance.byId('addApplicationPanel');
						instance._addContentForm = instance.byId('addContentForm');
						instance._closePanel = instance.byId('closePanel');
						instance._entriesContainer = instance.byId('entriesContainer');
						instance._numItems = instance.byId('numItems');
						instance._searchContentInput = instance.byId('searchContentInput');
						instance._searchApplicationInput = instance.byId('searchApplication');
						instance._styleButtonsList = instance.byId('styleButtons');

						instance._portlets = instance._addApplicationPanel.all('.lfr-portlet-item');
						instance._categories = instance._addApplicationPanel.all('.lfr-content-category');
						instance._categoryContainers = instance._addApplicationPanel.all('.lfr-add-content');

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

						instance._bindUI();

						LayoutConfiguration._loadContent();
					},

					_addApplicationPortlet: function(event) {
						var instance = this;

						var link = event.currentTarget;
						var portlet = link.ancestor('.lfr-portlet-item');

						if (!portlet.hasClass('lfr-portlet-used')) {
							instance._addPortletFromApplicationPanel(portlet);
						}
					},

					_addPortletFromApplicationPanel: function(portlet, options) {
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
								portletId: portletId,
								portletItemId: portletItemId
							};

							Portlet.add(portletOptions);
						}
					},

					_addPortletFromContentPanel: function(event) {
						event.halt();

						var item = event.currentTarget;

						var portletData = item.attr('data-class-pk') + ',' + item.attr('data-class-name');

						Portlet.add(
							{
								portletData: portletData,
								portletId: item.attr('data-portlet-id')
							}
						);
					},

					_afterPreviewFailure: function(event) {
						var instance = this;

						var errorMsg = Lang.sub(
							TPL_ERROR,
							[Liferay.Language.get('unable-to-load-content')]
						);

						instance._tooltip.set('bodyContent', errorMsg);
					},

					_afterPreviewSuccess: function(event) {
						var instance = this;

						var tooltip = instance._tooltip;

						tooltip.set('bodyContent', event.currentTarget.get('responseData'));

						tooltip.get('boundingBox').one('.add-button-preview input').on(STR_CLICK, instance._addPortletFromContentPanel, instance);
					},

					_afterSuccess: function(event) {
						var instance = this;

						instance._entriesContainer.setContent(event.currentTarget.get('responseData'));

						instance._createToolTip();
					},

					_bindUI: function() {
						var instance = this;

						instance._closePanel.on(STR_CLICK, Dockbar.loadPanel, Dockbar);

						instance._numItems.on('change', instance._onChangeNumItems, instance);

						instance._addApplicationPanel.delegate(STR_CLICK, instance._addApplicationPortlet, '.lfr-portlet-item a', instance);

						instance._entriesContainer.delegate(STR_CLICK, instance._addPortletFromContentPanel, '.content-shortcut');

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

						instance._searchContentInput.on('keydown', instance._onSearchInputKeyDown, instance);

						instance._searchApplicationInput.on('keydown', instance._onSearchInputKeyDown, instance);

						Liferay.on('closePortlet', instance._onPortletClose, instance);

						Liferay.on('showTab', instance._onShowTab, instance);
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

										currentNode.removeClass('over');
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

					_getIOPreview: function() {
						var instance = this;

						var ioPreview = instance._ioPreview;

						if (!ioPreview) {
							ioPreview = A.io.request(
								instance._addContentForm.getAttribute('action'),
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

						tooltip.set('bodyContent', TPL_LOADING);

						var currentNode = tooltip.get('currentNode');

						if (instance._previousNode && (instance._previousNode != currentNode)) {
							currentNode.addClass('over');

							instance._previousNode.removeClass('over');
						}

						instance._previousNode = currentNode;

						instance._loadPreviewTask(currentNode.attr('data-class-name'), currentNode.attr('data-class-pk'));
					},

					_onPortletClose: function(event) {
						var instance = this;

						var item = instance._addApplicationPanel.one('.lfr-portlet-item[data-plid=' + event.plid + '][data-portlet-id=' + event.portletId + '][data-instanceable=false]');

						if (item && item.hasClass('lfr-portlet-used')) {
							var portletId = item.attr('data-portlet-id');

							instance._enablePortletEntry(portletId);
						}
					},

					_refreshApplicationList: function(event) {
						var instance = this;

						var query = event.query;

						if (!query) {
							instance._categories.addClass('lfr-collapsed');

							instance._categoryContainers.show();
							instance._portlets.show();
						}
						else if (query == '*') {
							instance._categories.removeClass('lfr-collapsed');

							instance._categoryContainers.show();
							instance._portlets.show();
						}
						else {
							instance._categories.addClass('lfr-collapsed');

							instance._categoryContainers.hide();
							instance._portlets.hide();

							A.each(
								event.results,
								function(result) {
									var node = result.raw.node;

									node.show();

									var categoryParent = node.ancestorsByClassName('lfr-content-category');

									if (categoryParent) {
										categoryParent.removeClass('lfr-collapsed');
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
							instance._addContentForm.getAttribute('action'),
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

		Dockbar.AddApplicationSearch = AddApplicationSearch;
		Dockbar.AddContent = AddContent;
		Dockbar.AddContentSearch = AddContentSearch;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-request', 'aui-tooltip', 'autocomplete-base', 'autocomplete-filters', 'event-mouseenter', 'liferay-dockbar', 'liferay-layout-configuration', 'liferay-portlet-base']
	}
);