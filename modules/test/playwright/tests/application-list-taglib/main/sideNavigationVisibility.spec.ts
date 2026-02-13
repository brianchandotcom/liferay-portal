/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {waitForPageToBeLoaded} from '../../../utils/waitForPageToBeLoaded';

const test = mergeTests(
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	globalMenuPagesTest,
	loginTest()
);

test(
	'The side navigation is visible by default',
	{tag: '@LPD-73706'},
	async ({globalMenuPage, page}) => {
		const testCases = [
			{
				image: 'applications_sm.svg',
				name: 'Applications',
				navigateToPage: () => globalMenuPage.goToApplications(),
			},
			{
				image: 'commerce_sm.svg',
				name: 'Commerce',
				navigateToPage: () => globalMenuPage.goToCommerce(),
			},
			{
				image: 'control_panel_sm.svg',
				name: 'Control Panel',
				navigateToPage: () => globalMenuPage.goToControlPanel(),
			},
		];

		for (const {image, name, navigateToPage} of testCases) {
			await test.step(`Go to a page from the "${name}" panel and check if navigation is visible`, async () => {
				await navigateToPage();

				await waitForPageToBeLoaded(page);

				const sideNavigation = page.getByTestId('sideNavigation');

				await expect(sideNavigation).toBeVisible();
				await expect(sideNavigation).toHaveAccessibleName(name);
				await expect(
					page.getByTestId('sideNavigationLabel')
				).toHaveText(name);
				await expect(
					sideNavigation.getByTestId('sideNavigationProductIcon')
				).toHaveAttribute('src', new RegExp(`${image}$`));
			});
		}
	}
);

test(
	'The close button hides the side navigation',
	{tag: '@LPD-73706'},
	async ({globalMenuPage, page}) => {
		await test.step('Go to an Applications Panel page', async () => {
			await globalMenuPage.goToApplications();
		});

		const sideNavigation = page.getByTestId('sideNavigation');
		const toggler = page.getByTestId('sideNavigationToggler');

		await test.step('Click the close button and check if navigation is hidden', async () => {
			await expect(sideNavigation).toBeVisible();

			await sideNavigation.getByRole('button', {name: 'Close'}).click();

			await expect(sideNavigation).toBeHidden();
		});

		await test.step('Reset the navigation visibility for the next tests', async () => {
			await toggler.click();

			await expect(sideNavigation).toBeVisible();
		});
	}
);

test(
	'The toggle button controls the visibility of the side navigation',
	{tag: '@LPD-73706'},
	async ({globalMenuPage, page}) => {
		await test.step('Go to an Applications Panel page', async () => {
			await globalMenuPage.goToApplications();
		});

		const sideNavigation = page.getByTestId('sideNavigation');
		const toggler = page.getByTestId('sideNavigationToggler');

		async function expectNavigationToBeVisible(visible: boolean) {
			await expect(sideNavigation).toBeVisible({visible});
			await expect(toggler).toHaveAttribute(
				'aria-expanded',
				visible.toString()
			);
		}

		await test.step('Click the close button and check if navigation is hidden', async () => {
			await expectNavigationToBeVisible(true);

			await toggler.click();

			await expectNavigationToBeVisible(false);

			await toggler.click();

			await expectNavigationToBeVisible(true);
		});
	}
);

test(
	'The side navigation visibility persists across page reloads',
	{tag: '@LPD-73706'},
	async ({globalMenuPage, page}) => {
		await test.step('Go to an Applications Panel page', async () => {
			await globalMenuPage.goToApplications();
		});

		const sideNavigation = page.getByTestId('sideNavigation');
		const toggler = page.getByTestId('sideNavigationToggler');

		const testCases = [
			{expectedState: false, initialState: true},
			{expectedState: true, initialState: false},
		];

		for (const {expectedState, initialState} of testCases) {
			await test.step(`Set the navigation visibility to ${expectedState} and assert after reload`, async () => {
				await expect(sideNavigation).toBeVisible({
					visible: initialState,
				});

				await toggler.click();

				await expect(sideNavigation).toBeVisible({
					visible: expectedState,
				});

				await page.reload();

				await waitForPageToBeLoaded(page);

				await expect(sideNavigation).toBeVisible({
					visible: expectedState,
				});
			});
		}
	}
);
