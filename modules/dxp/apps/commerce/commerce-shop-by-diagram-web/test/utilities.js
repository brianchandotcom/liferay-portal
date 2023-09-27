/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import fetchMock from 'fetch-mock';

import {
	CART_FRONTSTORE_ENDPOINT_BASE,
	PINS_ADMIN_ENDPOINT_BASE,
	PINS_FRONTSTORE_ENDPOINT_BASE,
} from '../src/main/resources/META-INF/resources/js/utilities/data';

export function load(element, timeout) {
	return new Promise((resolve) => {
		setTimeout(() => {
			element.dispatchEvent(new Event('load'));

			resolve();
		}, timeout);
	});
}

export const defaultDiagramProps = {
	cartId: '1111',
	channelGroupId: '42150',
	channelId: 'fake-channel-id',
	commerceAccountId: '43615',
	commerceCurrencyCode: 'USD',
	datasetDisplayId: 'testDatasetDisplayId',
	diagramId: 'fake-diagram-id',
	imageURL: '/image/test-url.jpg',
	orderUUID: '1c25ed61-0a41-b490-fb52-9df18f3f2f33',
	pinsCSSSelectors: ['.sequences text'],
	pinsRadius: 1,
	productBaseURL: 'http://localhost:8080/group/minium/p/',
	productId: 'fake-product-id',
};

export const adminMappedProductTemplate = {
	id: 1,
	productExternalReferenceCode: 'MIN93016minium-full-initializer',
	productId: 1,
	productName: {
		en_US: 'First Product',
	},
	quantity: 1,
	sku: 'First SKU',
	skuExternalReferenceCode: 'min93016b',
	skuId: 1,
	type: 'sku',
};

export const adminMappedProducts = [
	{
		...adminMappedProductTemplate,
		id: 1,
		productId: 1,
		quantity: 3,
		sequence: 'first',
		sku: 'FIRST SKU',
		skuId: 1,
		type: 'sku',
	},
	{
		...adminMappedProductTemplate,
		id: 2,
		productId: 2,
		quantity: 1,
		sequence: 'second',
		sku: 'SECOND SKU',
		skuId: 2,
		type: 'diagram',
	},
	{
		...adminMappedProductTemplate,
		id: 3,
		productId: 3,
		quantity: 5,
		sequence: 'third',
		sku: 'THIRD SKU',
		skuId: 3,
		type: 'external',
	},
];

export const adminPinsData = [
	{
		id: 1,
		mappedProduct: adminMappedProducts[0],
		positionX: 50,
		positionY: 50,
		sequence: 'first',
	},
	{
		id: 2,
		mappedProduct: adminMappedProducts[1],
		positionX: 25,
		positionY: 25,
		sequence: 'second',
	},
	{
		id: 2,
		mappedProduct: adminMappedProducts[2],
		positionX: 75,
		positionY: 75,
		sequence: 'third',
	},
];

export const frontStoreMappedProductTemplate = {
	actions: {},
	availability: {
		label: 'available',
		label_i18n: 'Available',
		stockQuantity: 240,
	},
	options: {
		42638: '42639',
	},
	price: {
		currency: 'US Dollar',
		discount: '$ 5.40',
		discountPercentage: '20.00',
		discountPercentages: ['20.00', '0.00', '0.00', '0.00'],
		finalPrice: '$ 21.60',
		price: 27.0,
		priceFormatted: '$ 27.00',
	},
	productConfiguration: {
		allowBackOrder: true,
		allowedOrderQuantities: [],
		inventoryEngine: '',
		maxOrderQuantity: 10000,
		minOrderQuantity: 1,
		multipleOrderQuantity: 1,
	},
	productExternalReferenceCode: 'MIN93016minium-full-initializer',
	productId: 42627,
	productOptions: [
		{
			description: '',
			fieldType: 'select',
			id: 42638,
			key: 'package-quantity',
			name: 'Package Quantity',
			optionId: 42526,
			productOptionValues: [
				{
					id: 42639,
					key: '12',
					name: '12',
					priority: 1.0,
				},
				{
					id: 42640,
					key: '48',
					name: '48',
					priority: 3.0,
				},
				{
					id: 42641,
					key: '112',
					name: '112',
					priority: 4.0,
				},
			],
		},
	],
	skuExternalReferenceCode: 'min93016a',
	skuId: 42642,
	thumbnail: '/o/commerce-media/accounts/-1/images/42662?download=false',
};

export const frontStoreMappedProducts = [
	{
		...frontStoreMappedProductTemplate,
		id: 1,
		productName: {
			en_US: 'Brake Fluid',
		},
		quantity: 1,
		sequence: 'first',
		sku: 'SKU_TEST_FIRST',
		type: 'sku',
		urls: {
			en_US: 'brake-fluid',
		},
	},
	{
		...frontStoreMappedProductTemplate,
		id: 2,
		productExternalReferenceCode: 'MIN93016minium-full-initializer',
		productId: 3,
		productName: {
			en_US: 'Second Product',
		},
		quantity: 2,
		sequence: 'second',
		sku: 'SKU_TEST_SECOND',
		skuExternalReferenceCode: 'SKU_TEST_SECOND',
		skuId: 3,
		type: 'diagram',
		urls: {
			en_US: 'brake-fluid',
		},
	},
	{
		actions: {},
		customFields: [],
		id: 3,
		productExternalReferenceCode: 'MIN93016minium-full-initializer',
		productId: 5,
		quantity: 4,
		sequence: 'third',
		sku: 'SKU_TEST_THIRD',
		skuId: 4,
		type: 'external',
	},
];

export const frontStorePinsData = [
	{
		id: 1,
		mappedProduct: frontStoreMappedProducts[0],
		positionX: 50,
		positionY: 50,
		sequence: 'first',
	},
	{
		id: 2,
		mappedProduct: frontStoreMappedProducts[1],
		positionX: 25,
		positionY: 25,
		sequence: 'second',
	},
	{
		id: 2,
		mappedProduct: frontStoreMappedProducts[2],
		positionX: 75,
		positionY: 75,
		sequence: 'third',
	},
];

function getAdminMappedProducts() {
	return {
		actions: {},
		facets: [],
		items: adminMappedProducts,
		lastPage: 1,
		page: 1,
		pageSize: 100,
		totalCount: 3,
	};
}

function getAdminPins() {
	return {
		actions: {},
		facets: [],
		items: adminPinsData,
		lastPage: 1,
		page: 1,
		pageSize: 100,
		totalCount: 3,
	};
}

function getFrontStorePins() {
	return {
		actions: {},
		facets: [],
		items: frontStorePinsData,
		lastPage: 1,
		page: 1,
		pageSize: 100,
		totalCount: 3,
	};
}

function getDiagrams() {
	return {
		actions: {},
		facets: [],
		items: frontStorePinsData,
		lastPage: 1,
		page: 1,
		pageSize: 100,
		totalCount: 3,
	};
}

function getFrontStoreMappedProducts() {
	return {
		actions: {},
		facets: [],
		items: frontStoreMappedProducts,
		lastPage: 1,
		page: 1,
		pageSize: 100,
		totalCount: 3,
	};
}

function getSkus() {
	return {
		actions: {},
		facets: [],
		items: [],
		lastPage: 1,
		page: 1,
		pageSize: 100,
		totalCount: 1,
	};
}

export function mockCommonEndpoints() {
	const cartFrontStore = new RegExp(
		`${CART_FRONTSTORE_ENDPOINT_BASE}/${defaultDiagramProps.cartId}/items`
	);

	const diagramsEndpointRegExp = new RegExp(
		`${PINS_ADMIN_ENDPOINT_BASE}/products.*diagram.*`
	);

	const mappedProductAdmin = new RegExp(
		`${PINS_ADMIN_ENDPOINT_BASE}/products/${defaultDiagramProps.productId}/mapped-products`
	);

	const mappedProductFrontStore = new RegExp(
		`${PINS_FRONTSTORE_ENDPOINT_BASE}/channels/${defaultDiagramProps.channelId}/products/${defaultDiagramProps.productId}/mapped-products`
	);

	const pinsAdmin = new RegExp(
		`${PINS_ADMIN_ENDPOINT_BASE}/products/${defaultDiagramProps.productId}/pins`
	);

	const pinsFrontStore = new RegExp(
		`${PINS_FRONTSTORE_ENDPOINT_BASE}/channels/${defaultDiagramProps.channelId}/products/${defaultDiagramProps.productId}/pins`
	);

	const skusAdminEndpointRegExp = new RegExp(
		`${PINS_ADMIN_ENDPOINT_BASE}/skus`
	);

	const skusFrontStoreEndpointRegExp = new RegExp(
		`${PINS_FRONTSTORE_ENDPOINT_BASE}/skus`
	);

	fetchMock.mock(cartFrontStore, () => ({items: []}));
	fetchMock.mock(diagramsEndpointRegExp, () => getDiagrams());
	fetchMock.mock(mappedProductAdmin, () => getAdminMappedProducts());
	fetchMock.mock(mappedProductFrontStore, () =>
		getFrontStoreMappedProducts()
	);
	fetchMock.mock(pinsAdmin, () => getAdminPins());
	fetchMock.mock(pinsFrontStore, () => getFrontStorePins());
	fetchMock.mock(skusAdminEndpointRegExp, () => getSkus());
	fetchMock.mock(skusFrontStoreEndpointRegExp, () => getSkus());
}
