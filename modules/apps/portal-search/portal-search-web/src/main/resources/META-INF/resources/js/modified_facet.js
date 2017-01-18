AUI.add(
	'liferay-search-modified-facet',
	function(A) {
		var ModifiedFacetFilter = function(form, fromInputDatePicker, toInputDatePicker) {
			var instance = this;

			instance.form = form;
			instance.fromInputDatePicker = fromInputDatePicker;
			instance.toInputDatePicker = toInputDatePicker;

			var filterButton = instance.form.one('.modified-facet-custom-range-filter-button');

			filterButton.on('click', A.bind(instance.filter, instance));
		};

		A.mix(
			ModifiedFacetFilter.prototype,
			{
				filter: function() {
					var instance = this;

					var fromDate = instance.fromInputDatePicker.getDate();

					var toDate = instance.toInputDatePicker.getDate();

					var modifiedFromParameter = fromDate.toISOString().slice(0, 10);

					var modifiedToParameter = toDate.toISOString().slice(0, 10);

					var param = Liferay.Search.ModifiedFacetFilterUtil.getParameterName();
					var paramFrom = param + 'From';
					var paramTo = param + 'To';

					var parameterArray = document.location.search.substr(1).split('&');

					parameterArray = Liferay.Search.FacetUtil.removeURLParameters(param, parameterArray);

					parameterArray = Liferay.Search.FacetUtil.removeURLParameters(paramFrom, parameterArray);

					parameterArray = Liferay.Search.FacetUtil.removeURLParameters(paramTo, parameterArray);

					parameterArray = Liferay.Search.FacetUtil.addURLParameter(paramFrom, modifiedFromParameter, parameterArray);

					parameterArray = Liferay.Search.FacetUtil.addURLParameter(paramTo, modifiedToParameter, parameterArray);

					document.location.search = parameterArray.join('&');
				}
			}
		);

		Liferay.namespace('Search').ModifiedFacetFilter = ModifiedFacetFilter;

		var ModifiedFacetFilterUtil = {
			clearSelections: function(event) {
				var param = Liferay.Search.ModifiedFacetFilterUtil.getParameterName();
				var paramFrom = param + 'From';
				var paramTo = param + 'To';

				var parameterArray = document.location.search.substr(1).split('&');

				parameterArray = Liferay.Search.FacetUtil.removeURLParameters(param, parameterArray);

				parameterArray = Liferay.Search.FacetUtil.removeURLParameters(paramFrom, parameterArray);

				parameterArray = Liferay.Search.FacetUtil.removeURLParameters(paramTo, parameterArray);

				document.location.search = parameterArray.join('&');
			},

			getParameterName: function() {
				return 'modified';
			}
		};

		Liferay.namespace('Search').ModifiedFacetFilterUtil = ModifiedFacetFilterUtil;
	},
	'',
	{
		requires: ['liferay-search-facet-util']
	}
);