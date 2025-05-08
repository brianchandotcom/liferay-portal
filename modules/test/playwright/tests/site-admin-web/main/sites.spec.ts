/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {sitesAdminPagesTest} from './fixtures/sitesAdminPagesTest';

const test = mergeTests(apiHelpersTest, loginTest(), sitesAdminPagesTest);

test('User can add and delete site and child site', async ({
	page,
	sitesAdminPage,
}) => {
	const childSiteName = getRandomString();
	const parentSiteName = getRandomString();

	await sitesAdminPage.goto();

	await page.getByRole('link', {name: 'Add Site'}).click();
	await sitesAdminPage.addBlankSite(parentSiteName);

	await sitesAdminPage.goto();

	await expect(page.getByText(parentSiteName)).toBeVisible();

	await sitesAdminPage.addChildSite(childSiteName, parentSiteName);

	await sitesAdminPage.goto();

	await expect(
		page
			.getByRole('row', {name: parentSiteName})
			.getByRole('cell', {exact: true, name: '1 Child Sites'})
	).toBeVisible();

	await sitesAdminPage.viewChildSites(parentSiteName);

	await expect(page.getByText(childSiteName)).toBeVisible();

	await sitesAdminPage.deleteSite(childSiteName);

	await expect(page.getByText('No sites were found.')).toBeVisible();

	await page.getByRole('link', {name: 'Go to Sites'}).click();

	await sitesAdminPage.deleteSite(parentSiteName);

	await expect(page.getByText(parentSiteName)).not.toBeVisible();
});
