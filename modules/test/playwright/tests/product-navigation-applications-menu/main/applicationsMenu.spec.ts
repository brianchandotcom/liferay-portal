/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../../fixtures/applicationsMenuPageTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {PagesAdminPage} from '../../../pages/layout-admin-web/PagesAdminPage';
import getRandomString from '../../../utils/getRandomString';
import {StagingPage} from '../../export-import-web/main/pages/StagingPage';
import getPageDefinition from '../../layout-content-page-editor-web/main/utils/getPageDefinition';

const test = mergeTests(apiHelpersTest, applicationsMenuPageTest, loginTest());

const siteTest = mergeTests(
	test,
	isolatedSiteTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	})
);

test(
	'It shows "View All" when total amount of sites of "recently visited" and "my sites" exceeds 7',
	{tag: '@LPD-66980'},
	async ({apiHelpers, applicationsMenuPage, page}) => {
		const sites: Array<Site> = [];

		try {
			await test.step(`Assert "View All" link visibility after creating 6 more sites`, async () => {
				for (let index = 1; index < 7; index++) {
					sites.push(
						await apiHelpers.headlessSite.createSite({
							name: getRandomString(),
						})
					);

					if (index >= 5) {
						await applicationsMenuPage.goto();

						await expect(
							applicationsMenuPage.viewAllLink
						).toBeVisible({visible: index + 1 >= 7});
					}
				}
			});

			const randomSite = sites[Math.floor(Math.random() * sites.length)];

			await test.step(`Use the "View All" link to navigate to "${randomSite.name}" site`, async () => {
				await applicationsMenuPage.viewAllLink.click();

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
						apiHelpers.headlessSite.deleteSite(site.id)
					)
				);
			});
		}
	}
);

siteTest(
	'It displays Applications Menu and User Avatar in correct locations',
	{tag: '@LPD-66980'},
	async ({apiHelpers, applicationsMenuPage, page, site}) => {
		const controlMenu = page.locator('.control-menu');

		async function expectApplicationsMenuToBeInControlMenu() {
			await expect(
				controlMenu.getByTestId('applicationsMenu')
			).toBeVisible();
		}

		async function expectApplicationsMenuToBeHidden() {
			await expect(page.getByTestId('applicationsMenu')).toBeHidden();
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

			await expectApplicationsMenuToBeInControlMenu();
			await expectUserAvatarToBeInControlMenu();
		});

		await siteTest.step('Assert locations in page editor', async () => {
			await pagesAdminPage.editPage(sitePageName);

			await expectApplicationsMenuToBeHidden();
			await expectUserAvatarToBeInNavigationBar();
		});

		await siteTest.step('Assert locations in sites admin', async () => {
			await applicationsMenuPage.goToSites();

			await expectApplicationsMenuToBeInControlMenu();
			await expectUserAvatarToBeInControlMenu();
		});

		await siteTest.step('Assert locations in site page', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${sitePage.friendlyUrlPath}`
			);

			await expectApplicationsMenuToBeInControlMenu();
			await expectUserAvatarToBeInNavigationBar();
		});

		await siteTest.step('Assert locations in staging page', async () => {
			const stagingPage = new StagingPage(page);

			await stagingPage.goto(site.key);

			await stagingPage.enableLocalStaging();

			await page.goto(
				`/web${site.friendlyUrlPath}-staging${sitePage.friendlyUrlPath}`
			);

			await expectApplicationsMenuToBeInControlMenu();
			await expectUserAvatarToBeInNavigationBar();
		});

		await siteTest.step('Assert locations in live page', async () => {
			await page.goto(
				`/web${site.friendlyUrlPath}${sitePage.friendlyUrlPath}`
			);

			await expectApplicationsMenuToBeInControlMenu();
			await expectUserAvatarToBeInNavigationBar();
		});
	}
);

test(
	'Default instance name is Liferay and instance logo is shown',
	{tag: '@LPD-77422'},
	async ({applicationsMenuPage, page}) => {
		await applicationsMenuPage.goto();

		await expect(
			page
				.getByRole('dialog')
				.getByRole('link', {exact: true, name: 'Liferay'})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: 'Liferay DXP Site Current'})
		).toBeVisible();

		await expect(
			page
				.getByRole('navigation', {name: 'Applications Menu'})
				.locator('img')
				.first()
		).toHaveAttribute('src', /liferay_logo/);

		await expect(
			page.getByRole('dialog').getByText('Liferay DXP', {exact: true})
		).toBeVisible();

		await expect(
			page
				.getByRole('navigation', {name: 'Applications Menu'})
				.locator('img')
				.last()
		).toHaveAttribute('src', /default_logo/);
	}
);
