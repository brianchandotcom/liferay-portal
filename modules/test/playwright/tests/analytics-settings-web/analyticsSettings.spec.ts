/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {syncAnalyticsCloud} from './utils/analyticsSettings';

export const test = mergeTests(loginAnalyticsCloudTest(), loginTest());

test('creates a new data source and connects to DXP', async ({page}) => {
	syncAnalyticsCloud(page);
});
