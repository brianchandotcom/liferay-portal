/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {fetch} from 'frontend-js-web';

import createOdataFilter from './odata';

export function delay(duration) {
	return new Promise((resolve) => {
		setTimeout(() => resolve(), duration);
	});
}

export function getAcceptLanguageHeaderParam() {
	const browserLang = navigator.language ?? navigator.userLanguage;
	const themeLang = Liferay.ThemeDisplay.getLanguageId().replace('_', '-');

	return browserLang === themeLang
		? browserLang
		: `${browserLang}, ${themeLang};q=0.8`;
}

export function getData(apiURL, query) {
	const url = new URL(apiURL);

	if (query) {
		url.searchParams.append('search', query);
	}

	return fetch(url, {
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

export function liferayNavigate(url) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);
	}
	else {
		window.location.href = url;
	}
}

export function isValuesArrayChanged(prevValue = [], newValue = []) {
	if (prevValue.length !== newValue.length) {
		return true;
	}

	const prevValues = prevValue.map((el) => el.value || el).sort();
	const newValues = newValue.map((el) => el.value || el).sort();

	let changed = false;

	prevValues.forEach((element, i) => {
		if (element !== newValues[i]) {
			changed = true;
		}
	});

	return changed;
}

export function getValueFromItem(item, fieldName) {
	if (!fieldName) {
		return null;
	}
	if (Array.isArray(fieldName)) {
		return fieldName.reduce((acc, key) => {
			if (key === 'LANG') {
				return (
					acc[Liferay.ThemeDisplay.getLanguageId()] ||
					acc[Liferay.ThemeDisplay.getDefaultLanguageId()]
				);
			}

			return acc[key];
		}, item);
	}

	return item[fieldName];
}

export function getValueDetailsFromItem(item, fieldName) {
	if (!fieldName) {
		return null;
	}

	let rootPropertyName = fieldName;
	const valuePath = [];
	let navigatedValue = item;

	if (Array.isArray(fieldName)) {
		rootPropertyName = fieldName[0];

		fieldName.forEach((property) => {
			let formattedProperty = property;

			if (property === 'LANG') {
				formattedProperty = navigatedValue[
					Liferay.ThemeDisplay.getLanguageId()
				]
					? Liferay.ThemeDisplay.getLanguageId()
					: Liferay.ThemeDisplay.getDefaultLanguageId();
			}

			valuePath.push(formattedProperty);

			navigatedValue = navigatedValue[formattedProperty];
		});
	}
	else {
		valuePath.push(fieldName);
		navigatedValue = navigatedValue[fieldName];
	}

	return {
		rootPropertyName,
		value: navigatedValue,
		valuePath,
	};
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

export function executeAsyncAction(url, method = 'GET') {
	return fetch(url, {
		headers: {
			Accept: 'application/json',
			'Accept-Language': getAcceptLanguageHeaderParam(),
			'Content-Type': 'application/json',
		},
		method,
	});
}

export function formatActionURL(url, item) {
	const replacedURL = url.replace(new RegExp('{(.*?)}', 'mg'), (matched) =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('.')
		)
	);

	return replacedURL.replace(new RegExp('(%7B.*?%7D)', 'mg'), (matched) =>
		getValueFromItem(
			item,
			matched.substring(3, matched.length - 3).split('.')
		)
	);
}

export function getRandomId() {
	return Math.random().toString(36).substr(2, 9);
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

export function loadData(
	apiURL,
	currentURL,
	odataFiltersStrings,
	searchParam,
	delta,
	page = 1,
	sorting = []
) {
	const url = new URL(apiURL, themeDisplay.getPortalURL());
	const providedFilters = url.searchParams.get('filter');

	url.searchParams.delete('filter');
	url.searchParams.append('currentURL', currentURL);

	if (providedFilters || odataFiltersStrings.length) {
		url.searchParams.append(
			'filter',
			getFiltersString(odataFiltersStrings, providedFilters)
		);
	}

	url.searchParams.append('page', page);
	url.searchParams.append('pageSize', delta);

	if (searchParam) {
		url.searchParams.append('search', searchParam);
	}

	if (sorting.length) {
		url.searchParams.append(
			'sort',
			sorting.map((item) => `${item.key}:${item.direction}`).join(',')
		);
	}

	return executeAsyncAction(url, 'GET').then((response) => response.json());
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
