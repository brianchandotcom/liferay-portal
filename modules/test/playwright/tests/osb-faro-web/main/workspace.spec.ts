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
	'Unauthenticated access to a workspace URL redirects to the AC login screen',
	{
		tag: '@LRAC-9079',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await page.goto(`${faroConfig.environment.baseUrl}/c/portal/logout`);

		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/sites`
		);

		await expect(
			page.getByRole('textbox', {name: 'Email Address'})
		).toBeVisible();
	}
);

test(
	'Workspace settings cancel reverts the unsaved workspace name change',
	{
		tag: ['@LRAC-9134', '@LRAC-9118'],
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
	'Data Control & Privacy retention period dropdown can be changed and saved',
	{
		tag: '@LRAC-8890',
	},
	async ({apiHelpers, page}) => {
		const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

		const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

		await page.goto(
			`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/settings/data-privacy`
		);

		const retentionDropdown = page
			.getByText('Retention Period')
			.locator(
				'xpath=ancestor::*[self::div][.//button[contains(@class, "form-control")]]'
			)
			.locator('button.form-control');

		const initialValue = await retentionDropdown.textContent();

		await retentionDropdown.click();

		await page.keyboard.press('ArrowUp');

		await page.keyboard.press('Enter');

		await page.getByRole('button', {name: 'Change Period'}).click();

		await expect(retentionDropdown).not.toHaveText(initialValue ?? '');
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
