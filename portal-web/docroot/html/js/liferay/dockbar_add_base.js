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
					},

					_getNodes: function() {
						var instance = this;

						return instance._nodes;
					},

					_getSearchData: function() {
						var instance = this;

						return instance._searchData;
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