/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {defineConfig} from '@playwright/test';

import {wemSiteSetup, wemSiteTeardown} from './setup/wem-site/config';
import {config as accountAdminWebConfig} from './tests/account-admin-web/config';
import {config as analyticsSettingsWebConfig} from './tests/analytics-settings-web/config';
import {config as analyticsWebConfig} from './tests/analytics-web/config';
import {config as announcementsWebConfig} from './tests/announcements-web/config';
import {config as batchPlannerConfig} from './tests/batch-planner/config';
import {config as blogsWebConfig} from './tests/blogs-web/config';
import {config as changeTrackingWebConfig} from './tests/change-tracking-web/config';
import {config as clientExtensionWebConfig} from './tests/client-extension-web/config';
import {config as commerceConfig} from './tests/commerce/config';
import {config as dispatchWebConfig} from './tests/dispatch-web/config';
import {config as documentLibraryWebConfig} from './tests/document-library-web/config';
import {config as exportImportWebConfig} from './tests/export-import-web/config';
import {config as frontendDataSetViewsWebConfig} from './tests/frontend-data-set-views-web/config';
import {config as headlessBuilderImplConfig} from './tests/headless-builder-impl/config';
import {config as headlessBuilderWebConfig} from './tests/headless-builder-web/config';
import {config as journalWebConfig} from './tests/journal-web/config';
import {config as knowledgeBaseWebConfig} from './tests/knowledge-base-web/config';
import {config as layoutAdminWebConfig} from './tests/layout-admin-web/config';
import {config as layoutContentPageEditorWebConfig} from './tests/layout-content-page-editor-web/config';
import {config as layoutSetPrototypeWebConfig} from './tests/layout-set-prototype-web/config';
import {config as layoutTaglib} from './tests/layout-taglib/config';
import {config as lockedItemsConfig} from './tests/locked-items-web/config';
import {config as loginWebConfig} from './tests/login-web/config';
import {config as objectWebConfig} from './tests/object-web/config';
import {config as osbFaroWebConfig} from './tests/osb-faro-web/config';
import {config as portalWebConfig} from './tests/portal-web/config';
import {config as portalWorkflowKaleoDesignerWebConfig} from './tests/portal-workflow-kaleo-designer-web/config';
import {config as productNavigationControlMenuWebConfig} from './tests/product-navigation-control-menu-web/config';
import {config as productNavigationProductMenuWebConfig} from './tests/product-navigation-product-menu-web/config';
import {config as productNavigationUserPersonalBarWebConfig} from './tests/product-navigation-user-personal-bar-web/config';
import {config as stableConfig} from './tests/stable/config';
import {config as stylebookConfig} from './tests/style-book-web/config';
import {config as usersAdminWebConfig} from './tests/users-admin-web/config';

const setupProjects = [wemSiteSetup, wemSiteTeardown];

export default defineConfig({
	expect: {
		timeout: 15 * 1000,
	},
	forbidOnly: !!process.env.CI,
	projects: [
		accountAdminWebConfig,
		analyticsWebConfig,
		analyticsSettingsWebConfig,
		announcementsWebConfig,
		batchPlannerConfig,
		blogsWebConfig,
		changeTrackingWebConfig,
		clientExtensionWebConfig,
		commerceConfig,
		dispatchWebConfig,
		documentLibraryWebConfig,
		exportImportWebConfig,
		frontendDataSetViewsWebConfig,
		headlessBuilderImplConfig,
		headlessBuilderWebConfig,
		journalWebConfig,
		knowledgeBaseWebConfig,
		layoutAdminWebConfig,
		layoutContentPageEditorWebConfig,
		layoutSetPrototypeWebConfig,
		layoutTaglib,
		lockedItemsConfig,
		loginWebConfig,
		objectWebConfig,
		osbFaroWebConfig,
		portalWebConfig,
		portalWorkflowKaleoDesignerWebConfig,
		productNavigationControlMenuWebConfig,
		productNavigationProductMenuWebConfig,
		productNavigationUserPersonalBarWebConfig,
		stableConfig,
		stylebookConfig,
		usersAdminWebConfig,
		...setupProjects,
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
