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
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analyticsSettings';
import getFragmentDefinition from '../layout-content-page-editor-web/utils/getFragmentDefinition';
import getPageDefinition from '../layout-content-page-editor-web/utils/getPageDefinition';
import {faroConfig} from './faro.config';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const createSitePage = async function (apiHelpers, pageTitle) {
	const company = await apiHelpers.jsonWebServicesCompany.getCompanyByWebId(
		'liferay.com'
	);

	const group = await apiHelpers.jsonWebServicesGroup.getGroupByKey(
		company.companyId,
		'Guest'
	);

	return await apiHelpers.headlessDelivery.createSitePage({
		pageDefinition: getPageDefinition([
			getFragmentDefinition(getRandomString(), 'BASIC_COMPONENT-heading'),
		]),
		siteId: group.groupId,
		title: pageTitle,
	});
};

const goToWithReferrer = async function (page, referrer, url) {
	await page.goto(referrer);

	await page.evaluate((url) => {
		const aTag = document.createElement('a');

		aTag.href = url;

		aTag.click();
	}, url);
};

test('shows outside pages in path analysis', async ({apiHelpers, page}) => {
	const pageTitle = 'My Page';

	const sitePage = await createSitePage(apiHelpers, pageTitle);

	const channelName = 'My Property - ' + getRandomString();

	await syncAnalyticsCloud(page, channelName);

	await goToWithReferrer(
		page,
		'https://www.google.com',
		liferayConfig.environment.baseUrl
	);

	await page.waitForTimeout(10000);

	await page.getByText(pageTitle).first().click();

	await page.waitForTimeout(10000);

	await page.goto(faroConfig.environment.baseUrl);

	await page
		.getByRole('link', {
			name: 'FARO-DEV-liferay Liferay Demo Enterprise Plan',
		})
		.click();

	await page.locator('.channels-menu.button-root').click();

	await page.getByRole('link', {name: channelName}).click();

	await page.getByRole('link', {exact: true, name: 'Pages'}).click();

	await page.getByRole('button', {name: 'Last 30 days'}).click();

	await page.getByRole('menuitem', {name: 'Last 24 hours'}).click();

	await page
		.getByRole('cell', {name: 'Home - Liferay DXP'})
		.getByRole('link')
		.click();

	await page.getByRole('link', {name: 'Path'}).click();

	await expect(page.getByText('https://www.goo...')).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('My Page - Lifer...')).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('1', {exact: true}).first()).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('1', {exact: true}).nth(1)).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('1', {exact: true}).nth(2)).toBeVisible({
		timeout: 100 * 1000,
	});

	await page.goto(liferayConfig.environment.baseUrl);

	await apiHelpers.jsonWebServicesLayout.deleteLayout(String(sitePage.id));
});

test('shows tracked pages in path analysis', async ({apiHelpers, page}) => {
	const pageTitle1 = 'My Page 1';

	const sitePage1 = await createSitePage(apiHelpers, pageTitle1);

	const pageTitle2 = 'My Page 2';

	const sitePage2 = await createSitePage(apiHelpers, pageTitle2);

	const channelName = 'My Property - ' + getRandomString();

	await syncAnalyticsCloud(page, channelName);

	await page.waitForTimeout(10000);

	await page.getByText(pageTitle1).first().click();

	await page.getByText(pageTitle2).first().click();

	await page.waitForTimeout(10000);

	await page.goto(faroConfig.environment.baseUrl);

	await page
		.getByRole('link', {
			name: 'FARO-DEV-liferay Liferay Demo Enterprise Plan',
		})
		.click();

	await page.locator('.channels-menu.button-root').click();

	await page.getByRole('link', {name: channelName}).click();

	await page.getByRole('link', {exact: true, name: 'Pages'}).click();

	await page.getByRole('button', {name: 'Last 30 days'}).click();

	await page.getByRole('menuitem', {name: 'Last 24 hours'}).click();

	await page
		.getByRole('cell', {name: 'My Page 1 - Liferay DXP'})
		.getByRole('link')
		.click();

	await page.getByRole('link', {name: 'Path'}).click();

	await expect(page.getByText('Home - Liferay ...')).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('My Page 2 - Lif...')).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('1', {exact: true}).first()).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('1', {exact: true}).nth(1)).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('1', {exact: true}).nth(2)).toBeVisible({
		timeout: 100 * 1000,
	});

	await page.goto(liferayConfig.environment.baseUrl);

	await apiHelpers.jsonWebServicesLayout.deleteLayout(String(sitePage1.id));

	await apiHelpers.jsonWebServicesLayout.deleteLayout(String(sitePage2.id));
});
