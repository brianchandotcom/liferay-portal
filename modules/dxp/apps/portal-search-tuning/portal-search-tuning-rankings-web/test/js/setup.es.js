/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	).then((data) => ({
		items: data.documents,
		total: data.total,
	}));

	return p;
});
