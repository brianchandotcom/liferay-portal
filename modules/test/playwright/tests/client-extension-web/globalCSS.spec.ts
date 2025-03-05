/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const testSample = mergeTests(loginTest());

const SAMPLES = [
	{
		erc: 'LXC:liferay-sample-global-css-1',
		name: 'Liferay Sample Global CSS 1',
		url: '/o/liferay-sample-global-css-1/global.b5d59573c187989c3bf28db9f07841b318eea91.css',
	},
	{
		erc: 'LXC:liferay-sample-global-css-2',
		name: 'Liferay Sample Global CSS 2',
		url: '/o/liferay-sample-global-css-2/global.6a3837db279c3dcc37796b95896b9bda58ccce98.css',
	},
];

for (const sample of SAMPLES) {
	testSample(`${sample.name} is registered`, async ({page}) => {
		const viewClientExtensionPage = new ViewClientExtensionPage(
			page,
			sample.erc
		);

		await viewClientExtensionPage.goto();

		await expect(viewClientExtensionPage.nameLocator).toHaveValue(
			sample.name
		);

		await expect(viewClientExtensionPage.fieldLocator('URL')).toHaveValue(
			sample.url
		);
	});

	testSample(
		`${sample.name}'s .css file can be downloaded`,
		async ({page}) => {
			const response = await page.goto(sample.url);

			expect(response.status()).toBe(200);
		}
	);
}
