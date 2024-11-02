/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {applicationsMenuPageTest} from '../../fixtures/applicationsMenuPageTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {stagingConfigurationPageTest} from './fixtures/stagingConfigurationPageTest';

export const test = mergeTests(
	applicationsMenuPageTest,
	apiHelpersTest,
	loginTest(),
	stagingConfigurationPageTest
);

test('Check if local staging can be enabled', async ({
	apiHelpers,
	applicationsMenuPage,
	stagingConfigurationPage,
}) => {
	const siteName: string = getRandomString();

	await applicationsMenuPage.goToSites();

	const site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});

	await stagingConfigurationPage.gotoStagingConfiguration(
		site.friendlyUrlPath
	);

	await stagingConfigurationPage.enableLocalStaging({});
});
