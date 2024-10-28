/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ComponentsPage} from '../pages/ComponentsPage';

const componentsPageTest = test.extend<{
	componentsPage: ComponentsPage;
}>({
	componentsPage: async ({page}, use) => {
		await use(new ComponentsPage(page));
	},
});

export {componentsPageTest};
