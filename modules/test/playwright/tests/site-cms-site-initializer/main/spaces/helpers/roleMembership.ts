/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {DataApiHelpers} from '../../../../../helpers/ApiHelpers';
import {
	performUserSwitchViaApi,
	userData,
} from '../../../../../utils/performLogin';
import {SpaceSummaryPage} from '../../pages/SpaceSummaryPage';

export type SpaceRole = 'Space Administrator' | 'Space Content Reviewer' | null;

export function registerUserCredentials(user: TUserAccount) {
	userData[user.alternateName] = {
		name: user.givenName,
		password: 'test',
		surname: user.familyName,
	};
}

export async function addRoleMemberAndSwitch({
	apiHelpers,
	page,
	role,
	spaceName,
	spaceSummaryPage,
}: {
	apiHelpers: DataApiHelpers;
	page: Page;
	role: SpaceRole;
	spaceName: string;
	spaceSummaryPage: SpaceSummaryPage;
}) {
	const user = await apiHelpers.headlessAdminUser.postUserAccount();
	const userFullName = `${user.givenName} ${user.familyName}`;

	registerUserCredentials(user);

	await spaceSummaryPage.goto(spaceName);
	await spaceSummaryPage.addUserOrUserGroup(userFullName, 'users');

	if (role) {
		await spaceSummaryPage.addRoleToSpaceMember(role, userFullName);
	}

	await performUserSwitchViaApi(page, user.alternateName);

	await spaceSummaryPage.goto(spaceName);

	return {user, userFullName};
}
