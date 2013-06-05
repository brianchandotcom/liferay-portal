AUI.add(
	'liferay-dockbar-add-search',
	function(A) {
		var Lang = A.Lang;

		var Dockbar = Liferay.Dockbar;

		// @eduardolundgren I've discussed this with Iliyan. It seems like we need this intermediate
		// class in order to be able to override the default values in AutocompleteBase. If we just
		// try to do it inside this create, the attributes on AutocompleteBase take over the ones
		// here. Maybe the logic "extend then augment" could be reversed. Please ping Iliyan to talk
		// about it.
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