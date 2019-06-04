AUI.add(
	'liferay-search-modified-facet',
	function(A) {
		var FacetUtil = Liferay.Search.FacetUtil;

		var ModifiedFacetFilter = function(
			form,
			fromInputDatePicker,
			toInputDatePicker
		) {
			var instance = this;

			instance.form = form;
			instance.fromInputDatePicker = fromInputDatePicker;
			instance.toInputDatePicker = toInputDatePicker;

			var filterButton = instance.form.one(
				'.modified-facet-custom-range-filter-button'
			);

			filterButton.on('click', A.bind(instance.filter, instance));
		};

		A.mix(ModifiedFacetFilter.prototype, {
			filter: function() {
				var instance = this;

				var fromDate = instance.fromInputDatePicker.getDate();

				var toDate = instance.toInputDatePicker.getDate();

				var modifiedFromParameter = ModifiedFacetFilterUtil.toLocaleDateStringFormatted(
					fromDate
				);

				var modifiedToParameter = ModifiedFacetFilterUtil.toLocaleDateStringFormatted(
					toDate
				);

				var param = ModifiedFacetFilterUtil.getParameterName();
				var paramFrom = param + 'From';
				var paramTo = param + 'To';

				var parameterArray = document.location.search
					.substr(1)
					.split('&');

				parameterArray = FacetUtil.removeURLParameters(
					param,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramTo,
					parameterArray
				);

				parameterArray = FacetUtil.addURLParameter(
					paramFrom,
					modifiedFromParameter,
					parameterArray
				);

				parameterArray = FacetUtil.addURLParameter(
					paramTo,
					modifiedToParameter,
					parameterArray
				);

				ModifiedFacetFilterUtil.submitSearch(parameterArray.join('&'));
			}
		});

		Liferay.namespace('Search').ModifiedFacetFilter = ModifiedFacetFilter;

		var ModifiedFacetFilterUtil = {
			clearSelections: function(event) {
				var param = this.getParameterName();
				var paramFrom = param + 'From';
				var paramTo = param + 'To';

				var parameterArray = document.location.search
					.substr(1)
					.split('&');

				parameterArray = FacetUtil.removeURLParameters(
					param,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramFrom,
					parameterArray
				);

				parameterArray = FacetUtil.removeURLParameters(
					paramTo,
					parameterArray
				);

				this.submitSearch(parameterArray.join('&'));
			},

			getParameterName: function() {
				return 'modified';
			},

			submitSearch: function(parameterString) {
				document.location.search = parameterString;
			},

			/**
			 * Formats a date to 'YYYY-MM-DD' format.
			 * @param {Date} date The date to format.
			 * @returns {String} The date string.
			 */
			toLocaleDateStringFormatted: function(date) {
				var localDate = new Date(date);

				localDate.setMinutes(
					date.getMinutes() - date.getTimezoneOffset()
				);

				return localDate.toISOString().split('T')[0];
			}
		};

		Liferay.namespace(
			'Search'
		).ModifiedFacetFilterUtil = ModifiedFacetFilterUtil;
	},
	'',
	{
		requires: ['liferay-search-facet-util']
	}
);
