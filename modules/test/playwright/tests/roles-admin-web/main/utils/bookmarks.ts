/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect} from '@playwright/test';

import {DataApiHelpers} from '../../../../helpers/ApiHelpers';
import {BookmarksPage} from '../../../../pages/roles-admin-web/bookmarks/BookmarksPage';
import getRandomString from '../../../../utils/getRandomString';
import {waitForAlert} from '../../../../utils/waitForAlert';
import getPageDefinition from '../../layout-content-page-editor-web/utils/getPageDefinition';
import getWidgetDefinition from '../../layout-content-page-editor-web/utils/getWidgetDefinition';

export async function setupBookmark(
	apiHelpers: DataApiHelpers,
	bookmarkName: string,
	bookmarksPage: BookmarksPage,
	page: Page,
	site: Site
) {
	await bookmarksPage.goto(site.friendlyUrlPath);
	await bookmarksPage.bookmarksTable.newButton.click();
	await bookmarksPage.bookmarksMenuItem.click();

	await bookmarksPage.nameInput.fill(bookmarkName);
	await bookmarksPage.urlInput.fill('https://www.liferay.com');
	await bookmarksPage.permissionsButton.click();
	await bookmarksPage.viewableBySelect.selectOption('Owner');

	await bookmarksPage.saveButton.click();

	await waitForAlert(page);

	await expect(bookmarksPage.bookmarkItem(bookmarkName)).toBeVisible();

	const layout = await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getWidgetDefinition({
				id: getRandomString(),
				widgetName:
					'com_liferay_bookmarks_web_portlet_BookmarksPortlet',
			}),
		]),
		siteId: site.id,
		title: getRandomString(),
	});

	await page.goto(`/web/${site.name}/${layout.friendlyUrlPath}`);

	await expect(bookmarksPage.bookmarkItem(bookmarkName)).toBeVisible();

	return {layout, site};
}
