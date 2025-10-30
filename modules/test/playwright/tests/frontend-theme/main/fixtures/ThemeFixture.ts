/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {PagesAdminPage} from '../../../../pages/layout-admin-web/PagesAdminPage';
import {doAndGoBack} from '../../../../utils/doAndGoBack';

export class ThemeFixture {
	constructor(
		private pagesAdminPage: PagesAdminPage,
		private site: Site
	) {}

	async changePageThemeToDialect(pageName: string) {
		await this.changePageTheme(pageName, 'Dialect');
	}

	async changePageTheme(pageName: string, theme?: string) {
		await doAndGoBack(this.pagesAdminPage.page, async () => {
			await this.pagesAdminPage.goto(this.site.friendlyUrlPath);

			await this.pagesAdminPage.goToDesignTabConfiguration(pageName);

			await this.pagesAdminPage.changeTheme(theme ?? 'Classic');

			await this.pagesAdminPage.goto(this.site.friendlyUrlPath);
		});
	}
}
