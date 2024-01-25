/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {defineConfig} from '@playwright/test';

import {config as batchPlannerConfig} from './tests/batch-planner/config';
import {config as clientExtensionWebConfig} from './tests/client-extension-web/config';
import {config as commerceConfig} from './tests/commerce/config';
import {config as exportImportWebConfig} from './tests/export-import-web/config';
import {config as layoutContentPageEditorWebConfig} from './tests/layout-content-page-editor-web/config';
import {config as objectWebConfig} from './tests/object-web/config';
import {config as portalWebConfig} from './tests/portal-web/config';
import {config as usersAdminWebConfig} from './tests/users-admin-web/config';

export default defineConfig({
	expect: {
		timeout: 15 * 1000,
	},
	forbidOnly: !!process.env.CI,
	projects: [
		batchPlannerConfig,
		clientExtensionWebConfig,
		commerceConfig,
		exportImportWebConfig,
		layoutContentPageEditorWebConfig,
		objectWebConfig,
		portalWebConfig,
		usersAdminWebConfig,
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
