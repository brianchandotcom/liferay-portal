/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {InstanceSettingsPage} from '../../../pages/configuration-admin-web/InstanceSettingsPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

async function goToApplicationsMenuSetting(
	instanceSettingsPage: InstanceSettingsPage
) {
	await instanceSettingsPage.goto();

	await instanceSettingsPage.goToInstanceSetting(
		'Navigation',
		'Applications Menu'
	);
}

const test = mergeTests(
	instanceSettingsPagesTest,
	isolatedSiteTest,
	productMenuPageTest,
	loginTest()
);

test.beforeEach(async ({instanceSettingsPage, page, productMenuPage}) => {
	await goToApplicationsMenuSetting(instanceSettingsPage);

	const checkbox = page.getByRole('checkbox', {
		name: 'Enable Applications Menu',
	});

	if (await checkbox.isChecked()) {
		await checkbox.uncheck();
	}

	await instanceSettingsPage.saveAndWaitForAlert();

	await productMenuPage.openProductMenuIfClosed();
});

test.afterEach(async ({instanceSettingsPage}) => {
	await goToApplicationsMenuSetting(instanceSettingsPage);

	await instanceSettingsPage.resetInstanceSetting();
});

test(
	'The Product Menu replaces the Applications Menu when it is disabled',
	{tag: '@LPD-66980'},
	async ({page, productMenuPage, site}) => {
		const siteName = page.locator('.site-name');

		await test.step('It allows a user to choose a different site', async () => {
			await expect(siteName).toHaveText('Liferay DXP');

			const frame = page.frameLocator('iframe[title="Select Site"]');

			const allSitesTab = frame.getByRole('link', {name: 'All Sites'});

			await clickAndExpectToBeVisible({
				target: allSitesTab,
				trigger: page.getByRole('button', {name: 'Go to Other Site'}),
			});

			const siteLink = frame.getByRole('link', {
				exact: true,
				name: site.name,
			});

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: siteLink,
				trigger: allSitesTab,
			});

			await expect(siteName).toHaveText(site.name);
		});

		await test.step('Collapse Site Administration Panel', async () => {
			await clickAndExpectToBeVisible({
				target: page.getByRole('button', {
					exact: true,
					expanded: false,
					name: site.name,
				}),
				trigger: siteName,
			});
		});

		const testPages = [
			{
				category: 'Site Builder',
				name: 'Pages',
				panel: site.name,
			},
			{
				category: 'Content',
				name: 'Asset Libraries',
				panel: 'Applications',
			},
			{category: 'Order Management', name: 'Orders', panel: 'Commerce'},
			{category: 'Sites', name: 'Sites', panel: 'Control Panel'},
		];

		for (const {category, name, panel} of testPages) {
			await test.step(`Navigate to ${panel} > ${category} > ${name} via Product Menu`, async () => {
				await productMenuPage.goToPortlet(panel, category, name);

				await expect(
					page.getByRole('heading', {exact: true, name})
				).toBeVisible();
			});
		}
	}
);
