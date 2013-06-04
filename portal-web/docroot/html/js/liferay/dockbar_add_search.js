AUI.add(
	'liferay-dockbar-add-search',
	function(A) {
		var Dockbar = Liferay.Dockbar;

		var SearchImpl = A.Component.create (
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

		var AddSearch = A.Component.create(
			{
				EXTENDS: SearchImpl,

				NAME: 'addsearch',

				ATTRS: {
					minQueryLength: {
						value: 0
					},

					queryDelay: {
						value: 300
					},

					resultFilters: {
						value: 'phraseMatch'
					},

					resultTextLocator: {
						value: 'search'
					}
				}
			}
		);

		Dockbar.AddSearch = AddSearch;
	},
	'',
	{
		requires: ['aui-base', 'autocomplete-base', 'autocomplete-filters', 'liferay-dockbar']
	}
);