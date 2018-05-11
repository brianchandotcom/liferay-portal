AUI.add(
	'liferay-search-bar',
	function(A) {
		var FacetUtil = Liferay.Search.FacetUtil;

		var SearchBar = function(form) {
			var instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));

			var searchButton = instance.form.one('.search-bar-search-button');

			searchButton.on('click', A.bind(instance._onClick, instance));
		};

		A.mix(
			SearchBar.prototype,
			{
				search: function() {
					var instance = this;

					var keywordsInput = instance.form.one('.search-bar-keywords-input');

					var keywords = keywordsInput.val();

					keywords = keywords.replace(/^\s+|\s+$/, '');

					if (keywords !== '') {
						var keywordsParameterName = keywordsInput.get('name');

						var searchURL = instance.form.get('action');

						var queryString = document.location.search;

						var hasQuestionMark = false;

						if (queryString[0] === '?') {
							hasQuestionMark = true;
						}

						queryString = FacetUtil.updateQueryString(keywordsParameterName, [keywords], queryString);

						var scopeSelect = instance.form.one('.search-bar-scope-select');

						if (scopeSelect) {
							var scope = scopeSelect.val();

							var scopeParameterName = scopeSelect.get('name');

							queryString = FacetUtil.updateQueryString(scopeParameterName, [scope], queryString);
						}

						if (!hasQuestionMark) {
							queryString = '?' + queryString;
						}

						document.location.href = searchURL + queryString;
					}
				},

				_onClick: function(event) {
					var instance = this;

					instance.search();
				},

				_onSubmit: function(event) {
					var instance = this;

					event.stopPropagation();

					instance.search();
				}
			}
		);

		Liferay.namespace('Search').SearchBar = SearchBar;
	},
	'',
	{
		requires: ['liferay-search-facet-util']
	}
);