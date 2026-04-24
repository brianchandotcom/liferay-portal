/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {globalMenuPagesTest} from '../../../fixtures/globalMenuPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {PagesAdminPage} from '../../../pages/layout-admin-web/PagesAdminPage';
import getRandomString from '../../../utils/getRandomString';
import {StagingPage} from '../../export-import-web/main/pages/StagingPage';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';

const test = mergeTests(apiHelpersTest, globalMenuPagesTest, loginTest());

const siteTest = mergeTests(
	test,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	})
);

test(
	'Each global menu category becomes active when clicked and deactivates others',
	{tag: '@LPD-87314'},
	async ({globalMenuPage, page}) => {
		const names = ['Applications', 'Commerce', 'Control Panel'];

		for (const name of names) {
			await test.step(`"${name}" is active after clicking`, async () => {
				await globalMenuPage.openGlobalMenu();

				await globalMenuPage.categoriesList
					.getByRole('menuitem', {exact: true, name})
					.click();

				await expect(page.getByLabel(`${name} Menu`)).toBeVisible();
				await expect(globalMenuPage.categoriesList).toBeHidden();

				await globalMenuPage.openGlobalMenu();

				const selectedCategory = globalMenuPage.categoriesList
					.getByRole('menuitem')
					.and(page.locator('.active'));

				await expect(selectedCategory).toHaveAccessibleName(name);
				await expect(selectedCategory).toHaveCount(1);
			});
		}
	}
);

test(
	'It shows "View All" when total amount of sites of "recently visited" and "my sites" exceeds 7',
	{tag: '@LPD-66980'},
	async ({apiHelpers, globalMenuPage, page}) => {
		const sites: Array<Site> = [];

		try {
			await test.step(`Assert "View All" link visibility after creating 6 more sites`, async () => {
				for (let index = 1; index < 7; index++) {
					sites.push(
						await apiHelpers.headlessAdminSite.postSite({
							name: getRandomString(),
						})
					);

					if (index >= 5) {
						await globalMenuPage.goToHome();
						await globalMenuPage.openGlobalMenu();

						await expect(globalMenuPage.viewAllLink).toBeVisible({
							visible: index + 1 >= 7,
						});
					}
				}
			});

			const randomSite = sites[Math.floor(Math.random() * sites.length)];

			await test.step(`Use the "View All" link to navigate to "${randomSite.name}" site`, async () => {
				await globalMenuPage.viewAllLink.click();

				const frame = page.frameLocator('iframe[title="Select Site"]');

				await frame.getByRole('link', {name: 'All Sites'}).click();

				await frame
					.getByRole('link', {exact: true, name: randomSite.name})
					.click();

				await page.waitForURL(
					new RegExp(`/group${randomSite.friendlyUrlPath}`)
				);
			});
		}
		finally {
			await test.step('Cleanup sites', async () => {
				await Promise.all(
					sites.map((site) =>
						apiHelpers.headlessAdminSite.deleteSite(
							site.externalReferenceCode
						)
					)
				);
			});
		}
	}
);

siteTest(
	'It displays Global Menu and User Avatar in correct locations',
	{tag: '@LPD-66980'},
	async ({apiHelpers, globalMenuPage, page, site}) => {
		const controlMenu = page.locator('.control-menu');

		async function expectGlobalMenuToBeInControlMenu() {
			await expect(controlMenu.getByTestId('globalMenu')).toBeVisible();
		}

		async function expectGlobalMenuToBeHidden() {
			await expect(page.getByTestId('globalMenu')).toBeHidden();
		}

		async function expectUserAvatarToBeInControlMenu() {
			await expect(
				controlMenu.getByTestId('userPersonalMenu')
			).toBeVisible();
		}

		async function expectUserAvatarToBeInNavigationBar() {
			await expect(
				page
					.locator('.portlet-user-personal-bar')
					.getByTestId('userPersonalMenu')
			).toBeVisible();
		}

		const {sitePage, sitePageName} = await siteTest.step(
			'Create site page',
			async () => {
				const sitePageName = getRandomString();
				const sitePage =
					await apiHelpers.headlessDelivery.createSitePage({
						pageDefinition: getPageDefinition([]),
						siteId: site.id,
						title: sitePageName,
					});

				return {sitePage, sitePageName};
			}
		);

		const pagesAdminPage = new PagesAdminPage(page);

		await siteTest.step('Assert locations in pages admin', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await expectGlobalMenuToBeInControlMenu();
			await expectUserAvatarToBeInControlMenu();
		});

		await siteTest.step('Assert locations in page editor', async () => {
			await pagesAdminPage.editPage(sitePageName);

			await expectGlobalMenuToBeHidden();
			await expectUserAvatarToBeInNavigationBar();
		});

		await siteTest.step('Assert locations in site page', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${sitePage.friendlyUrlPath}`
			);

			await expectGlobalMenuToBeInControlMenu();
			await expectUserAvatarToBeInNavigationBar();
		});

		await siteTest.step('Assert locations in sites admin', async () => {
			await globalMenuPage.goToControlPanel('Sites');

			await expectGlobalMenuToBeInControlMenu();
			await expectUserAvatarToBeInControlMenu();
		});

		await siteTest.step('Assert locations in staging page', async () => {
			const stagingPage = new StagingPage(page);

			await stagingPage.goto(site.key);

			await stagingPage.enableLocalStaging();

			await page.goto(
				`/web${site.friendlyUrlPath}-staging${sitePage.friendlyUrlPath}`
			);

			await expectGlobalMenuToBeInControlMenu();
			await expectUserAvatarToBeInNavigationBar();
		});

		await siteTest.step('Assert locations in live page', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${sitePage.friendlyUrlPath}`
			);

			await expectGlobalMenuToBeInControlMenu();
			await expectUserAvatarToBeInNavigationBar();
		});
	}
);

test(
	'Default site and its logo are shown correctly',
	{tag: '@LPD-77422'},
	async ({globalMenuPage}) => {
		await globalMenuPage.openGlobalMenu();

		const defaultSiteItem = globalMenuPage.sitesList.getByRole('menuitem', {
			name: 'Liferay DXP Site',
		});

		await expect(defaultSiteItem).toBeVisible();
		await expect(defaultSiteItem).toHaveClass(/active/);

		await expect(defaultSiteItem.locator('img')).toHaveAttribute(
			'src',
			/company_logo/
		);
	}
);
