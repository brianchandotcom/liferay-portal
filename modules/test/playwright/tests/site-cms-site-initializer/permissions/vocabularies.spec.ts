/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {PORTLET_URLS} from '../../../utils/portletUrls';
import {addRoleMemberAndSwitch} from '../main/spaces/helpers/roleMembership';
import {cmsPagesTest} from './fixtures/cmsPagesTest';

const test = mergeTests(
	cmsPagesTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-11235': {enabled: false},
		'LPD-17564': {enabled: true},
	}),
	loginTest()
);

test(
	'A Space Content Reviewer cannot access the Vocabularies admin page',
	{tag: '@LPD-89497'},
	async ({apiHelpers, page, spaceSummaryPage}) => {
		const spaceName = getRandomString();

		await apiHelpers.headlessAssetLibrary.createAssetLibrary({
			name: spaceName,
			settings: {},
			type: 'Space',
		});

		await addRoleMemberAndSwitch({
			apiHelpers,
			page,
			role: 'Space Content Reviewer',
			spaceName,
			spaceSummaryPage,
		});

		await page.goto(PORTLET_URLS.cmsVocabularies);

		await expect(
			page.getByRole('heading', {name: 'Categorization'})
		).toBeHidden();
	}
);
