/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(apiHelpersTest, commercePagesTest, loginTest);

test('can sort specifications by specification group priority', async ({
	apiHelpers,
	commerceLayoutsPage,
	specificationFacetsPage,
}) => {
	const pageLabel = 'Specification Facet Page';

	await commerceLayoutsPage.goToPages();
	await commerceLayoutsPage.createWidgetPage(pageLabel);
	await specificationFacetsPage.goToPage();
	await specificationFacetsPage.addRequiredFacetWidgets();
	await specificationFacetsPage.configureSearchOptions();

	const site = await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
		'guest'
	);

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel(
		'Specification Facet Channel',
		site.id
	);

	const optionCategory1 =
		await apiHelpers.headlessCommerceAdminCatalog.postOptionCategory(
			'Warranty',
			1
		);
	const optionCategory2 =
		await apiHelpers.headlessCommerceAdminCatalog.postOptionCategory(
			'Material',
			0
		);

	const specification1 =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification(
			true,
			'Warranty1',
			{
				id: optionCategory1.id,
				key: optionCategory1.key,
				priority: optionCategory1.priority,
				title: {
					en_US: optionCategory1.key,
				},
			}
		);
	const specification2 =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification(
			true,
			'Material1',
			{
				id: optionCategory2.id,
				key: optionCategory2.key,
				priority: optionCategory2.priority,
				title: {
					en_US: optionCategory2.key,
				},
			}
		);

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog(
		'Specification Facet Catalog'
	);

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct(
		catalog.id,
		'Product1',
		[
			{
				specificationKey: specification1.key,
				value: {
					en_US: 'Product1',
				},
			},
		]
	);
	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct(
		catalog.id,
		'Product2',
		[
			{
				specificationKey: specification2.key,
				value: {
					en_US: 'Product2',
				},
			},
		]
	);

	await specificationFacetsPage.reloadPage();

	const panelList = await specificationFacetsPage.panelList.all();

	const specificationFacetsList = ['Material1', 'Warranty1'];

	for (let i = 0; i < specificationFacetsList.length; i++) {
		await expect(panelList[i]).toHaveText(specificationFacetsList[i]);
	}

	await Promise.all([
		apiHelpers.headlessCommerceAdminCatalog.deleteOptionCategory(
			optionCategory1.id
		),
		apiHelpers.headlessCommerceAdminCatalog.deleteOptionCategory(
			optionCategory2.id
		),
		apiHelpers.headlessCommerceAdminChannel.deleteChannel(channel.id),
	]);

	await apiHelpers.headlessCommerceAdminCatalog.deleteSpecification(
		specification1.id
	);
	await apiHelpers.headlessCommerceAdminCatalog.deleteSpecification(
		specification2.id
	);

	await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
		product1.productId
	);
	await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
		product2.productId
	);

	await apiHelpers.headlessCommerceAdminCatalog.deleteCatalog(catalog.id);

	await commerceLayoutsPage.goToPages();
	await specificationFacetsPage.deleteSpecificationPage();
});
