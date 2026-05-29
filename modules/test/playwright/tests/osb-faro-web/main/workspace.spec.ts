/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {faroConfig} from './faro.config';

const test = mergeTests(apiHelpersTest, loginAnalyticsCloudTest(), loginTest());

test(
	'Workspace settings cancel reverts the unsaved workspace name change',
	{
		tag: '@LRAC-9134',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/settings/workspace`
		);

		const nameField = page.getByLabel('Workspace Name');

		const originalName = await nameField.inputValue();

		await nameField.fill('UAT ' + Date.now());

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(nameField).toHaveValue(originalName);
	}
);

test(
	'Subscription and Usage settings page shows current plan, limits, and add-ons',
	{
		tag: ['@LRAC-9180', '@LRAC-9183'],
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/settings/usage`
		);

		await expect(page.getByText('Enterprise')).toBeVisible();

		await expect(page.getByText('100,000').first()).toBeVisible();

		await expect(page.getByText('60,000,000').first()).toBeVisible();

		await expect(page.getByText('10,000').first()).toBeVisible();

		await expect(page.getByText('15,000,000').first()).toBeVisible();

		await expect(page.getByText('110,000').first()).toBeVisible();

		await expect(page.getByText('75,000,000').first()).toBeVisible();
	}
);
