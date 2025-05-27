/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers, DataApiHelpers} from '../../../helpers/ApiHelpers';
import getPageDefinition from '../../../tests/layout-content-page-editor-web/main/utils/getPageDefinition';
import getWidgetDefinition from '../../../tests/layout-content-page-editor-web/main/utils/getWidgetDefinition';
import getRandomString from '../../../utils/getRandomString';
import {PageEditorPage} from '../../layout-content-page-editor-web/PageEditorPage';

export class UserAssociatedDataAnnouncementPage {
	readonly page: Page;

	constructor(page: Page) {
		this.page = page;
	}

	async createAnnouncementPage(
		apiHelpers: ApiHelpers | DataApiHelpers,
		site: Site,
		options?: {title?: string}
	) {
		options = {
			title: getRandomString(),
			...(options || {}),
		};

		const layout = await apiHelpers.headlessDelivery.createSitePage({
			pageDefinition: getPageDefinition([
				getWidgetDefinition({
					id: getRandomString(),
					widgetName:
						'com_liferay_announcements_web_portlet_AnnouncementsPortlet',
				}),
			]),
			siteId: String(site.id),
			title: options.title,
		});

		const pageEditorPage = new PageEditorPage(this.page);

		await pageEditorPage.goto(layout, site.friendlyUrlPath);
		await pageEditorPage.publishPage();

		return layout;
	}
}
