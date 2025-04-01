/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginTest(),
	featureFlagsTest({
		'LPD-39304': {enabled: true},
	}),
);

test(
	'Check Web contents can be published via their portlet using remote staging',
	{tag: '@LPS-81950'},
	async ({
		apiHelpers,
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
	}
);
