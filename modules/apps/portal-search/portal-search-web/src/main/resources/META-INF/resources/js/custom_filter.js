AUI.add(
	'liferay-search-custom-filter',
	function(A) {
		var FacetUtil = Liferay.Search.FacetUtil;

		var CustomFilter = function(form) {
			var instance = this;

			instance.form = form;

			instance.form.on('submit', A.bind(instance._onSubmit, instance));

			instance.filterValueInput = instance.form.one(
				'.custom-filter-value-input'
			);

			var applyButton = instance.form.one('.custom-filter-apply-button');

			applyButton.on('click', A.bind(instance._onClick, instance));
		};

		A.mix(CustomFilter.prototype, {
			getFilterValue: function() {
				var instance = this;

				var filterValue = instance.filterValueInput.val();

				return filterValue;
			},

			search: function() {
				var instance = this;

				var searchURL = instance.form.get('action');

				var queryString = instance.updateQueryString(
					document.location.search
				);

				document.location.href = searchURL + queryString;
			},

			updateQueryString: function(queryString) {
				var instance = this;

				var hasQuestionMark = false;

				if (queryString[0] === '?') {
					hasQuestionMark = true;
				}

				queryString = FacetUtil.updateQueryString(
					instance.filterValueInput.get('name'),
					[instance.getFilterValue()],
					queryString
				);

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
		});

		Liferay.namespace('Search').CustomFilter = CustomFilter;
	},
	'',
	{
		requires: ['liferay-search-facet-util']
	}
);
