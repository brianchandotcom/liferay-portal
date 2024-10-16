/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {notificationsPagesTest} from './fixtures/notificationsPagesTest';

const test = mergeTests(loginTest(), notificationsPagesTest);

test(
	'Back button is present while navigating between tabs',
	{
		tag: '@LPD-39468',
	},
	async ({notificationsPage}) => {
		await notificationsPage.goto();

		await expect(notificationsPage.backButton).toBeVisible();

		await notificationsPage.requestsTab.click();

		await expect(notificationsPage.backButton).toBeVisible();
	}
);
