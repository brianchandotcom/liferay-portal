/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {serverAdministrationPageTest} from '../../../fixtures/serverAdministrationPageTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	serverAdministrationPageTest
);

test(
	'Run check shows the result of the public friendly URL mapping check',
	{tag: '@LPD-93388'},
	async ({page, serverAdministrationPage}) => {

		// The trigger renders on first load

		await serverAdministrationPage.goto('friendly-urls');

		const runCheckButton = page.getByRole('button', {name: 'Run Check'});

		await expect(runCheckButton).toBeVisible();

		// Running the check renders a result and the trigger again

		await runCheckButton.click();

		await expect(
			page.locator('.sheet .alert-success, .sheet .alert-warning')
		).toBeVisible();

		await expect(
			page.getByRole('button', {name: 'Run Check Again'})
		).toBeVisible();
	}
);

test(
	'Run check reports a page that conflicts with a site friendly URL',
	{tag: '@LPD-93388'},
	async ({apiHelpers, page, serverAdministrationPage, site}) => {

		// Create a default site page whose friendly URL collides with the
		// isolated site's friendly URL

		const guestSite =
			await apiHelpers.headlessAdminUser.getSiteByFriendlyUrlPath(
				'guest'
			);

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: String(guestSite.id),
			title: site.friendlyUrlPath.substring(1),
		});

		try {

			// The conflict appears in the results table

			await serverAdministrationPage.goto('friendly-urls');

			await page.getByRole('button', {name: 'Run Check'}).click();

			await expect(page.locator('.sheet .alert-warning')).toBeVisible();

			await expect(
				page.getByText(site.friendlyUrlPath, {exact: true})
			).toBeVisible();
		}
		finally {
			await apiHelpers.jsonWebServicesLayout.deleteLayout(layout.plid);
		}
	}
);
