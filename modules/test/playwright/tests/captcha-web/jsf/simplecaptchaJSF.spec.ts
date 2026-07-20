/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {captchaConfigPageTest} from '../../../fixtures/captchaConfigPageTest';
import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageViewModePagesTest} from '../../../fixtures/pageViewModePagesTest';

const test = mergeTests(
	captchaConfigPageTest,
	isolatedLayoutTest({type: 'portlet'}),
	loginTest(),
	pageViewModePagesTest
);

test(
	'LPD-98644 check that the CAPTCHA refresh button works on a JSF portlet rendered through the Liferay Faces portal:captcha component',
	{tag: '@LPD-98644'},
	async ({captchaConfigPage, layout, page, widgetPagePage}) => {
		await captchaConfigPage.goTo();

		await captchaConfigPage.resetCaptchaConfiguration();

		await page.goto(`/web/guest/${layout.friendlyURL}`);

		await widgetPagePage.addPortlet('Sample CAPTCHA JSF Portlet', 'Sample');

		await page.waitForLoadState();

		await page.reload();

		const captchaImgSource = await page
			.getByAltText('Text to Identify')
			.getAttribute('src');

		await page.getByTitle('Refresh CAPTCHA').click();

		const refreshedCaptchaImgSource = await page
			.getByAltText('Text to Identify')
			.getAttribute('src');

		await expect(captchaImgSource).not.toEqual(refreshedCaptchaImgSource);
	}
);
