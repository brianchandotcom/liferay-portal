/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {VirtualInstancesPage} from '../../../pages/portal-instances-web/VirtualInstancesPage';
import {UserPersonalBarPage} from '../../../pages/product-navigation-user-personal-bar-web/UserPersonalBarPage';
import getRandomString from '../../../utils/getRandomString';

const DEFAULT_WEB_ID = 'liferay.com';

export async function assertDuplicateWebIdIsRejected(
	page: Page,
	virtualInstancesPage: VirtualInstancesPage
) {
	await virtualInstancesPage.goto();

	await virtualInstancesPage.newVirtualInstanceButton.click();

	// Sometimes the frame loads slowly

	await page.waitForTimeout(1000);

	// The default instance already owns this web ID, so it is rejected before
	// the background task is enqueued. Every other field is valid, so the web
	// ID is the only thing under test.

	await virtualInstancesPage.addInstanceWebIdField.fill(DEFAULT_WEB_ID);
	await virtualInstancesPage.addInstanceVirtualHost.fill(
		`${getRandomString()}.com`
	);
	await virtualInstancesPage.addInstanceMailDomain.fill(
		`${getRandomString()}.com`
	);

	await virtualInstancesPage.addInstanceAddButton.click();

	await expect(virtualInstancesPage.errorMessage).toContainText(
		'Please enter a valid web ID.'
	);

	// Nothing was enqueued, so the start message must never appear

	await expect(virtualInstancesPage.startMessage).toBeHidden();
}

export async function assertVirtualInstanceIsAddedAndNotified(
	page: Page,
	userPersonalBarPage: UserPersonalBarPage,
	virtualInstancesPage: VirtualInstancesPage
) {
	const name = getRandomString();

	try {

		// Add the instance, asserting the start message and then the row
		// showing up once the background task completes

		await virtualInstancesPage.addNewVirtualInstance(name);

		// Wait for the bell, since the notification is sent after the company
		// is committed

		await expect(async () => {
			await page.reload();

			await expect(userPersonalBarPage.notificationBadge).toBeVisible({
				timeout: 10 * 1000,
			});
		}).toPass({timeout: 120 * 1000});

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
