AUI.add(
	'liferay-dockbar-add-base',
	function(A) {
		var Lang = A.Lang;

		var Dockbar = Liferay.Dockbar;

		var AddBase = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'addbase',

				ATTRS: {
					focusItem: {
						setter: A.one
					},

					id: {
						validator: Lang.isString
					},

					inputNode: {
						setter: A.one
					},

					nodeSelector: {
						validator: Lang.isString
					},

					nodeList: {
						setter: A.one
					},

					nodes: {
						getter: '_getNodes',
						readOnly: true
					},

					searchData: {
						getter: '_getSearchData',
						readOnly: true
					},

					searchDataLocator: {
						value: 'data-search'
					},

					selected: {
						validator: Lang.isBoolean
					}
				},

				prototype: {
					initializer: function(config) {
						var instance = this;

						var nodeList = instance.get('nodeList');

						if (nodeList) {
							var nodeSelector = instance.get('nodeSelector');

							var nodes = nodeList.all(nodeSelector);

							var searchDataLocator = instance.get('searchDataLocator');

							var searchData = [];

							nodes.each(
								function(item, index, collection) {
									searchData.push(
										{
											node: item,
											search: item.attr(searchDataLocator)
										}
									);
								}
							);

							instance._nodes = nodes;
							instance._searchData = searchData;
						}

						if (instance.get('selected')) {
							var focusItem = instance.get('focusItem');

							if (focusItem) {
								focusItem.focus();
							}
						}

						instance._bindUIDABase();
					},

					destructor: function() {
						var instance = this;

						(new A.EventHandle(instance._eventHandles)).detach();
					},

					_bindUIDABase: function() {
						var instance = this;

						instance._eventHandles = [
							Liferay.after('showTab', instance._showTab, instance)
						];
					},

					_getNodes: function() {
						var instance = this;

						return instance._nodes;
					},

					_getSearchData: function() {
						var instance = this;

						return instance._searchData;
					},

					_showTab: function(event) {
						var instance = this;

						if (event.id === instance.get('id')) {
							var focusItem = instance.get('focusItem');

							if (focusItem) {
								focusItem.focus();
							}
						}
					}
				}
			}
		);

		Dockbar.AddBase = AddBase;
	},
	'',
	{
		requires: ['liferay-dockbar']
	}
);