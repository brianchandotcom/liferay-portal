/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {featureFlagPagesTest} from '../../../feature-flag-web/fixtures/featureFlagPagesTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';

export const test = mergeTests(
	dataSetsPageTest,
	featureFlagPagesTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest()
);

test.describe('Data Set Manager with Feature Flag Enabled', () => {
	test('Confirm the description in the FF Data Set @LPS-188590', async ({
		featureFlagsInstanceSettingsPage,
		page,
	}) => {
		await test.step('Navigate to Feature Flag page', async () => {
			await featureFlagsInstanceSettingsPage.goto('Beta');
		});

		await test.step('Check that the feature flag description is displayed', async () => {
			await expect(
				page.getByText(
					'Create tables to show data coming from headless resources. Choose the columns to show as well as how data will be paginated. Use a frontend data set cell renderer client extension to customize how data is rendered.'
				)
			).toBeVisible();
		});
	});

	test('Confirm that Cell Renderer is displayed in Client Extensions, when FF is enabled @LPS-188590', async ({
		dataSetsPage,
		page,
	}) => {
		await test.step('Navigate to Client Extensions page', async () => {
			await dataSetsPage.applicationsMenuPage.goToClientExtensions();
		});

		await test.step('Open add menu', async () => {
			await page.getByRole('button', {name: 'New'}).first().click();
		});

		await test.step('Check that Cell Renderer is displayed', async () => {
			await expect(
				page.getByRole('menuitem', {
					exact: true,
					name: 'Add Frontend Data Set Cell Renderer',
				})
			).toBeVisible();
		});
	});

	test('Confirm Data Set fragment is displayed if FF is enabled @LPS-188590', async ({
		page,
	}) => {
		await test.step('Go to home edit page', async () => {
			await page.goto(`/web/guest/home?p_l_mode=edit`);
		});

		await test.step('Check that "Data Set" is not displayed as a fragment', async () => {
			await page
				.getByLabel('Search Fragments and Widgets')
				.fill('Data Set');

			await expect(
				page.getByRole('menuitem', {
					exact: true,
					name: 'Data Set Add Data Set Mark Data Set as Favorite',
				})
			).toBeVisible();
		});
	});
});

export const disabledTest = mergeTests(
	dataSetsPageTest,
	featureFlagsTest({
		'LPS-164563': false,
	}),
	loginTest()
);

disabledTest.describe('Data Set Manager with Feature Flag Disabled', () => {
	disabledTest(
		'Confirm Data Set is not present if FF is not enabled @LPS-188590',
		async ({dataSetsPage, page}) => {
			await test.step('Open application menu and go to control panel tab', async () => {
				await dataSetsPage.applicationsMenuPage.goToControlPanel();
			});

			await test.step('Check that "Data Sets" is not displayed as a menu item', async () => {
				await expect(
					page.getByRole('menuitem', {
						exact: true,
						name: 'Data Sets',
					})
				).toBeHidden();
			});
		}
	);

	disabledTest(
		'Confirm that Cell Renderer is not displayed in Client Extensions, when FF is disabled @LPS-188590',
		async ({dataSetsPage, page}) => {
			await test.step('Navigate to Client Extensions page', async () => {
				await dataSetsPage.applicationsMenuPage.goToClientExtensions();
			});

			await test.step('Open add menu', async () => {
				await page.getByRole('button', {name: 'New'}).first().click();
			});

			await test.step('Check that Cell Renderer is displayed', async () => {
				await expect(
					page.getByRole('menuitem', {
						exact: true,
						name: 'Add Frontend Data Set Cell Renderer',
					})
				).toBeHidden();
			});
		}
	);

	disabledTest(
		'Confirm Data Set fragment is not displayed if FF is disabled @LPS-188590',
		async ({page}) => {
			await test.step('Go to home edit page', async () => {
				await page.goto(`/web/guest/home?p_l_mode=edit`);
			});

			await test.step('Check that "Data Set" is not displayed as a fragment', async () => {
				await page
					.getByLabel('Search Fragments and Widgets')
					.fill('Data Set');

				await expect(
					page.getByRole('menuitem', {
						exact: true,
						name: 'Data Set Add Data Set Mark Data Set as Favorite',
					})
				).toBeHidden();
			});
		}
	);
});
