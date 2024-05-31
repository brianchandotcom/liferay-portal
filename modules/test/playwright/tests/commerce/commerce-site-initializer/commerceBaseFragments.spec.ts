/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {commercePagesTest} from '../../../fixtures/commercePagesTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	applicationsMenuPageTest,
	commercePagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-20379': true,
	}),
	loginTest()
);

test('LPD-23780 Commerce Classic Header taglib fragments are correctly displayed', async ({
	apiHelpers,
	page,
}) => {
	const site = await apiHelpers.headlessSite.createSite({
		name: `classic-commerce`,
		templateKey: 'com.liferay.commerce.site.initializer',
		templateType: 'site-initializer',
	});

	apiHelpers.data.push({id: site.id, type: 'site'});

	await page.goto(`/web${site.friendlyUrlPath}`);

	const editPageLink = await page
		.locator('.control-menu-nav-item .lfr-portal-tooltip[title="Edit"] a')
		.getAttribute('href');

	await page.goto(editPageLink);

	await page.locator('button[title="Page Design Options"]').click();
	await page.locator('div[aria-label="Liferay Commerce Master"]').click();
	await page.getByText('Publish').click();

	const commerceHeaderTagFragments = page.locator(
		'#commerce-header-components'
	);

	await expect(commerceHeaderTagFragments).toBeVisible();

	await expect(
		commerceHeaderTagFragments.locator('.account-selector-root')
	).toHaveClass(/mr-6/);
	await expect(commerceHeaderTagFragments.locator('.cart-root')).toHaveClass(
		/sticky-top/
	);
});
