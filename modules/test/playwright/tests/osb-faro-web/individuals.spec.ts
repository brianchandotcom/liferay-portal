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
import {createIndividuals} from './utils/individuals';
import {waitForLoading} from './utils/loading';
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

	const individuals = [generateIndividual('vinicius')];

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

	await page.waitForTimeout(3000);

	const card = page.locator('.distribution-card-root');

	await test.step('Add a new breakdown', async () => {
		await card.getByLabel('Add').click();
		await card.getByPlaceholder('Select Field').click();

		// it should get the menuitem from the page due to the element
		// being rendered outside the card.

		await page.getByRole('menuitem', {name: 'abc email'}).click();

		await card.getByLabel('Breakdown Name').click();
		await card.getByLabel('Breakdown Name').fill('breakdown by email');
		await card.getByRole('button', {name: 'Save'}).click();
	});

	await waitForLoading(page);

	const ticks = card.locator(
		'.recharts-cartesian-axis.recharts-xAxis .recharts-layer.recharts-cartesian-axis-tick'
	);
	const ticksCount = await ticks.count();
	const lastTick = ticks.nth(ticksCount - 1);

	const lastTickValue = await lastTick.textContent();

	expect(card.getByText('vinicius@liferay.com')).toBeVisible();
	expect(lastTickValue).toEqual('1');

	await test.step('Close breakdown tab', async () => {
		await page.getByLabel('Close').click();
	});
});
