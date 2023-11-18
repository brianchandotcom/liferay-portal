/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

const {defineConfig, devices} = require('@playwright/test');

module.exports = defineConfig({
	forbidOnly: !!process.env.CI,
	fullyParallel: true,
	projects: [
		{
			name: 'chromium',
			use: {...devices['Desktop Chrome']},
		},
		{
			name: 'firefox',
			use: {...devices['Desktop Firefox']},
		},
		{
			name: 'webkit',
			use: {...devices['Desktop Safari']},
		},
	],
	reporter: [
		[
			'html',
			{
				open: 'never',
			},
		],
		[
			'junit',
			{
				outputFile: 'test-results/TEST-playwright.xml',
			},
		],
	],
	retries: process.env.CI ? 2 : 0,
	testDir: './tests',
	timeout: 60000,
	use: {
		baseURL: process.env.PORTAL_URL
			? process.env.PORTAL_URL
			: 'http://localhost:8080',
		trace: 'on-first-retry',
	},
	workers: process.env.CI ? 3 : undefined,
});
