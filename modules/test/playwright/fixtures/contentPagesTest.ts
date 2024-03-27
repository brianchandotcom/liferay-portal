/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ContentPage} from '../pages/layout-admin-web/ContentPage';

const contentPagesTest = test.extend<{
	contentPage: ContentPage;
}>({
	contentPage: async ({page}, use) => {
		await use(new ContentPage(page));
	},
});

export {contentPagesTest};
