/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import {PageEditorPage} from '../../../../pages/layout-content-page-editor-web/PageEditorPage';
import {doAndGoBack} from '../../../../utils/doAndGoBack';
import getRandomString from '../../../../utils/getRandomString';
import getContainerDefinition from '../../../layout-content-page-editor-web/main/utils/getContainerDefinition';
import getPageDefinition from '../../../layout-content-page-editor-web/main/utils/getPageDefinition';

export class PageFixture {
	constructor(
		private readonly apiHelpers: ApiHelpers,
		private readonly page: Page,
		private readonly pageEditorPage: PageEditorPage,
		private readonly site: Site
	) {}

	async goToPage(page: Layout) {
		await this.page.goto(
			`/web${this.site.friendlyUrlPath}${page.friendlyUrlPath}`
		);
	}

	async goToPageEditor(page: Layout) {
		await this.pageEditorPage.goto(page, this.site.friendlyUrlPath);
	}

	async createPageWithPrimaryBackgroundFragment() {
		const pageName = getRandomString();
		const fragmentId = getRandomString();

		return await doAndGoBack(this.page, async () => {
			const page = await this.createPage(pageName, [
				getContainerDefinition({id: fragmentId}),
			]);

			await this.pageEditorPage.goto(page, this.site.friendlyUrlPath);

			await this.pageEditorPage.changeFragmentConfiguration({
				fieldLabel: 'CSS Classes',
				fragmentId,
				tab: 'Advanced',
				value: 'bg-primary',
			});

			await this.pageEditorPage.publishPage();

			return {
				fragment: this.pageEditorPage.getFragment(fragmentId),
				fragmentId,
				page,
				pageName,
			};
		});
	}

	async createPage(pageName: string, pageElements: PageElement[] = []) {
		return await this.apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition(pageElements),
			siteId: this.site.id,
			title: pageName,
		});
	}
}
