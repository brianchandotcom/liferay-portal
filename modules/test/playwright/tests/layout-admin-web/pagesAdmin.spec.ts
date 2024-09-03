/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest
);

test('Changes the permissions of a group of pages', async ({
	apiHelpers,
	page,
	pagesAdminPage,
	site,
}) => {

	// Create two random pages

	const firstName = getRandomString();
	const secondName = getRandomString();

	for (const pageName of [firstName, secondName]) {
		await apiHelpers.headlessDelivery.createSitePage({
			siteId: site.id,
			title: pageName,
		});
	}

	// Go to admin page

	await pagesAdminPage.goto(site.friendlyUrlPath);

	// Select the first page and change the Guest-View permission

	await pagesAdminPage.selectPageAndChangePermissions(
		[firstName],
		['guest_ACTION_VIEW']
	);

	// Select the second page (keeping the first page checked) and open the modal of permissions

	await page
		.getByLabel(`Select ${secondName}`, {
			exact: true,
		})
		.check();

	await page.getByRole('button', {name: 'Permissions'}).click();

	const permissionsFrame = page.frameLocator('iframe[title="Permissions"]');

	await permissionsFrame
		.getByRole('cell', {exact: true, name: 'Role'})
		.waitFor();

	// Check that the Guest-View permission value for both pages is indeterminate

	const permission = permissionsFrame.locator('#guest_ACTION_VIEW');

	await expect(permission).toHaveValue('indeterminate');

	await page.getByLabel('close', {exact: true}).click();

	// Change the Guest-View permission for both pages

	await pagesAdminPage.selectPageAndChangePermissions(
		[firstName, secondName],
		['guest_ACTION_VIEW']
	);

	// Refresh the admin page

	await pagesAdminPage.goto(site.friendlyUrlPath);

	// Check if the pages are retricted pages

	for (const pageName of [firstName, secondName]) {
		await expect(
			page.getByLabel(`${pageName}. Restricted Page`)
		).toBeVisible();
	}
});

test('Checks the correct label for restricted pages in pages administration', async ({
	apiHelpers,
	page,
	pagesAdminPage,
	site,
}) => {

	// Create a page with only one permission

	const pageName = getRandomString();

	await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	// Go to admin page and check if the Restricted Page label is in the Miller Columns item

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await expect(
		page
			.locator('.miller-columns-item')
			.getByLabel(`${pageName}. Restricted Page`)
	).toBeVisible();
});

test('Can add and delete a child page', async ({
	apiHelpers,
	page,
	pagesAdminPage,
	site,
}) => {

	// Create parent page

	const parentPageName = getRandomString();

	await apiHelpers.jsonWebServicesLayout.addLayout({
		groupId: site.id,
		title: parentPageName,
	});

	// Create child page and check it actually appears as child

	const childPageName = getRandomString();

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await pagesAdminPage.createNewPage({
		draft: true,
		name: childPageName,
		parent: parentPageName,
	});

	await pagesAdminPage.goto(site.friendlyUrlPath);

	await page.getByRole('button', {name: parentPageName}).click();

	await expect(page.getByRole('link', {name: childPageName})).toBeVisible();

	// Check Draft label is shown and we can preview the draft

	await expect(
		page
			.locator('li', {has: page.getByText(childPageName)})
			.getByText('Draft')
	).toBeVisible();

	await clickAndExpectToBeVisible({
		target: page.getByRole('menuitem', {
			name: 'Preview Draft',
		}),
		trigger: page
			.locator('li', {has: page.getByText(childPageName)})
			.getByRole('button', {name: 'Open Page Options Menu'}),
	});

	// Delete child page

	await pagesAdminPage.deletePage(childPageName);

	await expect(
		page.getByRole('link', {name: childPageName})
	).not.toBeVisible();
});

test(
	'Can search a child page',
	{tag: ['@LPS-154130', '@LPS-149161', '@LPS-76825']},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Add a child page

		const layoutTitle = 'Parent Layout';

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: layoutTitle,
		});

		const childLayoutTitle = 'Child Layout';

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			parentLayoutId: layout.layoutId,
			title: childLayoutTitle,
		});

		// Go to admin page

		await pagesAdminPage.goto(site.friendlyUrlPath);

		// Search for parent page

		await pagesAdminPage.searchPage('Parent');

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: childLayoutTitle})
		).not.toBeVisible();

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: layoutTitle})
		).toBeVisible();

		// Search for child page

		await pagesAdminPage.searchPage('Child');

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: childLayoutTitle})
		).toBeVisible();

		await expect(
			page
				.locator('.lfr-title-column')
				.getByRole('link', {name: layoutTitle})
		).not.toBeVisible();

		// Order by create date

		await pagesAdminPage.searchPage('Layout');

		const listItem = page.locator('.lfr-title-column');

		await expect(listItem.nth(1)).toHaveText(layoutTitle);
		await expect(listItem.nth(2)).toHaveText(childLayoutTitle);

		// Navigate to page via relative path

		await page
			.locator('.breadcrumb-item')
			.getByRole('link', {name: layoutTitle})
			.click();

		await expect(page.getByText('Search Results')).not.toBeVisible();

		await expect(page.locator('.breadcrumb-item.active')).toHaveText(
			layoutTitle
		);
	}
);

test(
	'View the XSS is escaped when store it in widget page name',
	{
		tag: '@LPS-178476',
	},
	async ({apiHelpers, page, pagesAdminPage, site}) => {

		// Add listener with expect so it fails when a browser dialog is shown

		page.on('dialog', async (dialog) => {
			dialog.accept();

			expect(
				dialog.message(),
				'This alert should not be shown'
			).toBeNull();
		});

		// Create page and go to view mode to check dialog is not shown

		await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: '<script>alert(123);</script>',
		});

		await pagesAdminPage.goto(site.friendlyUrlPath);
	}
);
