/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import performLogin, {performLogout, userData} from '../../utils/performLogin';
import {openProductMenu} from '../../utils/productMenu';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest()
);

test('Checks the correct label for restricted page in the Page Tree', async ({
	apiHelpers,
	page,
	site,
}) => {

	// Create a content page with only one permission and open the edit mode

	const pageName = getRandomString();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pagePermissions: [
			{
				actionKeys: ['VIEW'],
				roleKey: 'Owner',
			},
		],
		siteId: site.id,
		title: pageName,
	});

	await page.goto(
		`${liferayConfig.environment.baseUrl}/en/web${site.friendlyUrlPath}${layout.friendlyUrlPath}`
	);

	// Open the Product Menu

	await openProductMenu(page);

	// Open tree if it's not already open

	if (!(await page.locator('.treeview').isVisible())) {
		await page
			.getByRole('button', {exact: true, name: 'Page Tree'})
			.click();

		await page.locator('.treeview').waitFor();
	}

	// Check the correct label for restricted page

	await expect(
		page
			.getByLabel('Product Menu', {exact: true})
			.locator('div', {
				hasText: pageName,
			})
			.getByLabel('Restricted Page')
	).toBeVisible();
});

test(
	'Checks unprivileged users can not add a page via Page Tree',
	{
		tag: '@LPS-129406',
	},
	async ({apiHelpers, page}) => {
		await page.goto('/');

		// Open the Product Menu

		await openProductMenu(page);

		// Open tree if it's not already open

		if (
			!(await page
				.getByLabel('Product Menu')
				.locator('.treeview')
				.isVisible())
		) {
			await page
				.getByRole('button', {exact: true, name: 'Page Tree'})
				.click();

			await page
				.getByLabel('Product Menu')
				.locator('.treeview')
				.waitFor();
		}

		// Assert add page button is visible for admin user

		await expect(
			page
				.locator('.page-type-selector')
				.getByTitle('Add Page', {exact: true})
		).toBeVisible();

		// Add new user with permissions

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'role' + getRandomInt(),
			rolePermissions: [
				{
					actionIds: ['ACCESS_IN_CONTROL_PANEL'],
					primaryKey: company.companyId,
					resourceName:
						'com_liferay_layout_admin_web_portlet_GroupPagesPortlet',
					scope: 1,
				},
				{
					actionIds: ['VIEW_SITE_ADMINISTRATION'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Group',
					scope: 1,
				},
			],
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user.alternateName] = {
			name: user.givenName,
			password: 'test',
			surname: user.familyName,
		};

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		// Logout and Login with the new user

		await performLogout(page);

		await performLogin(page, user.alternateName);

		// Open the Product Menu

		await page.goto('/');

		await openProductMenu(page);

		// Open tree if it's not already open

		if (
			!(await page
				.getByLabel('Product Menu')
				.locator('.treeview')
				.isVisible())
		) {
			await page
				.getByRole('button', {exact: true, name: 'Page Tree'})
				.click();

			await page
				.getByLabel('Product Menu')
				.locator('.treeview')
				.waitFor();
		}

		// Assert add page button is not visible

		await expect(
			page
				.locator('.page-type-selector')
				.getByTitle('Add Page', {exact: true})
		).not.toBeVisible();
	}
);
