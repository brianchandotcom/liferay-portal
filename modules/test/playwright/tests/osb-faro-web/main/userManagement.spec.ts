/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';

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
		tag: '@LRAC-9063',
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
