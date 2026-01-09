/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {instanceSettingsPagesTest} from '../../../fixtures/instanceSettingsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {productMenuPageTest} from '../../../fixtures/productMenuPageTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import {InstanceSettingsPage} from '../../../pages/configuration-admin-web/InstanceSettingsPage';
import {ApplicationsMenuPage} from '../../../pages/product-navigation-applications-menu/ApplicationsMenuPage';
import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import performLogin from '../../../utils/performLogin';

const MENU_TEST_PAGES = [
	{category: 'Sites', name: 'Sites', panel: 'Control Panel'},
	{category: 'Order Management', name: 'Orders', panel: 'Commerce'},
	{
		category: 'Content',
		name: 'Asset Libraries',
		panel: 'Applications',
	},
];

const VIRTUAL_INSTANCE_NAME = 'www.able.com';

async function disableApplicationsMenu(
	instanceSettingsPage: InstanceSettingsPage,
	forceReload: boolean
) {
	await goToApplicationsMenuSetting(instanceSettingsPage, forceReload);

	const checkbox = instanceSettingsPage.page.getByRole('checkbox', {
		name: 'Enable Applications Menu',
	});

	if (await checkbox.isChecked()) {
		await checkbox.uncheck();
	}

	await instanceSettingsPage.saveAndWaitForAlert();
}

async function goToApplicationsMenuSetting(
	instanceSettingsPage: InstanceSettingsPage,
	forceReload: boolean
) {
	await instanceSettingsPage.goto(forceReload);

	await instanceSettingsPage.goToInstanceSetting(
		'Navigation',
		'Applications Menu',
		forceReload
	);
}

async function resetApplicationsMenu(
	instanceSettingsPage: InstanceSettingsPage,
	forceReload: boolean
) {
	await goToApplicationsMenuSetting(instanceSettingsPage, forceReload);

	await instanceSettingsPage.resetInstanceSetting();
}

const test = mergeTests(
	instanceSettingsPagesTest,
	isolatedSiteTest,
	productMenuPageTest,
	virtualInstancesPagesTest,
	loginTest()
);

test.describe('Disabled Applications Menu - Default Instance', () => {
	test.beforeEach(async ({instanceSettingsPage, productMenuPage}) => {
		await disableApplicationsMenu(instanceSettingsPage, true);

		await productMenuPage.openProductMenuIfClosed();
	});

	test.afterEach(async ({instanceSettingsPage}) => {
		await resetApplicationsMenu(instanceSettingsPage, true);
	});

	test(
		'The Product Menu replaces the Applications Menu when it is disabled',
		{tag: '@LPD-66980'},
		async ({page, productMenuPage, site}) => {
			const siteName = page.locator('.site-name');

			await test.step('It allows a user to choose a different site', async () => {
				await expect(siteName).toHaveText(/Liferay DXP|Choose a Site/);

				const frame = page.frameLocator('iframe[title="Select Site"]');

				const allSitesTab = frame.getByRole('link', {
					name: 'All Sites',
				});

				await clickAndExpectToBeVisible({
					target: allSitesTab,
					trigger: page.getByRole('button', {
						name: 'Go to Other Site',
					}),
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

			const menuTestPages = MENU_TEST_PAGES.concat({
				category: 'Site Builder',
				name: 'Pages',
				panel: site.name,
			});

			for (const {category, name, panel} of menuTestPages) {
				await test.step(`Navigate to ${panel} > ${category} > ${name} via Product Menu`, async () => {
					await productMenuPage.goToPortlet(panel, category, name);

					await expect(
						page.getByRole('heading', {exact: true, name})
					).toBeVisible();
				});
			}
		}
	);
});

test.describe('Disabled Applications Menu - Virtual Instance', () => {
	let virtualInstancePage: Page;
	let virtualInstanceInstanceSettingsPage: InstanceSettingsPage;

	test.slow();

	test.beforeEach(
		async ({browser, instanceSettingsPage, virtualInstancesPage}) => {
			await virtualInstancesPage.addNewVirtualInstance(
				VIRTUAL_INSTANCE_NAME
			);

			virtualInstancePage = await browser.newPage({
				baseURL: `http://${VIRTUAL_INSTANCE_NAME}:8080`,
			});

			virtualInstanceInstanceSettingsPage = new InstanceSettingsPage(
				virtualInstancePage
			);

			await performLogin(
				virtualInstancePage,
				'test',
				'',
				`@${VIRTUAL_INSTANCE_NAME}.com`
			);

			await disableApplicationsMenu(instanceSettingsPage, true);
		}
	);

	test.afterEach(async ({instanceSettingsPage, virtualInstancesPage}) => {
		await resetApplicationsMenu(virtualInstanceInstanceSettingsPage, false);
		await resetApplicationsMenu(instanceSettingsPage, true);

		await virtualInstancePage?.close();

		await virtualInstancesPage.deleteVirtualInstance(VIRTUAL_INSTANCE_NAME);
	});

	test(
		'The Product Menu replaces the Applications Menu when it is disabled in a Virtual Instance',
		{tag: '@LPD-66980'},
		async () => {
			const virtualInstanceProductMenuPage = new ProductMenuPage(
				virtualInstancePage
			);

			await test.step('Disable Applications Menu in virtual instance', async () => {
				const virtualInstanceApplicationsMenuPage =
					new ApplicationsMenuPage(virtualInstancePage);

				expect(
					await virtualInstanceApplicationsMenuPage.isApplicationsMenuButtonVisible()
				).toBeTruthy();

				await disableApplicationsMenu(
					virtualInstanceInstanceSettingsPage,
					false
				);

				expect(
					await virtualInstanceApplicationsMenuPage.isApplicationsMenuButtonVisible()
				).toBeFalsy();

				await virtualInstanceProductMenuPage.openProductMenuIfClosed();
			});

			for (const {category, name, panel} of MENU_TEST_PAGES) {
				await test.step(`Navigate to ${panel} > ${category} > ${name} via Product Menu`, async () => {
					await virtualInstanceProductMenuPage.goToPortlet(
						panel,
						category,
						name
					);

					await expect(
						virtualInstancePage.getByRole('heading', {
							exact: true,
							name,
						})
					).toBeVisible();
				});
			}
		}
	);
});
