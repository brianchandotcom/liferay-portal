/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../fixtures/commercePagesTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	loginTest()
);

const data = [];

test.afterEach(async ({apiHelpers}) => {
	for await (const item of data.reverse()) {
		switch (item.type) {
			case 'catalog':
				await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(
					item.id
				);

				break;
			case 'channel':
				await apiHelpers.headlessCommerceAdminChannel.deleteChannel(
					item.id
				);

				break;
			case 'product':
				await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
					item.id
				);

				break;
			case 'site':
				await apiHelpers.headlessSite.deleteSite(item.id);

				break;
			case 'specification':
				await apiHelpers.headlessCommerceAdminCatalog.deleteSpecification(
					item.id
				);

				break;
			default:
				break;
		}
	}

	await apiHelpers.featureFlag.updateFeatureFlag('LPD-10856', false);
});

test('Product specification fragment only shows correct specifications', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
}) => {
	await apiHelpers.featureFlag.updateFeatureFlag('LPD-10856', true);

	const site = await apiHelpers.headlessSite.createSite({
		name: 'Specification Fragment Site',
	});

	data.push({id: site.id, type: 'site'});

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Specification Fragment Channel',
		siteGroupId: site.id,
	});

	data.push({id: channel.id, type: 'channel'});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Specification Fragment Catalog',
	});

	const specification =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification(
			true,
			0,
			'Test Specification'
		);

	data.push({id: specification.id, type: 'specification'});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
		productSpecifications: [
			{
				specificationKey: specification.key,
				value: {
					en_US: 'Product1',
				},
			},
		],
	});

	data.push({id: product1.productId, type: 'product'});

	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product2'},
		productSpecifications: [
			{
				specificationKey: specification.key,
				value: {
					en_US: 'Product2',
				},
			},
		],
	});

	data.push({id: product2.productId, type: 'product'});

	await applicationsMenuPage.goToSite('Specification Fragment Site');

	await commerceLayoutsPage.goToDisplayPageTemplates();
	await commerceLayoutsPage.createDisplayPageTemplate('Product Details');
	await commerceLayoutsPage.addProductFragment('Product Specification');

	await page
		.getByText('The Product Specification component will be shown here.')
		.click();
	await page.getByLabel('Key').fill(product1.productSpecifications[0].key);

	await commerceLayoutsPage.selectDisplayPageTemplatePreviewItem('Product1');

	await expect(page.getByText('Test Specification Product1')).toBeVisible();

	await commerceLayoutsPage.showLabelInput.uncheck();
	await commerceLayoutsPage.selectDisplayPageTemplatePreviewItem('Product2');

	await expect(page.getByText('Test Specification')).toBeHidden();
});
