/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {fetch} from 'frontend-js-web';

import {HEADERS} from './constants';

export const CART_FRONTSTORE_ENDPOINT_BASE =
	'/o/headless-commerce-delivery-cart/v1.0/carts';
export const PINS_ADMIN_ENDPOINT_BASE =
	'/o/headless-commerce-admin-catalog/v1.0';
export const PINS_FRONTSTORE_ENDPOINT_BASE =
	'/o/headless-commerce-delivery-catalog/v1.0';

export function loadPins(productId, channelId = null, accountId) {
	const url = new URL(
		channelId
			? `${themeDisplay.getPathContext()}${PINS_FRONTSTORE_ENDPOINT_BASE}/channels/${channelId}/products/${productId}/pins`
			: `${themeDisplay.getPathContext()}${PINS_ADMIN_ENDPOINT_BASE}/products/${productId}/pins`,
		themeDisplay.getPortalURL()
	);

	if (accountId) {
		url.searchParams.set('accountId', accountId);
	}

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
	return fetch(`${PINS_ADMIN_ENDPOINT_BASE}/pins/${pinId}`, {
		headers: HEADERS,
		method: 'DELETE',
	});
}

export function deleteMappedProduct(mappedProductId) {
	return fetch(
		`${PINS_ADMIN_ENDPOINT_BASE}/mapped-products/${mappedProductId}`,
		{
			headers: HEADERS,
			method: 'DELETE',
		}
	);
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

	return fetch(baseURL, {
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

	const body = {...mappedProduct};

	if (sequence) {
		body.sequence = sequence;
	}

	return fetch(baseURL, {
		body: JSON.stringify(body),
		headers: HEADERS,
		method: mappedProductId ? 'PATCH' : 'POST',
	}).then((response) => response.json());
}

export function updateGlobalPinsRadius(diagramId, radius, namespace) {
	return fetch(`${PINS_ADMIN_ENDPOINT_BASE}/diagrams/${diagramId}`, {
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

export function getMappedProducts(
	productId,
	channelId,
	query,
	page,
	pageSize,
	accountId
) {
	const url = new URL(
		channelId
			? `${themeDisplay.getPathContext()}${PINS_FRONTSTORE_ENDPOINT_BASE}/channels/${channelId}/products/${productId}/mapped-products`
			: `${themeDisplay.getPathContext()}${PINS_ADMIN_ENDPOINT_BASE}/products/${productId}/mapped-products`,

		themeDisplay.getPortalURL()
	);

	if (accountId) {
		url.searchParams.set('accountId', accountId);
	}

	if (query) {
		url.searchParams.set('search', query);
	}

	if (page) {
		url.searchParams.set('page', page);
	}

	url.searchParams.set('pageSize', pageSize);

	return fetch(url, {
		headers: HEADERS,
	}).then((response) => {
		if (!response.ok) {
			throw new Error(Liferay.Language.get('unexpected-error'));
		}

		return response.json();
	});
}

export function getCartItems(cartId, skuId) {
	const url = new URL(
		`${themeDisplay.getPathContext()}${CART_FRONTSTORE_ENDPOINT_BASE}/${cartId}/items`,
		themeDisplay.getPortalURL()
	);

	url.searchParams.set('skuId', skuId);

	return fetch(url, {
		headers: HEADERS,
	}).then((response) => response.json());
}
