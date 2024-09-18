/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(
	isolatedLayoutTest({publish: false}),
	loginTest()
);

const SAMPLES = [
	{
		erc: 'LXC:liferay-sample-theme-spritemap-1',
		name: 'Liferay Sample Theme Spritemap 1',
		url: '/o/liferay-sample-theme-spritemap-1/spritemap.7b88c355855cfccc64b716479219007e6493f139.svg',
	},
	{
		erc: 'LXC:liferay-sample-theme-spritemap-2',
		name: 'Liferay Sample Theme Spritemap 2',
		url: '/o/liferay-sample-theme-spritemap-2/spritemap.87f79d7e10ac5f96ecc168234ba026ccf2ecf451.svg',
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

	test(`${sample.name}'s .svg file can be downloaded`, async ({page}) => {
		const response = await page.goto(sample.url);

		expect(response.status()).toBe(200);
	});
}
