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

const APIUtil = require('../../src/main/resources/META-INF/resources/js/utils/api.es');
const LanguageUtil = require('../../src/main/resources/META-INF/resources/js/utils/language.es');
import {FETCH_HIDDEN_DOCUMENTS_URL, getMockResultsData} from './mocks/data.es';

/**
 * Extends the timeout session to prevent the following error: 'Timeout - Async
 * callback was not invoked within the 5000ms timeout specified by
 * jest.setTimeout.Error'
 */
jest.setTimeout(10000);

/**
 * Mocks the `sub` function to be able to test the correct values are being
 * passed and displayed.
 */
LanguageUtil.sub = (key, args) => [key, args];

/**
 * Mocks fetchDocuments for consistent data to test. Uses the getMockResultsData
 * in order to mock the fetch formula
 */
APIUtil.fetchDocuments = jest.fn((url, config) => {
	const {from, keywords, size} = config;

	const p = Promise.resolve(
		getMockResultsData(
			size,
			from,
			keywords,
			url === FETCH_HIDDEN_DOCUMENTS_URL
		)
	).then(data => ({
		items: data.documents,
		total: data.total,
	}));

	return p;
});
