/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageSelectorPagesTest} from '../../fixtures/pageSelectorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import getRandomString from '../../utils/getRandomString';
import {selectAndExpectToHaveValue} from '../../utils/selectAndExpectToHaveValue';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageSelectorPagesTest,
	pagesAdminPagesTest,
	pagesPagesTest
);

test('Checks the accessibility of the General page configuration', async ({
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

test('Can configure an embedded page', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {
	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		options: {
			type: 'embedded',
		},
		title: 'Embedded',
	});

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection('Embedded', 'General');

	await expect(page.getByLabel('URL').first()).toHaveValue('');

	await pageConfigurationPage.fillURL('https://www.google.com');

	await pageConfigurationPage.save();

	// Check URL was updated

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection('Embedded', 'General');

	await expect(page.getByLabel('URL').first()).toHaveValue(
		'https://www.google.com'
	);
});

test('Can configure a full page application', async ({
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

test('Can not select pages from other sites for Link to a Page', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pageSelectorPage,
	pagesAdminPage,
	site,
}) => {

	// Create a widget page and a link to layout page

	const name = getRandomString();

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: getRandomString(),
	});

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		options: {
			type: 'link_to_layout',
		},
		title: name,
	});

	// Try to select linked page and check Sites and Libraries
	// section is not shown

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection(name, 'General');

	await page
		.locator('.layout-type')
		.getByRole('button', {name: 'Select'})
		.click();

	const modal = await pageSelectorPage.getModal();

	await modal.locator('.treeview').waitFor();

	await expect(modal.getByText('Sites and Libraries')).not.toBeVisible();
});

test('Can configure a panel page', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {

	// Create page and go to General configuration

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		options: {
			type: 'panel',
		},
		title: 'Panel',
	});

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection('Panel', 'General');

	// Select Collaboration application

	await page
		.locator('.treeview-link[data-id*="collaboration"]')
		.getByRole('checkbox')
		.check();

	await pageConfigurationPage.save();

	// Go to view mode of page

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await expect(
		page.getByRole('link', {exact: true, name: 'Blogs'})
	).toBeVisible();
});

test('Can edit the page name and layout template via pages administration', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pagesAdminPage,
	site,
}) => {

	// Create page and go to page configuration

	const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: 'Test Page Title',
	});

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pageConfigurationPage.goToSection('Test Page Title', 'General');

	// Fill name and change layout to 1 column

	await pageConfigurationPage.fillName('Test Page Title Edit');

	await page.getByTitle('1 Column', {exact: true}).click();

	// Check card is selected and save

	const card = page.locator('.card.card-interactive').first();

	await expect(card).toHaveClass(/active/);

	await pageConfigurationPage.save();

	// Go to view mode of page and check layout

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

	await expect(
		page.getByRole('heading', {name: 'Test Page Title Edit'})
	).toBeVisible();

	await expect(page.locator('#layout-column_column-1')).toBeAttached();
});
