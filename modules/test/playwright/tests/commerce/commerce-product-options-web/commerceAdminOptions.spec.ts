/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-26249 Configure options and Product options', async ({
	apiHelpers,
	commerceAdminProductDetailsPage,
	commerceAdminProductDetailsProductOptionsPage,
	commerceAdminProductPage,
	page,
}) => {
	await page.goto('/');

	const option1 = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'color',
		'Color',
		1
	);

	const option2 = await apiHelpers.headlessCommerceAdminCatalog.postOption(
		'select',
		'size',
		'Size',
		2
	);

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Simple T-Shirt'},
		productOptions: [
			{
				fieldType: 'select',
				key: 'color',
				name: {
					en_US: 'Color',
				},
				optionId: option1.id,
				priority: 1,
				productOptionValues: [
					{
						key: 'black',
						name: {
							en_US: 'Black',
						},
						priority: 1,
					},
					{
						key: 'white',
						name: {
							en_US: 'White',
						},
						priority: 2,
					},
				],
				skuContributor: true,
			},
			{
				fieldType: 'select',
				key: 'size',
				name: {
					en_US: 'Size',
				},
				optionId: option2.id,
				priority: 2,
				productOptionValues: [
					{
						key: 'xs',
						name: {
							en_US: 'XS',
						},
						priority: 1,
					},
					{
						key: 'xl',
						name: {
							en_US: 'XL',
						},
						priority: 2,
					},
				],
				skuContributor: true,
			},
		],
	});

	await commerceAdminProductPage.gotoProduct(product.name['en_US']);

	await commerceAdminProductDetailsPage.goToProductOptions();

	await expect(
		(
			await commerceAdminProductDetailsProductOptionsPage.tableRow(
				0,
				option1.name['en_US'],
				true
			)
		).row
	).toBeVisible();
});
