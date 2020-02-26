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

const HEADERS = {
	Accept: 'application/json',
	'Accept-Language': Liferay.ThemeDisplay.getBCP47LanguageId(),
	'Content-Type': 'application/json',
};

export const addItem = (endpoint, item) =>
	fetch(getURL(endpoint), {
		body: JSON.stringify(item),
		headers: HEADERS,
		method: 'POST',
	}).then(response => response.json());

export const getURL = (path, params) => {
	params = {
		['p_auth']: Liferay.authToken,
		t: Date.now(),
		...params,
	};

	const uri = new URL(`${window.location.origin}${path}`);
	const keys = Object.keys(params);

	keys.forEach(key => uri.searchParams.set(key, params[key]));

	return uri.toString();
};

export const updateItem = (endpoint, item, params) =>
	fetch(getURL(endpoint, params), {
		body: JSON.stringify(item),
		headers: HEADERS,
		method: 'PUT',
	})
		.then(response => response.text())
		.then(text => (text ? JSON.parse(text) : {}));

export default ({
	contentType,
	dataDefinition,
	dataDefinitionId,
	dataLayout,
	groupId,
	params = {},
}) => {
	const normalizedDataLayout = {
		...dataLayout,
		dataLayoutPages: dataLayout.dataLayoutPages.map(dataLayoutPage => ({
			...dataLayoutPage,
			dataLayoutRows: (dataLayoutPage.dataLayoutRows || []).map(
				dataLayoutRow => ({
					...dataLayoutRow,
					dataLayoutColumns: (
						dataLayoutRow.dataLayoutColumns || []
					).map(dataLayoutColumn => ({
						...dataLayoutColumn,
						fieldNames: dataLayoutColumn.fieldNames || [],
					})),
				})
			),
			description: dataLayoutPage.description || {
				[themeDisplay.getLanguageId()]: '',
			},
			title: dataLayoutPage.title || {
				[themeDisplay.getLanguageId()]: '',
			},
		})),
		...(params.dataLayout || {}),
	};

	const saveDataDefinitionDataLayout = () => {
		if (dataDefinitionId) {
			return updateItem(
				`/o/data-engine/v2.0/data-definitions/${dataDefinitionId}`,
				{
					...dataDefinition,
					...(params.dataDefinition || {}),
					defaultDataLayout: normalizedDataLayout,
				}
			);
		}

		let endpoint = `/o/data-engine/v2.0/data-definitions/by-content-type/${contentType}`;

		if (groupId > 0) {
			endpoint = `/o/data-engine/v2.0/sites/${groupId}/data-definitions/by-content-type/${contentType}`;
		}

		return addItem(endpoint, {
			...dataDefinition,
			...(params.dataDefinition || {}),
			defaultDataLayout: normalizedDataLayout,
		});
	};

	return saveDataDefinitionDataLayout();
};
