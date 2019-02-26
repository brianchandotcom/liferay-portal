AUI.add(
	'liferay-search-sort-util',
	function(A) {
		var SortUtil = {
			addURLParameter: function(key, value, parameterArray) {
				key = encodeURIComponent(key);
				value = encodeURIComponent(value);

				parameterArray[parameterArray.length] = [key, value].join('=');

				return parameterArray;
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

			setURLParameters: function(key, values, parameterArray) {
				var newParameters = SortUtil.removeURLParameters(key, parameterArray);

				values.forEach(
					function(item) {
						newParameters = SortUtil.addURLParameter(key, item, newParameters);
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

				var newParameters = SortUtil.setURLParameters(key, selections, parameterArray);

				search = newParameters.join('&');

				if (hasQuestionMark) {
					search = '?' + search;
				}

				return search;
			}
		};

		Liferay.namespace('Search').SortUtil = SortUtil;
	},
	'',
	{
		requires: []
	}
);