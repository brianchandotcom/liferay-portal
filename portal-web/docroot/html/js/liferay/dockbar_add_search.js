AUI.add(
	'liferay-dockbar-add-search',
	function(A) {
		var Lang = A.Lang;

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
						validator: Lang.isNumber,
						value: 0
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