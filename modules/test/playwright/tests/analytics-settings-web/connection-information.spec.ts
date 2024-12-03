/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {
	PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX,
	PROPERTY_SITE_COLUMN_INDEX,
	expectPropertyColumn,
	goToSettingsStep,
	syncAnalyticsCloud,
	syncCommerce,
	syncSite,
} from './utils/analytics-settings';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-20640': true,
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Modify the sites and channels synchronized in the property review sidebar',
	{
		tag: '@LRAC-11044',
	},
	async ({apiHelpers, page}) => {
		const site1 = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site1.id, type: 'site'});

		const commerceChannel1 =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: getRandomString(),
				siteGroupId: site1.id,
			});

		const channelName = 'My Property - ' + getRandomString();

		await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			commerceChannelName: commerceChannel1.name,
			page,
			siteName: site1.name,
		});

		const site2 = await apiHelpers.headlessSite.createSite({
			name: getRandomString(),
		});

		apiHelpers.data.push({id: site2.id, type: 'site'});

		const commerceChannel2 =
			await apiHelpers.headlessCommerceAdminChannel.postChannel({
				name: getRandomString(),
				siteGroupId: site2.id,
			});

		await goToSettingsStep({
			page,
			stepName: 'Properties',
		});

		await expectPropertyColumn({
			channelName,
			expectedValue: '1',
			index: PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX,
			page,
		});

		await expectPropertyColumn({
			channelName,
			expectedValue: '1',
			index: PROPERTY_SITE_COLUMN_INDEX,
			page,
		});

		await syncSite({
			channelName,
			page,
			siteName: site2.name,
		});

		await syncCommerce({
			channelName,
			commerceChannelName: commerceChannel2.name,
			page,
		});

		await expectPropertyColumn({
			channelName,
			expectedValue: '2',
			index: PROPERTY_COMMERCE_CHANNEL_COLUMN_INDEX,
			page,
		});

		await expectPropertyColumn({
			channelName,
			expectedValue: '2',
			index: PROPERTY_SITE_COLUMN_INDEX,
			page,
		});
	}
);
