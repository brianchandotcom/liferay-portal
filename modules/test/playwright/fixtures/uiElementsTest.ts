/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {UIElementsPage} from '../pages/uielements/UIElementsPage';

const uiElementsPageTest = test.extend<{
	uiElementsPage: UIElementsPage;
}>({
	uiElementsPage: async ({page}, use) => {
		await use(new UIElementsPage(page));
	},
});

export {uiElementsPageTest};
