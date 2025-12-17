/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';
import * as path from 'path';

import {getTempDir} from './temp';

export async function getSiteHomePageScreenshot(
	page: Page,
	siteKey: string,
	{
		mask,
		staging = false,
	}: {
		mask?: Locator;
		staging?: boolean;
	} = {}
) {
	const url = `/web/${siteKey}${staging ? '-staging' : ''}`;

	await page.goto(`${url}?p_l_mode=preview`, {waitUntil: 'load'});

	await page.evaluate(() => document.fonts.ready);

	const screenshot = await page.screenshot({
		fullPage: true,
		mask: mask ? [mask] : undefined,
		path: path.join(
			getTempDir(),
			`${siteKey}-${staging ? 'staging' : 'live'}.png`
		),
	});

	await page.goto(url);

	return screenshot;
}
