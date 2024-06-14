/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {
	navigateToSitePage,
	syncAnalyticsCloud,
} from '../analytics-settings-web/utils/analyticsSettings';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import {faroConfig} from './faro.config';
import {createChannel} from './utils/channel';
import {performLogout} from '../../utils/performLogin';
import {headlessBuilderPagesTest} from '../headless-builder-web/fixtures/headlessBuilderPagesTest';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('TEST', async ({apiHelpers, page}) => {
	const {channel, project} = await createChannel(
		apiHelpers,
		'My Property - ' + getRandomString()
	);

	await syncAnalyticsCloud(apiHelpers, channel, page);

	await page.goto(liferayConfig.environment.baseUrl + '/home?utm_medium=paidsearch');

	await page.waitForTimeout(3000);

	const user = await apiHelpers.headlessAdminUser.postUserAccount({
		password: 'test',
	});

	await page.getByLabel('Test Test User Profile').click();
	
	await page.getByRole('menuitem', {name: 'Sign Out'}).click();

	await page.waitForTimeout(5000);

	await page.getByRole('button', {name: 'Sign In'}).click();

	await page.getByLabel('Email Address').click();

	await page.getByLabel('Email Address').fill(user.emailAddress);

	await page.getByLabel('Password').fill('test');

	await page
		.getByLabel('Sign In- Loading')
		.getByRole('button', {name: 'Sign In'})
		.click();

	await expect(
		page.getByRole('heading', {name: 'Sign In - Loading'})
	).toBeHidden({
		timeout: 5 * 1000,
	});

	await expect(page.getByText('Welcome to Liferay')).toBeVisible({
		timeout: 5 * 1000,
	});

	await page.goto(
		liferayConfig.environment.baseUrl + '/home?utm_medium=paidsearch'
	);

	await page.waitForTimeout(3000);

	await apiHelpers.jsonWebServicesOSBAsah.closeSessions();

	await page.waitForTimeout(10000);

	// await page.goto(
	// 	`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	// );

	// await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
	// 	`[${channel.id}]`,
	// 	project.groupId
	// );

	await apiHelpers.headlessAdminUser.deleteUserAccount(Number(user.id));
});
