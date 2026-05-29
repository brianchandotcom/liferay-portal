/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {createIndividuals, generateIndividual} from './utils/individuals';
import {ACPage, navigateToACPageViaURL} from './utils/navigation';

const test = mergeTests(
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test(
	'Site Metrics card can switch between Sessions/Visitor and Unique Visitors tabs',
	{
		tag: ['@LRAC-8331', '@LRAC-8332'],
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const individuals = [
			generateIndividual({name: 'user' + getRandomString()}),
		];

		await createIndividuals({apiHelpers, individuals});

		// Seed a pageViewed event so the Site Metrics card has data to render

		await apiHelpers.jsonWebServicesOSBAsah.createEvents(
			individuals.map((individual) => ({
				applicationId: 'Page',
				canonicalUrl: 'https://www.liferay.com',
				channelId: channel.id,
				eventDate: new Date().toISOString(),
				eventId: 'pageViewed',
				title: 'Site Metrics Page',
				userId: individual.id,
			}))
		);

		await navigateToACPageViaURL({
			acPage: ACPage.sitePage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		const cardTabs = page.locator('.card-tabs-root').first();

		for (const tabName of ['Sessions/Visitor', 'Unique Visitors']) {
			await cardTabs.getByText(tabName, {exact: true}).click();

			await expect(
				page
					.locator('li.card-tab.active')
					.getByText(tabName, {exact: true})
			).toBeVisible();
		}
	}
);
