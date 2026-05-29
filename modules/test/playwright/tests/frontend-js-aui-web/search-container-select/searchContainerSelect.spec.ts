/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {userGroupsPageTest} from '../../../fixtures/userGroupsPageTest';
import {DataApiHelpers} from '../../../helpers/ApiHelpers';
import {DataTablePage} from '../../../pages/account-admin-web/DataTablePage';
import {UserGroupsPage} from '../../../pages/users-admin-web/UserGroupsPage';
import {getRandomInt} from '../../../utils/getRandomInt';

const test = mergeTests(loginTest(), dataApiHelpersTest, userGroupsPageTest);

async function selectRowCheckbox(checkbox: Locator) {
	await expect(async () => {
		await checkbox.check();

		await expect(checkbox).toBeChecked({timeout: 2000});
	}).toPass({timeout: 15000});
}

async function assertSelectionsKeptAcrossPagination(
	apiHelpers: DataApiHelpers,
	page: Page,
	userGroupsPage: UserGroupsPage,
	displayStyle: string
) {
	const prefix = `User${getRandomInt()}`;

	const userGroup = await apiHelpers.headlessAdminUser.postUserGroup();

	const userAccounts = [
		await apiHelpers.headlessAdminUser.postUserAccount({
			familyName: 'A',
			givenName: prefix,
		}),
		await apiHelpers.headlessAdminUser.postUserAccount({
			familyName: 'B',
			givenName: prefix,
		}),
	];

	await userGroupsPage.goto(true);

	await new DataTablePage(page, userGroupsPage.userGroupsTable).search(
		userGroup.name
	);

	await userGroupsPage.userGroupsTableLink(userGroup.name, true).click();

	await expect(userGroupsPage.creationMenuNewButton).toBeVisible();

	await userGroupsPage.creationMenuNewButton.click();

	await userGroupsPage.addUsersTable.changeView(displayStyle);

	await userGroupsPage.addUsersTable.search(prefix);

	await selectRowCheckbox(
		userGroupsPage.addUsersIFrame
			.locator('[data-selectable="true"]')
			.first()
			.getByRole('checkbox')
	);

	await userGroupsPage.addUsersIFrame
		.getByRole('link', {exact: true, name: 'Page 2'})
		.click();

	await selectRowCheckbox(
		userGroupsPage.addUsersIFrame
			.locator('[data-selectable="true"]')
			.first()
			.getByRole('checkbox')
	);

	await expect(
		userGroupsPage.addUsersTable.table.locator(
			'input.hide[type="checkbox"]:checked'
		)
	).toHaveCount(1);

	await userGroupsPage.addUsersIFrameAddButton.click();

	await expect(userGroupsPage.addUsersIFrameAddButton).toBeHidden();

	for (const userAccount of userAccounts) {
		await userGroupsPage.userGroupUsersTable.search(
			userAccount.alternateName
		);

		await expect(
			userGroupsPage.userGroupUsersTable.table.getByText(
				`${userAccount.givenName} ${userAccount.familyName}`,
				{exact: true}
			)
		).toBeVisible();
	}
}

test(
	'Selections made on a previous page are kept when adding members across pagination in Table view',
	{tag: '@LPD-92564'},
	async ({apiHelpers, page, userGroupsPage}) => {
		await assertSelectionsKeptAcrossPagination(
			apiHelpers,
			page,
			userGroupsPage,
			'Table'
		);
	}
);

test(
	'Selections made on a previous page are kept when adding members across pagination in List view',
	{tag: '@LPD-92564'},
	async ({apiHelpers, page, userGroupsPage}) => {
		await assertSelectionsKeptAcrossPagination(
			apiHelpers,
			page,
			userGroupsPage,
			'List'
		);
	}
);

test(
	'Selections made on a previous page are kept when adding members across pagination in Cards view',
	{tag: '@LPD-92564'},
	async ({apiHelpers, page, userGroupsPage}) => {
		await assertSelectionsKeptAcrossPagination(
			apiHelpers,
			page,
			userGroupsPage,
			'Cards'
		);
	}
);
