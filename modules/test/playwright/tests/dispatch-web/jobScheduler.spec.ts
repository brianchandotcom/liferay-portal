/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {jobSchedulerPagesTest} from './fixtures/jobSchedulerPagesTest';

export const test = mergeTests(loginTest(), jobSchedulerPagesTest);

test('can create two job triggers and can delete them', async ({
	jobSchedulerPage,
	page,
}) => {
	await jobSchedulerPage.goTo();

	await jobSchedulerPage.createNewJobSchedulerTrigger('Job Trigger 1');
	await jobSchedulerPage.createNewJobSchedulerTrigger('Job Trigger 2');

	await page.getByTestId('row').nth(0).getByRole('checkbox').check();
	await page.getByTestId('row').nth(1).getByRole('checkbox').check();

	await expect(page.getByText('2 of 7 Items Selected')).toBeVisible();

	page.on('dialog', async (dialogWindow) => {
		await dialogWindow.accept();
	});

	await page.getByRole('button', {name: 'Delete'}).click();

	await expect(page.getByRole('link', {name: 'Job Trigger 1'})).toHaveCount(
		0
	);
	await expect(page.getByRole('link', {name: 'Job Trigger 2'})).toHaveCount(
		0
	);
});
