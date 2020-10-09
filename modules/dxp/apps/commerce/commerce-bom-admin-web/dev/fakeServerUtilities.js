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

const faker = require('faker');

const apiEndpointDefinitions = require('./apiEndpointDefinitions');

function generateRandomInt(min, max) {
	return Math.floor(Math.random() * max) + min;
}

function generateArray(max, min = 1) {
	const length = generateRandomInt(min, max);

	return Array(length).fill('');
}

function getFakeArea() {
	return {
		id: 'areaIdTest',
		imageUrl: '/schema.jpg',
		name: 'frozen metal chair',
		products: [
			{
				id: 'IS01',
				name: 'Product 1',
				price: '$ 12.99',
				sku: 'SKU01',
				thumbnailUrl: '/product_thumbnail.png',
				url: '/productUrl',
			},
			{
				id: 'IS03',
				name: 'Product 2',
				price: '$ 345.99',
				sku: 'SKU02',
				thumbnailUrl: '/product_thumbnail.png',
				url: '/productUrl',
			},
			{
				id: 'IS02',
				name: 'Product 3',
				price: '$ 345.99',
				sku: 'SKU03',
				thumbnailUrl: '/product_thumbnail.png',
				url: '/productUrl',
			},
		],
		spots: [
			{
				id: 'zxc',
				number: 3,
				position: {
					x: 0,
					y: 0,
				},
				productId: 'IS01',
			},
			{
				id: 'cvb',
				number: 3,
				position: {
					x: 56.34,
					y: 66.43,
				},
				productId: 'IS01',
			},
			{
				id: 'dfg',
				number: 7,
				position: {
					x: 20.14,
					y: 63.43,
				},
				productId: 'IS02',
			},
			{
				id: 'bnm',
				number: 12,
				position: {
					x: 37.94,
					y: 3.93,
				},
				productId: 'IS03',
			},
		],
	};
}

function generateProducts() {
	return generateArray(25, 10).map(() => ({
		id: faker.random.uuid().substring(0, 8),
		name: faker.commerce.productName(),
		price: `$ ${Math.random() * 300 + 10}.99`,
		sku: 'sdfasd',
		thumbnailUrl: '/detail.jpg',
		url: '/productUrl',
	}));
}

/**
 *
 * @param {*} app
 */
function defineServerResponses(app) {
	app.get(apiEndpointDefinitions.AREA + '/:areaId', (_, res) => {
		res.json({
			data: getFakeArea(),
		});
	});

	app.post(apiEndpointDefinitions.AREA + '/:areaId', (_, res) => {
		res.json({
			success: true, //placeholder: if statusCode === 200 then it calls the main get again
		});
	});

	app.put(apiEndpointDefinitions.AREA + '/:areaId/:spotId', (_, res) => {
		res.json({
			success: true, //placeholder: if statusCode === 200 then it calls the main get again
		});
	});

	app.delete(apiEndpointDefinitions.AREA + '/:areaId/:spotId', (_, res) => {
		res.json({
			success: true, //placeholder: if statusCode === 200 then it calls the main get again
		});
	});

	app.get(
		[
			apiEndpointDefinitions.PRODUCTS,
			apiEndpointDefinitions.PRODUCTS + '/:query',
		],
		(_, res) => {
			res.json({
				items: generateProducts(),
			});
		}
	);
}

// eslint-disable-next-line no-undef
module.exports = {
	defineServerResponses,
};
