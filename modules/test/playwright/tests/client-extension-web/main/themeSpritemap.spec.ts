/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedLayoutTest} from '../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

export const test = mergeTests(
	isolatedLayoutTest({publish: false}),
	loginTest()
);

const SAMPLES = [
	{
		erc: 'LXC:liferay-sample-theme-spritemap-1',
		name: 'Liferay Sample Theme Spritemap 1',
		url: '',
	},
	{
		erc: 'LXC:liferay-sample-theme-spritemap-2',
		name: 'Liferay Sample Theme Spritemap 2',
		url: '',
	},
];

for (const sample of SAMPLES) {
	test(`${sample.name} is registered`, async ({page}) => {
		const viewClientExtensionPage = new ViewClientExtensionPage(
			page,
			sample.erc
		);

		await viewClientExtensionPage.goto();

		sample.url = await viewClientExtensionPage
			.fieldLocator('URL')
			.inputValue();

		expect(viewClientExtensionPage.nameLocator).toHaveValue(sample.name);
	});

	test(`${sample.name}'s .svg file can be downloaded`, async ({page}) => {
		const response = await page.goto(sample.url);

		expect(response.status()).toBe(200);
	});
}
