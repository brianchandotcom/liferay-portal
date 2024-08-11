/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {fragmentsPagesTest} from '../../fixtures/fragmentPagesTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	fragmentsPagesTest
);

async function checkBackButtonTitle(page: Page, title: string) {
	await expect(
		page.locator('.control-menu-nav-item').getByTitle(title)
	).toBeVisible();
}

test(
	'Back button have correct title in edit fragment',
	{
		tag: '@LPS-177682',
	},
	async ({fragmentsPage, page, site}) => {

		// Go to fragment administration and create fragment set

		await fragmentsPage.goto(site.friendlyUrlPath);

		const setName = getRandomString();

		await fragmentsPage.createFragmentSet(setName);

		// Create fragment

		await fragmentsPage.createFragment(setName, getRandomString());

		await checkBackButtonTitle(page, 'Go to Fragments');
	}
);

test(
	'Can add fragment set during copy OOTB fragment when doesnt have fragment set',
	{
		tag: ['@LPS-166203', '@LPS-101354', '@LPS-89115'],
	},
	async ({fragmentsPage, page, site}) => {

		// Go to fragment administration

		await fragmentsPage.goto(site.friendlyUrlPath);

		// Go to Basic Components

		await fragmentsPage.gotoFragmentSet('Basic Components');

		// Copy Button into Untitled Set

		await fragmentsPage.copyToFragment('Button');

		// Go to Untitled Set

		await fragmentsPage.gotoFragmentSet('Untitled Set');

		await expect(
			page.getByRole('link').filter({hasText: 'Button (Copy)'})
		).toBeVisible();
	}
);

test(
	'Can add, delete, copy and rename a fragment via UI',
	{
		tag: '@LPS-97184',
	},
	async ({fragmentsPage, page, site}) => {

		// Go to fragment administration and create fragment set

		await fragmentsPage.goto(site.friendlyUrlPath);

		const fragmentSetName = getRandomString();

		await fragmentsPage.createFragmentSet(fragmentSetName);

		// Create fragment

		const fragmentName = getRandomString();

		await fragmentsPage.createFragment(fragmentSetName, fragmentName);

		await expect(
			page.getByTitle(fragmentName, {exact: true})
		).toBeVisible();

		// Copy fragment

		await fragmentsPage.copyFragment(fragmentName);

		await expect(
			page.getByTitle(`${fragmentName} (Copy)`, {exact: true})
		).toBeVisible();

		// Delete fragment

		await fragmentsPage.deleteFragment(`${fragmentName} (Copy)`);

		await expect(
			page.getByTitle(`${fragmentName} (Copy)`, {exact: true})
		).not.toBeVisible();

		// Rename fragment

		const newFragmentName = getRandomString();

		await fragmentsPage.renameFragment(newFragmentName, fragmentName);

		await expect(
			page.getByTitle(fragmentName, {exact: true})
		).not.toBeVisible();

		await expect(
			page.getByTitle(newFragmentName, {exact: true})
		).toBeVisible();
	}
);

test(
	'Can check cacheable for fragments when create them in portal and they are non-cacheable by default',
	{
		tag: '@LPS-108376',
	},
	async ({fragmentsPage, page, site}) => {

		// Go to fragment administration and create fragment set

		await fragmentsPage.goto(site.friendlyUrlPath);

		const fragmentSetName = getRandomString();

		await fragmentsPage.createFragmentSet(fragmentSetName);

		// Create fragment

		const fragmentName = getRandomString();

		await fragmentsPage.createFragment(fragmentSetName, fragmentName);

		await expect(
			page.locator('span').filter({hasText: 'Cached'}).first()
		).not.toBeVisible();

		await fragmentsPage.markAsDefault(fragmentName);

		await expect(
			page.locator('span').filter({hasText: 'Cached'}).first()
		).toBeVisible();
	}
);

test('Can delete fragment set', async ({fragmentsPage, page, site}) => {

	// Go to fragment administration

	await fragmentsPage.goto(site.friendlyUrlPath);

	// Create fragment set

	const fragmentSetName = getRandomString();

	await fragmentsPage.createFragmentSet(fragmentSetName);

	await expect(
		page.getByRole('menuitem', {exact: true, name: fragmentSetName})
	).toBeVisible();

	// Go to Basic Components fragment set

	await fragmentsPage.deleteFragmentSet();

	await expect(
		page.getByRole('menuitem', {exact: true, name: fragmentSetName})
	).not.toBeVisible();
});

test(
	'Can see contributed fragment set in fragment administration',
	{
		tag: '@LPS-89115',
	},
	async ({fragmentsPage, page, site}) => {

		// Go to fragment administration

		await fragmentsPage.goto(site.friendlyUrlPath);

		// Go to Basic Components fragment set

		await fragmentsPage.gotoFragmentSet('Basic Components');

		await expect(
			page.getByRole('link').filter({hasText: 'Button'})
		).toBeVisible();

		// Go to Basic Components fragment set

		await fragmentsPage.gotoFragmentSet('Featured Content');

		await expect(
			page.getByRole('link').filter({hasText: 'Banner Slider'})
		).toBeVisible();
	}
);

test(
	'No alert popup when add a fragment set with XSS name',
	{
		tag: '@LPS-121200',
	},
	async ({apiHelpers, fragmentsPage, page}) => {

		// Add listener with expect so it fails when a browser dialog is shown

		page.on('dialog', async (dialog) => {
			dialog.accept();

			expect(
				dialog.message(),
				'This alert should not be shown'
			).toBeNull();
		});

		// Create go to fragment administration to check dialog is not shown

		const site = await apiHelpers.headlessSite.createSite({
			name: '<script>alert(123);</script>',
		});

		await fragmentsPage.goto(site.friendlyUrlPath);
	}
);
