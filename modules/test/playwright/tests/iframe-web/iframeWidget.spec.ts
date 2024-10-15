/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../fixtures/pageViewModePagesTest';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest
);

test(
	'Verifying js prototype pollution vulnerability in IFrame widget',
	{
		tag: '@LPS-171989',
	},
	async ({apiHelpers, context, page, site, widgetPagePage}) => {

		// Add listener with expect so it fails when a browser dialog is shown

		page.on('dialog', async (dialog) => {
			await dialog.accept();

			expect(
				dialog.message(),
				'This alert should not be shown'
			).toBeNull();
		});

		// Add widget page and navigate to view

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		// Add iframe widget and configure it to show the home page of guest site

		await widgetPagePage.addPortlet('IFrame');

		await widgetPagePage.clickOnAction('IFrame', 'Configuration');

		const configurationIFrame = page.frameLocator(
			'iframe[title*="IFrame"]'
		);

		await configurationIFrame.getByLabel('Source URL').waitFor();

		await configurationIFrame.getByLabel('Source URL').fill('/');

		await configurationIFrame.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(
			configurationIFrame,
			'Success:You have successfully updated the setup.'
		);

		// Navigate to a new page and assert alert is not shown

		const newPage = await context.newPage();

		await newPage.goto(
			`/web${site.friendlyUrlPath}${layout.friendlyURL}#constructor[prototype][comboBase]=data%3A%2Calert%281%29`
		);

		const viewIFrame = newPage.frameLocator('iframe[name*="IFrame"]');

		await expect(viewIFrame.getByText('Welcome to Liferay')).toBeVisible();
	}
);
