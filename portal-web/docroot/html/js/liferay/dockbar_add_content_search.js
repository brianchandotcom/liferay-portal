AUI.add(
	'liferay-dockbar-add-content-search',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var CSS_LFR_COLLAPSED = 'lfr-collapsed';

		var AddContentSearch = function() {
		};

		AddContentSearch.prototype = {
			initializer: function(config) {
				var instance = this;

				instance._searchApplication = instance.byId('searchApplication');

				instance._portlets = instance._addPanelContainer.all('.lfr-content-item');
				instance._categories = instance._addPanelContainer.all('.lfr-content-category');
				instance._categoryContainers = instance._addPanelContainer.all('.lfr-add-content');

				var results = [];

				instance._portlets.each(
					function(item, index, collection) {
						results.push(
							{
								node: item,
								search: item.attr('data-search')
							}
						);
					}
				);

				var addApplicationTabSearch = new AddApplicationTabSearch(
					{
						inputNode: instance._searchApplication,
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

				instance._bindUISearch();
			},

			_bindUISearch: function() {
				var instance = this;

				instance._addApplicationTabSearch.on('results', instance._refreshApplicationList, instance);

				instance._addContentTabSearch.after('query', instance._refreshContentList, instance);

				instance._searchContentInput.on('keydown', instance._onSearchInputKeyDown, instance);
				instance._searchApplication.on('keydown', instance._onSearchInputKeyDown, instance);
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
						function(item, index, collection) {
							if (!item.hasClass(CSS_LFR_COLLAPSED)) {
								instance._openedCategories.push(item);
							}
						}
					);
				}

				if (!query) {
					instance._categories.addClass(CSS_LFR_COLLAPSED);

					if (instance._openedCategories) {
						A.each(
							instance._openedCategories,
							function(item, index, collection) {
								item.removeClass(CSS_LFR_COLLAPSED);
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
						function(item, index, collection) {
							var node = item.raw.node;

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
		};

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