/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {PartnerHelper} from '../helpers/PartnerHelper';
import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {loginTest} from '../../../../fixtures/loginTest';

const test = mergeTests(
	apiHelpersTest,
	loginTest({screenName: 'test'})
);

export const partnerHelperTest = test.extend<{
	partnerHelper: PartnerHelper;
}>({
	partnerHelper: async ({apiHelpers, page}, use) => {
		const site =
			await apiHelpers.headlessSite.getSiteByERC('LIFERAY_PARTNER');
		
		await use(new PartnerHelper(page, site));
	},
});
