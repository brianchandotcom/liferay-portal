/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {loginAnalyticsCloudTest} from '../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../fixtures/loginTest';
import {liferayConfig} from '../../liferay.config';
import getRandomString from '../../utils/getRandomString';
import {syncAnalyticsCloud} from '../analytics-settings-web/utils/analyticsSettings';
import {createChannel, switchChannel} from './utils/channel';
import {goToDistributionTabAndSelectAttribute} from './utils/distribution';
import {changeEventDisplayName} from './utils/event-definitions';
import {createIndividuals} from './utils/individuals';
import {
	navigateTo,
	navigateToACSitesPageViaURL,
	navigateToACWorkspace,
} from './utils/navigation';
import {
	addSegmentField,
	addStaticMember,
	createDynamicSegment,
	createStaticSegment,
	editCriteriaAttributeValue,
	editSegment,
	saveSegment,
	setSegmentName,
} from './utils/segments';
import {
	searchByTerm,
	viewNameNotPresentOnTableList,
	viewNameOnTableList,
} from './utils/utils';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

test('check if updated custom event displayName is shown on segment criteria card', async ({
	apiHelpers,
	page,
}) => {
	const channelName = 'My Property - ' + getRandomString();
	const customEventName = 'CustomEvent' + new Date().getTime();
	const newCustomEventName = `${customEventName}EV`;

	await test.step('Connect the DXP to AC', async () => {
		await syncAnalyticsCloud({
			apiHelpers,
			channelName,
			page,
		});
	});

	await test.step('Go to DXP Home Page > Create a custom event', async () => {
		await page.goto(liferayConfig.environment.baseUrl);
		await page.waitForTimeout(3000);

		await page.evaluate(
			({customEventName}) => {

				// @ts-ignore

				if (window.Analytics) {

					// @ts-ignore

					window.Analytics.track(customEventName, {
						propBool: true,
						propDate: '2024-05-20T01:00:00.000',
						propDuration: 66840000,
						propNum: 18,
						propString: 'test',
					});
				}
			},
			{customEventName}
		);

		await page.waitForTimeout(3000);
	});

	await test.step('Go to Analytics Cloud and Switch the property', async () => {
		await navigateToACWorkspace({page});
		await switchChannel({
			channelName,
			page,
		});
	});

	await test.step('Go to Settings > Go to Events > Go to Custom Events Tab', async () => {
		await navigateTo({
			page,
			pageName: 'Settings',
		});
		await navigateTo({
			page,
			pageName: 'Definitions',
		});
		await navigateTo({
			page,
			pageName: 'Events',
		});
		await navigateTo({
			page,
			pageName: 'Custom Events',
		});
	});

	await test.step('Change the display name of the event', async () => {
		await changeEventDisplayName({
			eventName: customEventName,
			newEventName: newCustomEventName,
			page,
		});

		await expect(page.getByText(newCustomEventName).nth(1)).toBeVisible();
		await page.locator('button.close').click();
	});

	await test.step('Go to Segments', async () => {
		await navigateTo({
			page,
			pageName: 'Exit Settings',
		});
		await navigateTo({
			page,
			pageName: 'Segments',
		});
	});

	await test.step('Create dynamic segment', async () => {
		await createDynamicSegment(page);
	});

	await test.step('Check that the custom event with the updated name appears in the list of criteria', async () => {
		await expect(page.getByText(newCustomEventName)).toBeVisible();
	});

	await test.step('Add the custom event criteria to the segment', async () => {
		await addSegmentField({
			criterionName: newCustomEventName,
			criterionType: 'Events',
			page,
		});
	});

	await test.step('Check that the added criteria is using the name of the updated custom event', async () => {
		expect(
			page.locator('div').filter({hasText: `/^${newCustomEventName}$/`})
		).toBeTruthy();
	});

	await test.step('Add a value to the attribute value field', async () => {
		await editCriteriaAttributeValue({
			attributeValue: 'testAttribute',
			page,
		});
	});

	await test.step('Add a name to the segment', async () => {
		await setSegmentName({
			page,
			segmentName: 'Test Dynamic Segment',
		});
	});

	await test.step('Save the segment', async () => {
		await saveSegment(page);
	});

	await test.step('Check that the Segment Criteria card is displaying the segment rule with the name of the updated custom event', async () => {
		expect(
			page.getByRole('heading', {name: 'Segment Criteria'})
		).toBeTruthy();
		expect(page.getByText(newCustomEventName)).toBeTruthy();
	});

	await test.step('Edit the segment', async () => {
		await editSegment(page);
	});

	await test.step('Check that the list of criteria and the criteria being used in the segment are both using the name of the updated custom event', async () => {
		expect(
			page.locator('div').filter({hasText: `/^${newCustomEventName}$/`})
		).toBeTruthy();

		expect(
			page.locator('li').filter({hasText: `/^${newCustomEventName}$/`})
		).toBeTruthy();
	});
});

test('Search the Segment Profile Distribution', async ({apiHelpers, page}) => {
	const channelName = 'My Property - ' + getRandomString();

	const firstIndividualsName = 'ac';
	const secondIndividualsName = 'dxp';

	const {channel, project} = await createChannel({
		apiHelpers,
		channelName,
	});

	const date1 = new Date();

	const generateIndividual = (name) => {
		const id = getRandomString();

		return {
			id,
			name,
		};
	};

	const firstIndividuals = [generateIndividual(firstIndividualsName)];

	const secondIndividuals = [generateIndividual(secondIndividualsName)];

	await test.step('Create the first and second Individuals', async () => {
		await createIndividuals({
			apiHelpers,
			individuals: firstIndividuals,
		});

		await createIndividuals({
			apiHelpers,
			individuals: secondIndividuals,
		});
	});

	await test.step('Create the first and second Individuals Events', async () => {
		const firstIndividualsEvents = firstIndividuals.map((individual) => ({
			applicationId: 'Page',
			canonicalUrl: 'https://www.liferay.com',
			channelId: channel.id,
			eventDate: date1.toISOString(),
			eventId: 'pageViewed',
			title: 'Liferay',
			userId: individual.id,
		}));

		await apiHelpers.jsonWebServicesOSBAsah.createEvents(
			firstIndividualsEvents
		);

		const secondEvents = secondIndividuals.map((individual) => ({
			applicationId: 'Page',
			canonicalUrl: 'https://www.liferay.com',
			channelId: channel.id,
			eventDate: date1.toISOString(),
			eventId: 'pageViewed',
			title: 'Liferay',
			userId: individual.id,
		}));

		await apiHelpers.jsonWebServicesOSBAsah.createEvents(secondEvents);
	});

	await test.step('Create the first and second Individual Session', async () => {
		const firstSessions = firstIndividuals.map((individual) => ({
			channelId: channel.id,
			id: individual.id,
			sessionEnd: date1.toISOString(),
			sessionStart: date1.toISOString(),
			userId: individual.id,
		}));

		await apiHelpers.jsonWebServicesOSBAsah.createSessions(firstSessions);

		const secondSessions = secondIndividuals.map((individual) => ({
			channelId: channel.id,
			id: individual.id,
			sessionEnd: date1.toISOString(),
			sessionStart: date1.toISOString(),
			userId: individual.id,
		}));

		await apiHelpers.jsonWebServicesOSBAsah.createSessions(secondSessions);
	});

	await test.step('Go to Analytics Cloud and Switch the property', async () => {
		await navigateToACSitesPageViaURL({
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});
	});

	await test.step('Go to Segments Dashboard and create a Static Segment', async () => {
		await navigateTo({page, pageName: 'Segments'});

		await createStaticSegment(page);

		await setSegmentName({page, segmentName: 'Test Static Segment'});
	});

	await test.step('Add static member and save segment', async () => {
		await addStaticMember({
			memberNames: [
				`${firstIndividualsName}@liferay.com`,
				`${secondIndividualsName}@liferay.com`,
			],
			page,
		});

		await saveSegment(page);
	});

	await test.step('Click on distribution tab and select birthDate attribute', async () => {
		await goToDistributionTabAndSelectAttribute({
			attributeName: 'familyName',
			page,
		});
	});

	await test.step('Click on attribute result row', async () => {
		await page.locator('g.recharts-layer .recharts-bar-rectangle').click();
	});

	await test.step('Check on side modal if a individual matches the attribute selected', async () => {
		await searchByTerm({
			page,
			searchTerm: `${firstIndividualsName} Smith`,
		});

		await viewNameOnTableList({
			itemNames: `${firstIndividualsName} Smith`,
			page,
		});
	});

	await test.step('Check on side modal if second individual is not visible after search', async () => {
		await viewNameNotPresentOnTableList({
			itemNames: `${secondIndividualsName} Smith`,
			page,
		});
	});

	await test.step('Do a search with random user and assert there are no results found', async () => {
		await searchByTerm({page, searchTerm: 'lorem'});

		expect(
			page.getByText(
				'There are no results found.Please try a different search term.'
			)
		).toBeVisible();
	});

	await test.step('delete channel', async () => {
		await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
			`[${channel.id}]`,
			project.groupId
		);
	});
});
