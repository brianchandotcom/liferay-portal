/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {formsPagesTest} from '../../../fixtures/formsPagesTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {syncAnalyticsCloud} from '../../analytics-settings-web/main/utils/analytics-settings';
import {captureAnalyticsEvents} from './utils/captureAnalyticsEvents';

const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	formsPagesTest,
	isolatedSiteTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Form interaction events fire with the expected form properties and request common properties',
	{
		tag: ['@LRAC-8682', '@LRAC-8691'],
	},
	async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		page,
		site,
	}) => {

		// Create a form with a single Text field and publish it

		await formBuilderPage.goToNew(site.friendlyUrlPath);

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('AC Form');

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderPage.clickSaveButton();

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		// Sync the site with Analytics Cloud so the form analytics script loads

		const {channel} = await syncAnalyticsCloud({
			apiHelpers,
			channelName: 'My Property - ' + getRandomString(),
			page,
			siteName: site.name,
		});

		const capturedEvents = captureAnalyticsEvents(page);

		// Visit the published form to fire formViewed

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await expect
			.poll(() =>
				capturedEvents.find((event) => event.eventId === 'formViewed')
			)
			.toBeTruthy();

		const formViewed = capturedEvents.find(
			(event) => event.eventId === 'formViewed'
		);

		expect(formViewed.properties.formId).toBeTruthy();
		expect(formViewed.properties.title).toBe('AC Form');
		expect(formViewed.channelId).toBe(channel.id);
		expect(formViewed.dataSourceId).toBeTruthy();

		// Fill the Text field and submit the form to fire formSubmitted

		await page.getByLabel('Text').fill('First Record');

		await page.getByRole('button', {exact: true, name: 'Submit'}).click();

		await expect
			.poll(() =>
				capturedEvents.find(
					(event) => event.eventId === 'formSubmitted'
				)
			)
			.toBeTruthy();

		const formSubmitted = capturedEvents.find(
			(event) => event.eventId === 'formSubmitted'
		);

		expect(formSubmitted.properties.formId).toBe(
			formViewed.properties.formId
		);
		expect(formSubmitted.properties.title).toBe('AC Form');
		expect(formSubmitted.channelId).toBe(channel.id);
		expect(formSubmitted.dataSourceId).toBeTruthy();
	}
);
