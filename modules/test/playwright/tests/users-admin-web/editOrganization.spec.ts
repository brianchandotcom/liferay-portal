/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../fixtures/usersAndOrganizationsPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(
	apiHelpersTest,
	loginTest(),
	usersAndOrganizationsPagesTest
);

test('LPD-18470 check edit orgLabor renders properly', async ({
	apiHelpers,
	editOrganizationPage,
	usersAndOrganizationsPage,
}) => {
	const organization = await apiHelpers.headlessAdminUser.postOrganization({
		name: 'Organization' + getRandomInt(),
		services: [
			{
				hoursAvailable: [
					{
						closes: '-1',
						opens: '-1',
					},
				],
				serviceType: 'donation',
			},
		],
	});
	await usersAndOrganizationsPage.goToOrganizations();
	await editOrganizationPage.gotoOrganizationEditOpeningHoursTab(
		organization.name
	);
	await expect(editOrganizationPage.orgLaborListTypeSelectedValue).toHaveText(
		'Donation'
	);
	await apiHelpers.headlessAdminUser.deleteOrganization(organization.id);
});
