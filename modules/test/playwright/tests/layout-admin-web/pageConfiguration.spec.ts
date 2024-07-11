/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import {selectAndExpectToHaveValue} from '../../utils/selectAndExpectToHaveValue';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	pagesPagesTest
);

test('checks the accessibility of the General page configuration', async ({
	page,
}) => {
	await page.goto('/');

	await page.getByLabel('Configure Page').click();

	await expect(page).toHaveURL(/edit_layout/);

	await checkAccessibility({
		page,
		selectors: ['.input-container[aria-label="General"]'],
	});
});

test('Can configure a full page application.', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		options: {
			type: 'full_page_application',
		},
		title: 'Full Page Application',
	});

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection('Full Page Application', 'General');

	await selectAndExpectToHaveValue({
		optionLabel: 'Wiki',
		select: page.getByLabel('Full Page Application'),
	});

	await pageConfigurationPage.save();

	// Go to view mode of page

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await expect(page.getByRole('heading', {name: 'Wiki'})).toBeVisible();
});

test('Can edit the page name and layout template via pages administration.', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: 'Test Page Title',
	});

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection('Test Page Title', 'General');

	await pageConfigurationPage.fillName('Test Page Title Edit');

	await page.getByTitle('1 Column', {exact: true}).click();

	await pageConfigurationPage.save();

	// Go to view mode of page

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await expect(
		page.getByRole('heading', {name: 'Test Page Title Edit'})
	).toBeVisible();

	await expect(page.locator('#layout-column_column-1')).toBeAttached();
});
