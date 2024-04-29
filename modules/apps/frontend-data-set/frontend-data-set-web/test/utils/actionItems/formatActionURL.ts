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
		const target = 'link';
		const formattedURL = formatActionURL(givenURL, testItem, target);

		expect(formattedURL).toEqual('');
	});

	it('returns the raw URL if there is no interpolation argument', () => {
		const givenURL = 'https://www.liferay.com';
		const target = 'link';
		const formattedURL = formatActionURL(givenURL, testItem, target);

		expect(formattedURL).toEqual(givenURL);
	});

	it('returns the URL with interpolate values', () => {
		const URLWithParam = '/o/data-test/{id}';
		const target = 'link';
		const formattedURLWithParam = formatActionURL(
			URLWithParam,
			testItem,
			target
		);

		expect(formattedURLWithParam).toEqual(`/o/data-test/${testItem.id}`);

		const URLWithParams = '/o/data-test/{id}/{name}';
		const formattedURLWithParams = formatActionURL(
			URLWithParams,
			testItem,
			target
		);

		expect(formattedURLWithParams).toEqual(
			`/o/data-test/${testItem.id}/${testItem.name}`
		);
	});

	it('returns the URL, changing the _redirect parameter to use the actual URL', () => {
		const URLWithRedirect =
			'/test/page?p_p_id=random&_random_redirect=http://www.somewhere.com';
		const target = 'link';
		const formattedURL = formatActionURL(URLWithRedirect, testItem, target);

		expect(formattedURL).toEqual(
			'/test/page?p_p_id=random&_random_redirect=http://localhost/'
		);
	});

	it('returns the URL, changing the portlet_ns_backURL parameter to use the actual URL', () => {
		const URLWithRedirect =
			'/test/page?p_p_id=random&_random_backURL=http://www.somewhere.com';
		const target = 'link';
		const formattedURL = formatActionURL(URLWithRedirect, testItem, target);

		expect(formattedURL).toEqual(
			'/test/page?p_p_id=random&_random_backURL=http://localhost/'
		);
	});

	it('returns the URL, changing any _backURL parameter to use the actual URL', () => {
		const URLWithRedirect = '/test/page?backURL=http://www.somewhere.com';
		const target = 'link';
		const formattedURL = formatActionURL(URLWithRedirect, testItem, target);

		expect(formattedURL).toEqual('/test/page?backURL=http://localhost/');
	});

	it('returns the URL, adding the _redirect and _backURL parameters to use the actual URL if the url includes a p_p_id parameter', () => {
		const URLWithoutRedirect = '/test/page?p_p_id=random';
		const target = 'link';
		const formattedURL = formatActionURL(
			URLWithoutRedirect,
			testItem,
			target
		);

		expect(formattedURL).toEqual(
			'/test/page?p_p_id=random&_random_redirect=http://localhost/&_random_backURL=http://localhost/'
		);
	});

	it('returns the URL, without changing the _redirect and _backURL parameters if the target is different from "link"', () => {
		const URLWithBackURL =
			'/test/page?p_p_id=random&_random_backURL=http://www.somewhere.com';
		const modalTarget = 'modal';
		const formattedURL = formatActionURL(
			URLWithBackURL,
			testItem,
			modalTarget
		);

		expect(formattedURL).toEqual(URLWithBackURL);

		const URLWithRedirect =
			'/test/page?p_p_id=random&_random_redirect=http://www.somewhere.com';
		const panelTarget = 'sidePanel';
		const anotherFormattedURL = formatActionURL(
			URLWithRedirect,
			testItem,
			panelTarget
		);

		expect(anotherFormattedURL).toEqual(URLWithRedirect);
	});

	it('returns the URL, without adding the _redirect and _backURL parameters if the target is different from "link"', () => {
		const URLWithoutRedirect = '/test/page?p_p_id=random';
		const modalTarget = 'modal';
		const formattedURL = formatActionURL(
			URLWithoutRedirect,
			testItem,
			modalTarget
		);

		expect(formattedURL).toEqual('/test/page?p_p_id=random');

		const panelTarget = 'sidePanel';
		const anotherFormattedURL = formatActionURL(
			URLWithoutRedirect,
			testItem,
			panelTarget
		);

		expect(anotherFormattedURL).toEqual('/test/page?p_p_id=random');
	});
});
