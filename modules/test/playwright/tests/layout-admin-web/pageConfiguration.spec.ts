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
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
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

test.describe('General configuration', () => {
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

		await clickAndExpectToBeVisible({
			target: page.locator('.modal-dialog'),
			trigger: page
				.locator('.layout-type')
				.getByRole('button', {name: 'Select'}),
		});

		const modal = await pageSelectorPage.getModal();
		await modal.locator('.treeview').waitFor();

		await expect(modal.getByText('Sites and Libraries')).not.toBeVisible();
	});
});

test.describe('Page types configuration', () => {
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

		await pageConfigurationPage.setInputValueAndSave(
			page.getByLabel('URL').first(),
			'Embedded',
			'General',
			'https://www.google.com'
		);

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

		await pageConfigurationPage.goToSection(
			'Full Page Application',
			'General'
		);

		await selectAndExpectToHaveValue({
			optionLabel: 'Wiki',
			select: page.getByLabel('Full Page Application'),
		});

		await pageConfigurationPage.save();

		// Go to view mode of page

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		await expect(page.getByRole('heading', {name: 'Wiki'})).toBeVisible();
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

	test(
		'Can configure a link to page',
		{
			tag: '@LPS-159631',
		},
		async ({
			apiHelpers,
			page,
			pageConfigurationPage,
			pagesAdminPage,
			site,
		}) => {

			// Create widget page

			const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: getRandomString(),
			});

			// Create link to URL page

			const linkToURLTitle = getRandomString();

			await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				options: {
					type: 'url',
				},
				title: linkToURLTitle,
			});

			// Update URL

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pageConfigurationPage.setInputValueAndSave(
				page.getByLabel('URL').first(),
				linkToURLTitle,
				'General',
				'https://www.google.com'
			);

			// Navigate to page

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

			// Check target is not set

			await expect(
				page.getByRole('menuitem', {name: linkToURLTitle})
			).not.toHaveAttribute('target');

			// Update specific target to _blank

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pageConfigurationPage.setInputValueAndSave(
				page.getByLabel('Target', {exact: true}),
				linkToURLTitle,
				'General',
				'_blank'
			);

			// Navigate to page

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

			// Check target is set to _blank

			await expect(
				page.getByRole('menuitem', {name: linkToURLTitle})
			).toHaveAttribute('target', '_blank');

			// Update target type to new tab

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pageConfigurationPage.goToSection(linkToURLTitle, 'General');

			await selectAndExpectToHaveValue({
				optionLabel: 'New Tab',
				select: page.getByLabel('Target type'),
			});

			await pageConfigurationPage.save();

			// Navigate to page

			await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

			// Check target is set to _blank

			await expect(
				page.getByRole('menuitem', {name: linkToURLTitle})
			).toHaveAttribute('target', '_blank');
		}
	);
});

test.describe('SEO configuration', () => {
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
});

test.describe('Utility Page', () => {
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
});

test.describe('JavaScript Client Extensions', () => {
	test(
		'Add JS extension to page',
		{
			tag: '@LPS-153658',
		},
		async ({apiHelpers, page, pagesAdminPage, site}) => {

			// Create a new JS client extension with a script element attribute

			const clientExtensionName = getRandomString();

			const clientExtension =
				await apiHelpers.jsonWebServicesClientExtension.addClientExtension(
					{
						name: clientExtensionName,
						type: 'globalJS',
						url: 'https://www.example.com/script.js',
					}
				);

			// Create a layout

			const layoutTitle = getRandomString();

			await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: layoutTitle,
			});

			// Apply JS client extension to page

			await pagesAdminPage.selectJavaScriptClientExtension(
				clientExtensionName,
				layoutTitle,
				site.friendlyUrlPath
			);

			// Check JS is attached to the page in view mode

			await page.goto(`/web${site.friendlyUrlPath}/${layoutTitle}`);

			await expect(
				page.locator(`script[src="https://www.example.com/script.js"]`)
			).toBeAttached();

			// Clean up

			await apiHelpers.jsonWebServicesClientExtension.deleteClientExtension(
				clientExtension.clientExtensionEntryId
			);
		}
	);

	test(
		'Inherited JS extensions from pages should be read-only mode',
		{
			tag: '@LPS-153658',
		},
		async ({
			apiHelpers,
			page,
			pageConfigurationPage,
			pagesAdminPage,
			site,
		}) => {

			// Create a new JS client extension with a script element attribute

			const clientExtensionName = getRandomString();

			const clientExtension =
				await apiHelpers.jsonWebServicesClientExtension.addClientExtension(
					{
						name: clientExtensionName,
						type: 'globalJS',
						url: 'https://www.example.com/script.js',
					}
				);

			// Apply JS client extension to all pages

			await pagesAdminPage.selectJavaScriptClientExtension(
				clientExtensionName,
				site.friendlyUrlPath
			);

			// Create a layout

			const layoutTitle = getRandomString();

			await apiHelpers.jsonWebServicesLayout.addLayout({
				groupId: site.id,
				title: layoutTitle,
			});

			// Check inherited JS client extension is read-only

			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pageConfigurationPage.goToSection(layoutTitle, 'Design');

			await pagesAdminPage.clickOnJavaScriptClientExtensionsTab();

			await expect(
				page
					.locator(
						'.global-js-cets-configuration .table tbody .disabled'
					)
					.getByText('From Pages')
			).toBeVisible();

			// Clean up

			await apiHelpers.jsonWebServicesClientExtension.deleteClientExtension(
				clientExtension.clientExtensionEntryId
			);
		}
	);
});
