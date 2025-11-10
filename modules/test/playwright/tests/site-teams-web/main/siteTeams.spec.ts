/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {usersAndOrganizationsPagesTest} from '../../../fixtures/usersAndOrganizationsPagesTest';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {siteTeamsPagesTest} from './fixtures/siteTeamsPagesTest';

const test = mergeTests(
	dataApiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	siteTeamsPagesTest,
	usersAndOrganizationsPagesTest
);

test(
	'Can Add / Update / Delete a site team',
	{tag: ['@TICKET']},
	async ({page, site, teamsPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const team = {
			teamDescription: getRandomString(),
			teamName: getRandomString(),
		};

		await teamsPage.goTo(site.friendlyUrlPath);

		await teamsPage.newTeamButton.click();
		await teamsPage.newTeam(team);

		await expect(
			await teamsPage.teamsTable.cellLink(team.teamName)
		).toBeVisible();

		await expect(async () => {
			await (
				await teamsPage.teamsTable.rowActions(team.teamName)
			).click();

			await expect(teamsPage.editButton).toBeVisible();
		}).toPass({timeout: 5000});

		await teamsPage.editButton.click();

		await expect(teamsPage.nameInput).toHaveValue(team.teamName);
		await expect(teamsPage.descriptionInput).toHaveValue(
			team.teamDescription
		);

		const newTeam = {
			teamDescription: getRandomString(),
			teamName: getRandomString(),
		};

		await teamsPage.nameInput.fill(newTeam.teamName);
		await teamsPage.descriptionInput.fill(newTeam.teamDescription);
		await teamsPage.saveButton.click();

		await waitForAlert(page);

		await expect(teamsPage.teamsTable.cell(team.teamName)).toHaveCount(0);
		await expect(
			await teamsPage.teamsTable.cellLink(newTeam.teamName)
		).toBeVisible();

		await expect(async () => {
			await (
				await teamsPage.teamsTable.rowActions(newTeam.teamName)
			).click();

			await expect(teamsPage.editButton).toBeVisible();
		}).toPass({timeout: 5000});

		await teamsPage.editButton.click();

		await expect(teamsPage.nameInput).toHaveValue(newTeam.teamName);
		await expect(teamsPage.descriptionInput).toHaveValue(
			newTeam.teamDescription
		);

		await teamsPage.goTo(site.friendlyUrlPath);

		await expect(async () => {
			await (
				await teamsPage.teamsTable.rowActions(newTeam.teamName)
			).click();

			await expect(teamsPage.deleteButton).toBeVisible();
		}).toPass({timeout: 5000});

		await teamsPage.deleteButton.click();

		await waitForAlert(page);

		await expect(teamsPage.teamsTable.cell(team.teamName)).toHaveCount(0);
		await expect(teamsPage.teamsTable.cell(newTeam.teamName)).toHaveCount(
			0
		);
	}
);

test(
	'Can add multiple teams to a site',
	{tag: ['@TICKET']},
	async ({page, site, teamsPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const teams = [
			{
				teamDescription: getRandomString(),
				teamName: getRandomString(),
			},
			{
				teamDescription: getRandomString(),
				teamName: getRandomString(),
			},
			{
				teamDescription: getRandomString(),
				teamName: getRandomString(),
			},
		];

		await teamsPage.goTo(site.friendlyUrlPath);

		for (const team of teams) {
			await teamsPage.newTeamButton.click();
			await teamsPage.newTeam(team);

			await expect(
				await teamsPage.teamsTable.cellLink(team.teamName)
			).toBeVisible();
		}

		await teamsPage.teamsTable.selectAllItemsCheckbox.click();
		await teamsPage.deleteButton.click();

		for (const team of teams) {
			await expect(teamsPage.teamsTable.cell(team.teamName)).toHaveCount(
				0
			);
		}
	}
);

test(
	'Can assign / Unassing a user to a site team',
	{tag: ['@TICKET']},
	async ({apiHelpers, page, selectUserPage, site, teamsPage, usersPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const user = await apiHelpers.headlessAdminUser.postUserAccount();

		apiHelpers.jsonWebServicesUser.assignUsersToSite(site.id, user.id);

		const team = {
			teamDescription: getRandomString(),
			teamName: getRandomString(),
		};

		await teamsPage.goTo(site.friendlyUrlPath);

		await teamsPage.newTeamButton.click();
		await teamsPage.newTeam(team);

		await expect(
			await teamsPage.teamsTable.cellLink(team.teamName)
		).toBeVisible();

		await (await teamsPage.teamsTable.cellLink(team.teamName)).click();

		await expect(usersPage.usersTable.searchInput).toBeEnabled();

		await usersPage.usersTable.changeView('Table');

		await expect(usersPage.noUsersMessage).toBeVisible();

		await expect(async () => {
			await usersPage.newButton.click();

			await expect(selectUserPage.addButton).toBeVisible({timeout: 2000});
		}).toPass({timeout: 5000});

		await (await selectUserPage.usersTable.rowCheckbox(user.name)).click();
		await selectUserPage.addButton.click();

		await expect(usersPage.usersTable.cell(user.name)).toBeVisible();

		await page.reload();

		await expect(usersPage.usersTable.cell(user.name)).toBeVisible();

		await expect(async () => {
			await (await usersPage.usersTable.rowActions(user.name)).click();

			await expect(usersPage.deleteButton).toBeVisible({timeout: 200});
		}).toPass({timeout: 1000});

		await usersPage.deleteButton.click();

		await expect(usersPage.usersTable.cell(user.name)).toHaveCount(0);

		await page.reload();

		await expect(usersPage.usersTable.cell(user.name)).toHaveCount(0);
	}
);

test(
	'Can search users connected to a site team',
	{tag: ['@TICKET']},
	async ({apiHelpers, page, selectUserPage, site, teamsPage, usersPage}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const user1 = await apiHelpers.headlessAdminUser.postUserAccount();

		apiHelpers.jsonWebServicesUser.assignUsersToSite(site.id, user1.id);

		const user2 = await apiHelpers.headlessAdminUser.postUserAccount();

		apiHelpers.jsonWebServicesUser.assignUsersToSite(site.id, user2.id);

		const team = {
			teamDescription: getRandomString(),
			teamName: getRandomString(),
		};

		await teamsPage.goTo(site.friendlyUrlPath);

		await teamsPage.newTeamButton.click();
		await teamsPage.newTeam(team);

		await expect(
			await teamsPage.teamsTable.cellLink(team.teamName)
		).toBeVisible();

		await (await teamsPage.teamsTable.cellLink(team.teamName)).click();

		await expect(usersPage.usersTable.searchInput).toBeEnabled();

		await usersPage.usersTable.changeView('Table');

		await expect(usersPage.noUsersMessage).toBeVisible();

		await expect(async () => {
			await usersPage.newButton.click();

			await expect(selectUserPage.addButton).toBeVisible({timeout: 2000});
		}).toPass({timeout: 5000});

		await (await selectUserPage.usersTable.rowCheckbox(user1.name)).click();
		await (await selectUserPage.usersTable.rowCheckbox(user2.name)).click();
		await selectUserPage.addButton.click();

		await expect(usersPage.usersTable.cell(user1.name)).toBeVisible();
		await expect(usersPage.usersTable.cell(user2.name)).toBeVisible();

		await usersPage.usersTable.search(user1.name);

		await expect(usersPage.usersTable.cell(user1.name)).toBeVisible();
		await expect(usersPage.usersTable.cell(user2.name)).toHaveCount(0);

		await usersPage.usersTable.search(user2.name);

		await expect(usersPage.usersTable.cell(user1.name)).toHaveCount(0);
		await expect(usersPage.usersTable.cell(user2.name)).toBeVisible();

		await usersPage.usersTable.search(getRandomString());

		await expect(usersPage.usersTable.cell(user1.name)).toHaveCount(0);
		await expect(usersPage.usersTable.cell(user2.name)).toHaveCount(0);

		await usersPage.usersTable.search('');

		await expect(usersPage.usersTable.cell(user1.name)).toBeVisible();
		await expect(usersPage.usersTable.cell(user2.name)).toBeVisible();
	}
);

test(
	'Can assign / Unassing a user group to a site team',
	{tag: ['@TICKET']},
	async ({
		apiHelpers,
		page,
		selectUserGroupPage,
		site,
		teamsPage,
		userGroupsPage,
	}) => {
		page.on('dialog', (dialog) => dialog.accept());

		const userGroup = await apiHelpers.headlessAdminUser.postUserGroup();

		apiHelpers.jsonWebServicesUserGroup.assignUserGroupsToGroup(
			site.id,
			String(userGroup.id)
		);

		const team = {
			teamDescription: getRandomString(),
			teamName: getRandomString(),
		};

		await teamsPage.goTo(site.friendlyUrlPath);

		await teamsPage.newTeamButton.click();
		await teamsPage.newTeam(team);

		await expect(
			await teamsPage.teamsTable.cellLink(team.teamName)
		).toBeVisible();

		await (await teamsPage.teamsTable.cellLink(team.teamName)).click();

		await teamsPage.userGroupTab.click();

		await expect(userGroupsPage.userGroupsTable.searchInput).toBeEnabled();

		await userGroupsPage.userGroupsTable.changeView('Table');

		await expect(userGroupsPage.noUserGroupsMessage).toBeVisible();

		await expect(async () => {
			await userGroupsPage.newButton.click();

			await expect(selectUserGroupPage.addButton).toBeVisible({
				timeout: 2000,
			});
		}).toPass({timeout: 5000});

		await selectUserGroupPage.userGroupsTable.changeView('Table');

		await (
			await selectUserGroupPage.userGroupsTable.rowCheckbox(
				userGroup.name
			)
		).click();
		await selectUserGroupPage.addButton.click();

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup.name)
		).toBeVisible();

		await page.reload();

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup.name)
		).toBeVisible();

		await expect(userGroupsPage.deleteButton).toBeVisible({timeout: 200});

		await userGroupsPage.deleteButton.click();

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup.name)
		).toHaveCount(0);

		await page.reload();

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup.name)
		).toHaveCount(0);
	}
);

test(
	'Can search user groups connected to a site team',
	{tag: ['@TICKET']},
	async ({
		apiHelpers,
		selectUserGroupPage,
		site,
		teamsPage,
		userGroupsPage,
	}) => {
		const userGroup1 = await apiHelpers.headlessAdminUser.postUserGroup();

		apiHelpers.jsonWebServicesUserGroup.assignUserGroupsToGroup(
			site.id,
			String(userGroup1.id)
		);

		const userGroup2 = await apiHelpers.headlessAdminUser.postUserGroup();

		apiHelpers.jsonWebServicesUserGroup.assignUserGroupsToGroup(
			site.id,
			String(userGroup2.id)
		);

		const team = {
			teamDescription: getRandomString(),
			teamName: getRandomString(),
		};

		await teamsPage.goTo(site.friendlyUrlPath);

		await teamsPage.newTeamButton.click();
		await teamsPage.newTeam(team);

		await expect(
			await teamsPage.teamsTable.cellLink(team.teamName)
		).toBeVisible();

		await (await teamsPage.teamsTable.cellLink(team.teamName)).click();

		await teamsPage.userGroupTab.click();

		await expect(userGroupsPage.userGroupsTable.searchInput).toBeEnabled();

		await userGroupsPage.userGroupsTable.changeView('Table');

		await expect(userGroupsPage.noUserGroupsMessage).toBeVisible();

		await expect(async () => {
			await userGroupsPage.newButton.click();

			await expect(selectUserGroupPage.addButton).toBeVisible({
				timeout: 2000,
			});
		}).toPass({timeout: 5000});

		await selectUserGroupPage.userGroupsTable.changeView('Table');

		await (
			await selectUserGroupPage.userGroupsTable.rowCheckbox(
				userGroup1.name
			)
		).click();
		await (
			await selectUserGroupPage.userGroupsTable.rowCheckbox(
				userGroup2.name
			)
		).click();
		await selectUserGroupPage.addButton.click();

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup1.name)
		).toBeVisible();
		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup2.name)
		).toBeVisible();

		await userGroupsPage.userGroupsTable.search(userGroup1.name);

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup1.name)
		).toBeVisible();
		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup2.name)
		).toHaveCount(0);

		await userGroupsPage.userGroupsTable.search(userGroup2.name);

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup1.name)
		).toHaveCount(0);
		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup2.name)
		).toBeVisible();

		await userGroupsPage.userGroupsTable.search(getRandomString());

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup1.name)
		).toHaveCount(0);
		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup2.name)
		).toHaveCount(0);

		await userGroupsPage.userGroupsTable.search('');

		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup1.name)
		).toBeVisible();
		await expect(
			userGroupsPage.userGroupsTable.cell(userGroup2.name)
		).toBeVisible();
	}
);
