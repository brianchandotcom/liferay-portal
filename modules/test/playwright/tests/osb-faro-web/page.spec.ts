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
			getFragmentDefinition({
				id: getRandomString(),
				key: 'BASIC_COMPONENT-heading',
			}),
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

test('shows individuals who viewed a page less than 24 hours ago', async ({
	apiHelpers,
	page,
}) => {
	const projects = await apiHelpers.jsonWebServicesOSBFaro.getProjects();

	const project = projects.find(({name}) => name === 'FARO-DEV-liferay');

	const channel = await apiHelpers.jsonWebServicesOSBFaro.createChannel(
		'My Property - ' + getRandomString(),
		project.groupId
	);

	await apiHelpers.jsonWebServicesOSBAsah.createIndividuals([
		{
			emailAddress: 'user1@liferay.com',
			fields: [
				{
					dataSourceId: 0,
					name: 'givenName',
					value: 'user1',
				},
				{
					dataSourceId: 0,
					name: 'familyName',
					value: 'user1',
				},
				{
					dataSourceId: 0,
					name: 'email',
					value: 'user1@liferay.com',
				},
			],
			firstName: 'user1',
			id: 'user1@liferay.com',
			lastName: 'user1',
		},
		{
			emailAddress: 'user2@liferay.com',
			fields: [
				{
					dataSourceId: 0,
					name: 'givenName',
					value: 'user2',
				},
				{
					dataSourceId: 0,
					name: 'familyName',
					value: 'user2',
				},
				{
					dataSourceId: 0,
					name: 'email',
					value: 'user2@liferay.com',
				},
			],
			firstName: 'user2',
			id: 'user2@liferay.com',
			lastName: 'user2',
		},
		{
			emailAddress: 'user3@liferay.com',
			fields: [
				{
					dataSourceId: 0,
					name: 'givenName',
					value: 'user3',
				},
				{
					dataSourceId: 0,
					name: 'familyName',
					value: 'user3',
				},
				{
					dataSourceId: 0,
					name: 'email',
					value: 'user3@liferay.com',
				},
			],
			firstName: 'user3',
			id: 'user3@liferay.com',
			lastName: 'user3',
		},
	]);

	const date1 = new Date();

	await apiHelpers.jsonWebServicesOSBAsah.createIdentities([
		{
			createDate: date1.toISOString(),
			id: '1',
			individualId: 'user1@liferay.com',
		},
		{
			createDate: date1.toISOString(),
			id: '2',
			individualId: 'user2@liferay.com',
		},
		{
			createDate: date1.toISOString(),
			id: '3',
			individualId: 'user3@liferay.com',
		},
	]);

	await apiHelpers.jsonWebServicesOSBAsah.createEvents([
		{
			applicationId: 'Page',
			canonicalUrl: 'https://www.liferay.com',
			channelId: channel.id,
			eventDate: date1.toISOString(),
			eventId: 'pageViewed',
			title: 'Liferay',
			userId: '1',
		},
		{
			applicationId: 'Page',
			canonicalUrl: 'https://www.liferay.com',
			channelId: channel.id,
			eventDate: date1.toISOString(),
			eventId: 'pageViewed',
			title: 'Liferay',
			userId: '2',
		},
	]);

	const date2 = new Date();

	date2.setDate(date2.getDate() - 5);

	await apiHelpers.jsonWebServicesOSBAsah.createPagesDaily([
		{
			canonicalUrl: 'https://www.liferay.com',
			channelId: channel.id,
			eventDate: date2.toISOString(),
			title: 'Liferay',
			userId: '3',
			views: 1,
		},
	]);

	await page.goto(
		`${faroConfig.environment.baseUrl}/workspace/${project.groupId}/${channel.id}/sites`
	);

	await page.getByRole('link', {exact: true, name: 'Pages'}).click();

	await page.getByRole('link', {name: 'Liferay'}).click();

	await page.getByRole('link', {name: 'Known Individuals'}).click();

	await expect(
		page.getByRole('cell', {name: 'user3 user3 user3@liferay.com'})
	).toBeVisible({
		timeout: 100 * 1000,
	});

	await page.getByRole('button', {name: 'Last 30 days'}).click();

	await page.getByRole('menuitem', {name: 'Last 24 hours'}).click();

	await expect(
		page.getByRole('cell', {name: 'user1 user1 user1@liferay.com'})
	).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(
		page.getByRole('cell', {name: 'user2 user2 user2@liferay.com'})
	).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(
		page.getByRole('cell', {name: 'user3 user3 user3@liferay.com'})
	).toBeHidden({
		timeout: 100 * 1000,
	});

	await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
		`[${channel.id}]`,
		project.groupId
	);
});

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

	await navigateToSitePage(page, '', pageTitle1);

	await page.waitForTimeout(10000);

	await page.getByText(pageTitle2).first().click();

	await page.waitForTimeout(10000);

	await page.getByText(pageTitle1).first().click();

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

	await expect(
		page.getByText('My Page 2 - Lif...', {exact: true}).first()
	).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('Direct Traffic')).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(
		page.getByText('My Page 2 - Lif...', {exact: true}).nth(1)
	).toBeVisible({
		timeout: 100 * 1000,
	});

	await expect(page.getByText('Drop Offs')).toBeVisible({
		timeout: 100 * 1000,
	});

	await page.goto(liferayConfig.environment.baseUrl);

	await apiHelpers.jsonWebServicesLayout.deleteLayout(String(sitePage1.id));

	await apiHelpers.jsonWebServicesLayout.deleteLayout(String(sitePage2.id));
});
