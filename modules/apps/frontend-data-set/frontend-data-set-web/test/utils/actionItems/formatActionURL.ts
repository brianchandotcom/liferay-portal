/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import formatActionURL from '../../../src/main/resources/META-INF/resources/utils/actionItems/formatActionURL';

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

	it('returns the URL, changing the _redirect parameter to use the actual URL', () => {
		const URLWithRedirect =
			'/test/page?p_p_id=random&random_redirect=http://www.somewhere.com';
		const formattedURL = formatActionURL(URLWithRedirect, testItem);

		expect(formattedURL).toEqual(
			'/test/page?p_p_id=random&random_redirect=http://localhost/'
		);
	});

	it('returns the URL, changing the _backURL parameter to use the actual URL', () => {
		const URLWithRedirect =
			'/test/page?p_p_id=random&random_backURL=http://www.somewhere.com';
		const formattedURL = formatActionURL(URLWithRedirect, testItem);

		expect(formattedURL).toEqual(
			'/test/page?p_p_id=random&random_backURL=http://localhost/'
		);
	});

	it('returns the URL, adding the _redirect and _backURL parameters to use the actual URL if the url includes a p_p_id parameter', () => {
		const URLWithRedirect = '/test/page?p_p_id=random';
		const formattedURL = formatActionURL(URLWithRedirect, testItem);

		expect(formattedURL).toEqual(
			'/test/page?p_p_id=random&random_redirect=http://localhost/&random_backURL=http://localhost/'
		);
	});
});
