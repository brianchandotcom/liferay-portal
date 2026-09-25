/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {notificationsPagesTest} from '../../../fixtures/notificationsPagesTest';
import {virtualInstancesPagesTest} from '../../../fixtures/virtualInstancesPagesTest';
import {ApiHelpers} from '../../../helpers/ApiHelpers';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(
	loginTest(),
	notificationsPagesTest,
	virtualInstancesPagesTest
);

test(
	'LPD-93374 Deleting an instance notifies the user once the instance is gone',
	{tag: '@LPD-93374'},
	async ({notificationsPage, virtualInstancesPage}) => {
		test.setTimeout(360000);

		const name = getRandomString();

		await virtualInstancesPage.addNewVirtualInstance(name);

		await virtualInstancesPage.deleteVirtualInstance(name);

		await notificationsPage.goto();

		await expect(
			notificationsPage.getNotification(
				`The instance ${name} is no longer available.`,
				`The instance ${name} was deleted.`
			)
		).toBeVisible();
	}
);

test(
	'LPD-93374 Deleting an instance that does not exist notifies the user of the failure',
	{tag: '@LPD-93374'},
	async ({notificationsPage, page}) => {
		test.setTimeout(360000);

		const name = getRandomString();

		const apiHelpers = new ApiHelpers(page);

		const headlessBatchEngine = apiHelpers.headlessBatchEngine;

		const headlessPortalInstance = apiHelpers.headlessPortalInstance;

		const importTask =
			await headlessPortalInstance.deleteVirtualInstancesBatch([name]);

		await expect
			.poll(
				async () => {
					const currentImportTask =
						await headlessBatchEngine.getImportTask(importTask.id);

					return currentImportTask.executeStatus;
				},
				{intervals: [1000], timeout: 180 * 1000}
			)
			.toBe('FAILED');

		await notificationsPage.goto();

		await expect(
			notificationsPage.getNotification(
				'An unexpected error occurred.',
				`The instance ${name} could not be deleted.`
			)
		).toBeVisible();
	}
);
