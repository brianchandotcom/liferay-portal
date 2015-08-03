AUI.add(
	'liferay-panel-search',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var Lang = A.Lang;

		var PanelSearch = A.Component.create(
			{
				ATTRS: {
					categorySelector: {
						validator: Lang.isString
					},

					inputNode: {
						setter: A.one
					},

					nodeContainerSelector: {
						validator: Lang.isString
					},

					nodeList: {
						setter: A.one
					},

					nodeSelector: {
						validator: Lang.isString
					}
				},

				EXTENDS: A.Base,

				NAME: 'panelsearch',

				prototype: {
					initializer: function(config) {
						var instance = this;

						var nodeList = instance.get('nodeList');

						instance._categories = nodeList.all(instance.get('categorySelector'));

						instance._togglerDelegate = Liferay.component(config.togglerId);

						var applicationSearch = new Liferay.SearchFilter(
							{
								inputNode: instance.get('inputNode'),
								nodeList: nodeList,
								nodeSelector: instance.get('nodeSelector')
							}
						);

						instance._nodes = applicationSearch._nodes;
						instance._search = applicationSearch;

						instance._bindUISearch();
					},

					_bindUISearch: function() {
						var instance = this;

						instance._eventHandles = instance._eventHandles || [];

						instance._eventHandles.push(
							instance._search.on('results', instance._updateList, instance),
							instance.get('inputNode').on('keydown', instance._onSearchInputKeyDown, instance)
						);
					},

					_onSearchInputKeyDown: function(event) {
						if (event.isKey('ENTER')) {
							event.halt();
						}
					},

					_setItemsVisibility: function(visible) {
						var instance = this;

						instance._nodes.each(
							function(item, index) {
								var contentItem = item;

								var nodeContainerSelector = instance.get('nodeContainerSelector');

								if (nodeContainerSelector) {
									contentItem = item.ancestor(nodeContainerSelector);
								}

								if (contentItem) {
									contentItem.toggle(visible);
								}
							}
						);
					},

					_updateList: function(event) {
						var instance = this;

						instance._togglerDelegate.createAll();

						var categories = instance._categories;

						var query = event.query;

						if (!instance._collapsedCategories) {
							instance._collapsedCategories = [];

							categories.each(
								function(item, index) {
									var header = item.one('.toggler-header');

									if (header && header.hasClass('toggler-header-collapsed')) {
										instance._collapsedCategories.push(item);
									}
								}
							);
						}

						if (!query) {
							categories.show();

							instance._setItemsVisibility(true);

							if (instance._collapsedCategories) {
								instance._collapsedCategories.forEach(
									function(item, index) {
										var categoryIndex = categories.indexOf(item);

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
								categories.show();

								instance._setItemsVisibility(true);
							}
							else {
								categories.hide();

								instance._setItemsVisibility(false);

								event.results.forEach(
									function(item, index) {
										var node = item.raw.node;

										var nodeContainerSelector = instance.get('nodeContainerSelector');

										if (nodeContainerSelector) {
											node = node.ancestor(nodeContainerSelector);
										}

										if (node) {
											node.show();
										}

										var contentParent = node.ancestorsByClassName(instance.get('categorySelector'));

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
				}
			}
		);

		Liferay.PanelSearch = PanelSearch;
	},
	'',
	{
		requires: ['aui-base', 'liferay-search-filter']
	}
);