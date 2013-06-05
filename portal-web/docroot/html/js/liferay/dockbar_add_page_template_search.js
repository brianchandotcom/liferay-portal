AUI.add(
	'liferay-dockbar-add-page-template-search',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var RESULTS = 'results';

		var AddPageTemplateSearch = A.Component.create(
			{
				AUGMENTS: [A.AutoCompleteBase],
				EXTENDS: A.Base,
				NAME: 'addpagetemplatesearch',
				prototype: {
					initializer: function(config) {
						var instance = this;

						instance._entries = config.entries;

						instance._bindUI();

						instance._syncUIACBase();
					},

					_bindUI: function() {
						var instance = this;

						instance._bindUIACBase();

						instance.on(RESULTS, instance._refreshEntriesList, instance);
					},

					_refreshEntriesList: function(event) {
						var instance = this;

						var query = event.query;

						instance._entries.toggle(!query || query == '*');

						if (query) {
							A.Array.each(
								event.results,
								function(item, index, collection) {
									item.raw.node.show();
								}
							);
						}
					}
				}
			}
		);

		Dockbar.AddPageTemplateSearch = AddPageTemplateSearch;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'autocomplete-filters', 'liferay-dockbar']
	}
);