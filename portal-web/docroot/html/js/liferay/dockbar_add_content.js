AUI.add(
	'liferay-dockbar-add-content',
	function(A) {
		var Lang = A.Lang;

		var LayoutConfiguration = Liferay.LayoutConfiguration;

		var Node = A.Node;

		var CLICK = 'click';

		var TPL_ERROR =
			'<div class="portlet-msg-error">' +
				Liferay.Language.get('failed-to-load-content') +
			'</div>';

		var isString = Lang.isString;

		var ParseContent = A.Plugin.ParseContent;

		var AddContent = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'addcontent',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._addContentForm = instance.byId('addContentForm');

						instance._styleButtonsList = instance.byId('styleButtons');

						instance._styleButtons = instance._styleButtonsList.all('.button');

						instance._styleButtons.on(CLICK, instance._onChangeDisplayStyle, instance);

						instance._numItems = instance.byId('numItems');

						instance._numItems.on('change', instance._onChangeNumItems, instance);

						instance._searchInput = instance.byId('searchInput');

						instance._createAddContentSearch();

						instance._entriesContainer = instance.byId('entriesContainer');

						instance._entriesContainer.delegate(CLICK, instance._addPortlet, '.content-shortcut');

						instance._createToolTip();

						LayoutConfiguration._loadContent();

						Liferay.on('showTab', instance._onShowTab, instance);
					},

					_addPortlet: function(event) {
						event.halt();

						var item = event.currentTarget;

						var portletId = item.attr('data-portlet-id');

						var portletData = item.attr('data-class-pk') + "," + item.attr('data-class-name');

						Liferay.Portlet.add(
							{
								portletData: portletData,
								portletId: portletId
							}
						);
					},

					_afterPreviewFailure: function(event) {
						var instance = this;

						instance._tooltip.set('bodyContent', TPL_ERROR);
					},

					_afterPreviewSuccess: function(event) {
						var instance = this;

						var data = event.currentTarget.get('responseData');

						var content = Node.create(data);

						var tooltip = instance._tooltip;

						tooltip.set('bodyContent', content);

						tooltip.get('boundingBox').one('.add-button-preview input').on(CLICK, instance._addPortlet, instance);
					},

					_afterSuccess: function(event) {
						var instance = this;

						var data = event.currentTarget.get('responseData');

						var content = Node.create(data);

						instance._entriesContainer.setContent(content);

						instance._createToolTip();
					},

					_createAddContentSearch: function() {
						var instance = this;

						var addContentSearch = new AddContentSearch(
							{
								inputNode: instance._searchInput,
								minQueryLength: 0,
								queryDelay: 300
							}
						);

						addContentSearch.after(
							'query',
							function(event) {
								instance._restartSearch = true;

								instance._refreshContentList(event);
							}
						);

						instance._searchInput.on('keydown', instance._onSearchInputKeyDown, instance);

						instance._addContentSearch = addContentSearch;
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
								hideOn: 'mouseleave',
								on: {
									show: function() {
										var currentNode = this.get('currentNode');

										if (instance._previousNode && (instance._previousNode != currentNode)) {
											currentNode.addClass('over');

											instance._previousNode.removeClass('over');
										}

										var classPK = currentNode.attr('data-class-pk');
										var className = currentNode.attr('data-class-name');

										var uri = instance._addContentForm.getAttribute('action');

										instance._ioPreview = A.io.request(
											uri,
											{
												after: {
													failure: A.bind(instance._afterPreviewFailure, instance),
													success: A.bind(instance._afterPreviewSuccess, instance)
												},
												data: {
													classPK: classPK,
													className: className,
													viewEntries: false,
													viewPreview: true
												}
											}
										);

										instance._previousNode = currentNode;
									},
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

					_onChangeDisplayStyle: function(event) {
						var instance = this;

						instance._styleButtons.removeClass('selected');

						event.currentTarget.addClass('selected');

						var styleButton = instance._styleButtonsList.one('.selected');

						var displayStyle = styleButton.attr('data-style');

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

					_refreshContentList: function(event) {
						var instance = this;

						var styleButton = instance._styleButtonsList.one('.selected');

						var displayStyle = styleButton.attr('data-style');

						var uri = instance._addContentForm.getAttribute('action');

						A.io.request(
							uri,
							{
								after: {
									success: A.bind(instance._afterSuccess, instance)
								},
								data: {
									delta: instance._numItems.val(),
									displayStyle: displayStyle,
									keywords: instance._searchInput.val(),
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

		Liferay.AddContent = AddContent;
	},
	'',
	{
		requires: ['aui-dialog', 'aui-io-request', 'aui-tooltip', 'autocomplete-base', 'event-mouseenter', 'liferay-layout-configuration', 'liferay-portlet-base']
	}
);