/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analyticsSettings';
import {faroConfig} from './faro.config';
import {dragAndDropCriteriaItem} from './utils/segments';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('check if updated custom event displayName is shown on segment criteria card', async ({
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();
	const customEventName = 'CustomEvent' + new Date().getTime();

	await syncAnalyticsCloud(page, channelName);
	await page.goto(liferayConfig.environment.baseUrl);
	await page.waitForTimeout(3000);

	await page.evaluate(
		({customEventName}) => {

			// @ts-ignore

			if (window.Analytics) {

				// @ts-ignore

				window.Analytics.track(customEventName, {
					propBool: true,
					propDate: '2024-05-20T01:00:00.000',
					propDuration: 66840000,
					propNum: 18,
					propString: 'test',
				});
			}
		},
		{customEventName}
	);

	await page.waitForTimeout(3000);
	await page.goto(faroConfig.environment.baseUrl);

	await page
		.getByRole('link', {
			name: 'FARO-DEV-liferay Liferay Demo Enterprise Plan',
		})
		.click();

	await page.locator('.channels-menu.button-root').click();
	await page.getByRole('link', {name: channelName}).click();
	await page.getByRole('link', {name: 'Settings'}).click();
	await page.getByRole('link', {name: 'Definitions'}).click();
	await page.getByRole('link', {name: 'Events'}).click();
	await page.getByRole('link', {name: 'Custom Events'}).click();
	await page.getByPlaceholder('Search').click();
	await page.getByPlaceholder('Search').fill(customEventName);
	await page.getByPlaceholder('Search').press('Enter');

	expect(page.getByText(customEventName)).toBeTruthy();

	await page.getByRole('link', {name: customEventName}).click();
	await page.getByRole('button', {name: 'Edit'}).click();
	await page.getByLabel('Display Name').click();
	await page.getByLabel('Display Name').fill(customEventName + 'EV');
	await page.getByRole('button', {name: 'Save'}).click();

	await page.waitForTimeout(3000);

	await page.evaluate(() => {
		const element = document.querySelector('.alert');

		if (element) {

			// @ts-ignore

			element.style.display = 'none';
		}
	});

	expect(page.getByRole('link', {name: customEventName + 'EV'})).toBeTruthy();

	await page.getByRole('link', {name: 'Exit Settings'}).click();
	await page.getByRole('link', {name: 'Segments'}).click();
	await page.getByLabel('Menu').click();
	await page.getByRole('menuitem', {name: 'Dynamic Segment'}).click();

	expect(page.getByText(customEventName + 'EV')).toBeTruthy();

	await dragAndDropCriteriaItem(page, `${customEventName}EV`);

	await page.waitForTimeout(3000);

	expect(
		page.locator('div').filter({hasText: `/^${customEventName}EV$/`})
	).toBeTruthy();

	await page.getByTestId('attribute-value-string-input').click();

	await page
		.getByTestId('attribute-value-string-input')
		.fill('testAttribute');

	await page
		.locator('div')
		.filter({hasText: /^Unnamed Segment$/})
		.first()
		.click();

	await page.getByPlaceholder('Unnamed Segment').fill('Test Dynamic Segment');
	await page.getByRole('button', {name: 'Save Segment'}).click();
	await page.waitForTimeout(3000);

	expect(page.getByRole('heading', {name: 'Segment Criteria'})).toBeTruthy();
	expect(page.getByText(customEventName + 'EV')).toBeTruthy();

	await page.getByRole('link', {name: 'Edit Segment'}).click();
	await page.waitForTimeout(3000);

	expect(
		page.locator('div').filter({hasText: `/^${customEventName}EV$/`})
	).toBeTruthy();

	expect(
		page.locator('li').filter({hasText: `/^${customEventName}EV$/`})
	).toBeTruthy();
});
