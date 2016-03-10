AUI.add(
	'liferay-search-filter',
	function(A) {
		var Lang = A.Lang;

		var SearchImpl = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],

				EXTENDS: A.Base,

				NAME: 'searchimpl',

				prototype: {
					initializer: function() {
						var instance = this;

						this._bindUIACBase();
						this._syncUIACBase();
					}
				}
			}
		);

		var SearchFilter = A.Component.create(
			{
				ATTRS: {
					minQueryLength: {
						validator: Lang.isNumber,
						value: 0
					},

					nodeList: {
						setter: A.one
					},

					nodeSelector: {
						validator: Lang.isString
					},

					queryDelay: {
						validator: Lang.isNumber,
						value: 300
					},

					resultFilters: {
						setter: '_setResultFilters',
						value: 'phraseMatch'
					},

					resultTextLocator: {
						setter: '_setLocator',
						value: 'search'
					},

					searchDataLocator: {
						value: 'data-search'
					}
				},

				EXTENDS: SearchImpl,

				NAME: 'searchfilter',

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
								function(item, index) {
									searchData.push(
										{
											node: item,
											search: item.attr(searchDataLocator)
										}
									);
								}
							);

							instance.set('source', searchData);

							instance._nodes = nodes;
							instance._searchData = searchData;
						}
					}
				}
			}
		);

		Liferay.SearchFilter = SearchFilter;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'autocomplete-filters']
	}
);