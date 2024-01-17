/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {defineConfig} from '@playwright/test';

import {config as exportImportWeb} from './tests/export-import-web/config';
import {config as batchPlanner} from './tests/batch-planner/config';
import {config as setup} from './tests/global.setup.config';
import {config as object} from './tests/object-web/config';
import {config as portalWeb} from './tests/portal-web/config';
import {config as usersAdminWeb} from './tests/users-admin-web/config';

export default defineConfig({
	expect: {
		timeout: 15 * 1000,
	},
	forbidOnly: !!process.env.CI,
	projects: [batchPlanner, exportImportWeb, object, portalWeb, setup, usersAdminWeb],
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
	timeout: 90 * 1000,
	use: {
		baseURL: process.env.PORTAL_URL
			? process.env.PORTAL_URL
			: 'http://localhost:8080',
		screenshot: 'only-on-failure',
		trace: 'retain-on-failure',
	},
	workers: 1,
});
