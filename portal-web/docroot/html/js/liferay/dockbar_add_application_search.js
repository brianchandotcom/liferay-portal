AUI.add(
	'liferay-dockbar-add-application-search',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var AddSearch = Dockbar.AddSearch;

		var CSS_LFR_CONTENT_ITEM_SELECTOR = '.lfr-content-item';

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

				var namespace = config.namespace || "";

				var nodeList = instance.get('nodeList');

				instance._categories = nodeList.all(CSS_LFR_CONTENT_CATEGORY_SELECTOR);
				instance._categoryContainers = nodeList.all(CSS_LFR_CATEGORY_CONTAINER_SELECTOR);
				instance._togglerDelegate = Liferay.component(namespace + "addApplicationPanelContainer");

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

			_setItemsVisibility: function(visible) {
				var instance = this;

				var visibleFn = visible ? 'show' : 'hide';

				instance.get(STR_NODES).each(
					function(item, index, collection) {
						var contentItem = item.ancestor(CSS_LFR_CONTENT_ITEM_SELECTOR);

						if (contentItem) {
							contentItem[visibleFn].call(contentItem);
						}
					}
				);
			},

			_updateList: function(event) {
				var instance = this;

				instance._togglerDelegate.createAll();

				var query = event.query;

				if (!instance._collapsedCategories) {
					instance._collapsedCategories = [];

					instance._categories.each(
						function(item, index, collection) {
							var header = item.one('.toggler-header');

							if (header && header.hasClass('toggler-header-collapsed')) {
								instance._collapsedCategories.push(item);
							}
						}
					);
				}

				if (!query) {
					instance._categoryContainers.show();

					instance._setItemsVisibility(true);

					if (instance._collapsedCategories) {
						A.each(
							instance._collapsedCategories,
							function(item, index, collection) {
								var categoryIndex = instance._categories.indexOf(item);

								var togglerItems = instance._togglerDelegate.items;

								togglerItems[categoryIndex].collapse(
									{
										silent: true
									}
								);
							}
						);

						instance._collapsedCategories = null;
					}
				}
				else {
					if (query === '*') {
						instance._categoryContainers.show();

						instance._setItemsVisibility(true);
					}
					else {
						instance._categoryContainers.hide();

						instance._setItemsVisibility(false);

						A.each(
							event.results,
							function(item, index, collection) {
								var node = item.raw.node;

								node.ancestor(CSS_LFR_CONTENT_ITEM_SELECTOR).show();

								var contentParent = node.ancestorsByClassName(CSS_LFR_CATEGORY_CONTAINER);

								if (contentParent) {
									contentParent.show();
								}
							}
						);
					}

					instance._togglerDelegate.expandAll(
						{
							silent: true
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