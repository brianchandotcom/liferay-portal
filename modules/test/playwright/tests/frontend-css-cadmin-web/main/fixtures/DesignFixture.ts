/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesAdminPage} from '../../../../pages/layout-admin-web/PagesAdminPage';
import {PageEditorPage} from '../../../../pages/layout-content-page-editor-web/PageEditorPage';

export class DesignFixture {
	constructor(
		private pageEditorPage: PageEditorPage,
		private pagesAdminPage: PagesAdminPage,
		private site: Site
	) {}

	async addCustomCSS(pageName: string, css: string) {
		await this.pagesAdminPage.goto(this.site.friendlyUrlPath);

		await this.pagesAdminPage.goToDesignTabConfiguration(pageName);

		await this.pagesAdminPage.addCustomCSS(css);

		await this.pagesAdminPage.goto(this.site.friendlyUrlPath);

		await this.pagesAdminPage.editPage(pageName);

		await this.pageEditorPage.publishPage();

		await this.pagesAdminPage.editPage(pageName);
	}
}
