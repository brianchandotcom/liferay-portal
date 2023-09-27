/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

AUI.add(
	'liferay-search-sort-util',
	() => {
		const SortUtil = {
			addURLParameter(key, value, parameterArray) {
				key = encodeURIComponent(key);
				value = encodeURIComponent(value);

				parameterArray[parameterArray.length] = [key, value].join('=');

				return parameterArray;
			},

			removeURLParameters(key, parameterArray) {
				key = encodeURIComponent(key);

				const newParameters = parameterArray.filter((item) => {
					const itemSplit = item.split('=');

					if (itemSplit && itemSplit[0] === key) {
						return false;
					}

					return true;
				});

				return newParameters;
			},

			setURLParameters(key, values, parameterArray) {
				let newParameters = SortUtil.removeURLParameters(
					key,
					parameterArray
				);

				values.forEach((item) => {
					newParameters = SortUtil.addURLParameter(
						key,
						item,
						newParameters
					);
				});

				return newParameters;
			},

			updateQueryString(key, selections, queryString) {
				let search = queryString;

				let hasQuestionMark = false;

				if (search[0] === '?') {
					hasQuestionMark = true;
				}

				if (hasQuestionMark) {
					search = search.substr(1);
				}

				const parameterArray = search.split('&').filter((item) => {
					return item.trim() !== '';
				});

				const newParameters = SortUtil.setURLParameters(
					key,
					selections,
					parameterArray
				);

				search = newParameters.join('&');

				if (hasQuestionMark) {
					search = '?' + search;
				}

				return search;
			},
		};

		Liferay.namespace('Search').SortUtil = SortUtil;
	},
	'',
	{
		requires: [],
	}
);
