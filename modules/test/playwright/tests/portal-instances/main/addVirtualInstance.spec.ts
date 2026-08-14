/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	loginTest(),
	notificationPagesTest,
	virtualInstancesPagesTest
);

test(
	'Reports an invalid web ID without starting the operation',
	{tag: '@LPD-93373'},
	async ({page, virtualInstancesPage}) => {
		await virtualInstancesPage.goto();

		await virtualInstancesPage.newVirtualInstanceButton.click();

		await page.waitForTimeout(1000);

		// A web ID that is not a valid domain is rejected before the background
		// task is enqueued

		await virtualInstancesPage.addInstanceWebIdField.fill(
			getRandomString()
		);
		await virtualInstancesPage.addInstanceVirtualHost.fill('');
		await virtualInstancesPage.addInstanceMailDomain.fill(
			`${getRandomString()}.com`
		);

		await virtualInstancesPage.addInstanceAddButton.click();

		await expect(virtualInstancesPage.errorMessage).toBeVisible();

		await expect(virtualInstancesPage.addInstanceWebIdField).toBeVisible();
	}
);

test(
	'Acknowledges the start and notifies the completion',
	{tag: '@LPD-93373'},
	async ({page, userPersonalBarPage, virtualInstancesPage}) => {
		const name = getRandomString();

		try {

			// Add the instance, asserting the start toast and then the row
			// showing up once the background task completes

			await virtualInstancesPage.addNewVirtualInstance(name);

			// Wait for the bell, since the notification is sent after the
			// company is committed

			await expect(async () => {
				await page.reload();

				await expect(userPersonalBarPage.notificationBadge).toBeVisible(
					{timeout: 10 * 1000}
				);
			}).toPass({timeout: 120 * 1000});

			// Check the completion notification

			await userPersonalBarPage.notificationBadge.click();

			await expect(
				page.getByRole('link', {
					name: `The virtual instance ${name} was added successfully.`,
				})
			).toBeVisible();
		}
		finally {
			await virtualInstancesPage.deleteVirtualInstance(name);
		}
	}
);
