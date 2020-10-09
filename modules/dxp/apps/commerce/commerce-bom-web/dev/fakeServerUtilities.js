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

function generateFolderShape() {
	const productName = faker.commerce.productName();
	const type = ['folder', 'area'][Math.round(Math.random())];

	return {
		id: faker.random.uuid(),
		name: productName,
		slug: faker.helpers.slugify(productName).toLowerCase(),
		thumbnail: '/schema.jpg',
		type,
		url:
			(type === 'folder' ? '/folders/' : '/areas/') + faker.random.uuid(),
	};
}

function generateBreadcrumbs(type = 'folder') {
	const array = generateArray(4, 2);

	return array.map((_, i) => {
		return i === array.length - 1
			? {
					label: type === 'folder' ? `Folder ${i}` : `Area ${i}`,
			  }
			: {
					label: `Folder ${i}`,
					url: `/folders/folder-${i}`,
			  };
	});
}

function generateBrands() {
	return generateArray(4, 2).map(() => ({
		models: generateArray(10).map(() => {
			const productionYear = generateRandomInt(2000, 60);

			return {
				name: faker.commerce.productName(),
				power: generateRandomInt(50, 60) + 'kw',
				productionYears: [productionYear, productionYear + 2],
			};
		}),
		name: faker.commerce.productName(),
	}));
}

function generateFolders() {
	return generateArray(40, 10).map(generateFolderShape);
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
					x: 73.34,
					y: 33.43,
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
					x: 100,
					y: 100,
				},
				productId: 'IS02',
			},
			{
				id: 'bnm',
				number: 12,
				position: {
					x: 0,
					y: 0,
				},
				productId: 'IS03',
			},
		],
	};
}

/**
 *
 * @param {*} app
 */
function defineServerResponses(app) {
	app.get(
		[
			apiEndpointDefinitions.MAKER,
			apiEndpointDefinitions.MAKER + '/:params',
		],
		(_, res) => {
			res.json({
				data: generateArray(20, 4).map(() => ({
					id: faker.random.uuid(),
					name: faker.company.companyName(),
				})),
			});
		}
	);

	app.get(
		[apiEndpointDefinitions.YEAR, apiEndpointDefinitions.YEAR + '/:params'],
		(_, res) => {
			res.json({
				data: generateArray(15, 5).map(() => ({
					year: generateRandomInt(2000, 2019),
				})),
			});
		}
	);

	app.get(
		[
			apiEndpointDefinitions.MODEL,
			apiEndpointDefinitions.MODEL + '/:params',
		],
		(_, res) => {
			res.json({
				data: generateArray(15, 5).map(() => ({
					id: faker.random.uuid(),
					name: faker.commerce.product(),
				})),
			});
		}
	);

	app.get(apiEndpointDefinitions.AREAS + '/:areaId', (_, res) => {
		res.json({
			breadcrumbs: generateBreadcrumbs('area'),
			data: getFakeArea(),
		});
	});

	app.get(
		[
			apiEndpointDefinitions.FOLDERS,
			apiEndpointDefinitions.FOLDERS + '/:folderId',
		],
		(_, res) => {
			res.json({
				breadcrumbs: generateBreadcrumbs(),
				data: {
					brands: generateBrands(),
					content: generateFolders(),
				},
			});
		}
	);
}

// eslint-disable-next-line no-undef
module.exports = {
	defineServerResponses,
};
