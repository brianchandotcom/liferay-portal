/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {LayoutSetPrototypePage} from '../pages/LayoutSetPrototypePage';

const layoutSetPrototypePageTest = test.extend<{
	layoutSetPrototypePage: LayoutSetPrototypePage;
}>({
	layoutSetPrototypePage: async ({page}, use) => {
		await use(new LayoutSetPrototypePage(page));
	},
});

export {layoutSetPrototypePageTest};
