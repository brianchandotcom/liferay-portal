/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {contentDashboardPagesTest} from './fixtures/contentDashboardPagesTest';

export const test = mergeTests(
	contentDashboardPagesTest,
	featureFlagsTest({
		'LPD-25680': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test('displays second dropdown level for Size item with Small, Medium and Large options', async ({
	contentDashboardPage,
	page,
	site,
}) => {
	await contentDashboardPage.goto(site.friendlyUrlPath);

	await contentDashboardPage.openFilterDropdown();

	const sizeMenuItem = page.getByRole('menuitem', {name: 'Size'});

	await sizeMenuItem.hover();

	for (const size of ['Small', 'Medium', 'Large']) {
		await expect(page.getByText(size)).toBeVisible();
	}
});
