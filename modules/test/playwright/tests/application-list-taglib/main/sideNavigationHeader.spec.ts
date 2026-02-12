/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {waitForPageToBeLoaded} from '../../../utils/waitForPageToBeLoaded';

const test = mergeTests(
	applicationsMenuPageTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest()
);

test.beforeEach(async ({applicationsMenuPage, page}) => {
	await applicationsMenuPage.goToInstanceSettings();

	await expect(page.getByTestId('sideNavigation')).toBeVisible();
});

test(
	'A user can select a site using the site selector',
	{tag: '@LPD-73706'},
	async ({page}) => {
		await test.step('Open site selector and select a site', async () => {
			const sideNavigation = page.getByTestId('sideNavigation');

			await clickAndExpectToBeVisible({
				target: page.getByRole('heading', {
					name: 'Select Site',
				}),
				trigger: sideNavigation.getByRole('button', {
					name: 'Go to Other Site',
				}),
			});

			await page
				.frameLocator('iframe[title="Select Site"]')
				.getByRole('link', {name: /^Liferay( DXP)?$/})
				.click();

			await waitForPageToBeLoaded(page);

			await expect(sideNavigation).toBeHidden();
		});
	}
);
