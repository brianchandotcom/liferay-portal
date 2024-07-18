/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-30188 Product publisher tag filters can be added and removed', async ({
	apiHelpers,
	applicationsMenuPage,
	commerceLayoutsPage,
	page,
	productPublisherPage,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: getRandomString(),
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		siteGroupId: site.id,
	});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog();

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
		tags: ['tag1'],
	});
	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product2'},
		tags: ['tag2'],
	});

	await applicationsMenuPage.goToSite(site.name);

	await commerceLayoutsPage.goToPages(false);
	await commerceLayoutsPage.createWidgetPage(getRandomString());

	await page.goto(`/web/${site.name}`);

	await productPublisherPage.addProductPublisherWidget();

	await expect(
		await productPublisherPage.productLink(product1.name.en_US)
	).toBeVisible();
	await expect(
		await productPublisherPage.productLink(product2.name.en_US)
	).toBeVisible();

	await productPublisherPage.addProductPublisherTagFilter('tag1');

	await page.goto(`/web/${site.name}`);

	await expect(
		await productPublisherPage.productLink(product1.name.en_US)
	).toBeVisible();
	await expect(
		await productPublisherPage.productLink(product2.name.en_US)
	).toHaveCount(0);

	await productPublisherPage.removeProductPublisherTagFilter('tag1');

	await page.goto(`/web/${site.name}`);

	await expect(
		await productPublisherPage.productLink(product1.name.en_US)
	).toBeVisible();
	await expect(
		await productPublisherPage.productLink(product2.name.en_US)
	).toBeVisible();
});
