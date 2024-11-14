/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {waitForAlert} from '../../utils/waitForAlert';
import {tagsPagesTest} from './fixtures/tagsAdminPagesTest';

const test = mergeTests(
	apiHelpersTest,
	isolatedSiteTest,
	tagsPagesTest,
	loginTest()
);

test('Add and edit tag', async ({page, site, tagsEditPage}) => {
	await tagsEditPage.add('tag name', site.friendlyUrlPath);

	await waitForAlert(page);
});
