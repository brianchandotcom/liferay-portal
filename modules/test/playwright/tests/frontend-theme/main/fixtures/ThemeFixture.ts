/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';
import path from 'path';

import {PagesAdminPage} from '../../../../pages/layout-admin-web/PagesAdminPage';
import {PageEditorPage} from '../../../../pages/layout-content-page-editor-web/PageEditorPage';
import {AppManagerPage} from '../../../../pages/marketplace-app-manager-web/AppManagerPage';
import {BundleBlacklistPage} from '../../../../pages/marketplace-app-manager-web/BundleBlacklistPage';
import {doAndGoBack} from '../../../../utils/doAndGoBack';

export class ThemeFixture {
	private readonly testThemeName = 'test-theme-7-4';
	private readonly testThemeAppName = 'test-theme';
	private readonly testThemeAppPath = path.join(
		__dirname,
		'..',
		'..',
		'..',
		'..',
		'..',
		'..',
		'..',
		'portal-web',
		'test',
		'functional',
		'com',
		'liferay',
		'portalweb',
		'dependencies',
		'test-theme.war'
	);

	constructor(
		private readonly appManagerPage: AppManagerPage,
		private readonly bundleBlacklistPage: BundleBlacklistPage,
		private readonly page: Page,
		private readonly pageEditorPage: PageEditorPage,
		private readonly pagesAdminPage: PagesAdminPage,
		private readonly site: Site
	) {}

	async addTestTheme() {
		await doAndGoBack(this.page, async () => {
			await this.appManagerPage.uploadAppFromLocalDirectory(
				this.testThemeAppName,
				path.resolve(this.testThemeAppPath)
			);
		});
	}

	async deactivateTestTheme(testPageName: string) {
		await doAndGoBack(this.page, async () => {
			await this.appManagerPage.deactivateApp(this.testThemeAppName);

			await this.pagesAdminPage.goto(this.site.friendlyUrlPath);

			await this.pagesAdminPage.goToDesignTabConfiguration(testPageName);

			await this.pagesAdminPage.expectThemeToBeDeactivated(
				this.testThemeName
			);
		});
	}

	async activateTestTheme(testPageName: string) {
		await doAndGoBack(this.page, async () => {
			await this.appManagerPage.activateApp(this.testThemeAppName);

			await this.pagesAdminPage.goto(this.site.friendlyUrlPath);

			await this.pagesAdminPage.goToDesignTabConfiguration(testPageName);

			await this.pagesAdminPage.expectThemeToBeActivated(
				this.testThemeName
			);
		});
	}

	async changePageThemeToDialect(pageName: string) {
		await this.changePageTheme(pageName, 'Dialect');
	}

	async changePageThemeToClassic(pageName: string) {
		await this.changePageTheme(pageName, 'Classic');
	}

	async changePageThemeToTestTheme(pageName: string) {
		await this.changePageTheme(pageName, this.testThemeName);
	}

	async changePageTheme(pageName: string, theme: string) {
		await doAndGoBack(this.page, async () => {
			await this.pagesAdminPage.goto(this.site.friendlyUrlPath);

			await this.pagesAdminPage.goToDesignTabConfiguration(pageName);

			await this.pagesAdminPage.changeTheme(theme);

			await this.pagesAdminPage.goto(this.site.friendlyUrlPath);
		});
	}

	async publishPage(pageName: string) {
		await doAndGoBack(this.page, async () => {
			await this.pagesAdminPage.goto(this.site.friendlyUrlPath);

			await this.pagesAdminPage.editPage(pageName);

			await this.pageEditorPage.publishPage();
		});
	}
}
