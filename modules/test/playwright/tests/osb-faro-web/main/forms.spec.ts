/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {assetPublisherPagesTest} from '../../../fixtures/assetPublisherPagesTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import {pageEditorPagesTest} from '../../../fixtures/pageEditorPagesTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	assetPublisherPagesTest,
	pageEditorPagesTest,
	featureFlagsTest({
		'LPD-39304': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

const pageTitle = 'My Page';

test(
	'Form overview shows submissions, views, appears-on, and technology metrics',
	{
		tag: ['@LRAC-8121', '@LRAC-8429', '@LRAC-8433'],
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const formTitle = 'My Form 1';
		const canonicalUrl = '/web/site-name';

		// Seed three views and three submissions from a Chrome desktop on one
		// page, within the Last 24 hours period

		const date = new Date();

		const formEvent = (eventId: string, userId: string) => ({
			applicationId: 'Form',
			assetId: '1',
			assetTitle: formTitle,
			browserName: 'Chrome',
			canonicalUrl,
			channelId: channel.id,
			deviceType: 'Desktop',
			eventDate: date.toISOString(),
			eventId,
			title: pageTitle,
			userId,
		});

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			formEvent('formViewed', '1'),
			formEvent('formViewed', '2'),
			formEvent('formViewed', '3'),
			formEvent('formSubmitted', '1'),
			formEvent('formSubmitted', '2'),
			formEvent('formSubmitted', '3'),
		]);

		await navigateToACPageViaURL({
			acPage: ACPage.assetPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Last 24 hours'}),
			trigger: page.getByRole('button', {name: 'Last 30 days'}),
		});

		// Open the form overview from the asset list

		await page.getByRole('link', {exact: true, name: formTitle}).click();

		// Visitors Behavior reflects the seeded submissions and views

		await expect(page.getByText('Visitors Behavior')).toBeVisible();

		await expect(
			page
				.locator('.analytics-metrics-tabs .card-tab')
				.filter({hasText: 'Submissions'})
				.locator('.metric-value')
		).toHaveText('3');

		await expect(
			page
				.locator('.analytics-metrics-tabs .card-tab')
				.filter({hasText: 'Views'})
				.locator('.metric-value')
		).toHaveText('3');

		// Asset Appears On lists the page where the form was submitted

		await expect(
			page.getByRole('cell', {name: canonicalUrl})
		).toBeVisible();

		// Submissions by Technology surfaces the Chrome browser

		const technologyCard = page
			.locator('.card-root')
			.filter({hasText: 'Submissions by Technology'});

		await technologyCard.getByText('Browsers').click();

		await expect(technologyCard.getByText('Chrome')).toBeVisible();
	}
);

test(
	'Forms list shows all of the forms in the asset list',
	{
		tag: '@LRAC-8120',
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const formTitles = ['AC Form 1', 'AC Form 2', 'AC Form 3', 'AC Form 4'];

		// Seed a view for each form within the Last 24 hours period

		const date = new Date();

		await apiHelpers.jsonWebServicesOSBAsah.createEvents(
			formTitles.map((formTitle, index) => ({
				applicationId: 'Form',
				assetId: String(index + 1),
				assetTitle: formTitle,
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: date.toISOString(),
				eventId: 'formViewed',
				title: pageTitle,
				userId: '1',
			}))
		);

		await navigateToACPageViaURL({
			acPage: ACPage.assetPage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: page.getByRole('menuitem', {name: 'Last 24 hours'}),
			trigger: page.getByRole('button', {name: 'Last 30 days'}),
		});

		for (const formTitle of formTitles) {
			await expect(
				page.getByRole('link', {exact: true, name: formTitle})
			).toBeVisible();
		}
	}
);
