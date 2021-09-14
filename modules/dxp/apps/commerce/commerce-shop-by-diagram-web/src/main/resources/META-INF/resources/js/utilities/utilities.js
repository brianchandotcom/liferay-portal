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

import {fetch} from 'frontend-js-web';

export const HEADERS = new Headers({
	Accept: 'application/json',
	'Content-Type': 'application/json',
});

const SKUS_ENDPOINT = '/o/headless-commerce-admin-catalog/v1.0/skus';
const DIAGRAMS_ENDPOINT = '/o/headless-commerce-admin-catalog/v1.0/products';

export function searchSkus(query) {
	const url = new URL(SKUS_ENDPOINT, themeDisplay.getPortalURL());

	url.searchParams.append('search', query);

	return fetch(url, {
		headers: HEADERS,
	}).then((response) => response.json());
}

export function searchDiagrams(query) {
	const url = new URL(DIAGRAMS_ENDPOINT, themeDisplay.getPortalURL());

	url.searchParams.append('search', query);
	url.searchParams.append('filter', `(productType eq 'diagram')`);

	return fetch(url, {
		headers: HEADERS,
	}).then((response) => response.json());
}
