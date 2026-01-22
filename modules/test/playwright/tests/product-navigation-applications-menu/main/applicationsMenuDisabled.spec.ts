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
import {ProductMenuPage} from '../../../pages/product-navigation-control-menu-web/ProductMenuPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import performLogin from '../../../utils/performLogin';

const MENU_TEST_PAGES = [
	{category: 'Sites', panel: 'Control Panel', portlet: 'Sites'},
	{category: 'Order Management', panel: 'Commerce', portlet: 'Orders'},
	{
		category: 'Content',
		panel: 'Applications',
		portlet: 'Asset Libraries',
	},
];

const VIRTUAL_INSTANCE_NAME = 'www.able.com';

async function disableApplicationsMenu(
	instanceSettingsPage: InstanceSettingsPage,
	reload: boolean
) {
	await goToApplicationsMenuSetting({
		instanceSettingsPage,
		reload,
		useProductMenu: false,
	});

	const checkbox = instanceSettingsPage.page.getByRole('checkbox', {
		name: 'Enable Applications Menu',
	});

	if (await checkbox.isChecked()) {
		await checkbox.uncheck();
	}

	await instanceSettingsPage.saveAndWaitForAlert();
}

async function goToApplicationsMenuSetting({
	instanceSettingsPage,
	reload,
	useProductMenu,
}: {
	instanceSettingsPage: InstanceSettingsPage;
	reload: boolean;
	useProductMenu: boolean;
}) {
	await instanceSettingsPage.goToInstanceSetting(
		'Navigation',
		'Applications Menu',
		reload,
		undefined,
		useProductMenu
	);
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
		await goToApplicationsMenuSetting({
			instanceSettingsPage,
			reload: true,
			useProductMenu: true,
		});

		await instanceSettingsPage.resetInstanceSetting();
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
				panel: site.name,
				portlet: 'Pages',
			});

			for (const {category, panel, portlet} of menuTestPages) {
				await test.step(`Navigate to ${panel} > ${category} > ${portlet} via Product Menu`, async () => {
					await productMenuPage.goToPortlet({
						category,
						panel,
						portlet,
					});

					await expect(
						page.getByRole('heading', {exact: true, name: portlet})
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
		await instanceSettingsPage.resetInstanceSetting();

		await virtualInstancePage?.close();

		await virtualInstancesPage.deleteVirtualInstance(VIRTUAL_INSTANCE_NAME);
	});

	test(
		'The Product Menu replaces the Applications Menu when it is disabled',
		{tag: '@LPD-66980'},
		async () => {
			const virtualInstanceProductMenuPage = new ProductMenuPage(
				virtualInstancePage
			);

			await test.step('Disable Applications Menu in virtual instance', async () => {
				await disableApplicationsMenu(
					virtualInstanceInstanceSettingsPage,
					false
				);

				await virtualInstanceProductMenuPage.openProductMenuIfClosed();
			});

			for (const {category, panel, portlet} of MENU_TEST_PAGES) {
				await test.step(`Navigate to ${panel} > ${category} > ${portlet} via Product Menu`, async () => {
					await virtualInstanceProductMenuPage.goToPortlet({
						category,
						panel,
						portlet,
					});

					await expect(
						virtualInstancePage.getByRole('heading', {
							exact: true,
							name: portlet,
						})
					).toBeVisible();
				});
			}
		}
	);
});
