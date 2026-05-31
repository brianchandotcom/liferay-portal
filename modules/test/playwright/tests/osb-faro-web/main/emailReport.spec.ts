/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedDXPSyncedChannelTest} from '../../../fixtures/isolatedDXPSyncedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {faroConfig} from './faro.config';

const test = mergeTests(
	apiHelpersTest,
	isolatedDXPSyncedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Cancel after configuring email reports leaves them disabled',
	{tag: '@LRAC-11844'},
	async ({analyticsChannel, page, project}) => {
		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/settings/properties/${analyticsChannel.id}`
		);

		await expect(page.getByText('Email Reports: Disabled')).toBeVisible();

		// Open the Configure Email Reports modal

		await page
			.getByRole('button', {name: 'Configure Email Reports'})
			.click();

		// Enable the toggle and pick Daily

		await page.locator('.toggle-switch-bar').click();

		await page.locator('select[name="frequency"]').selectOption('daily');

		// Cancel without saving

		await page.getByRole('button', {name: 'Cancel'}).click();

		// The property status stays Disabled

		await expect(page.getByText('Email Reports: Disabled')).toBeVisible();
	}
);

test(
	'Email report frequency can be set to daily, weekly, or monthly',
	{tag: '@LRAC-11841'},
	async ({analyticsChannel, page, project}) => {
		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/settings/properties/${analyticsChannel.id}`
		);

		// Enable the toggle once; the property keeps its enabled state across
		// frequency changes within the same modal session

		await page
			.getByRole('button', {name: 'Configure Email Reports'})
			.click();

		await page.locator('.toggle-switch-bar').click();

		for (const frequency of ['daily', 'weekly', 'monthly']) {
			await page
				.locator('select[name="frequency"]')
				.selectOption(frequency);

			await page.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(page, 'Changes to email reports saved', {
				autoClose: false,
				first: true,
			});

			await expect(
				page.getByText('Email Reports: Enabled')
			).toBeVisible();

			await page
				.getByRole('button', {name: 'Configure Email Reports'})
				.click();
		}
	}
);
