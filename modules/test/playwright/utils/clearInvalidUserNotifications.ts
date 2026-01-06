/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {AccountNotificationsPage} from '../pages/portal-workflow-task-web/AccountNotificationsPage';

export default async function (page: Page) {
	const accountNotificationsPage = new AccountNotificationsPage(page);

	await accountNotificationsPage.goToAccountNotifications();

	await accountNotificationsPage.deleteEnabledNotifications();

	while (
		(await page.getByText('Notification no longer applies.').count()) > 0
	) {
		await page.reload();

		if (
			(await page
				.getByText('Notification no longer applies.')
				.count()) === 0
		) {
			break;
		}
	}
}
