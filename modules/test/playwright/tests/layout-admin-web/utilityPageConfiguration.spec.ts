/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import getRandomString from '../../utils/getRandomString';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
	pagesPagesTest
);

test('LPD-4459: Asserts the Utility Pages configuration view.', async ({
	page,
	pageEditorPage,
	site,
	utilityPageConfigurationPage,
	utilityPagesPage,
}) => {
	await page.goto('/');

	// The configuration action must be available from the card
	// The configuration view should only allow setting the htmlTitle and htmlDescription SEO fields

	await utilityPagesPage.goto(site.friendlyUrlPath);
	await utilityPageConfigurationPage.setUtilityPageConfiguration(
		getRandomString(),
		getRandomString(),
		'404 Error'
	);

	// During editing the "More Page Design Options" link should not be available

	await utilityPagesPage.goto(site.friendlyUrlPath);
	await utilityPagesPage.goToEdit('404 Error');
	await pageEditorPage.goToSidebarTab('Page Design Options');

	await expect(page.getByText('Master', {exact: true})).toBeVisible();
	expect(await page.getByTitle('More Page Design Options').count()).toEqual(
		0
	);
});
