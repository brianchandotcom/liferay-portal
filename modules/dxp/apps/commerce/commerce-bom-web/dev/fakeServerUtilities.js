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

function generateFolderShape() {
	const productName = faker.commerce.productName();
	const type = ['folder', 'area'][Math.round(Math.random())];

	return {
		name: productName,
		slug: faker.helpers.slugify(productName).toLowerCase(),
		id: faker.random.uuid(),
		thumbnail: '/schema.jpg',
		type,
		url: (type === 'folder' ? '/folders/' : '/areas/') + faker.random.uuid(),
	};
}

function generateBreadcrumbs(type = 'folder') {
	const array = generateArray(4, 2);

	return array.map((_, i) => {
		return i === (array.length - 1)
			? {
				label: type === 'folder'
					? `Folder ${i}`
					: `Area ${i}`,
			}
			: {
				label: `Folder ${i}`,
				url: `/folders/folder-${i}`,
			};
	});
}

function generateBrands() {
	return generateArray(4, 2).map(() => ({
		name: faker.commerce.productName(),
		models: generateArray(10).map(() => {
			const productionYear = generateRandomInt(2000, 60);

			return {
				name: faker.commerce.productName(),
				power: generateRandomInt(50, 60) + 'kw',
				productionYears: [productionYear, productionYear + 2],
			};
		}),
	}));
}

function generateFolders() {
	return generateArray(40, 10).map(generateFolderShape);
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
					x: 73.34,
					y: 33.43,
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
					y: 100,
					x: 100,
				},
				productId: 'IS02',
			},
			{
				number: 12,
				id: 'bnm',
				position: {
					x: 0,
					y: 0,
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

/**
 *
 * @param {*} app
 */
function defineServerResponses(app) {
	app.get([
		apiEndpointDefinitions.MAKER,
		apiEndpointDefinitions.MAKER + '/:params',
	], (_, res) => {
		res.json({
			data: generateArray(20, 4).map(() => ({
				id: faker.random.uuid(),
				name: faker.company.companyName(),
			})),
		});
	});

	app.get([
		apiEndpointDefinitions.YEAR,
		apiEndpointDefinitions.YEAR + '/:params',
	], (_, res) => {
		res.json({
			data: generateArray(15, 5).map(() => ({
				year: generateRandomInt(2000, 2019),
			})),
		});
	});

	app.get([
		apiEndpointDefinitions.MODEL,
		apiEndpointDefinitions.MODEL + '/:params',
	], (_, res) => {
		res.json({
			data: generateArray(15, 5).map(() => ({
				id: faker.random.uuid(),
				name: faker.commerce.product(),
			})),
		});
	});

	app.get(apiEndpointDefinitions.AREAS + '/:areaId', (_, res) => {
		res.json({
			data: getFakeArea(),
			breadcrumbs: generateBreadcrumbs('area'),
		});
	});

	app.get([
		apiEndpointDefinitions.FOLDERS,
		apiEndpointDefinitions.FOLDERS + '/:folderId'
	], (_, res) => {
		res.json({
			data: {
				content: generateFolders(),
				brands: generateBrands(),
			},
			breadcrumbs: generateBreadcrumbs(),
		});
	});
}

// eslint-disable-next-line no-undef
module.exports = {
	defineServerResponses,
};
