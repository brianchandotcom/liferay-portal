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
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	loginTest()
);
test(
	'Recursive Product Window Reopens When Saving Specification Value',
	{tag: '@LPD-46276'},
	async ({
		apiHelpers,
		commerceAdminProductPage,
		page,
		productDetailsPage,
	}) => {
		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog({
				name: getRandomString(),
			});

		apiHelpers.data.push({id: catalog.id, type: 'catalog'});

		const specification =
			await apiHelpers.headlessCommerceAdminCatalog.postSpecification();

		const product =
			await apiHelpers.headlessCommerceAdminCatalog.postProduct({
				catalogId: catalog.id,
				name: {en_US: getRandomString()},
				productSpecifications: [
					{
						specificationKey: specification.key,
						value: {
							en_US: getRandomString(),
						},
					},
				],
			});

		apiHelpers.data.push({id: product.id, type: 'product'});

		await commerceAdminProductPage.gotoProduct(product.name['en_US']);

		await expect(
			await productDetailsPage.checkSpecificationProduct(
				specification.title.en_US
			)
		).toBeVisible();

		await productDetailsPage.ellipsisProductSpecification.click();
		await productDetailsPage.dropdownProductSpecification('Edit').click();

		const randomSpecificationValue = getRandomString();

		await productDetailsPage.editFrameSpecificationProductValue.fill(
			randomSpecificationValue
		);

		await productDetailsPage.saveButtonEditFrame.click();

		await waitForAlert(
			productDetailsPage.ellipsisFrameProductSpecification
		);

		await productDetailsPage.closeEditFrame.click();

		await expect(page.getByText(randomSpecificationValue)).toBeVisible();
	}
);
