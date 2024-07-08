/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {createChannel} from './utils/channel';
import {
	addBreakdownByAttribute,
	viewBreakdownRechartsData,
} from './utils/distribution';
import {createIndividuals} from './utils/individuals';
import {navigateTo, navigateToACSitesPageViaURL} from './utils/navigation';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('Add a new breakdown by an attribute and assert that correct results appear', async ({
	apiHelpers,
	page,
}) => {
	const individualName = 'ac';
	const channelName = 'My Property - ' + getRandomString();
	const {channel, project} = await createChannel({
		apiHelpers,
		channelName,
	});
	const date = new Date();
	const generateIndividual = (name) => {
		const id = getRandomString();

		return {
			id,
			name,
		};
	};

	const individuals = [generateIndividual(individualName)];

	await test.step('Create new Individual', async () => {
		await createIndividuals({
			apiHelpers,
			individuals,
		});
	});

	await test.step('Create Individual Identity', async () => {
		const identities = individuals.map((individual) => ({
			createDate: date.toISOString(),
			id: individual.id,
			individualId: individual.id,
		}));

		await apiHelpers.jsonWebServicesOSBAsah.createIdentities(identities);
	});

	await test.step('Create Individual Event', async () => {
		const events = individuals.map((individual) => ({
			applicationId: 'Page',
			canonicalUrl: 'https://www.liferay.com',
			channelId: channel.id,
			eventDate: date.toISOString(),
			eventId: 'pageViewed',
			title: 'Liferay',
			userId: individual.id,
		}));

		await apiHelpers.jsonWebServicesOSBAsah.createEvents(events);
	});

	await test.step('Create Individual Session', async () => {
		const sessions = individuals.map((individual) => ({
			channelId: channel.id,
			id: individual.id,
			sessionEnd: date.toISOString(),
			sessionStart: date.toISOString(),
			userId: individual.id,
		}));

		await apiHelpers.jsonWebServicesOSBAsah.createSessions(sessions);
	});

	await test.step('Go to Analytics Cloud and Switch the property', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});
	});

	await test.step('Go to Individuals Dashboard', async () => {
		await navigateTo({page, pageName: 'Individuals'});
	});

	await test.step('Add a new breakdown', async () => {
		await addBreakdownByAttribute({
			attributeName: 'email',
			page,
		});
	});

	await test.step('Check if the correct results appear (email and maximum count)', async () => {
		await viewBreakdownRechartsData({
			attributeValue: `${individualName}@liferay.com`,
			maxCount: '1',
			page,
		});
	});

	await test.step('Close breakdown tab', async () => {
		await page.getByLabel('Close').click();
	});

	await test.step('Delete the property that was used during automation execution', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});
