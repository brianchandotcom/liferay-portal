/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import {systemSettingsPageTest} from '../../fixtures/systemSettingsPageTest';
import {waitForAlert} from '../../utils/waitForAlert';
import isSPAEnabled from './utils/isSPAEnabled';

export const test = mergeTests(
	isolatedLayoutTest({publish: false}),
	loginTest(),
	systemSettingsPageTest
);

test(
	'Exclude path in SPA Settings',
	{
		tag: '@LPS-108376',
	},
	async ({layout, page, systemSettingsPage}) => {
		await test.step('Navigate to an isolated page', async () => {
			await page.goto(`/web/guest/${layout.friendlyURL}`);
		});

		await test.step('Check if SPA is enabled', async () => {
			expect(await isSPAEnabled({page})).toBeTruthy();
		});

		await test.step('Exclude path in SPA Settings', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Infrastructure',
				'Frontend SPA Infrastructure'
			);

			const customExcludedPathsInput = page.getByLabel(
				'Custom Excluded Paths',
				{
					exact: true,
				}
			);

			await customExcludedPathsInput.waitFor({state: 'visible'});
			await customExcludedPathsInput.click();
			await customExcludedPathsInput.fill(
				`/web/guest/${layout.friendlyURL}`
			);

			const updateButton = page.getByRole('button', {
				name: 'Update',
			});

			const saveButton = page.getByRole('button', {
				name: 'Save',
			});

			if (await saveButton.isVisible()) {
				await saveButton.click();
			}
			else if (await updateButton.isVisible()) {
				await updateButton.click();
			}

			await waitForAlert(page);
		});

		await test.step('Go back to isolated page and check SPA', async () => {
			await page.goto(`/web/guest/${layout.friendlyURL}`);

			expect(await isSPAEnabled({page})).toBeFalsy();
		});
	}
);

test(
	'Can change user notification timeout',
	{tag: '@LPS-67072'},
	async ({page, systemSettingsPage}) => {
		await test.step('Navigate to SPA Settings page', async () => {
			await systemSettingsPage.goToSystemSetting(
				'Infrastructure',
				'Frontend SPA Infrastructure'
			);
		});

		const userNotificationTimeoutLabel = page.getByLabel(
			'User Notification Timeout',
			{
				exact: true,
			}
		);

		const updateButton = page.getByRole('button', {
			name: 'Update',
		});

		const saveButton = page.getByRole('button', {
			name: 'Save',
		});

		await test.step('Check if SPA is enabled', async () => {
			expect(await isSPAEnabled({page})).toBeTruthy();
		});

		await test.step('Change the default timeout from 30000ms to 30ms', async () => {
			await userNotificationTimeoutLabel.waitFor({state: 'visible'});
			expect(userNotificationTimeoutLabel).toHaveValue('30000');

			await userNotificationTimeoutLabel.click();
			await userNotificationTimeoutLabel.fill('30');

			if (await saveButton.isVisible()) {
				await saveButton.click();
			}
			else if (await updateButton.isVisible()) {
				await updateButton.click();
			}

			await waitForAlert(page);
		});

		await test.step('Reload SPA Settings page, navigate and check that the User Notificacion appears in the page', async () => {
			await page.reload();

			await userNotificationTimeoutLabel.waitFor({state: 'visible'});

			if (await saveButton.isVisible()) {
				await saveButton.click();
			}
			else if (await updateButton.isVisible()) {
				await updateButton.click();
			}

			await waitForAlert(
				page,
				'Oops:It looks like this is taking longer than expected.',
				{type: 'warning'}
			);
		});

		await test.step('Change the timeout back to 30000ms', async () => {
			await userNotificationTimeoutLabel.waitFor({state: 'visible'});
			expect(userNotificationTimeoutLabel).toHaveValue('30');

			await userNotificationTimeoutLabel.click();
			await userNotificationTimeoutLabel.fill('30000');

			if (await saveButton.isVisible()) {
				await saveButton.click();
			}
			else if (await updateButton.isVisible()) {
				await updateButton.click();
			}

			await waitForAlert(page);
		});
	}
);
