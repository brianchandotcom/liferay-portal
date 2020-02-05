/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

export const FETCH_HIDDEN_DOCUMENTS_URL = 'https://getHidden';

export const FETCH_SEARCH_DOCUMENTS_URL = 'https://getSearch';

export const FETCH_VISIBLE_DOCUMENTS_URL = 'https://getVisible';

export const FORM_NAME = 'testForm';

export const VALIDATE_FORM_URL = 'https://getValidate';

export function getMockResultsData(
	size = 10,
	startId = 0,
	searchBarTerm = '',
	hidden = false,
	properties = {}
) {
	const mockData = [];

	const PINNED_AMOUNT = 5;

	let LEVEL;

	if (searchBarTerm === '') {
		LEVEL = hidden ? 200 : 100;
	}
	else {
		LEVEL = 300;
	}

	for (let i = 0; i < size; i++) {
		const icon = i % 2 === 0 ? 'documents-and-media' : 'web-content';
		const typeOfItem = i % 2 === 0 ? 'Document' : 'Web Content';

		const k = i + startId;

		mockData.push({
			author: 'Test Test',
			clicks: k + LEVEL,
			date: 'Apr 18 2018, 11:04 AM',
			description:
				'Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod',
			hidden,
			icon,
			id: k + LEVEL,
			pinned: hidden ? false : k < PINNED_AMOUNT,
			title: `${k + LEVEL} This is a ${typeOfItem} Example`,
			type: typeOfItem,
			...properties
		});
	}

	return {
		documents: mockData,
		total: 100
	};
}

/**
 * Mocks a single document result.
 * @param {number} id The id of the document result.
 * @param {Object} properties The properties to add or override.
 * @return {Object} The document result object.
 */
export function mockDocument(id = 1, properties) {
	return {
		author: 'Test Author',
		clicks: 123,
		date: 'Apr 18 2018, 11:04 AM',
		description: 'This is a test description.',
		hidden: false,
		icon: 'documents-and-media',
		id,
		pinned: false,
		title: 'Test Title',
		type: 'Document',
		...properties
	};
}
