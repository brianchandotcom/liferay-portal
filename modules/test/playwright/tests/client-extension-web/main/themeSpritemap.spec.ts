/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../../fixtures/loginTest';
import {ViewClientExtensionPage} from './pages/ViewClientExtensionPage';

const testSample = mergeTests(loginTest());
const test = mergeTests(loginTest());

testSample.describe('Samples', () => {
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
		testSample(`${sample.name} is registered`, async ({page}) => {
			const viewClientExtensionPage = new ViewClientExtensionPage(
				page,
				sample.erc
			);

			await viewClientExtensionPage.goto();

			sample.url = await viewClientExtensionPage
				.getInputByLabel('URL')
				.inputValue();

			await expect(viewClientExtensionPage.nameInput).toHaveValue(
				sample.name
			);
		});

		testSample(
			`${sample.name}'s .svg file can be downloaded`,
			async ({page}) => {
				const response = await page.goto(sample.url);

				expect(response.status()).toBe(200);
				expect(await response.headerValue('Content-Type')).toBe(
					'image/svg+xml'
				);
			}
		);
	}
});

test.fixme(
	'Theme SVG can extend SVG file from Document & Media',
	async ({page: _page}) => {
		// This test requires uploading an SVG to Documents and Media,
		// creating a Theme SVG client extension using the WebDAV URL,
		// updating the site Look and Feel with the registered theme
		// spritemap, and verifying custom SVG icons are displayed.
		//
		// Requires DM page objects and site settings page objects for
		// full implementation.
	}
);
