AUI.add(
	'liferay-dockbar-add-application-search',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var AddSearch = Dockbar.AddSearch;

		var CSS_LFR_COLLAPSED = 'lfr-collapsed';

		var CSS_LFR_CATEGORY_CONTAINER = 'lfr-add-content';

		var CSS_LFR_CATEGORY_CONTAINER_SELECTOR = '.' + CSS_LFR_CATEGORY_CONTAINER;

		var CSS_LFR_CONTENT_CATEGORY = 'lfr-content-category';

		var CSS_LFR_CONTENT_CATEGORY_SELECTOR = '.' + CSS_LFR_CONTENT_CATEGORY;

		var STR_NODES = 'nodes';

		var AddApplicationSearch = function() {
		};

		AddApplicationSearch.prototype = {
			initializer: function(config) {
				var instance = this;

				var nodeList = instance.get('nodeList');

				instance._categories = nodeList.all(CSS_LFR_CONTENT_CATEGORY_SELECTOR);
				instance._categoryContainers = nodeList.all(CSS_LFR_CATEGORY_CONTAINER_SELECTOR);

				var applicationSearch = new AddSearch(
					{
						inputNode: instance.get('inputNode'),
						source: instance.get('searchData')
					}
				);

				instance._search = applicationSearch;

				instance._bindUISearch();
			},

			_bindUISearch: function() {
				var instance = this;

				instance._search.on('results', instance._updateList, instance);

				instance.get('inputNode').on('keydown', instance._onSearchInputKeyDown, instance);
			},

			_onSearchInputKeyDown: function(event) {
				if (event.isKey('ENTER')) {
					event.halt();
				}
			},

			_updateList: function(event) {
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

					instance.get(STR_NODES).show();
				}
				else if (query === '*') {
					instance._categories.removeClass(CSS_LFR_COLLAPSED);

					instance._categoryContainers.show();

					instance.get(STR_NODES).show();
				}
				else {
					instance._categoryContainers.hide();

					instance.get(STR_NODES).hide();

					A.each(
						event.results,
						function(item, index, collection) {
							var node = item.raw.node;

							node.show();

							var categoryParent = node.ancestorsByClassName(CSS_LFR_CONTENT_CATEGORY);

							if (categoryParent) {
								categoryParent.removeClass(CSS_LFR_COLLAPSED);
							}

							var contentParent = node.ancestorsByClassName(CSS_LFR_CATEGORY_CONTAINER);

							if (contentParent) {
								contentParent.show();
							}
						}
					);
				}
			}
		};

		Dockbar.AddApplicationSearch = AddApplicationSearch;
	},
	'',
	{
		requires: ['aui-base', 'liferay-dockbar', 'liferay-dockbar-add-search']
	}
);