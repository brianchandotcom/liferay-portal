/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

const testSample = mergeTests(loginTest());

testSample.describe('Samples', () => {
	const SAMPLES = [
		{
			bareSpecifier: 'jquery',
			erc: 'LXC:liferay-sample-js-import-maps-entry',
			name: 'Liferay Sample JS Import Maps Entry',
			url: '',
		},
		{
			bareSpecifier: 'my-utils',
			erc: 'LXC:liferay-sample-etc-frontend-js-import-maps-entry',
			name: 'Liferay Sample Etc Frontend JS Import Maps Entry',
			url: '',
		},
	];

	for (const sample of SAMPLES) {
		testSample(`${sample.name} is registered`, async ({page}) => {
			const viewClientExtensionPage = new ViewClientExtensionPage(
				page,
				sample.erc
			);

			await viewClientExtensionPage.goto();

			sample.url = await viewClientExtensionPage
				.fieldLocator('URL')
				.inputValue();

			await expect(viewClientExtensionPage.nameLocator).toHaveValue(
				sample.name
			);
		});

		testSample(
			`${sample.name}'s .js file can be downloaded`,
			async ({page}) => {
				const response = await page.goto(sample.url);

				expect(response.status()).toBe(200);
				expect(await response.headerValue('Content-Type')).toBe(
					'text/javascript'
				);
			}
		);

		testSample(`${sample.name} appears in import maps`, async ({page}) => {
			await page.goto('/');

			const importMap = await page
				.locator('script[type="importmap"]')
				.evaluate((node: HTMLScriptElement) => node.innerText);

			expect(importMap).toContain(
				`"${sample.bareSpecifier}":"${sample.url}"`
			);
		});
	}
});
