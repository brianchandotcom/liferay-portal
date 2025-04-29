/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {DisplayPageTemplatesPage} from '../../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';
import getRandomString from '../../../../utils/getRandomString';

export async function createDPTandMarkAsDefault({
	page,
	site,
}: {
	page: Page;
	site: Site;
}) {
	const displayPageTemplatesPage = new DisplayPageTemplatesPage(page);

	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentType: 'Blogs Entry',
		name: displayPageTemplateName,
	});

	await displayPageTemplatesPage.markAsDefault(displayPageTemplateName);
}
