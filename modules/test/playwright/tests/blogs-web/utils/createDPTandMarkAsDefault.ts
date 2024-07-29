/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../../../utils/getRandomString';

import type {DisplayPageTemplatesPage} from '../../../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';

export async function createDPTandMarkAsDefault({
	displayPageTemplatesPage,
	site,
}: {
	displayPageTemplatesPage: DisplayPageTemplatesPage;
	site: Site;
}) {
	await displayPageTemplatesPage.goto(site.friendlyUrlPath);

	const displayPageTemplateName = getRandomString();

	await displayPageTemplatesPage.createTemplate({
		contentType: 'Blogs Entry',
		name: displayPageTemplateName,
	});

	await displayPageTemplatesPage.markAsDefault(displayPageTemplateName);
}
