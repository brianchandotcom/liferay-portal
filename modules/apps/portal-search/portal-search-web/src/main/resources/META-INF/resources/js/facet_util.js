AUI.add(
	'liferay-search-facet-util',
	function(A) {
		var FacetUtil = {
			addURLParameter: function(key, value, parameterArray) {
				key = encodeURI(key);
				value = encodeURI(value);

				parameterArray[parameterArray.length] = [key, value].join('=');

				return parameterArray;
			},

			changeSelection: function(event) {
				var form = event.currentTarget.form;

				if (!form) {
					return;
				}

				var selections = [];

				var formCheckboxes = $('#' + form.id + ' input.facet-term');

				formCheckboxes.each(
					function(index, value) {
						if (value.checked) {
							selections.push(value.getAttribute('data-term-id'));
						}
					}
				);

				FacetUtil.setURLParameters(form, selections);
			},

			clearSelections: function(event) {
				var form = $(event.currentTarget).closest('form')[0];

				if (!form) {
					return;
				}

				var selections = [];

				FacetUtil.setURLParameters(form, selections);
			},

			removeURLParameters: function(key, parameterArray) {
				key = encodeURI(key);

				var newParameters = [];

				AUI.$.each(
					parameterArray,
					function(index, item) {
						var itemSplit = item.split('=');

						if (itemSplit) {
							if (itemSplit[0] != key) {
								newParameters.push(item);
							}
						}
					}
				);

				return newParameters;
			},

			setURLParameters: function(form, selections) {
				var formParameterName = $('#' + form.id + ' input.facet-parameter-name');

				var key = formParameterName[0].value;

				var parameterArray = document.location.search.substr(1).split('&');

				var newParameters = FacetUtil.removeURLParameters(key, parameterArray);

				for (var i = 0; i < selections.length; i++) {
					newParameters = FacetUtil.addURLParameter(key, selections[i], newParameters);
				}

				document.location.search = newParameters.join('&');
			}
		};

		Liferay.namespace('Search').FacetUtil = FacetUtil;
	},
	'',
	{
		requires: []
	}
);