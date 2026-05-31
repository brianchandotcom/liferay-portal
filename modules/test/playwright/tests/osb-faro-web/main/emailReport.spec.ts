/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedDXPSyncedChannelTest} from '../../../fixtures/isolatedDXPSyncedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
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
