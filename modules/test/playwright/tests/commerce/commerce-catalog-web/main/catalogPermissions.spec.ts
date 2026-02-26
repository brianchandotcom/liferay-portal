/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest()
);

test(
	'Site roles should not appear in Catalog permissions menus',
	{tag: '@LPD-55197'},
	async ({apiHelpers, commerceAdminCatalogsPage}) => {
		const catalog =
			await apiHelpers.headlessCommerceAdminCatalog.postCatalog();
		await commerceAdminCatalogsPage.goto();

		await commerceAdminCatalogsPage
			.catalogActionsButton(catalog.name)
			.click();
		await commerceAdminCatalogsPage.permissionsMenuItem.click();

		await expect(
			commerceAdminCatalogsPage.permissionsFrame.getByText(
				'Site Member',
				{exact: true}
			)
		).toHaveCount(0);
	}
);
