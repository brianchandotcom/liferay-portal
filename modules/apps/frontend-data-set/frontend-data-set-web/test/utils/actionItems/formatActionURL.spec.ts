/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {formatActionURL} from '../../../src/main/resources/META-INF/resources/utils/actionItems/formatActionURL';

const testItem = {
	id: 1235,
	name: 'test_item_name',
};

describe('formatActionURL helper', () => {
	it('returns an empty string if no URL is provided', () => {
		const givenURL = undefined;
		const formattedURL = formatActionURL(givenURL, testItem);

		expect(formattedURL).toEqual('');
	});

	it('returns the raw URL if there is no interpolation argument', () => {
		const givenURL = 'https://www.liferay.com';
		const formattedURL = formatActionURL(givenURL, testItem);

		expect(formattedURL).toEqual(givenURL);
	});

	it('returns the URL with interpolate values', () => {
		const URLWithParam = '/o/data-test/{id}';
		const formattedURLWithParam = formatActionURL(URLWithParam, testItem);

		expect(formattedURLWithParam).toEqual(`/o/data-test/${testItem.id}`);

		const URLWithParams = '/o/data-test/{id}/{name}';
		const formattedURLWithParams = formatActionURL(URLWithParams, testItem);

		expect(formattedURLWithParams).toEqual(
			`/o/data-test/${testItem.id}/${testItem.name}`
		);
	});
});
