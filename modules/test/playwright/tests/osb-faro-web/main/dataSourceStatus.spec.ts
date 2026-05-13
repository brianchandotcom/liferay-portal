/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {
	connectToAnalyticsCloud,
	disconnectFromAnalyticsCloud,
	goNextStep,
	goToAnalyticsCloudInstanceSettings,
	syncAllContacts,
	toggleSiteSync,
} from '../../analytics-settings-web/main/utils/analytics-settings';
import {
	checkDataSourceStatus,
	createDataSource,
	disconnectDataSourceFromAC,
	gotoLatestLiferayDXPDataSource,
} from './utils/data-source';

export const test = mergeTests(
	apiHelpersTest,
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Data source status reflects DXP sync state for sites and contacts',
	{
		tag: '@LRAC-12439',
	},
	async ({analyticsChannel: channel, page, project}) => {
		const {token} = await createDataSource(page);

		// Connect DXP to AC with the token, no sites or contacts synced yet

		await goToAnalyticsCloudInstanceSettings(page);

		await disconnectFromAnalyticsCloud(page);

		await connectToAnalyticsCloud(page, {token});

		await expect(
			page.getByRole('heading', {name: 'Property Assignment'})
		).toBeVisible();

		// Verify status after token connect

		await gotoLatestLiferayDXPDataSource(page, project);

		await checkDataSourceStatus({
			dataSourceStatus: 'Connected',
			page,
			syncedContactsStatus: 'Disconnected',
			syncedSitesStatus: 'Disconnected',
		});

		// Back to DXP, sync sites

		await goToAnalyticsCloudInstanceSettings(page);

		await goNextStep(page);

		await toggleSiteSync({channelName: channel.name, page});

		// Verify status after sync sites

		await gotoLatestLiferayDXPDataSource(page, project);

		await checkDataSourceStatus({
			dataSourceStatus: 'Active',
			page,
			syncedContactsStatus: 'Disconnected',
			syncedSitesStatus: 'Connected',
		});

		// Back to DXP, sync contacts

		await goToAnalyticsCloudInstanceSettings(page);

		await goNextStep(page);

		await goNextStep(page);

		await syncAllContacts(page);

		// Verify status after sync contacts

		await gotoLatestLiferayDXPDataSource(page, project);

		await checkDataSourceStatus({
			dataSourceStatus: 'Active',
			page,
			syncedContactsStatus: 'Connected',
			syncedSitesStatus: 'Connected',
		});

		// Disconnect the data source from AC

		await disconnectDataSourceFromAC(page);

		await checkDataSourceStatus({
			dataSourceStatus: 'Disconnected',
			page,
			syncedContactsStatus: 'Disconnected',
			syncedSitesStatus: 'Disconnected',
		});
	}
);
