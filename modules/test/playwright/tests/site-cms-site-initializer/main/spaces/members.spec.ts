/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import getRandomString from '../../../../utils/getRandomString';
import {cmsPagesTest} from '../fixtures/cmsPagesTest';
import {SpaceSummaryPage} from '../pages/SpaceSummaryPage';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-17564': {enabled: true},
		'LPD-34594': {enabled: true},
	}),
	loginTest()
);

type SpaceMemberRole = 'Space Administrator' | 'Space Content Reviewer';

async function promoteNewMember({
	apiHelpers,
	page,
	role,
	spaceSummaryPage,
}: {
	apiHelpers: DataApiHelpers;
	page: Page;
	role: SpaceMemberRole;
	spaceSummaryPage: SpaceSummaryPage;
}) {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();
	const userFullName = `${user.givenName} ${user.familyName}`;

	await spaceSummaryPage.addUserOrUserGroup(userFullName, 'users');

	await spaceSummaryPage.addRoleToSpaceMember(role, userFullName);

	await spaceSummaryPage.viewAllMembersLink.click();

	const userRow = page.getByRole('listitem').filter({hasText: userFullName});

	await expect(userRow.getByRole('button', {name: role})).toBeVisible();

	await spaceSummaryPage.closeButton.click();
}

test(
	'A user can be added as a member of a space and removed afterwards',
	{tag: '@LPD-85670'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = `Space ${getRandomString()}`;
		const user = await apiHelpers.headlessAdminUser.postUserAccount();
		const userFullName = `${user.givenName} ${user.familyName}`;

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {},
			type: 'Space',
		});

		await spaceSummaryPage.goto(spaceName);

		await spaceSummaryPage.addUserOrUserGroup(userFullName, 'users');

		await spaceSummaryPage.viewAllMembersLink.click();

		await expect(
			page.getByRole('listitem').filter({hasText: userFullName})
		).toHaveCount(1);

		await spaceSummaryPage.closeButton.click();

		await spaceSummaryPage.removeUserOrUserGroup(userFullName, 'users');

		await spaceSummaryPage.viewAllMembersLink.click();

		await expect(
			page.getByRole('listitem').filter({hasText: userFullName})
		).toHaveCount(0);
	}
);

test(
	'A space member can be promoted to Space Administrator or Space Content Reviewer',
	{tag: '@LPD-85670'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = `Space ${getRandomString()}`;

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {},
			type: 'Space',
		});

		await spaceSummaryPage.goto(spaceName);

		await test.step('Promote a member to Space Administrator', () =>
			promoteNewMember({
				apiHelpers,
				page,
				role: 'Space Administrator',
				spaceSummaryPage,
			}));

		await test.step('Promote a member to Space Content Reviewer', () =>
			promoteNewMember({
				apiHelpers,
				page,
				role: 'Space Content Reviewer',
				spaceSummaryPage,
			}));
	}
);
