/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {DEFAULT_FETCH_HEADERS} from '../constants';
import getValueFromItem from './getValueFromItem';
import createOdataFilter from './odata';

export function getData(apiURL, query) {
	const url = new URL(apiURL);

	if (query) {
		url.searchParams.append('search', query);
	}

	return fetch(url, {
		headers: DEFAULT_FETCH_HEADERS,
		method: 'GET',
	}).then((data) => data.json());
}

export function getSchemaString(object, path) {
	if (!Array.isArray(path)) {
		return object[path];
	}
	else {
		return path.reduce((acc, path) => acc[path], object);
	}
}

export function isValuesArrayChanged(prevValue = [], newValue = []) {
	if (prevValue.length !== newValue.length) {
		return true;
	}

	const prevValues = prevValue
		.map((element) => element.value || element)
		.sort();
	const newValues = newValue
		.map((element) => element.value || element)
		.sort();

	let changed = false;

	prevValues.forEach((element, i) => {
		if (element !== newValues[i]) {
			changed = true;
		}
	});

	return changed;
}

export function formatItemChanges(itemChanges) {
	const formattedChanges = Object.values(itemChanges).reduce(
		(changes, {value, valuePath}) => {
			const nestedValue = valuePath.reduceRight((acc, item) => {
				return {[item]: acc};
			}, value);

			return {
				...changes,
				...nestedValue,
			};
		},
		{}
	);

	return formattedChanges;
}

export function createSortingString(values) {
	if (!values.length) {
		return null;
	}

	return values
		.map((value) => {
			return `${
				Array.isArray(value.fieldName)
					? value.fieldName[0]
					: value.fieldName
			}:${value.direction}`;
		})
		.join(',');
}

export function getFiltersString(odataFiltersStrings, providedFilters) {
	let filtersString = '';

	if (providedFilters) {
		filtersString += providedFilters;
	}

	if (providedFilters && odataFiltersStrings.length) {
		filtersString += ' and ';
	}

	if (odataFiltersStrings.length) {
		filtersString += createOdataFilter(odataFiltersStrings);
	}

	return filtersString;
}

export async function loadData(
	apiURL,
	currentURL,
	odataFiltersStrings,
	searchParam,
	delta,
	page = 1,
	sorts = [],
	queryString
) {
	const fullUrl = apiURL.startsWith('/')
		? themeDisplay.getPortalURL() + themeDisplay.getPathContext() + apiURL
		: apiURL;

	const url = new URL(fullUrl);

	if (currentURL) {
		url.searchParams.set('currentURL', currentURL);
	}

	const providedFilters = url.searchParams.get('filter');

	url.searchParams.delete('filter');

	if (providedFilters || odataFiltersStrings.length) {
		url.searchParams.append(
			'filter',
			getFiltersString(odataFiltersStrings, providedFilters)
		);
	}

	if (themeDisplay.isImpersonated()) {
		url.searchParams.append('doAsUserId', themeDisplay.getUserId());
	}

	url.searchParams.append('page', page);
	url.searchParams.append('pageSize', delta);

	if (searchParam) {
		url.searchParams.append('search', searchParam);
	}

	if (sorts.length) {
		url.searchParams.delete('sort');

		url.searchParams.append(
			'sort',
			sorts.map((item) => `${item.key}:${item.direction}`).join(',')
		);
	}

	if (Liferay.FeatureFlags['LPD-25230'] && queryString) {
		const queryStringArray = queryString.split('&');

		queryStringArray.forEach((parameter) => {
			const [key, value] = parameter.split('=');

			if (key === 'filter' && url.searchParams.get('filter')) {

				// Add new filters to the end of the existing filter.

				const existingFilters = url.searchParams.get('filter');

				url.searchParams.delete('filter');

				url.searchParams.append(
					'filter',
					existingFilters + ' and ' + value
				);
			}
			else if (key === 'sort' && url.searchParams.get('sort')) {

				// Add new sort values to the end of the existing sort.
				// Don't add new sort values for any already existing fields.

				const newSortParams = [];

				const existingSortArray = url.searchParams
					.get('sort')
					.split(',');

				const existingSortParamFields = existingSortArray.map(
					(sort) => sort.split(':')[0]
				);

				const queryStringSortValueArray = value.split(',');

				queryStringSortValueArray.forEach(
					(queryStringSortValueItem) => {
						if (
							!existingSortParamFields.includes(
								queryStringSortValueItem.split(':')[0]
							)
						) {
							newSortParams.push(queryStringSortValueItem);
						}
					}
				);

				url.searchParams.delete('sort');

				url.searchParams.append(
					'sort',
					existingSortArray.concat(newSortParams).join(',')
				);
			}
			else if (
				key === 'nestedFields' &&
				url.searchParams.get('nestedFields')
			) {

				// Merge nestedFields and don't add any fields already in the
				// existing nestedFields.

				const existingNestedFieldsArray = url.searchParams
					.get('nestedFields')
					.split(',');
				const queryStringNestedFieldsValueArray = value.split(',');

				const newNestedFields = [...existingNestedFieldsArray];

				queryStringNestedFieldsValueArray.forEach(
					(queryStringNestedFieldsItem) => {
						if (
							!existingNestedFieldsArray.includes(
								queryStringNestedFieldsItem
							)
						) {
							newNestedFields.push(queryStringNestedFieldsItem);
						}
					}
				);

				url.searchParams.delete('nestedFields');

				url.searchParams.append('nestedFields', newNestedFields);
			}
			else {
				url.searchParams.append(key, value);
			}
		});
	}

	const response = await fetch(url, {
		headers: DEFAULT_FETCH_HEADERS,
		method: 'GET',
	});
	const responseJSON = await response.json();

	return {
		data: responseJSON,
		ok: response.ok,
		status: response.status,
	};
}

export function getCurrentItemUpdates(
	items,
	itemsChanges,
	selectedItemsKey,
	itemKey,
	property,
	value,
	valuePath
) {
	const itemChanged = items.find(
		(item) => item[selectedItemsKey] === itemKey
	);

	const itemChanges = itemsChanges[itemKey];

	if (!itemChanges) {
		return {
			[property]: {
				value,
				valuePath,
			},
		};
	}

	if (itemChanged && getValueFromItem(itemChanged, valuePath) === value) {
		const filteredProperties = Object.entries(itemChanges).reduce(
			(properties, [propertyKey, propertyValue]) => {
				return propertyKey !== property
					? {...properties, [propertyKey]: propertyValue}
					: properties;
			},
			{}
		);

		return filteredProperties;
	}

	return {
		...itemChanges,
		[property]: {
			value,
			valuePath,
		},
	};
}
