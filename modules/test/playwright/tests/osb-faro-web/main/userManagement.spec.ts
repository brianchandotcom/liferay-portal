/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {faroConfig} from './faro.config';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';
import {signInToAnalyticsCloud} from './utils/signInToAnalyticsCloud';

export const test = mergeTests(
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Delete button to manage user permissions',
	{
		tag: ['@LRAC-9063', '@LRAC-9069', '@LRAC-9070', '@LRAC-9055'],
	},

	async ({page, project}) => {
		await test.step('go to AC Properties Page', async () => {
			await navigateToACSettingsViaURL({
				acPage: ACPage.userManagementPage,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('add two users', async () => {
			await page.getByRole('button', {name: 'Invite Users'}).click();

			await page
				.getByPlaceholder('Enter Email Address')
				.fill('user1@liferay.com');

			await page.keyboard.press('Enter');

			await page
				.getByPlaceholder('Enter Email Address')
				.fill('user2@liferay.com');

			await page.keyboard.press('Enter');

			await page.getByRole('button', {name: 'Send'}).click();

			await expect(
				page.getByText('Success:Invitations have been sent.')
			).toBeVisible();
		});

		await test.step('check users', async () => {
			await expect(
				page.getByRole('cell', {name: 'user1@liferay.com'})
			).toBeVisible();
			await expect(
				page.getByRole('cell', {name: 'user2@liferay.com'})
			).toBeVisible();
		});

		await test.step('check users', async () => {
			await page
				.getByRole('row', {name: 'user1@liferay.com'})
				.getByLabel('Delete')
				.click();

			await page.getByRole('button', {name: 'Continue'}).click();

			await expect(
				page.getByText('Success:1 user has been deleted.').first()
			).toBeVisible();
			await expect(
				page.getByRole('cell', {name: 'user1@liferay.com'})
			).not.toBeVisible();
		});

		await test.step('check users', async () => {
			await page
				.getByRole('row', {name: 'user2@liferay.com'})
				.getByLabel('Delete')
				.click();

			await page.getByRole('button', {name: 'Continue'}).click();

			await expect(
				page.getByText('Success:1 user has been deleted.').nth(1)
			).toBeVisible();
			await expect(
				page.getByRole('cell', {name: 'user2@liferay.com'})
			).not.toBeVisible();
		});
	}
);

test(
	'User Management settings page surfaces the header and Invite Users action',
	{
		tag: '@LRAC-9048',
	},

	async ({page, project}) => {
		await navigateToACSettingsViaURL({
			acPage: ACPage.userManagementPage,
			page,
			projectID: project.groupId,
		});

		await expect(
			page.getByRole('heading', {name: 'User Management'})
		).toBeVisible();

		await expect(
			page.getByRole('button', {name: 'Invite Users'})
		).toBeVisible();
	}
);

test(
	'Admin row in User Management can be selected via its checkbox',
	{
		tag: ['@LRAC-9052', '@LRAC-9054'],
	},

	async ({page, project}) => {
		await navigateToACSettingsViaURL({
			acPage: ACPage.userManagementPage,
			page,
			projectID: project.groupId,
		});

		const adminEmail = 'corbin.murakami@faro.io';

		await page.getByPlaceholder('Search').first().fill(adminEmail);

		const adminRow = page.getByRole('row', {name: new RegExp(adminEmail)});

		const adminCheckbox = adminRow.getByRole('checkbox');

		await adminCheckbox.check();

		await expect(adminCheckbox).toBeChecked();
	}
);

test(
	'Member user cannot add a data source or create a property',
	{
		tag: ['@LRAC-9096', '@LRAC-9083'],
	},
	async ({page, project}) => {
		try {
			await signInToAnalyticsCloud(page, 'corbin.murakami@faro.io');

			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			await expect(
				page.getByRole('button', {name: 'Add Data Source'})
			).toHaveCount(0);

			await navigateToACSettingsViaURL({
				acPage: ACPage.propertiesPage,
				page,
				projectID: project.groupId,
			});

			await expect(
				page.getByRole('button', {name: 'New Property'})
			).toHaveCount(0);
		}
		finally {
			await signInToAnalyticsCloud(page, faroConfig.user.login);
		}
	}
);

test(
	'Member user cannot invite, delete, or change roles in User Management',
	{
		tag: ['@LRAC-9066', '@LRAC-9073', '@LRAC-9098'],
	},
	async ({page, project}) => {
		try {
			await signInToAnalyticsCloud(page, 'corbin.murakami@faro.io');

			await navigateToACSettingsViaURL({
				acPage: ACPage.userManagementPage,
				page,
				projectID: project.groupId,
			});

			await expect(
				page.getByRole('button', {name: 'Invite Users'})
			).toHaveCount(0);

			await expect(page.getByLabel('Delete')).toHaveCount(0);

			await expect(page.getByLabel('Edit')).toHaveCount(0);
		}
		finally {
			await signInToAnalyticsCloud(page, faroConfig.user.login);
		}
	}
);

test(
	'Admin and Owner users can delete an invited user from the User Management page',
	{
		tag: ['@LRAC-9065', '@LRAC-8143'],
	},
	async ({page, project}) => {
		const deleters = [
			{email: 'michelle.hoshi@faro.io', role: 'admin'},
			{email: 'bryan.cheung@faro.io', role: 'owner'},
		];

		try {
			for (const deleter of deleters) {
				await signInToAnalyticsCloud(page, deleter.email);

				await navigateToACSettingsViaURL({
					acPage: ACPage.userManagementPage,
					page,
					projectID: project.groupId,
				});

				const invitedEmail = `${deleter.role}-delete-${Date.now()}@liferay.com`;

				await page
					.getByRole('button', {name: 'Invite Users'})
					.click();

				await page
					.getByPlaceholder('Enter Email Address')
					.fill(invitedEmail);

				await page.keyboard.press('Enter');

				await page.getByRole('button', {name: 'Send'}).click();

				await expect(
					page.getByText('Success:Invitations have been sent.')
				).toBeVisible();

				await page
					.getByRole('row', {name: invitedEmail})
					.getByLabel('Delete')
					.click();

				await page.getByRole('button', {name: 'Continue'}).click();

				await expect(
					page.getByRole('cell', {name: invitedEmail})
				).toHaveCount(0);
			}
		}
		finally {
			await signInToAnalyticsCloud(page, faroConfig.user.login);
		}
	}
);

test(
	'Admin and Owner users can invite a new user via the User Management page',
	{
		tag: ['@LRAC-9071', '@LRAC-9072'],
	},
	async ({page, project}) => {
		const inviters = [
			{email: 'michelle.hoshi@faro.io', role: 'Admin'},
			{email: 'bryan.cheung@faro.io', role: 'Owner'},
		];

		try {
			for (const inviter of inviters) {
				await signInToAnalyticsCloud(page, inviter.email);

				await navigateToACSettingsViaURL({
					acPage: ACPage.userManagementPage,
					page,
					projectID: project.groupId,
				});

				const invitedEmail = `invited-${inviter.role.toLowerCase()}-${Date.now()}@liferay.com`;

				await page
					.getByRole('button', {name: 'Invite Users'})
					.click();

				await page
					.getByPlaceholder('Enter Email Address')
					.fill(invitedEmail);

				await page.keyboard.press('Enter');

				await page.getByRole('button', {name: 'Send'}).click();

				await expect(
					page.getByText('Success:Invitations have been sent.')
				).toBeVisible();

				await expect(
					page.getByRole('cell', {name: invitedEmail})
				).toBeVisible();

				await page
					.getByRole('row', {name: invitedEmail})
					.getByLabel('Delete')
					.click();

				await page.getByRole('button', {name: 'Continue'}).click();
			}
		}
		finally {
			await signInToAnalyticsCloud(page, faroConfig.user.login);
		}
	}
);

test(
	'User Management settings page search filters the list to matching users',
	{
		tag: '@LRAC-8142',
	},

	async ({page, project}) => {
		await navigateToACSettingsViaURL({
			acPage: ACPage.userManagementPage,
			page,
			projectID: project.groupId,
		});

		for (const userName of ['michelle hoshi', 'corbin murakami']) {
			await page.getByPlaceholder('Search').first().fill(userName);

			await expect(
				page.getByRole('cell', {name: userName})
			).toBeVisible();
		}
	}
);

test(
	'Selecting multiple invited users and clicking bulk Delete removes them at once',
	{
		tag: '@LRAC-9064',
	},

	async ({page, project}) => {
		await navigateToACSettingsViaURL({
			acPage: ACPage.userManagementPage,
			page,
			projectID: project.groupId,
		});

		const emails = [
			`bulk1-${Date.now()}@liferay.com`,
			`bulk2-${Date.now()}@liferay.com`,
		];

		await page.getByRole('button', {name: 'Invite Users'}).click();

		for (const email of emails) {
			await page.getByPlaceholder('Enter Email Address').fill(email);

			await page.keyboard.press('Enter');
		}

		await page.getByRole('button', {name: 'Send'}).click();

		await expect(
			page.getByText('Success:Invitations have been sent.')
		).toBeVisible();

		for (const email of emails) {
			await page
				.getByRole('row', {name: new RegExp(email)})
				.getByRole('checkbox')
				.check();
		}

		await page.getByRole('button', {exact: true, name: 'Delete'}).click();

		await page.getByRole('button', {name: 'Continue'}).click();

		for (const email of emails) {
			await expect(page.getByRole('cell', {name: email})).toHaveCount(0);
		}
	}
);
