/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(loginTest());

const SAMPLES = [
	{
		bareSpecifier: 'jquery',
		erc: 'LXC:liferay-sample-js-import-maps-entry',
		name: 'Liferay Sample JS Import Maps Entry',
		url: '/o/liferay-sample-js-import-maps-entry/jquery.62d686ddc1e428e249ff.js',
	},
	{
		bareSpecifier: 'my-utils',
		erc: 'LXC:liferay-sample-etc-frontend-js-import-maps-entry',
		name: 'Liferay Sample Etc Frontend JS Import Maps Entry',
		url: '/o/liferay-sample-etc-frontend/my-utils.41642c66e4920076a271.js',
	},
];

for (const sample of SAMPLES) {
	test(`${sample.name} is registered`, async ({page}) => {
		const viewClientExtensionPage = new ViewClientExtensionPage(
			page,
			sample.erc
		);

		await viewClientExtensionPage.goto();

		expect(viewClientExtensionPage.nameLocator).toHaveValue(sample.name);
		expect(viewClientExtensionPage.fieldLocator('URL')).toHaveValue(
			sample.url
		);
	});

	test(`${sample.name}'s .js file can be downloaded`, async ({page}) => {
		const response = await page.goto(sample.url);

		expect(response.status()).toBe(200);
	});

	test(`${sample.name} appears in import maps`, async ({page}) => {
		await page.goto('/');

		const importMap = await page
			.locator('script[type="importmap"]')
			.evaluate((node: HTMLScriptElement) => node.innerText);

		expect(importMap).toContain(
			`"${sample.bareSpecifier}":"${sample.url}"`
		);
	});
}
