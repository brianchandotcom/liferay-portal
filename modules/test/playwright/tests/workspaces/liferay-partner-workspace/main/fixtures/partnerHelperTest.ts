/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {loginTest} from '../../../../fixtures/loginTest';
import {PartnerHelper} from '../helpers/PartnerHelper';

const test = mergeTests(loginTest({screenName: 'test'}));

export const partnerHelperTest = test.extend<{
	partnerHelper: PartnerHelper;
}>({
	partnerHelper: async ({page}, use) => {
		await use(new PartnerHelper(page));
	},
});
