/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {remoteStagingApiHelperTest} from '../../fixtures/remoteStagingApiHelpersTest';
import {performLoginViaApi} from '../../utils/performLogin';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	featureFlagsTest({
		'LPD-39304': {enabled: true},
	}),
	remoteStagingApiHelperTest,
);

test(
	'Check Web contents can be published via their portlet using remote staging',
	{tag: '@LPS-81950'},
	async ({
		apiHelpers,
		page,
		remoteStagingApiHelper
	}) => {
		const site = await apiHelpers.headlessSite.createSite({
			name: 'Site Name',
		});

		apiHelpers.data.push({id: site.id, type: 'site'});

		const layout = await apiHelpers.jsonWebServicesLayout.addLayout({
			groupId: site.id,
			options: {
				type: 'portlet',
			},
			title: 'Staging Test Page',
		});

		const remoteUrl = remoteStagingApiHelper.baseUrl.substring(
			0,
			remoteStagingApiHelper.baseUrl.length - 3
		);

		await performLoginViaApi({
			apiHelpers: remoteStagingApiHelper,
			loginUrl: remoteUrl,
			page,
			screenName: 'test',
		});

		const remoteSite = await remoteStagingApiHelper.headlessSite.createSite(
			{
				name: 'Remote Site Name',
			}
		);

		await performLoginViaApi({
			page,
			screenName: 'test',
		});

		await apiHelpers.jsonWebServicesStaging.enableRemoteStaging({
			groupId: site.id,
			remoteGroupId: remoteSite.id,
			remotePort: 9080,
		});
	}
);
