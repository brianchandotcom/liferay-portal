/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {loginTest} from '../../../fixtures/loginTest';
import {notificationPagesTest} from '../../../fixtures/notificationPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	notificationPagesTest
);

test.describe('Notification templates', () => {
	test(
		'can search for notifications',
		{tag: '@LPD-78504'},
		async ({apiHelpers, notificationTemplatesPage, page}) => {
			const templateNames = [];

			for (const suffix of ['Test', 'Liferay', 'Site']) {
				const name = `Notification ${suffix} ${getRandomInt()}`;

				const notificationTemplate =
					await apiHelpers.notification.postRandomNotificationTemplate(
						name
					);

				apiHelpers.data.push({
					id: notificationTemplate.id,
					type: 'notificationTemplate',
				});

				templateNames.push(name);
			}

			await notificationTemplatesPage.goto();

			for (const name of templateNames) {
				await expect(
					notificationTemplatesPage.getFrontEndDatasetItemLocator(name)
				).toBeVisible();
			}

			const searchTerm = templateNames[1].split(' ')[1];

			await page
				.getByTestId('visualization-mode-table')
				.getByRole('searchbox', {name: 'Search'})
				.fill(searchTerm);

			await page.keyboard.press('Enter');

			await expect(
				notificationTemplatesPage.getFrontEndDatasetItemLocator(
					templateNames[1]
				)
			).toBeVisible();

			await expect(
				notificationTemplatesPage.getFrontEndDatasetItemLocator(
					templateNames[0]
				)
			).not.toBeVisible();

			await expect(
				notificationTemplatesPage.getFrontEndDatasetItemLocator(
					templateNames[2]
				)
			).not.toBeVisible();
		}
	);
});
