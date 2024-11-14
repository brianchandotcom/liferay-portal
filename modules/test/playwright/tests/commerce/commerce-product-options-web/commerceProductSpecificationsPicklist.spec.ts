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

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);

test('LPD-22572 Picklist on product specifications page', async ({
	apiHelpers,
	commerceAdminProductPage,
	productDetailsPage,
}) => {
	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Catalog',
	});

	apiHelpers.data.push({id: catalog.id, type: 'catalog'});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

	apiHelpers.data.push({id: product.id, type: 'product'});

	const specification =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification();

	const picklist =
		await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

	await apiHelpers.listTypeAdmin.postListTypeEntry(
		picklist.externalReferenceCode,
		'item1'
	);

	await apiHelpers.headlessCommerceAdminCatalog.patchSpecification(
		specification.id,
		[picklist.id]
	);

	await commerceAdminProductPage.gotoProduct(product.name['en_US']);

	await productDetailsPage.addSpecificationToProduct(
		'Add an Existing Specification',
		specification.title.en_US,
		'item1'
	);

	await expect(
		await productDetailsPage.checkSpecificationProduct(
			specification.title.en_US
		)
	).toBeVisible();

	await apiHelpers.listTypeAdmin.postListTypeEntry(
		picklist.externalReferenceCode,
		'item2'
	);

	await productDetailsPage.changeValueInProductSpecification('Edit', 'item2');

	await expect(productDetailsPage.waitForEditScuccessMessage).toBeVisible();

	await productDetailsPage.closeEditFrame.click();

	await expect(
		await productDetailsPage.checkSpecificationProduct('item2')
	).toBeVisible();

	await productDetailsPage.createSpecificationProduct(
		'Create New Specification',
		'Specification-1',
		'item3'
	);

	await expect(
		await productDetailsPage.checkSpecificationProduct('item3')
	).toBeVisible();

	const specifications =
		await apiHelpers.headlessCommerceAdminCatalog.getSpecifications();

	for (let i = 0; i < specifications.totalCount; i++) {
		await apiHelpers.headlessCommerceAdminCatalog.deleteSpecification(
			specifications.items[i].id
		);
	}

	await apiHelpers.listTypeAdmin.deleteListTypeDefinition(picklist.id);
});

test('LPD-29336 Multiple picklist on product specifications page', async ({
	apiHelpers,
	commerceAdminProductPage,
	productDetailsPage,
}) => {
	const catalog = await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
		name: 'Catalog',
	});

	apiHelpers.data.push({id: catalog.id, type: 'catalog'});

	const product = await apiHelpers.headlessCommerceAdminCatalog.postProduct({
		catalogId: catalog.id,
		name: {en_US: 'Product1'},
	});

	apiHelpers.data.push({id: product.id, type: 'product'});

	const specification =
		await apiHelpers.headlessCommerceAdminCatalog.postSpecification();

	const picklist1 =
		await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

	await apiHelpers.listTypeAdmin.postListTypeEntry(
		picklist1.externalReferenceCode,
		'item1'
	);

	const picklist2 =
		await apiHelpers.listTypeAdmin.postRandomListTypeDefinition();

	await apiHelpers.listTypeAdmin.postListTypeEntry(
		picklist2.externalReferenceCode,
		'item2'
	);

	await apiHelpers.headlessCommerceAdminCatalog.patchSpecification(
		specification.id,
		[picklist1.id, picklist2.id]
	);

	await commerceAdminProductPage.gotoProduct(product.name['en_US']);

	await productDetailsPage.addSpecificationToProduct(
		'Add an Existing Specification',
		specification.title.en_US,
		'item1'
	);

	await expect(
		await productDetailsPage.checkSpecificationProduct('item1')
	).toBeVisible();

	await productDetailsPage.addSpecificationToProduct(
		'Add an Existing Specification',
		specification.title.en_US,
		'item2'
	);

	await expect(
		await productDetailsPage.checkSpecificationProduct('item2')
	).toBeVisible();

	const specifications =
		await apiHelpers.headlessCommerceAdminCatalog.getSpecifications();

	for (let i = 0; i < specifications.totalCount; i++) {
		await apiHelpers.headlessCommerceAdminCatalog.deleteSpecification(
			specifications.items[i].id
		);
	}

	await apiHelpers.listTypeAdmin.deleteListTypeDefinition(picklist1.id);
	await apiHelpers.listTypeAdmin.deleteListTypeDefinition(picklist2.id);
});
