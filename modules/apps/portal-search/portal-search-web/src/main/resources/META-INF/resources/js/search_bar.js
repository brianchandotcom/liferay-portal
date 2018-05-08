AUI.add(
	'liferay-search-bar',
	function(A) {
		var SearchBar = function(form) {
			var instance = this;

			instance.form = form;

			var searchButton = instance.form.one('.search-bar-search-button');

			searchButton.on('click', A.bind(instance.search, instance));
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
						submitForm(instance.form);
					}
				}
			}
		);

		Liferay.namespace('Search').SearchBar = SearchBar;
	},
	'',
	{
		requires: []
	}
);