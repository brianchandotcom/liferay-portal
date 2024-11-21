/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {faroConfig} from './faro.config';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Modal is displayed on first access to a new workspace',
	{
		tag: '@LRAC-8720',
	},

	async ({page}) => {
		await test.step('Create a new workspace', async () => {
			await page.goto(faroConfig.environment.baseUrl);

			await page.getByRole('link', {name: 'Start Free Trial'}).click();

			const workspaceName = 'Workspace ' + getRandomString();

			await page.getByLabel('Workspace Name').fill(workspaceName);

			await page.getByRole('textbox').nth(4).fill('test@liferay.com');

			await page.getByLabel('I Agree').check();

			await page.getByRole('button', {name: 'Finish Setup'}).click();

			await expect(page.getByText('Success:Success')).toBeVisible();
		});

		await test.step('Check the first screen of the onboarding modal', async () => {
			await page.waitForTimeout(1000);

			expect(page.getByText('Welcome to Analytics Cloud')).toBeVisible();

			expect(
				page.getByText(
					'Just a few more steps to set up your workspace.'
				)
			).toBeVisible();
		});

		await test.step('Navigate to next page', async () => {
			await page.getByRole('button', {name: 'Next'}).click();
		});

		await test.step('Check the onboarding connection modal information', async () => {
			expect(page.getByText('Connect Your DXP Analytics')).toBeVisible();
			expect(
				page.getByText('Copy this token to your DXP instance.')
			).toBeVisible();
			expect(
				page.locator('.modal-container').getByText('DXP Requirements')
			).toBeVisible();

			await page.getByRole('combobox').click();

			expect(
				page.getByRole('option', {
					name: 'DXP 2024.Q1.1 Quarterly Release',
				})
			).toBeHidden();
			expect(
				page.getByRole('option', {
					name: 'DXP Version 7.3 U30 + and above with hotfix',
				})
			).toBeHidden();
			expect(page.getByRole('link', {name: 'Download'})).toBeVisible();
		});

		await test.step('Click on "click here" and check if a new tab with connection documentation appears', async () => {
			await page.getByRole('link', {name: 'Click here'}).click();

			const newPage = await page.waitForEvent('popup');

			const href = newPage.url();

			await expect(href).toContain('learn.liferay.com');
			await expect(href).toContain(
				'/getting-started/connecting-liferay-dxp-to-analytics-cloud'
			);

			await newPage.close();
		});

		await test.step('Navigate to next page', async () => {
			await page.getByRole('link', {name: 'Next'}).click();

			await page.getByRole('button', {name: 'Skip'}).click();
		});

		await test.step('Create a new data source', async () => {
			await page.getByRole('link', {name: 'Add Data Source'}).click();

			await page.getByRole('button', {name: 'Liferay DXP'}).click();
		});

		await test.step('Check the data source connection modal information', async () => {
			expect(page.getByText('Connect Your DXP Analytics')).toBeVisible();
			expect(
				page.getByText('Copy this token to your DXP instance.')
			).toBeVisible();
			expect(
				page.locator('.modal-container').getByText('DXP Requirements')
			).toBeVisible();

			await page.getByRole('combobox').click();

			expect(
				page.getByRole('option', {
					name: 'DXP 2024.Q1.1 Quarterly Release',
				})
			).toBeHidden();
			expect(
				page.getByRole('option', {
					name: 'DXP Version 7.3 U30 + and above with hotfix',
				})
			).toBeHidden();
			expect(page.getByRole('link', {name: 'Download'})).toBeVisible();
		});
	}
);
