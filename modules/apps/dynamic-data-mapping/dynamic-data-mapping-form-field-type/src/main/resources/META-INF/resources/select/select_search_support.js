AUI.add(
	'liferay-ddm-form-field-select-search-support',
	function(A) {
		var AArray = A.Array;

		var AHighlight = A.Highlight;

		var CSS_SEARCH_INPUT = 'drop-chosen-search';

		var Lang = A.Lang;

		var SelectFieldSearchSupport = function() {};

		SelectFieldSearchSupport.ATTRS = {
			keywords: {
				value: ''
			}
		};

		SelectFieldSearchSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.bindContainerEvent('input', A.debounce(instance._afterStartSearching, 400), '.' + CSS_SEARCH_INPUT),
					instance.after('closeList', A.bind('_afterCloseList', instance))
				);
			},

			clearFilter: function() {
				var instance = this;

				instance.set('keywords', '');
			},

			_afterCloseList: function() {
				var instance = this;

				if (instance._showSearch()) {
					instance.clearFilter();
				}
			},

			getOptions: function() {
				var instance = this;

				var keywords = instance.get('keywords');
				var options = instance.get('options');

				if (!keywords) {
					return options;
				}

				return AArray.filter(
					options,
					function(option) {
						return instance._containsString(option.label, keywords);
					}
				);
			},

			_afterStartSearching: function(event) {
				var instance = this;

				var target = event.target;

				instance.set('keywords', Lang.trim(target.get('value')).toLowerCase())

				instance.render();
			},

			_containsString: function(fullString, term) {
				return fullString.toLowerCase().indexOf(term) >= 0;
			},

			_getInputSearch: function() {
				var instance = this;

				return instance.get('container').one('.' + CSS_SEARCH_INPUT);
			}
		};

		Liferay.namespace('DDM.Field').SelectFieldSearchSupport = SelectFieldSearchSupport;
	},
	'',
	{
		requires: ['highlight', 'liferay-ddm-soy-template-util']
	}
);