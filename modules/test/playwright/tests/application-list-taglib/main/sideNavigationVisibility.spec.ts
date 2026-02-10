/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {waitForPageToBeLoaded} from '../../../utils/waitForPageToBeLoaded';

const test = mergeTests(
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest()
);

test(
	'The side navigation is visible by default',
	{tag: '@LPD-73706'},
	async ({applicationsMenuPage, page}) => {
		const testCases = [
			{
				name: 'Applications',
				navigateToPage: () =>
					applicationsMenuPage.goToClientExtensions(),
			},
			{
				name: 'Commerce',
				navigateToPage: () =>
					applicationsMenuPage.goToCommerceSpecifications(),
			},
			{
				name: 'Control Panel',
				navigateToPage: () =>
					applicationsMenuPage.goToInstanceSettings(),
			},
		];

		for (const {name, navigateToPage} of testCases) {
			await test.step(`Go to a page from the "${name}" panel and check if navigation is visible`, async () => {
				await navigateToPage();

				await waitForPageToBeLoaded(page);

				const sideNavigation = page.getByTestId('sideNavigation');

				await sideNavigation.waitFor({state: 'visible'});

				await expect(sideNavigation).toBeVisible();
				await expect(sideNavigation).toHaveAccessibleName(name);
				await expect(
					page.getByTestId('sideNavigationLabel')
				).toHaveText(name);
			});
		}
	}
);

test(
	'The close button hides the side navigation',
	{tag: '@LPD-73706'},
	async ({applicationsMenuPage, page}) => {
		await test.step('Go to an Applications Panel page', async () => {
			await applicationsMenuPage.goToClientExtensions();
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
	async ({applicationsMenuPage, page}) => {
		await test.step('Go to an Applications Panel page', async () => {
			await applicationsMenuPage.goToClientExtensions();
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
	async ({applicationsMenuPage, page}) => {
		await test.step('Go to an Applications Panel page', async () => {
			await applicationsMenuPage.goToClientExtensions();
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
