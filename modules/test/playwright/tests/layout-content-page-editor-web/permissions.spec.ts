/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import createUserWithPermissions from '../../utils/createUserWithPermissions';
import {getRandomInt} from '../../utils/getRandomInt';
import getRandomString from '../../utils/getRandomString';
import getContainerDefinition from './utils/getContainerDefinition';
import getPageDefinition from './utils/getPageDefinition';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	isolatedSiteTest,
	loginTest(),
	pageEditorPagesTest
);

test(
	'User with Update - Basic and Update - Advanced Options permission cannot define custom css of fragment',
	{
		tag: ['@LPS-147787', '@LPS-136411'],
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Add new user with 'Update - Advanced Options' and 'Update - Basic' permissions

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const user = await createUserWithPermissions({
			apiHelpers,
			rolePermissions: [
				{
					actionIds: ['UPDATE_LAYOUT_ADVANCED_OPTIONS'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Layout',
					scope: 1,
				},
				{
					actionIds: ['UPDATE_LAYOUT_BASIC'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Layout',
					scope: 1,
				},
			],
		});

		// Create page

		const containerId = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getContainerDefinition({id: containerId}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, site.friendlyUrlPath, user.id);

		// Assert configuration is present in general tab

		await pageEditorPage.selectFragment(containerId);

		await expect(page.getByLabel('Width', {exact: true})).toBeAttached();

		// Assert color is present in styles tab

		await pageEditorPage.goToConfigurationTab('Styles');

		await expect(page.getByText('Background')).not.toBeAttached();
		await expect(page.getByText('Borders')).not.toBeAttached();
		await expect(page.getByText('Effects')).not.toBeAttached();
		await expect(page.getByText('Text')).not.toBeAttached();

		// Assert in advanced tab

		await pageEditorPage.goToConfigurationTab('Advanced');

		await expect(page.getByLabel('HTML Tag', {exact: true})).toBeAttached();

		await expect(
			page.getByLabel('Custom CSS', {exact: true})
		).not.toBeAttached();
	}
);

test(
	'User with Update - Limited permission does not have access to advance tab',
	{
		tag: '@LPS-147787',
	},
	async ({apiHelpers, page, pageEditorPage, site}) => {

		// Add new user with 'Update - Limited' permission

		const company =
			await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
				'liferay.com'
			);

		const role = await apiHelpers.headlessAdminUser.postRole({
			name: 'role' + getRandomInt(),
			rolePermissions: [
				{
					actionIds: ['UPDATE_LAYOUT_LIMITED'],
					primaryKey: company.companyId,
					resourceName: 'com.liferay.portal.kernel.model.Layout',
					scope: 1,
				},
			],
		});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToRole(
			role.externalReferenceCode,
			user.id
		);

		// Create page

		const containerId = getRandomString();

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getContainerDefinition({id: containerId}),
			]),
			siteId: site.id,
			title: getRandomString(),
		});

		// Go to edit mode

		await pageEditorPage.goto(layout, site.friendlyUrlPath, user.id);

		// Assert configuration is present in general tab

		await pageEditorPage.selectFragment(containerId);

		await expect(page.getByLabel('Width', {exact: true})).toBeAttached();

		// Assert color is present in styles tab

		await pageEditorPage.goToConfigurationTab('Styles');

		await expect(
			page
				.getByLabel('Background ColorImage')
				.getByLabel('Color', {exact: true})
		).toBeAttached();

		// Assert advanced tab is not present

		await expect(
			page.getByRole('tab', {exact: true, name: 'Advanced'})
		).not.toBeAttached();
	}
);
