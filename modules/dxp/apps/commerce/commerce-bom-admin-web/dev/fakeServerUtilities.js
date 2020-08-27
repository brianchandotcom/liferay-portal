/* eslint-disable require-jsdoc */
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
		imageUrl: '/schema.jpg',
		name: 'frozen metal chair',
		id: 'areaIdTest',
		spots: [
			{
				number: 3,
				id: 'zxc',
				position: {
					y: 0,
					x: 0,
				},
				productId: 'IS01',
			},
			{
				number: 3,
				id: 'cvb',
				position: {
					y: 66.43,
					x: 56.34,
				},
				productId: 'IS01',
			},
			{
				number: 7,
				id: 'dfg',
				position: {
					y: 63.43,
					x: 20.14,
				},
				productId: 'IS02',
			},
			{
				number: 12,
				id: 'bnm',
				position: {
					y: 3.93,
					x: 37.94,
				},
				productId: 'IS03',
			},
		],
		products: [
			{
				id: 'IS01',
				sku: 'SKU01',
				name: 'Product 1',
				thumbnailUrl: '/product_thumbnail.png',
				url: '/productUrl',
				price: '$ 12.99',
			},
			{
				id: 'IS03',
				sku: 'SKU02',
				name: 'Product 2',
				thumbnailUrl: '/product_thumbnail.png',
				url: '/productUrl',
				price: '$ 345.99',
			},
			{
				id: 'IS02',
				sku: 'SKU03',
				name: 'Product 3',
				thumbnailUrl: '/product_thumbnail.png',
				url: '/productUrl',
				price: '$ 345.99',
			},
		],
	};
}

function generateProducts() {
	return generateArray(25, 10).map(
	  () => ({
		id: faker.random.uuid().substring(0,8),
		sku: 'sdfasd',
		name: faker.commerce.productName(),
		thumbnailUrl: '/detail.jpg',
		url: '/productUrl',
		price: `$ ${(Math.random() * 300) + 10}.99`
	  })
	)
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
			success: true //placeholder: if statusCode === 200 then it calls the main get again
		});
	});

	app.put(apiEndpointDefinitions.AREA + '/:areaId/:spotId', (_, res) => {
		res.json({
			success: true //placeholder: if statusCode === 200 then it calls the main get again
		});
	});

	app.delete(apiEndpointDefinitions.AREA + '/:areaId/:spotId', (_, res) => {
		res.json({
			success: true //placeholder: if statusCode === 200 then it calls the main get again
		});
	});

	app.get([
		apiEndpointDefinitions.PRODUCTS,
		apiEndpointDefinitions.PRODUCTS + '/:query',
	], (_, res) => {
		res.json({
			items: generateProducts(),
		});
	});
}

// eslint-disable-next-line no-undef
module.exports = {
	defineServerResponses,
};
