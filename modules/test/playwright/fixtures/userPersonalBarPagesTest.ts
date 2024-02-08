/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {UserPersonalBarPage} from '../pages/product-navigation-user-personal-bar-web/UserPersonalBarPage';

const userPersonalBarPagesTest = test.extend<{
	userPersonalBarPage: UserPersonalBarPage;
}>({
	userPersonalBarPage: async ({page}, use) => {
		await use(new UserPersonalBarPage(page));
	},
});

export {userPersonalBarPagesTest};
