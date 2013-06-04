AUI.add(
	'liferay-dockbar-add-base',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var AddBase = A.Component.create(
			{
				EXTENDS: A.Base,

				NAME: 'addbase',

				ATTRS: {
					inputNode: {
						value: null
					},

					nodeSelector: {
						value: ''
					},

					nodeList: {
						value: null
					},

					nodes: {
						value: null
					},

					searchData: {
						value: []
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

							instance.set('nodes', nodes);
							instance.set('searchData', searchData);
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