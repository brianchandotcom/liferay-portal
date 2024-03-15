/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {test} from '@playwright/test';

import {ChangeTrackingPage} from '../pages/change-tracking-web/ChangeTrackingPage';

const changeTrackingPagesTest = test.extend<{
	changeTrackingPage: ChangeTrackingPage;
}>({
	changeTrackingPage: async ({page}, use) => {
		await use(new ChangeTrackingPage(page));
	},
});

export {changeTrackingPagesTest};
