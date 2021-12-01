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

import {HEADERS} from './constants';

export const PINS_ADMIN_ENDPOINT_BASE =
	'/o/headless-commerce-admin-catalog/v1.0';
export const PINS_FRONTSTORE_ENDPOINT_BASE =
	'/o/headless-commerce-delivery-catalog/v1.0';

export function loadPins(productId, channelId = null) {
	const url = new URL(
		channelId
			? `${PINS_FRONTSTORE_ENDPOINT_BASE}/channels/${channelId}/products/${productId}/pins`
			: `${PINS_ADMIN_ENDPOINT_BASE}/products/${productId}/pins`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.set('pageSize', 200);

	return fetch(url, {
		headers: HEADERS,
	})
		.then((response) => response.json())
		.then((jsonResponse) =>
			jsonResponse.items.filter((item) => item.mappedProduct)
		);
}

export function deletePin(pinId) {
	const url = new URL(
		`${PINS_ADMIN_ENDPOINT_BASE}/pins/${pinId}`,
		themeDisplay.getPortalURL()
	);

	return fetch(url, {
		headers: HEADERS,
		method: 'DELETE',
	});
}

export function deleteMappedProduct(mappedProductId) {
	const url = new URL(
		`${PINS_ADMIN_ENDPOINT_BASE}/mapped-products/${mappedProductId}`,
		themeDisplay.getPortalURL()
	);

	return fetch(url, {
		headers: HEADERS,
		method: 'DELETE',
	});
}

export function savePin(
	pinId,
	mappedProduct,
	sequence,
	positionX,
	positionY,
	productId
) {
	const baseURL = pinId
		? `${PINS_ADMIN_ENDPOINT_BASE}/pins/${pinId}`
		: `${PINS_ADMIN_ENDPOINT_BASE}/products/${productId}/pins`;

	const url = new URL(baseURL, themeDisplay.getPortalURL());

	const body = {};

	if (mappedProduct) {
		body.mappedProduct = mappedProduct;
	}

	if (positionX || positionY) {
		body.positionX = positionX;
		body.positionY = positionY;
	}

	if (sequence) {
		body.sequence = sequence;
	}

	return fetch(url, {
		body: JSON.stringify(body),
		headers: HEADERS,
		method: pinId ? 'PATCH' : 'POST',
	}).then((response) => response.json());
}

export function saveMappedProduct(
	mappedProductId,
	mappedProduct,
	sequence,
	productId
) {
	const baseURL = mappedProductId
		? `${PINS_ADMIN_ENDPOINT_BASE}/mapped-products/${mappedProductId}`
		: `${PINS_ADMIN_ENDPOINT_BASE}/products/${productId}/mapped-products`;

	const url = new URL(baseURL, themeDisplay.getPortalURL());

	const body = {...mappedProduct};

	if (sequence) {
		body.sequence = sequence;
	}

	return fetch(url, {
		body: JSON.stringify(body),
		headers: HEADERS,
		method: mappedProductId ? 'PATCH' : 'POST',
	}).then((response) => response.json());
}

export function updateGlobalPinsRadius(diagramId, radius, namespace) {
	const url = new URL(
		`${PINS_ADMIN_ENDPOINT_BASE}/diagrams/${diagramId}`,
		themeDisplay.getPortalURL()
	);

	return fetch(url, {
		body: JSON.stringify({radius}),
		headers: HEADERS,
		method: 'PATCH',
	}).then((response) => {
		if (response.ok) {
			const radiusInput = document.getElementById(`${namespace}radius`);

			if (radiusInput) {
				radiusInput.value = radius;
			}
		}
	});
}

export function getMappedProducts(productId, channelId, query, page, pageSize) {
	const url = new URL(
		channelId
			? `${PINS_FRONTSTORE_ENDPOINT_BASE}/channels/${channelId}/products/${productId}/mapped-products`
			: `${PINS_ADMIN_ENDPOINT_BASE}/products/${productId}/mapped-products`,

		themeDisplay.getPortalURL()
	);

	if (query) {
		url.searchParams.set('search', query);
	}

	if (page) {
		url.searchParams.set('page', page);
	}

	url.searchParams.set('pageSize', pageSize);

	return fetch(url, {
		headers: HEADERS,
	}).then((response) => response.json());
}
