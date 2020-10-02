/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

function defineServerResponses(app) {
	app.post('/account-selector/setCurrentAccounts', (_, res) => {
		res.json({});
	});

	app.get('/dataset-display-nested-items', (_, res) => {
		res.json({
			items: [
				{
					actionDropdownItems: [
						{
							href: '/view/url',
							icon: 'view',
							id: 'view',
							label: 'View',
						},
						{
							href: '/select/url',
							icon: 'message-boards',
							id: 'select',
							label: 'Select',
							target: 'modal',
						},
						{
							href: '/delete/url',
							icon: 'trash',
							id: 'delete',
							label: 'Delete',
							method: 'delete',
							target: 'async',
						},
						{
							href: '/edit/url',
							icon: 'pencil',
							id: 'edit',
							label: 'Edit',
							target: 'sidePanel',
						},
						{
							icon: 'warning-full',
							id: 'alert',
							label: 'Alert',
							onClick: 'alert("asd")',
						},
					],
					id: 'asd',
					img: {
						src: '//via.placeholder.com/250x250',
					},
					name: 'ABS Sensor',
					productPage: '/test/link/1',
					skuId: 7654,
					testLink: {
						href: '/test/link/1',
						label: 'Test 1',
					},
					testQuantity: {
						allowedQuantities: [3, 6, 7, 100],
						disabled: false,
						inputName: 'asd-quantity',
						quantity: 6,
					},
					testSubItems: [
						{
							actionDropdownItems: [
								{
									href: '/view/url',
									icon: 'view',
									id: 'view',
									label: 'View',
								},
								{
									href: '/select/url',
									icon: 'message-boards',
									id: 'select',
									label: 'Select',
									target: 'modal',
								},
								{
									href: '/delete/url',
									icon: 'trash',
									id: 'delete',
									label: 'Delete',
									method: 'delete',
									target: 'async',
								},
								{
									href: '/edit/url',
									icon: 'pencil',
									id: 'edit',
									label: 'Edit',
									target: 'sidePanel',
								},
								{
									icon: 'warning-full',
									id: 'alert',
									label: 'Alert',
									onClick: 'alert("asd")',
								},
							],
							id: '111',
							img: {
								src: '//via.placeholder.com/250x250',
							},
							name: 'Sub item 1',
							price: '$ 123.40',
							productPage: '/test/link/1',
							skuId: 35663,
							testLink: {
								href: '/test/link/1',
								label: 'Test 1',
							},
						},
						{
							actionDropdownItems: [
								{
									href: '/view/url',
									icon: 'view',
									id: 'view',
									label: 'View',
								},
								{
									href: '/select/url',
									icon: 'message-boards',
									id: 'select',
									label: 'Select',
									target: 'modal',
								},
								{
									href: '/delete/url',
									icon: 'trash',
									id: 'delete',
									label: 'Delete',
									method: 'delete',
									target: 'async',
								},
								{
									href: '/edit/url',
									icon: 'pencil',
									id: 'edit',
									label: 'Edit',
									target: 'sidePanel',
								},
								{
									icon: 'warning-full',
									id: 'alert',
									label: 'Alert',
									onClick: 'alert("asd")',
								},
							],
							id: '112',
							img: {
								src: '//via.placeholder.com/250x250',
							},
							name: 'Sub item 2',
							price: '$ 709.90',
							productPage: '/test/link/1',
							skuId: 356637,
							testLink: {
								href: '/test/link/1',
								label: 'Test 1',
							},
						},
						{
							id: '113',
							img: {
								src: '//via.placeholder.com/250x250',
							},
							name: 'Sub item 3',
							price: '$ 10.00',
							productPage: '/test/link/1',
							skuId: 356638,
							testLink: {
								href: '/test/link/1',
								label: 'Test 1',
							},
						},
						{
							id: '114',
							img: {
								src: '//via.placeholder.com/250x250',
							},
							name: 'Sub item 4',
							price: '$ 90.00',
							productPage: '/test/link/1',
							skuId: 3566312,
							testLink: {
								href: '/test/link/1',
								label: 'Test 1',
							},
						},
					],
				},
				{
					actionDropdownItems: [
						{
							href: '/view/url',
							icon: 'view',
							id: 'view',
							label: 'View',
						},
						{
							href: '/select/url',
							icon: 'message-boards',
							id: 'select',
							label: 'Select',
							target: 'modal',
						},
						{
							href: '/delete/url',
							icon: 'trash',
							id: 'delete',
							label: 'Delete',
							method: 'delete',
							target: 'async',
						},
						{
							href: '/edit/url',
							icon: 'pencil',
							id: 'edit',
							label: 'Edit',
							target: 'sidePanel',
						},
						{
							icon: 'warning-full',
							id: 'alert',
							label: 'Alert',
							onClick: 'alert("asd")',
						},
					],
					id: 'sdf',
					img: {
						src: '//via.placeholder.com/500x500',
					},
					name: 'SBA Sensor',
					productPage: '/test/link/1',
					skuId: 345345,
					testLink: {
						href: '/test/link/1',
						label: 'Test 1',
					},
					testQuantity: {
						inputName: 'sdf-quantity',
						maxQuantity: 1000,
						minQuantity: 2,
						multipleQuantity: 2,
						quantity: 6,
					},
					type: {
						content: 'DOC',
						displayType: 'danger',
					},
				},
			],
			lastPage: 6,
			page: 1,
			pageSize: 10,
			totalCount: 52,
		});
	});

	app.get('/dataset-display-selectable-data', (_, res) => {
		res.json({
			items: [
				{
					countryId: '001',
					countryName: 'United States',
					fields: [
						{
							label: 'Money Order',
							name: 'autorizeDotNet',
							value: false,
						},
						{
							label: 'Money Order',
							name: 'moneyOrder',
							value: false,
						},
						{
							label: 'PayPal',
							name: 'payPal',
							value: false,
						},
					],
				},
				{
					countryId: '002',
					countryName: 'Afghanistan',
					fields: [
						{
							label: 'Money Order',
							name: 'autorizeDotNet',
							value: true,
						},
						{
							label: 'Money Order',
							name: 'moneyOrder',
							value: false,
						},
						{
							label: 'PayPal',
							name: 'payPal',
							value: false,
						},
					],
				},
				{
					countryId: '003',
					countryName: 'Albania',
					fields: [
						{
							label: 'Money Order',
							name: 'autorizeDotNet',
							value: false,
						},
						{
							label: 'Money Order',
							name: 'moneyOrder',
							value: true,
						},
						{
							label: 'PayPal',
							name: 'payPal',
							value: false,
						},
					],
				},
				{
					countryId: '004',
					countryName: 'Algeria',
					fields: [
						{
							label: 'Money Order',
							name: 'autorizeDotNet',
							value: false,
						},
						{
							label: 'Money Order',
							name: 'moneyOrder',
							value: false,
						},
						{
							label: 'PayPal',
							name: 'payPal',
							value: false,
						},
					],
				},
				{
					countryId: '005',
					countryName: 'American Samoa',
					fields: [
						{
							label: 'Money Order',
							name: 'autorizeDotNet',
							value: true,
						},
						{
							label: 'Money Order',
							name: 'moneyOrder',
							value: false,
						},
						{
							label: 'PayPal',
							name: 'payPal',
							value: true,
						},
					],
				},
			],
			lastPage: 1,
			page: 1,
			pageSize: 10,
			totalCount: 2,
		});
	});

	app.get('/dataset-display-email-data', (_, res) => {
		res.json({
			items: [
				{
					actionDropdownItems: [
						{
							href: '/delete/action/url',
							icon: 'trash',
							label: 'Delete',
						},
					],
					author: {
						avatarSrc: 'https://via.placeholder.com/150',
						email: 'john.doe@gmail.com',
						name: 'John Doe',
					},
					date: '1 day ago',
					href: '/side-panel/email.html',
					status: {
						displayStyle: 'danger',
						label: 'Order not placed',
					},
					subject:
						'Mauris blandit aliquet elit, eget tincidunt nibh pulvinar.',
					summary:
						'Pellentesque in ipsum id orci porta dapibus. Vivamus magna justo, lacinia eget consectetur sed, convallis at tellus. Nulla quis lorem ut libero malesuada feugiat. Pellentesque in ipsum id orci porta dapibus...',
				},
				{
					actionDropdownItems: [
						{
							href: '/delete/action/url',
							icon: 'trash',
							label: 'Delete',
						},
					],
					author: {
						avatarSrc: 'https://via.placeholder.com/150',
						email: 'john.doe@gmail.com',
						name: 'John Doe',
					},
					date: '14th April 2018',
					href: '/side-panel/email.html',
					status: {
						displayStyle: 'success',
						label: 'Order placed',
					},
					subject:
						'Curabitur aliquet quam id dui posuere blandit. Proin eget tortor risus.',
					summary:
						'Cras ultricies ligula sed magna dictum porta. Donec rutrum congue leo eget malesuada. Proin eget tortor risus. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Donec velit neque, auctor sit amet aliquam vel, ullamcorper sit amet ligula. Quisque velit nisi, pretium ut lacinia in, elementum id enim...',
				},
			],
			lastPage: 1,
			page: 1,
			pageSize: 10,
			totalCount: 2,
		});
	});

	let timeoutOn = false;
	let processEnded = false;
	let success = false;

	app.delete('/o/fake-bulk-action/v1.0/products/0/batch', (req, res) => {
		if (!timeoutOn) {
			timeoutOn = true;
			success = !success;
			setTimeout(() => {
				timeoutOn = false;
				processEnded = true;
			}, 2000);
		}

		return res.json({
			className:
				'com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product',
			contentType: 'JSON',
			errorMessage: '',
			executeStatus: 'INITIAL',
			id: 110,
			operation: 'DELETE',
		});
	});

	app.get('/o/fake-batch-engine/v1.0/import-task/:id', (req, res) => {
		if (processEnded && !timeoutOn) {
			processEnded = false;

			return res.json({
				className:
					'com.liferay.headless.commerce.admin.order.dto.v1_0.Order',
				contentType: 'JSON',
				endTime: '2020-06-08T15:08:02Z',
				errorMessage: 'Error: chocoPenguins are not defined',
				executeStatus: success ? 'COMPLETED' : 'FAILED',
				id: req.params.id,
				operation: 'DELETE',
				startTime: '2020-06-08T15:08:01Z',
			});
		}

		return res.json({
			className:
				'com.liferay.headless.commerce.admin.catalog.dto.v1_0.Product',
			contentType: 'JSON',
			endTime: '2020-06-08T15:13:34Z',
			errorMessage: '',
			executeStatus: 'STARTED',
			id: req.params.id,
			operation: 'DELETE',
			startTime: '2020-06-08T15:13:34Z',
		});
	});
}

module.exports = {
	defineServerResponses,
};
