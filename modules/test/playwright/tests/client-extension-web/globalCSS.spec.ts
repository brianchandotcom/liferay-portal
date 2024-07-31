/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(loginTest());

const SAMPLE = {
	erc: 'LXC:liferay-sample-global-css',
	name: 'Liferay Sample Global CSS',
	url: '/o/liferay-sample-global-css/global.ed43ae1d215bfee1d2df.css',
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

test(`${SAMPLE.name}'s .css file can be downloaded`, async ({page}) => {
	const response = await page.goto(SAMPLE.url);

	expect(response.status()).toBe(200);
});
