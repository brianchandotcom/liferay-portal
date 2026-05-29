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
	'Member user cannot invite or delete users in User Management',
	{
		tag: ['@LRAC-9066', '@LRAC-9073'],
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
