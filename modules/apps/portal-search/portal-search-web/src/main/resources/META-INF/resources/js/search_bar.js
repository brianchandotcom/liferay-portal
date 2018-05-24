AUI.add(
	'liferay-search-bar',
	function(A) {
		var FacetUtil = Liferay.Search.FacetUtil;

		var SearchBar = function(form) {
			var instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));

			var emptySearchInput = instance.form.one('.search-bar-empty-search-input');

			if (emptySearchInput.val() === 'true') {
				instance.emptySearchEnabled = true;
			}
			else {
				instance.emptySearchEnabled = false;
			}

			instance.keywordsInput = instance.form.one('.search-bar-keywords-input');

			instance.scopeSelect = instance.form.one('.search-bar-scope-select');

			var searchButton = instance.form.one('.search-bar-search-button');

			searchButton.on('click', A.bind(instance._onClick, instance));
		};

		A.mix(
			SearchBar.prototype,
			{
				getKeywords: function() {
					var instance = this;

					var keywords = instance.keywordsInput.val();

					return keywords.replace(/^\s+|\s+$/, '');
				},

				isSubmitEnabled: function() {
					var instance = this;

					return (instance.getKeywords() !== '') || instance.emptySearchEnabled;
				},

				search: function() {
					var instance = this;

					if (instance.isSubmitEnabled()) {
						var searchURL = instance.form.get('action');

						var queryString = instance.updateQueryString(document.location.search);

						document.location.href = searchURL + queryString;
					}
				},

				updateQueryString: function(queryString) {
					var instance = this;

					var hasQuestionMark = false;

					if (queryString[0] === '?') {
						hasQuestionMark = true;
					}

					queryString = FacetUtil.updateQueryString(
						instance.keywordsInput.get('name'),
						[instance.getKeywords()],
						queryString
					);

					if (instance.scopeSelect) {
						queryString = FacetUtil.updateQueryString(
							instance.scopeSelect.get('name'),
							[instance.scopeSelect.val()],
							queryString
						);
					}

					if (!hasQuestionMark) {
						queryString = '?' + queryString;
					}

					return queryString;
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