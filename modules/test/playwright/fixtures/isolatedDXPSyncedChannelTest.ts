/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {ApiHelpers} from '../helpers/ApiHelpers';
import {isolatedChannelTest} from './isolatedChannelTest';
import {isolatedSiteTest} from './isolatedSiteTest';

const test = mergeTests(isolatedSiteTest, isolatedChannelTest);

const isolatedDXPSyncedChannelTest = test.extend<{
	dxpSyncedAnalyticsChannel: void;
}>({
	dxpSyncedAnalyticsChannel: [
		async ({analyticsChannel, backendPage, project, site}, use) => {
			const apiHelpers = new ApiHelpers(backendPage);

			try {
				const connectionToken =
					await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
						project.groupId
					);

				await apiHelpers.analyticsSettingsRest.postDataSource(
					connectionToken
				);

				await apiHelpers.analyticsSettingsRest.syncSitesToChannel(
					analyticsChannel.id,
					[Number(site.id)]
				);

				await use();
			}
			finally {
				try {
					await apiHelpers.analyticsSettingsRest.deleteDataSource();
				}
				catch {

					// Best-effort cleanup; the data source may already be gone if
					// the test removed it explicitly.

				}
			}
		},
		{auto: true},
	],
});

export {isolatedDXPSyncedChannelTest};
