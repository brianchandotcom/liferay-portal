/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {assetPublisherPagesTest} from '../../fixtures/assetPublisherPagesTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../fixtures/pageEditorPagesTest';
import {pagesAdminPagesTest} from '../../fixtures/pagesAdminPagesTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analytics-settings';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';
import {
	addBreakdown,
	addCustomEvent,
	addFilter,
	removeAttribute,
	setEventAnalysisName,
} from './utils/events';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';
import {createSitePage, navigateToSitePage} from './utils/portal';
import {closeSessions} from './utils/sessions';
import {changeTimeFilter} from './utils/time-filter';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	assetPublisherPagesTest,
	pageEditorPagesTest,
	pagesPagesTest,
	pagesAdminPagesTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const randomString = getRandomString();

const channelName = 'My Property ' + randomString;
const pageTitle = 'My Page';
const siteName = 'My Site ' + randomString;

let channel;
let layout;
let project;
let site;

test.beforeEach(async ({apiHelpers, page}) => {
	site = await apiHelpers.headlessSite.createSite({
		name: siteName,
	});

	layout = await createSitePage({
		apiHelpers,
		pageTitle,
		siteName,
	});

	const result = await syncAnalyticsCloud({
		apiHelpers,
		channelName,
		page,
		siteName,
	});

	channel = result.channel;
	project = result.project;
});

test.afterEach(async ({apiHelpers, page}) => {
	await test.step('Delete channel and delete site on de DXP side', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);

		await page.goto(liferayConfig.environment.baseUrl);

		await apiHelpers.headlessSite.deleteSite(String(site.id));
	});
});

test(
	'The analysis result should not appear if any of the attribute conditions are not contained within the result',
	{
		tag: '@LRAC-11746',
	},

	async ({
		apiHelpers,
		page,
		pageConfigurationPage,
		pageEditorPage,
		pagesAdminPage,
	}) => {
		await test.step('Go to configure My Page and create a custom event', async () => {
			await pagesAdminPage.goto(site.friendlyUrlPath);

			await pageConfigurationPage.goToSection(pageTitle, 'Design');

			await page.getByRole('tab', {name: 'JavaScript'}).click();

			const customEventContent = `Analytics.track('pageTitleEvent', {
'pageTitleEvent': '${pageTitle}',
});`;

			await page.getByPlaceholder('JavaScript').fill(customEventContent);

			await pageConfigurationPage.save();
		});

		await test.step('Publish My Page and generate a custom event', async () => {
			await pageEditorPage.goto(layout, site.friendlyUrlPath);

			await pageEditorPage.publishPage();

			await navigateToSitePage({
				page,
				pageName: pageTitle,
				siteName,
			});

			await page.reload();

			await page.waitForTimeout(5000);

			await closeSessions(apiHelpers, page);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.eventAnalysisPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Add the custom event, create an analysis and add a brekdown', async () => {
			await page.getByRole('link', {name: 'Create Analysis'}).click();
		});

		await test.step('Add a name to the segment', async () => {
			await setEventAnalysisName({
				eventAnalysisName: `Event Analysis ${randomString}`,
				page,
			});
		});

		await test.step('Add the custom event to the analysis', async () => {
			await addCustomEvent({
				customEventName: 'pageTitleEvent',
				page,
			});
		});

		await test.step('Add a breakdown to the analysis', async () => {
			await addBreakdown({
				breakdownName: 'pageTitle',
				page,
				tab: 'Event',
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Add a filter to the analysis', async () => {
			await addFilter({
				filterName: 'pageTitle',
				operator: 'contains',
				page,
				input: `${pageTitle} - ${siteName}`,
			});
		});

		await test.step('View the information displayed', async () => {
			await expect(
				page.getByTitle(`${pageTitle} - ${siteName}`)
			).toBeVisible();
			await expect(
				page.getByRole('cell', {exact: true, name: 'pageTitleEvent'})
			).toBeVisible();
			await expect(page.locator('.percentage-column')).toContainText(
				'100%'
			);
		});

		await test.step('Add another attribute in the filter and use not contains condition', async () => {
			await removeAttribute({
				page,
				section: 'Filter',
			});

			await addFilter({
				filterName: 'pageTitle',
				operator: 'does not contain',
				page,
				pageTitle: `${pageTitle} - ${siteName}`,
			});
		});

		await test.step('View the information displayed and see that there are no results', async () => {
			await expect(
				page.getByRole('cell', {name: 'No Results'}).first()
			).toBeVisible();
			await expect(
				page.getByRole('cell', {name: 'No Results'}).nth(1)
			).toBeVisible();
		});
	}
);
