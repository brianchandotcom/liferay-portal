/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, test} from '@playwright/test';

import {liferayConfig} from '../../../liferay.config';

test('JavaScript URLs honor CDN', {tag: '@LPD-66044'}, async ({page}) => {
	await page.goto(liferayConfig.environment.baseUrl);

	const pattern = /^http:\/\/cdn:8080\//;

	await test.step('CDN is referenced in import maps', async () => {
		const json = JSON.parse(
			await page.locator('script[type="importmap"]').textContent()
		);

		for (const url of Object.values(json.imports)) {
			await test.step(`Check URL "${url}"`, () =>
				expect(url).toMatch(pattern));
		}
	});

	await test.step('CDN is referenced in <script> tags', async () => {
		const scripts = await page.locator('script[src]').all();

		for (const script of scripts) {
			const src = await script.getAttribute('src');

			await test.step(`Check <script src="${src}">`, () =>
				expect(src).toMatch(pattern));
		}
	});
});
