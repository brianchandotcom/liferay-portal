AUI.add(
	'liferay-search-facet-util',
	function(A) {
		var FacetUtil = {
			addURLParameter: function(key, value, parameterArray) {
				key = encodeURIComponent(key);
				value = encodeURIComponent(value);

				parameterArray[parameterArray.length] = [key, value].join('=');

				return parameterArray;
			},

			changeSelection: function(event) {
				var form = event.currentTarget.form;

				if (!form) {
					return;
				}

				var selections = [];

				var formCheckboxes = document.querySelectorAll('#' + form.id + ' input.facet-term');

				Array.prototype.forEach.call(
					formCheckboxes,
					function(checkbox, index) {
						if (checkbox.checked) {
							selections.push(checkbox.getAttribute('data-term-id'));
						}
					}
				);

				FacetUtil.selectTerms(form, selections);
			},

			clearSelections: function(event) {
				var form = A.one(event.target).ancestor('form');

				if (!form) {
					return;
				}

				var selections = [];

				FacetUtil.selectTerms(form._node, selections);
			},

			removeURLParameters: function(key, parameterArray) {
				key = encodeURIComponent(key);

				var newParameters = parameterArray.filter(
					function(item) {
						var itemSplit = item.split('=');

						if (itemSplit && (itemSplit[0] === key)) {
							return false;
						}

						return true;
					}
				);

				return newParameters;
			},

			selectTerms: function(form, selections) {
				var formParameterName = document.querySelector('#' + form.id + ' input.facet-parameter-name');

				document.location.search = FacetUtil.updateQueryString(formParameterName.value, selections, document.location.search);
			},

			setURLParameter: function(url, name, value) {
				var parts = url.split('?');

				var address = parts[0];

				var queryString = parts[1];

				if (!queryString) {
					queryString = '';
				}

				queryString = Liferay.Search.FacetUtil.updateQueryString(name, [value], queryString);

				return address + '?' + queryString;
			},

			setURLParameters: function(key, values, parameterArray) {
				var newParameters = FacetUtil.removeURLParameters(key, parameterArray);

				values.forEach(
					function(item) {
						newParameters = FacetUtil.addURLParameter(key, item, newParameters);
					}
				);

				return newParameters;
			},

			updateQueryString: function(key, selections, queryString) {
				var search = queryString;

				var hasQuestionMark = false;

				if (search[0] === '?') {
					hasQuestionMark = true;
				}

				if (hasQuestionMark) {
					search = search.substr(1);
				}

				var parameterArray = search.split('&').filter(
					function(item) {
						return item.trim() !== '';
					}
				);

				var newParameters = FacetUtil.setURLParameters(key, selections, parameterArray);

				search = newParameters.join('&');

				if (hasQuestionMark) {
					search = '?' + search;
				}

				return search;
			}
		};

		Liferay.namespace('Search').FacetUtil = FacetUtil;
	},
	'',
	{
		requires: []
	}
);