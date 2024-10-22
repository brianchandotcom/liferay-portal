/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-13652 Product specification fragment only shows correct specifications', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Specification Fragment Channel',
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Specification Fragment Catalog',
	});

	const specification =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification(
			true,
			0,
			'Test Specification'
		);

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

	await apiHelpers.headlessCommerceAdminCatalog.postProduct({
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

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToDisplayPageTemplates();
	await commerceLayoutsPage.createDisplayPageTemplate(
		'Product Details',
		'Product',
		site.name
	);
	await commerceLayoutsPage.addProductFragment('Price');

	await commerceLayoutsPage.addProductFragment('Product Specification');

	await page
		.getByText('The Product Specification component will be shown here.')
		.click();
	await page
		.getByLabel('Key', {exact: true})
		.fill(product1.productSpecifications[0].key);

	await commerceLayoutsPage.selectDisplayPageTemplatePreviewItem('Product1');

	await expect(page.getByText('Test Specification Product1')).toBeVisible();

	await commerceLayoutsPage.showLabelInput.uncheck();
	await commerceLayoutsPage.selectDisplayPageTemplatePreviewItem('Product2');

	await expect(page.getByText('Test Specification')).toBeHidden();
});
