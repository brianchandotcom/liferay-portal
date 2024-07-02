/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {WidgetPagePage} from '../pages/layout-admin-web/WidgetPagePage';

const pageViewModePagesTest = test.extend<{
	widgetPagePage: WidgetPagePage;
}>({
	widgetPagePage: async ({page}, use) => {
		await use(new WidgetPagePage(page));
	},
});

export {pageViewModePagesTest};
