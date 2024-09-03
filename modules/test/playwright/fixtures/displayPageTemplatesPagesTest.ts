/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {test} from '@playwright/test';

import {DisplayPageTemplatesPage} from '../pages/layout-page-template-admin-web/DisplayPageTemplatesPage';

const displayPageTemplatesPagesTest = test.extend<{
	displayPageTemplatesPage: DisplayPageTemplatesPage;
}>({
	displayPageTemplatesPage: async ({page}, use) => {
		await use(new DisplayPageTemplatesPage(page));
	},
});

export {displayPageTemplatesPagesTest};
