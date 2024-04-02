/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(loginTest());

const SAMPLE = {
	erc: 'LXC:liferay-sample-theme-favicon',
	name: 'Liferay Sample Theme Favicon',
	url: '/o/liferay-sample-theme-favicon/favicon.ede86c41bfce8f631bb7386d5682f3ad4c1ab19c.ico',
};

test(`${SAMPLE.name} is registered`, async ({page}) => {
	const viewClientExtensionPage = new ViewClientExtensionPage(
		page,
		SAMPLE.erc
	);

	await viewClientExtensionPage.goto();

	expect(viewClientExtensionPage.nameLocator).toHaveValue(SAMPLE.name);
	expect(viewClientExtensionPage.fieldLocator('URL')).toHaveValue(SAMPLE.url);
});

test(`${SAMPLE.name}'s .ico file can be downloaded`, async ({page}) => {
	const response = await page.goto(SAMPLE.url);

	expect(response.status()).toBe(200);
});
