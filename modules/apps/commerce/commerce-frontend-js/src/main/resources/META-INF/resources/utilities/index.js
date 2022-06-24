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

import {getProductMinQuantity} from './quantities';

export const fetchHeaders = new Headers({
	'Accept': 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
});

export const fetchParams = {
	headers: fetchHeaders,
};

export function getData(apiURL, query, page, pageSize) {
	const url = new URL(apiURL, Liferay.ThemeDisplay.getPortalURL());

	if (query) {
		url.searchParams.append('search', query);
	}

	if (page) {
		url.searchParams.append('page', page);
	}

	if (pageSize) {
		url.searchParams.append('pageSize', pageSize);
	}

	return fetch(url.pathname + url.search, {
		...fetchParams,
	}).then((data) => data.json());
}

export function liferayNavigate(url) {
	if (Liferay.SPA) {
		Liferay.SPA.app.navigate(url);
	}
	else {
		window.location.href = url;
	}
}

export function getObjectFromPath(path, value) {
	return path.reduceRight((item, key) => {
		const formattedKey =
			key === 'LANG' ? Liferay.ThemeDisplay.getLanguageId() : key;

		return {
			[formattedKey]: item,
		};
	}, value);
}

export function formatAutocompleteItem(id, idKey, label, labelKey) {
	const idObj = getObjectFromPath(Array.isArray(idKey) ? idKey : [idKey], id);
	const labelObj = getObjectFromPath(
		Array.isArray(labelKey) ? labelKey : [labelKey],
		label
	);

	return {
		...idObj,
		...labelObj,
	};
}

export function getValueFromItem(item, fieldName) {
	if (!fieldName || typeof item === 'string') {
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

export function formatActionUrl(url, item) {
	let regex = new RegExp('{(.*?)}', 'mg');

	let replacedUrl = url.replace(regex, (matched) =>
		getValueFromItem(
			item,
			matched.substring(1, matched.length - 1).split('.')
		)
	);

	regex = new RegExp('(%7B.*?%7D)', 'mg');

	replacedUrl = replacedUrl.replace(regex, (matched) =>
		getValueFromItem(
			item,
			matched.substring(3, matched.length - 3).split('.')
		)
	);

	return replacedUrl;
}

export function getRandomId() {
	return Math.random().toString(36).substr(2, 9);
}

export function sortByKey(items, keyName) {
	const arrangedItems = items.reduce(
		(data, item) => {
			if (typeof item[keyName] === 'number') {
				return {
					...data,
					sorted: {
						...data.sorted,
						[item[keyName]]: item,
					},
				};
			}
			else {
				return {
					...data,
					unsortable: data.unsortable.concat(item),
				};
			}
		},
		{
			sorted: {},
			unsortable: [],
		}
	);

	const sortedItems = [
		...Object.values(arrangedItems.sorted),
		...arrangedItems.unsortable,
	];

	return sortedItems;
}

export function isProductPurchasable(
	availability,
	productConfiguration,
	purchasable
) {
	if (purchasable === false) {
		return false;
	}

	if (productConfiguration.allowBackOrder) {
		return true;
	}

	if (
		availability.stockQuantity > getProductMinQuantity(productConfiguration)
	) {
		return true;
	}

	return false;
}
