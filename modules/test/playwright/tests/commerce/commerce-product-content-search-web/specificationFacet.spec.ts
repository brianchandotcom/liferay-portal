/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(apiHelpersTest, commercePagesTest, loginTest());

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
			case 'optionCategory':
				apiHelpers.headlessCommerceAdminCatalog.deleteOptionCategory(
					item.id
				);

				break;
			case 'product':
				await apiHelpers.headlessCommerceAdminCatalog.deleteProduct(
					item.id
				);

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
});

test('can sort specifications by specification group and label priority', async ({
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

	const channel = await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Specification Facet Channel',
		siteGroupId: site.id,
	});

	data.push({id: channel.id, type: 'channel'});

	const optionCategory1 =
		await apiHelpers.headlessCommerceAdminCatalog.postOptionCategory(
			'Warranty',
			1
		);

	data.push({id: optionCategory1.id, type: 'optionCategory'});

	const optionCategory2 =
		await apiHelpers.headlessCommerceAdminCatalog.postOptionCategory(
			'Material',
			0
		);

	data.push({id: optionCategory2.id, type: 'optionCategory'});

	const specification1 =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification(
			true,
			optionCategory2.priority,
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

	data.push({id: specification1.id, type: 'specification'});

	const specification2 =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification(
			true,
			optionCategory1.priority,
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

	data.push({id: specification2.id, type: 'specification'});

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Specification Facet Catalog',
	});

	data.push({id: catalog.id, type: 'catalog'});

	const product1 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'Product1',
		},
		productSpecifications: [
			{
				specificationKey: specification1.key,
				value: {
					en_US: 'Product1',
				},
			},
		],
	});

	data.push({id: product1.productId, type: 'product'});

	const product2 = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {
			en_US: 'Product2',
		},
		productSpecifications: [
			{
				specificationKey: specification2.key,
				value: {
					en_US: 'Product2',
				},
			},
		],
	});

	data.push({id: product2.productId, type: 'product'});

	await specificationFacetsPage.reloadPage();

	const panelList = await specificationFacetsPage.panelList.all();

	const specificationFacetsList = ['Material1', 'Warranty1'];

	for (let i = 0; i < specificationFacetsList.length; i++) {
		await expect(panelList[i]).toHaveText(specificationFacetsList[i]);
	}

	await specificationFacetsPage.configureSpecificationFacetOrdering(
		'label-priority:asc'
	);

	await specificationFacetsPage.reloadPage();

	const reverseSpecificationFacetsList = specificationFacetsList.reverse();

	for (let i = 0; i < reverseSpecificationFacetsList.length; i++) {
		await expect(panelList[i]).toHaveText(
			reverseSpecificationFacetsList[i]
		);
	}

	await commerceLayoutsPage.goToPages();
	await specificationFacetsPage.deleteSpecificationPage();
});
