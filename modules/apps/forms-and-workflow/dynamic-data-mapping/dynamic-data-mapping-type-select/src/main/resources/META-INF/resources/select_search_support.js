AUI.add(
	'liferay-ddm-form-field-select-search-support',
	function(A) {
		var AArray = A.Array;

		var AHighlight = A.Highlight;

		var CSS_SEARCH_INPUT = 'drop-chosen-search';

		var Lang = A.Lang;

		var SoyTemplateUtil = Liferay.DDM.SoyTemplateUtil;

		var SelectFieldSearchSupport = function() {};

		SelectFieldSearchSupport.prototype = {
			initializer: function() {
				var instance = this;

				instance._eventHandlers.push(
					instance.bindContainerEvent('input', A.debounce(instance._afterStartSearching, 400), '.' + CSS_SEARCH_INPUT),
					instance.after('closeList', A.bind('_afterCloseList', instance)),
					instance.after(A.bind('_afterOpenList', instance), instance, 'openList')
				);
			},

			clearFilter: function() {
				var instance = this;

				var searchInputNode = instance._getInputSearch();

				var options = instance.get('options');

				instance._renderList(options);

				searchInputNode.val('');
			},

			_afterCloseList: function() {
				var instance = this;

				instance.clearFilter();
			},

			_afterOpenList: function() {
				var instance = this;

				var searchInputNode = instance._getInputSearch();

				searchInputNode.focus();
			},

			_afterStartSearching: function(event) {
				var instance = this;

				var target = event.target;

				var term = Lang.trim(target.get('value')).toLowerCase();

				var filteredOptions = AArray.filter(
					instance.get('options'),
					function(option) {
						return instance._containsString(option.label, term);
					}
				);

				instance._renderList(filteredOptions);

				instance._visitDOMListItems(
					A.bind(instance._applyFilterStyleOnItem, instance, term)
				);
			},

			_applyFilterStyleOnItem: function(term, item) {
				var content = item.one('a').getContent();

				item.one('a').setContent(AHighlight.all(content, term));
			},

			_containsString: function(fullString, term) {
				return fullString.toLowerCase().indexOf(term) >= 0;
			},

			_getInputSearch: function() {
				var instance = this;

				return instance.get('container').one('.' + CSS_SEARCH_INPUT);
			},

			_getTemplate: function(context) {
				var instance = this;

				var renderer = SoyTemplateUtil.getTemplateRenderer('DDMSelect.select_options');

				var container = document.createDocumentFragment();

				new renderer(context, container);

				return container.firstChild.firstElementChild.innerHTML;
			},

			_renderList: function(options) {
				var instance = this;

				var template = instance._getTemplate(
					{
						multiple: instance.get('multiple'),
						options: options,
						strings: instance.get('strings'),
						value: instance.getValue()
					}
				);

				var optionsList = instance.get('container').one('.inline-scroller');

				if (optionsList) {
					optionsList.setHTML(template);
				}
			},

			_visitDOMListItems: function(callBack) {
				var instance = this;

				instance.get('container').all('li.select-option-item.unfixed').each(callBack);

				return instance;
			}
		};

		Liferay.namespace('DDM.Field').SelectFieldSearchSupport = SelectFieldSearchSupport;
	},
	'',
	{
		requires: ['highlight', 'liferay-ddm-soy-template-util']
	}
);