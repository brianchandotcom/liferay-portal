AUI.add(
	'liferay-dockbar-add-content',
	function(A) {
		var Lang = A.Lang;
		var LayoutConfiguration = Liferay.LayoutConfiguration;
		var Dockbar = Liferay.Dockbar;

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

					_addPortlet: function(event) {
						event.halt();

						var item = event.currentTarget;

						var portletData = item.attr('data-class-pk') + ',' + item.attr('data-class-name');

						Liferay.Portlet.add(
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

						tooltip.get('boundingBox').one('.add-button-preview input').on(STR_CLICK, instance._addPortlet, instance);
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

						instance._entriesContainer.delegate(STR_CLICK, instance._addPortlet, '.content-shortcut');

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