/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloudViaAPI} from '../../analytics-settings-web/main/utils/analytics-settings';
import {captureAnalyticsEvents} from './utils/captureAnalyticsEvents';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	formsPagesTest,
	isolatedChannelTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Form view and submit events fire with the expected form properties',
	{
		tag: '@LRAC-8121',
	},
	async ({
		analyticsChannel: channel,
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		page,
		project,
		site,
	}) => {
		const formTitle = 'AC Form ' + getRandomString();

		// Create and publish a form with a single text field

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await formBuilderPage.fillFormTitle(formTitle);

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await syncAnalyticsCloudViaAPI({
			apiHelpers,
			channel,
			project,
			siteId: Number(site.id),
		});

		const capturedEvents = captureAnalyticsEvents(page);

		// View the form

		await page.goto(formSubmissionURL);

		await expect
			.poll(() =>
				capturedEvents.find((event) => event.eventId === 'formViewed')
			)
			.toBeTruthy();

		// Submit the form

		await page.getByLabel('Text', {exact: true}).fill(getRandomString());

		await page.getByRole('button', {name: 'Submit'}).click();

		await expect
			.poll(() =>
				capturedEvents.find(
					(event) => event.eventId === 'formSubmitted'
				)
			)
			.toBeTruthy();
	}
);
