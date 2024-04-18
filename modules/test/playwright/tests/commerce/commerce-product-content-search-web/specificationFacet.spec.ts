/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-13560 Can sort specifications by specification group and label priority', async ({
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

	await apiHelpers.headlessCommerceAdminChannel.postChannel({
		name: 'Specification Facet Channel',
		siteGroupId: site.id,
	});

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

	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Specification Facet Catalog',
	});

	await apiHelpers.headlessCommerceAdminCatalog.postProduct({
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

	await apiHelpers.headlessCommerceAdminCatalog.postProduct({
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
