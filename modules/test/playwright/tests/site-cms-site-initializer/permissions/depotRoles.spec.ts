/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {cmsPagesTest} from './fixtures/cmsPagesTest';
import {
	clickMenuItem,
	createSpace,
	deleteSpace,
	goToAllSpaces,
} from './utils/permissions';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPD-17564': {enabled: true},
		'LPD-58677': {enabled: true},
	}),
	loginTest()
);

test(
	'Individual Permissions modal lists only roles matching the depot domain',
	{tag: ['@LPD-83689']},
	async ({apiHelpers, page, permissionsPage, spaceSummaryPage}) => {
		test.setTimeout(90000);

		const role1 = await apiHelpers.headlessAdminUser.postRole({
			name: 'RoleNoSubtype' + getRandomInt(),
			roleType: 'depot',
		});
		const role2 = await apiHelpers.headlessAdminUser.postRole({
			name: 'RoleProject' + getRandomInt(),
			roleType: 'depot',
			subtype: 'project',
		});
		const role3 = await apiHelpers.headlessAdminUser.postRole({
			name: 'RoleSpace' + getRandomInt(),
			roleType: 'depot',
			subtype: 'space',
		});

		await goToAllSpaces(page);

		const spaceName = 'Space' + getRandomInt();

		await createSpace(page, spaceName);

		try {
			await spaceSummaryPage.goto(spaceName);

			const folderName = 'Folder' + getRandomInt();

			await spaceSummaryPage.createContentFolder(folderName);

			await spaceSummaryPage.viewAllContentLink.click();

			await expect(async () => {
				await clickMenuItem('Permissions', page, folderName);

				await expect(
					permissionsPage.permissionsModalCloseButton
				).toBeVisible({
					timeout: 2000,
				});
			}).toPass({timeout: 20000});

			await expect(permissionsPage.roleCell(role1.name)).toBeVisible();
			await expect(permissionsPage.roleCell(role2.name)).toHaveCount(0);
			await expect(permissionsPage.roleCell(role3.name)).toBeVisible();

			await permissionsPage.permissionsModalCloseButton.click();
		}
		finally {
			await goToAllSpaces(page);

			await deleteSpace(page, spaceName);
		}
	}
);
