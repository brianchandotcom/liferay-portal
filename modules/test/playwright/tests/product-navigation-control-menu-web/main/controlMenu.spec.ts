/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pagesAdminPagesTest} from '../../../fixtures/pagesAdminPagesTest';
import {siteSettingsPagesTest} from '../../../fixtures/siteSettingsPagesTest';
import {TRole} from '../../../helpers/HeadlessAdminUserApiHelper';
import getRandomString from '../../../utils/getRandomString';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';

const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pagesAdminPagesTest,
	siteSettingsPagesTest
);

test(
	'Verify role-based Control Menu access',
	{tag: '@LPD-55260'},
	async ({apiHelpers, page, pagesAdminPage, site, siteSettingsPage}) => {
		await test.step('Create a page for the site so there is a non admin place for the user to navigate to', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pagesAdminPage.createNewPage({
				draft: false,
				name: getRandomString(),
				template: 'Blank',
			});
		});

		let user: TUserAccount;

		await test.step('Create a new user', async () => {
			user = await apiHelpers.headlessAdminUser.postUserAccount();

			userData[user.alternateName] = {
				name: user.givenName,
				password: 'test',
				surname: user.familyName,
			};
		});

		let role: TRole;

		await test.step('Create a new role that has access to Web Content', async () => {
			const companyId = await page.evaluate(() => {
				return Liferay.ThemeDisplay.getCompanyId();
			});

			role = await apiHelpers.headlessAdminUser.postRole({
				name: getRandomString(),
				rolePermissions: [
					{
						actionIds: ['VIEW_SITE_ADMINISTRATION'],
						primaryKey: companyId,
						resourceName: 'com.liferay.portal.kernel.model.Group',
						scope: 1,
					},
				],
				roleType: 'regular',
			});
		});

		await test.step('Assign the new role to the new user', async () => {
			await apiHelpers.headlessAdminUser.assignUserToRole(
				role.externalReferenceCode,
				user.id
			);
		});

		await test.step('Assert that the new user can see the control menu', async () => {
			await performLogout(page);

			await performLoginViaApi({page, screenName: user.alternateName});

			await page.goto(`/web${site.friendlyUrlPath}`);

			await expect(page.getByLabel('Control Menu')).toBeVisible();
			await expect(page.locator('.has-control-menu')).toBeVisible();
		});

		await test.step('Enable Menu Access configuration', async () => {
			await performLogout(page);

			await performLoginViaApi({page, screenName: 'test'});

			await siteSettingsPage.goToSiteSetting(
				'Site Configuration',
				'Menu Access',
				site.friendlyUrlPath
			);

			await page
				.getByRole('checkbox', {name: 'Show Control Menu by Role'})
				.check();

			await siteSettingsPage.saveConfiguration();
		});

		await test.step('Assert that the new user can not see the control menu because the role is not in the Menu Access configuration', async () => {
			await performLogout(page);

			await performLoginViaApi({page, screenName: user.alternateName});

			await page.goto(`/web${site.friendlyUrlPath}`);

			await expect(page.getByLabel('Control Menu')).toBeHidden();
			await expect(page.locator('.has-control-menu')).toBeHidden();
		});

		await test.step('Log in with the Test user and navigate to Menu Access configuration', async () => {
			await performLogout(page);

			await performLoginViaApi({page, screenName: 'test'});

			await siteSettingsPage.goToSiteSetting(
				'Site Configuration',
				'Menu Access',
				site.friendlyUrlPath
			);
		});

		await test.step('Assert that there are no remove buttons for the Administrator and Site Administrator roles', async () => {
			await expect(
				page.getByText('Administrator', {exact: true})
			).toBeVisible();

			await expect(
				page.getByText('Site Administrator', {exact: true})
			).toBeVisible();

			await expect(page.getByLabel('Remove')).toBeHidden();
		});

		await test.step('Select the new role to allow accessing the control menu', async () => {
			await page.getByRole('button', {name: 'Select'}).click();

			const frameLocator = page.frameLocator(
				'[id="_com_liferay_site_admin_web_portlet_SiteSettingsPortlet_selectRole>_iframe_"]'
			);

			await frameLocator
				.locator(`input[type="checkbox"][value="${role.id}"]`)
				.check();

			await page.getByRole('button', {exact: true, name: 'Add'}).click();

			await siteSettingsPage.saveConfiguration();
		});

		await test.step('Assert that the new user can see the control menu', async () => {
			await performLogout(page);

			await performLoginViaApi({page, screenName: user.alternateName});

			await page.goto(`/web${site.friendlyUrlPath}`);

			await expect(page.getByLabel('Control Menu')).toBeVisible();
			await expect(page.locator('.has-control-menu')).toBeVisible();
		});
	}
);
