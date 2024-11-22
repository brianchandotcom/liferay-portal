/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {createChannel} from './utils/channel';
import {ACPage, navigateToACSettingsViaURL} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);
let channel;
let project;

test.beforeEach(async ({apiHelpers}) => {
	const channelName = 'My Property - ' + getRandomString();

	const result = await createChannel({
		apiHelpers,
		channelName,
	});

	channel = result.channel;
	project = result.project;
});

test.afterEach(async ({apiHelpers}) => {
	await test.step('Delete channel and delete site on the DXP side', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});

test(
	'Delete button to manage user permissions',
	{
		tag: '@LRAC-9063',
	},

	async ({page}) => {
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

			expect(
				page.getByText('Success:Invitations have been sent.')
			).toBeVisible();
		});

		await test.step('check users', async () => {
			expect(
				page.getByRole('cell', {name: 'user1@liferay.com'})
			).toBeVisible();
			expect(
				page.getByRole('cell', {name: 'user2@liferay.com'})
			).toBeVisible();
		});

		await test.step('check users', async () => {
			await page
				.getByRole('row', {name: 'user1@liferay.com'})
				.getByLabel('Delete')
				.click();

			await page.getByRole('button', {name: 'Continue'}).click();

			expect(
				page.getByText('Success:1 user has been deleted.').first()
			).toBeVisible();
			expect(
				page.getByRole('cell', {name: 'user1@liferay.com'})
			).not.toBeVisible();
		});

		await test.step('check users', async () => {
			await page
				.getByRole('row', {name: 'user2@liferay.com'})
				.getByLabel('Delete')
				.click();

			await page.getByRole('button', {name: 'Continue'}).click();

			expect(
				page.getByText('Success:1 user has been deleted.').nth(1)
			).toBeVisible();
			expect(
				page.getByRole('cell', {name: 'user2@liferay.com'})
			).not.toBeVisible();
		});
	}
);
