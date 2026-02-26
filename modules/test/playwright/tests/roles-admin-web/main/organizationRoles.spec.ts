/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {rolesPagesTest} from '../../../fixtures/rolesPagesTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-36105': {enabled: true},
	}),
	loginTest(),
	rolesPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'Select Organization Role should show country name, not key',
	{
		tag: ['@LPD-50296'],
	},
	async ({apiHelpers, editUserPage, usersAndOrganizationsPage}) => {
		const organization1 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: 'Organization' + getRandomInt(),
			});
		const organization2 =
			await apiHelpers.headlessAdminUser.postOrganization({
				name: 'Organization' + getRandomInt(),
				organizationContactInformation: {
					postalAddresses: [
						{
							addressCountry: 'United States',
							addressLocality: getRandomString(),
							addressType: 'billing',
							postalCode: getRandomString(),
							streetAddressLine1: getRandomString(),
						},
					],
				},
			});

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization1.id,
			user.emailAddress
		);

		await apiHelpers.headlessAdminUser.assignUserToOrganizationByEmailAddress(
			organization2.id,
			user.emailAddress
		);

		await usersAndOrganizationsPage.goToUsers();
		await (
			await usersAndOrganizationsPage.usersTableRowLink(
				user.alternateName
			)
		).click();

		await editUserPage.rolesLink.waitFor({state: 'visible'});
		await editUserPage.rolesLink.click();
		await editUserPage.selectOrganizationRolesButton.click();

		await expect(
			editUserPage.selectOrganizationRolesFrameCell('united-states')
		).not.toBeVisible();
		await expect(
			editUserPage.selectOrganizationRolesFrameCell('United States')
		).toBeVisible();
	}
);
