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

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	loginTest(),
	pageViewModePagesTest
);

test(
	'Checks center text alignment in Look and Feel',
	{
		tag: '@LPD-31641',
	},
	async ({apiHelpers, page, site, widgetPagePage}) => {

		// Create page and go to view mode

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			title: getRandomString(),
		});

		await page.goto(`/web${site.friendlyUrlPath}${layout.friendlyURL}`);

		// Add Asset Publisher widget

		await widgetPagePage.addPortlet('Asset Publisher');

		// Open Look and Feel Configuration

		await widgetPagePage.clickOnAction(
			'Look and Feel Configuration',
			page.locator('.portlet-asset-publisher').first()
		);

		const lookAndFeelIFrame = page.frameLocator(
			'iframe[title="Look and Feel Configuration"]'
		);

		// Update Look and Feel Configuration

		await lookAndFeelIFrame.getByRole('tab', {name: 'Text Styles'}).click();

		await lookAndFeelIFrame.getByLabel('Alignment').selectOption('center');

		await lookAndFeelIFrame.getByRole('button', {name: 'Save'}).click();

		// Assert custom styles

		await expect(lookAndFeelIFrame.getByLabel('Alignment')).toHaveValue(
			'center'
		);
	}
);
