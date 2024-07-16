/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pageSelectorPagesTest} from '../../fixtures/pageSelectorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {checkAccessibility} from '../../utils/checkAccessibility';
import getRandomString from '../../utils/getRandomString';
import {selectAndExpectToHaveValue} from '../../utils/selectAndExpectToHaveValue';
import {pagesPagesTest} from './fixtures/pagesPagesTest';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest,
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

test(
	'Asserts the Utility Pages configuration view',
	{
		tag: '@LPD-4459',
	},
	async ({
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
		expect(
			await page.getByTitle('More Page Design Options').count()
		).toEqual(0);
	}
);

test('Checks page SEO HTML title is not shown in edit mode', async ({
	apiHelpers,
	page,
	pageConfigurationPage,
	pageEditorPage,
	pagesAdminPage,
	site,
}) => {

	// Create page

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		siteId: site.id,
		title: pageName,
	});

	// Change SEO HTML title

	await pagesAdminPage.goto(site.friendlyUrlPath);
	await pageConfigurationPage.goToSection(pageName, 'SEO');

	const HTMLTitle = getRandomString();

	await pageConfigurationPage.setHTMLTitle(HTMLTitle);

	// Check SEO HTML title is shown in view mode

	await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`);

	expect(await page.title()).toBe(
		`${HTMLTitle} - ${site.name} - Liferay DXP`
	);

	// Check SEO HTML title is not shown in view mode

	await pageEditorPage.goto(layout, site.friendlyUrlPath);

	expect(await page.title()).toBe(
		`${pageName} - ${site.name} - Liferay DXP (Editing)`
	);
});
