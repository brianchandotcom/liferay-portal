AUI.add(
	'liferay-dockbar-add-content-search',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var CSS_LFR_COLLAPSED = 'lfr-collapsed';

		var AddContentSearch = A.Component.create(
			{
				AUGMENTS: [Liferay.PortletBase],

				EXTENDS: A.Base,

				NAME: 'addcontentsearch',

				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._config = config;

						instance._addContentForm = instance.byId('addContentForm');
						instance._addPanel = instance.byId('addPanelContainer');
						instance._entriesContainer = instance.byId('entriesContainer');
						instance._searchContentInput = instance.byId('searchContentInput');
						instance._searchApplicationInput = instance.byId('searchApplication');

						instance._portlets = instance._addPanel.all('.content-item');
						instance._categories = instance._addPanel.all('.lfr-content-category');
						instance._categoryContainers = instance._addPanel.all('.lfr-add-content');

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

						var addApplicationTabSearch = new AddApplicationTabSearch(
							{
								inputNode: instance._searchApplicationInput,
								minQueryLength: 0,
								queryDelay: 300,
								source: results,
								resultFilters: 'phraseMatch',
								resultTextLocator: 'search'
							}
						);

						instance._addApplicationTabSearch = addApplicationTabSearch;

						var addContentTabSearch = new AddContentTabSearch(
							{
								inputNode: instance._searchContentInput,
								minQueryLength: 0,
								queryDelay: 300
							}
						);

						instance._addContentTabSearch = addContentTabSearch;

						instance._bindUI();
					},

					_bindUI: function() {
						var instance = this;

						instance._addApplicationTabSearch.on(
							'results',
							function(event) {
								instance._restartSearch = true;

								instance._refreshApplicationList(event);
							}
						);

						instance._addContentTabSearch.after(
							'query',
							function(event) {
								instance._restartSearch = true;

								Liferay.fire('AddContent:refreshContentList');
							}
						);

						instance._searchContentInput.on('keydown', instance._onSearchInputKeyDown, instance);

						instance._searchApplicationInput.on('keydown', instance._onSearchInputKeyDown, instance);
					},

					_onSearchInputKeyDown: function(event) {
						if (event.isKey('ENTER')) {
							event.halt();
						}
					},

					_refreshApplicationList: function(event) {
						var instance = this;

						var query = event.query;

						if (!instance._openedCategories) {
							instance._openedCategories = [];

							instance._categories.each(
								function(node) {
									if (!node.hasClass(CSS_LFR_COLLAPSED)) {
										instance._openedCategories.push(node);
									}
								}
							);
						}

						if (!query) {
							instance._categories.addClass(CSS_LFR_COLLAPSED);

							if (instance._openedCategories) {
								A.each(
									instance._openedCategories,
									function(node) {
										node.removeClass(CSS_LFR_COLLAPSED);
									}
								);

								instance._openedCategories = null;
							}

							instance._categoryContainers.show();

							instance._portlets.show();
						}
						else if (query == '*') {
							instance._categories.removeClass(CSS_LFR_COLLAPSED);

							instance._categoryContainers.show();

							instance._portlets.show();
						}
						else {
							instance._categoryContainers.hide();

							instance._portlets.hide();

							A.each(
								event.results,
								function(result) {
									var node = result.raw.node;

									node.show();

									var categoryParent = node.ancestorsByClassName('lfr-content-category');

									if (categoryParent) {
										categoryParent.removeClass(CSS_LFR_COLLAPSED);
									}

									var contentParent = node.ancestorsByClassName('lfr-add-content');

									if (contentParent) {
										contentParent.show();
									}
								}
							);
						}
					}
				}
			}
		);

		var AddApplicationTabSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],
				EXTENDS: A.Base,
				NAME: 'addapplicationtabsearch',
				prototype: {
					initializer: function() {
						this._bindUIACBase();
						this._syncUIACBase();
					}
				}
			}
		);

		var AddContentTabSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],
				EXTENDS: A.Base,
				NAME: 'addcontenttabsearch',
				prototype: {
					initializer: function() {
						this._bindUIACBase();
						this._syncUIACBase();
					}
				}
			}
		);

		Dockbar.AddApplicationTabSearch = AddApplicationTabSearch;
		Dockbar.AddContentSearch = AddContentSearch;
		Dockbar.AddContentTabSearch = AddContentTabSearch;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'autocomplete-filters', 'liferay-dockbar']
	}
);