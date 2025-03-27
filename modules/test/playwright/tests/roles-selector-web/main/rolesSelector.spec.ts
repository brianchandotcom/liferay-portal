/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {rolesPagesTest} from '../../../fixtures/rolesPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {
	performLoginViaApi,
	performLogout,
	userData,
} from '../../../utils/performLogin';
import {PORTLET_URLS} from '../../../utils/portletUrls';

export const test = mergeTests(
	dataApiHelpersTest,
	loginTest(),
	rolesPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'Do not show users list when assigning a role without proper permission',
	{
		tag: ['@LPD-54145'],
	},
	async ({apiHelpers, page, usersAndOrganizationsPage}) => {
		const org1 = await apiHelpers.headlessAdminUser.postOrganization({
			name: 'Organization' + getRandomInt(),
		});
		const user1 = await apiHelpers.headlessAdminUser.postUserAccount();

		userData[user1.alternateName] = {
			name: user1.givenName,
			password: 'test',
			surname: user1.familyName,
		};

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			org1.id,
			user1.emailAddress
		);

		const role = await apiHelpers.headlessAdminUser.getRoleByName(
			'Organization Administrator'
		);

		await apiHelpers.headlessAdminUser.assignUserToOrganizationRole(
			role.id,
			user1.id,
			org1.id
		);

		const org2 = await apiHelpers.headlessAdminUser.postOrganization({
			name: 'Organization' + getRandomInt(),
		});
		const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			org2.id,
			user2.emailAddress
		);

		const companyId = await page.evaluate(() => {
			return Liferay.ThemeDisplay.getCompanyId();
		});
		const org2Group = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
			companyId,
			`${org2.name} LFR_ORGANIZATION`
		);
		const authToken = await page.evaluate(() => Liferay.authToken);
		const portletName =
			'_com_liferay_roles_selector_web_portlet_RolesSelectorPortlet_';

		const urlSearchParams = new URLSearchParams();
		urlSearchParams.append('p_p_auth', authToken);
		urlSearchParams.append('p_p_lifecycle', '0');
		urlSearchParams.append('p_p_mode', 'view');
		urlSearchParams.append('p_p_state', 'pop_up');
		urlSearchParams.append(
			`${portletName}className`,
			'com.liferay.portal.kernel.model.User'
		);
		urlSearchParams.append(`${portletName}groupId`, org2Group.groupId);
		urlSearchParams.append(`${portletName}keywords`, '');
		urlSearchParams.append(`${portletName}roleId`, role.id.toString());
		urlSearchParams.append(`${portletName}tabs1`, 'available');

		const baseUrl = `/group/guest${PORTLET_URLS.roleSelector}`;
		const response = await page.request.get(
			baseUrl + '&' + urlSearchParams.toString()
		);

		await page.goto(response.url());

		await expect(
			await usersAndOrganizationsPage.assignOrganizationRolesUserCell(
				user2.name
			)
		).toBeVisible();

		await performLogout(page);
		await performLoginViaApi({page, screenName: user1.alternateName});

		await usersAndOrganizationsPage.goToOrganizationsWithLimitedAccess();

		await (
			await usersAndOrganizationsPage.organizationActionsMenu(org1.name)
		).click();
		await usersAndOrganizationsPage.assignOrganizationRolesMenuItem.click();
		await (
			await usersAndOrganizationsPage.assignOrganizationRolesTableRowLink(
				'Account Manager'
			)
		).click();
		await usersAndOrganizationsPage.assignOrganizationRolesSearchBarButton.click();

		await expect(
			await usersAndOrganizationsPage.assignOrganizationRolesUserTableCell(
				user1.name
			)
		).toBeVisible();

		await page.goto(response.url());

		await expect(
			await usersAndOrganizationsPage.assignOrganizationRolesUserCell(
				user2.name
			)
		).toHaveCount(0);
	}
);
