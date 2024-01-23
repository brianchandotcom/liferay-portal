/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {devices} from '@playwright/test';

export const config = {
	dependencies: ['setup'],
	name: 'headless-builder-web',
	testDir: 'tests/headless-builder-web',
	use: {
		...devices['Desktop Chrome'],
		storageState: 'tmp/.auth/user.json',
		testIdAttribute: 'data-qa-id',
	},
};
