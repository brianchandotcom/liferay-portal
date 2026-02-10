/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

const test = mergeTests(
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest()
);

async function setAllCategoriesExpanded(page: Page, expanded: boolean) {
	const buttons = page
		.getByTestId('sideNavigation')
		.getByRole('menuitem', {expanded: !expanded});

	const initialCount = await buttons.count();

	for (let position = 1; position <= initialCount; position++) {
		await buttons.first().click();

		await expect(buttons).toHaveCount(initialCount - position);
	}

	await expect(buttons).toHaveCount(0);
}

test.beforeEach(async ({applicationsMenuPage, page}) => {
	await applicationsMenuPage.goToInstanceSettings();

	await expect(page.getByTestId('sideNavigation')).toBeVisible();
});

test(
	'The category containing the current page is expanded by default',
	{tag: '@LPD-73706'},
	async ({applicationsMenuPage, page}) => {
		await test.step('Collapse all categories', async () => {
			await setAllCategoriesExpanded(page, false);
		});

		const testCases = [
			{
				categoryName: 'Configuration',
				navigateToPage: () =>
					applicationsMenuPage.goToInstanceSettings(),
			},
			{
				categoryName: 'Users',
				navigateToPage: () =>
					applicationsMenuPage.goToUsersAndOrganizations(),
			},
			{
				categoryName: 'Object',
				navigateToPage: () => applicationsMenuPage.goToPicklists(),
			},
		];

		for (const {categoryName, navigateToPage} of testCases) {
			await test.step(`Go to ${categoryName} and expect it to be expanded`, async () => {
				await navigateToPage();

				await expect(page.getByTestId('sideNavigation')).toBeVisible();

				const categories = page
					.getByTestId('sideNavigation')
					.getByRole('menuitem', {expanded: true});

				await expect(categories).toHaveCount(1);
				await expect(categories).toHaveAccessibleName(categoryName);
			});
		}
	}
);

test(
	'Each leaf navigation item has an icon, a label and a link to navigate to',
	{tag: '@LPD-73706'},
	async ({page}) => {
		await test.step('Expand categories', async () => {
			await setAllCategoriesExpanded(page, true);
		});

		await test.step('Check that each leaf item has an icon and a label', async () => {
			const leafItems = page
				.getByRole('menuitem')
				.and(page.locator('a.nav-link'));

			await expect(leafItems).not.toHaveCount(0);

			for (const leafItem of await leafItems.all()) {
				await expect(leafItem.locator('svg')).toBeVisible();
				await expect(leafItem).toHaveAccessibleName(
					await leafItem.textContent()
				);
				await expect(leafItem).toHaveAttribute('href', /.+/);
			}
		});

		await test.step('Reset the navigation visibility for the next tests', async () => {
			await setAllCategoriesExpanded(page, false);
		});
	}
);

test(
	'The leaf item links have active state based on the current page',
	{tag: '@LPD-73706'},
	async ({page}) => {
		await test.step('Click on a leaf item and check if it navigates to the correct page', async () => {
			const instanceSettingsItem = page.getByRole('menuitem', {
				name: 'Instance Settings',
			});

			await expect(instanceSettingsItem).toHaveClass(/active/);

			const systemSettingsItem = page.getByRole('menuitem', {
				name: 'System Settings',
			});

			await expect(systemSettingsItem).not.toHaveClass(/active/);

			await clickAndExpectToBeVisible({
				target: page.getByRole('heading', {name: 'System Settings'}),
				trigger: systemSettingsItem,
			});

			await expect(instanceSettingsItem).not.toHaveClass(/active/);
			await expect(systemSettingsItem).toHaveClass(/active/);
		});
	}
);
