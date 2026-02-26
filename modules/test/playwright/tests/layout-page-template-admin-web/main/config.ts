/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {devices} from '@playwright/test';

export const config = {
	dependencies: ['page-management-site.main'],
	name: 'layout-page-template-admin-web.main',
	testDir: 'tests/layout-page-template-admin-web/main',
	use: {
		...devices['Desktop Chrome'],
		testIdAttribute: 'data-qa-id',
	},
};
