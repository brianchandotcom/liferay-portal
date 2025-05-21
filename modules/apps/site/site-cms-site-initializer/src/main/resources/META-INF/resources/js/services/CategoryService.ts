/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {HEADERS_ALL_LANGUAGES} from './ApiHelper';

const createCategory = async (
	categoryByVocabularyIdAPIURL: string,
	category: TaxonomyCategory
) => {
	const response = await fetch(categoryByVocabularyIdAPIURL, {
		body: JSON.stringify(category),
		headers: HEADERS_ALL_LANGUAGES,
		method: 'POST',
	});

	if (response.ok) {
		return await response.json();
	}
	else {
		throw new Error(
			`POST request failed to create a new Category under 'vocabularyId = ${category.taxonomyVocabularyId}' using the following provided data: ${JSON.stringify(category)}`
		);
	}
};

const getCategory = async (
	categoryByCategoryIdAPIURL: string,
	categoryId: number
) => {
	const response = await fetch(categoryByCategoryIdAPIURL, {
		headers: HEADERS_ALL_LANGUAGES,
		method: 'GET',
	});

	if (response.ok) {
		return await response.json();
	}
	else {
		throw new Error(
			`GET request failed to fetch a Category with 'categoryId = ${categoryId}'`
		);
	}
};

/**
 * Updates the TaxonomyCategory specified by the provided ID in the API URL.
 * Defaults to a 'PUT' request unless specified to 'PATCH'.
 *
 * @param categoryByCategoryIdAPIURL API URL with the ID of the category being updated
 * @param category the updated category data
 * @param updateMethod whether to partially update or replace the category. Defaults to 'PUT'.
 */
const updateCategory = async (
	categoryByCategoryIdAPIURL: string,
	category: TaxonomyCategory | Partial<TaxonomyCategory>,
	updateMethod: 'PUT' | 'PATCH' = 'PUT'
) => {
	const response = await fetch(categoryByCategoryIdAPIURL, {
		body: JSON.stringify(category),
		headers: HEADERS_ALL_LANGUAGES,
		method: updateMethod,
	});

	if (response.ok) {
		return await response.json();
	}
	else {
		throw new Error(
			`${updateMethod} request failed to update a Category at ${categoryByCategoryIdAPIURL} using the following provided data: ${JSON.stringify(category)}`
		);
	}
};

export default {createCategory, getCategory, updateCategory};
