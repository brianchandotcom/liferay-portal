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
	goToDistributionTabAndSelectAttribute,
	viewBreakdownRechartsData,
} from './utils/distribution';
import {changeEventDisplayName} from './utils/event-definitions';
import {createIndividuals, generateIndividual} from './utils/individuals';
import {waitForLoading} from './utils/loading';
import {Nanites, runNanites} from './utils/nanites';
import {navigateTo, navigateToACSitesPageViaURL} from './utils/navigation';
import {
	addSegmentField,
	addStaticMember,
	createDynamicSegment,
	createStaticSegment,
	editCriteriaAttributeValue,
	editCriteriaConjunction,
	editSegment,
	includeAnonymousToggle,
	saveSegment,
	selectAsset,
	selectOperator,
	setSegmentName,
} from './utils/segments';
import {SegmentConditions} from './utils/selectors';
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

test(
	'Check if updated custom event displayName is shown on segment criteria card',
	{
		tag: '@LPD-27065',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const {channel, project} = await createChannel({
			apiHelpers,
			channelName,
		});

		const customEventName = 'CustomEvent' + new Date().getTime();

		await test.step('Send a custom event', async () => {
			const eventAttributeName = 'propString';
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: customEventName,
					properties: [
						{
							name: eventAttributeName,
							value: 'testAttribute',
						},
					],
					title: 'Liferay',
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: customEventName,
					eventAttributeDefinitions: [
						{
							dataType: 'STRING',
							displayName: eventAttributeName,
							name: eventAttributeName,
							type: 'LOCAL',
						},
					],
					name: customEventName,
					type: 'CUSTOM',
				},
			]);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACSitesPageViaURL({
				channelID: channel.id,
				page,
				projectID: project.groupId,
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

		const newCustomEventName = `${customEventName}EV`;

		await test.step('Change the display name of the event', async () => {
			await changeEventDisplayName({
				eventName: customEventName,
				newEventName: newCustomEventName,
				page,
			});

			await expect(
				page.getByText(newCustomEventName).nth(1)
			).toBeVisible();
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
				page
					.locator('div')
					.filter({hasText: `/^${newCustomEventName}$/`})
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
				page
					.locator('div')
					.filter({hasText: `/^${newCustomEventName}$/`})
			).toBeTruthy();

			expect(
				page
					.locator('li')
					.filter({hasText: `/^${newCustomEventName}$/`})
			).toBeTruthy();
		});
	}
);

test(
	'Search the Segment Profile Distribution',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const {channel, project} = await createChannel({
			apiHelpers,
			channelName,
		});

		const firstIndividualsName = 'ac';
		const secondIndividualsName = 'dxp';
		const knownIndividuals = [
			generateIndividual({
				name: firstIndividualsName,
			}),
			generateIndividual({
				name: secondIndividualsName,
			}),
		];

		await test.step('Create 2 individuals directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals: knownIndividuals,
			});
		});

		const date = new Date();

		await test.step('Create the first and second Individuals Events', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createEvents(
				knownIndividuals.map((individual) => ({
					applicationId: 'Page',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'pageViewed',
					title: 'Liferay',
					userId: individual.id,
				}))
			);
		});

		await test.step('Create the first and second Individual Session', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createSessions(
				knownIndividuals.map((individual) => ({
					channelId: channel.id,
					id: individual.id,
					sessionEnd: date.toISOString(),
					sessionStart: date.toISOString(),
					userId: individual.id,
				}))
			);
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

		await test.step('Click on distribution tab and select familyName attribute', async () => {
			await goToDistributionTabAndSelectAttribute({
				attributeName: 'familyName',
				page,
			});
		});

		await test.step('Click on attribute result row', async () => {
			await page
				.locator('g.recharts-layer .recharts-bar-rectangle')
				.click();
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
	}
);

test(
	'Segment Composition shows Active and Known individuals',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const {channel, project} = await createChannel({
			apiHelpers,
			channelName,
		});

		const knownIndividualName = 'ac';
		const knownIndividual = [
			generateIndividual({
				name: knownIndividualName,
			}),
		];

		await test.step('Create the known individuals directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals: knownIndividual,
			});
		});

		const anonymousIdentityID = '87';
		const date = new Date();

		await test.step('Create an identity for an anonymous directly in the AC database', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createIdentities([
				{
					createDate: date.toISOString(),
					id: anonymousIdentityID,
				},
			]);
		});

		const pageName = 'Liferay - AC Page';

		await test.step('Create events for the anonymous and known individual to appear in AC', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createEvents(
				knownIndividual.map((individual) => ({
					applicationId: 'Page',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'pageViewed',
					title: pageName,
					userId: individual.id,
				}))
			);

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'Page',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'pageViewed',
					title: pageName,
					userId: anonymousIdentityID,
				},
			]);
		});

		await test.step('Create a session for the known individual', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createSessions(
				knownIndividual.map((individual) => ({
					channelId: channel.id,
					id: individual.id,
					sessionEnd: date.toISOString(),
					sessionStart: date.toISOString(),
					userId: individual.id,
				}))
			);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACSitesPageViaURL({
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		const dynamicSegmentName = 'Test Dynamic Segment';

		await test.step('Create dynamic segment', async () => {
			await navigateTo({
				page,
				pageName: 'Segments',
			});

			await createDynamicSegment(page);

			await addSegmentField({
				criterionName: 'email',
				criterionType: 'Individual Attributes',
				page,
			});

			await selectOperator({
				operator: 'is known',
				operatorField: SegmentConditions.criteriaCondition,
				page,
			});

			await addSegmentField({
				criterionName: 'email',
				criterionType: 'Individual Attributes',
				page,
			});

			await selectOperator({
				index: 1,
				operator: 'is unknown',
				operatorField: SegmentConditions.criteriaCondition,
				page,
			});

			await editCriteriaConjunction({page});

			await includeAnonymousToggle({
				enable: true,
				page,
			});

			await setSegmentName({
				page,
				segmentName: dynamicSegmentName,
			});

			await saveSegment(page);
		});

		await test.step('Create static segment', async () => {
			await navigateTo({
				page,
				pageName: 'Segments',
			});

			await createStaticSegment(page);

			await setSegmentName({
				page,
				segmentName: 'Test Static Segment',
			});

			await addStaticMember({
				memberNames: knownIndividualName,
				page,
			});

			await saveSegment(page);
		});

		await test.step('Run the Segment Nanite', async () => {
			await runNanites({
				apiHelpers,
				naniteNames: [Nanites.UpdateMembershipsNanite],
				page,
			});
		});

		await test.step('Check the Segment Composition card data for the Static Segment', async () => {
			let activeCount = page
				.locator('li')
				.filter({hasText: 'Active Last 30 Days'})
				.getByTestId('active-count');

			await expect(activeCount).toHaveText('1');

			let activePorcentage = page
				.locator('li')
				.filter({hasText: 'Active Last 30 Days'})
				.getByTestId('active-porcentage');

			await expect(activePorcentage).toHaveText('100%');

			activeCount = page
				.locator('li')
				.filter({hasText: 'Known Members'})
				.getByTestId('active-count');

			await expect(activeCount).toHaveText('1');

			activePorcentage = page
				.locator('li')
				.filter({hasText: 'Known Members'})
				.getByTestId('active-porcentage');

			await expect(activePorcentage).toHaveText('100%');
		});

		await test.step('Go to Segments > Access the Dynamic Segment', async () => {
			await navigateTo({
				page,
				pageName: 'Segments',
			});

			await navigateTo({
				page,
				pageName: dynamicSegmentName,
			});
		});

		await test.step('Reload the segment page to clear the cache', async () => {
			await page.reload();

			await waitForLoading(page);
		});

		await test.step('Check the Segment Composition card data for the Dynamic Segment', async () => {
			let activeCount = page
				.locator('li')
				.filter({hasText: 'Active Last 30 Days'})
				.getByTestId('active-count');

			await expect(activeCount).toHaveText('2');

			let activePorcentage = page
				.locator('li')
				.filter({hasText: 'Active Last 30 Days'})
				.getByTestId('active-porcentage');

			await expect(activePorcentage).toHaveText('100%');

			activeCount = page
				.locator('li')
				.filter({hasText: 'Known Members'})
				.getByTestId('active-count');

			await expect(activeCount).toHaveText('1');

			activePorcentage = page
				.locator('li')
				.filter({hasText: 'Known Members'})
				.getByTestId('active-porcentage');

			await expect(activePorcentage).toHaveText('50%');
		});

		await test.step('delete channel', async () => {
			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${channel.id}]`,
				project.groupId
			);
		});
	}
);

test(
	'Create a segment with behavior of commenting on a blog',
	{
		tag: '@Legacy',
	},

	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const {channel, project} = await createChannel({
			apiHelpers,
			channelName,
		});

		const knownIndividualName = 'ac';
		const knownIndividual = [
			generateIndividual({
				name: knownIndividualName,
			}),
		];

		await test.step('Create the known individuals directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals: knownIndividual,
			});
		});

		const anonymousIdentityID = '87';
		const date = new Date();

		await test.step('Create an identity for an anonymous individual directly in the AC database', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createIdentities([
				{
					createDate: date.toISOString(),
					id: anonymousIdentityID,
				},
			]);
		});

		const pageName = 'Liferay Blog - AC Page';

		await test.step('Create blogViewed and posted events for known and anonymous individuals', async () => {
			const blogId = '1905';

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(
				knownIndividual.map((individual) => ({
					applicationId: 'Blog',
					assetId: blogId,
					assetTitle: pageName,
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					dataSourceId: 0,
					eventDate: date.toISOString(),
					eventId: 'blogViewed',
					title: pageName,
					userId: individual.id,
				}))
			);

			await apiHelpers.jsonWebServicesOSBAsah.createEvents(
				knownIndividual.map((individual) => ({
					applicationId: 'Comment',
					assetId: blogId,
					assetTitle: pageName,
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					dataSourceId: 0,
					eventDate: date.toISOString(),
					eventId: 'posted',
					eventProperties:
						'{"className":"com.liferay.blogs.model.BlogsEntry"}',
					properties: [
						{
							name: 'className',
							value: 'com.liferay.blogs.model.BlogsEntry',
						},
					],
					title: pageName,
					userId: individual.id,
				}))
			);

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'Comment',
					assetId: blogId,
					assetTitle: pageName,
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					dataSourceId: 0,
					eventDate: date.toISOString(),
					eventId: 'posted',
					eventProperties:
						'{"className":"com.liferay.blogs.model.BlogsEntry"}',
					properties: [
						{
							name: 'className',
							value: 'com.liferay.blogs.model.BlogsEntry',
						},
					],
					title: pageName,
					userId: anonymousIdentityID,
				},
			]);
		});

		await test.step('Create a session for the known individual', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createSessions(
				knownIndividual.map((individual) => ({
					channelId: channel.id,
					id: individual.id,
					sessionEnd: date.toISOString(),
					sessionStart: date.toISOString(),
					userId: individual.id,
				}))
			);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACSitesPageViaURL({
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Create dynamic segment', async () => {
			await navigateTo({
				page,
				pageName: 'Segments',
			});

			await createDynamicSegment(page);

			await addSegmentField({
				criterionName: 'Commented on Blog',
				criterionType: 'Events',
				page,
			});

			await selectAsset({
				assetName: pageName,
				page,
			});

			await includeAnonymousToggle({
				enable: true,
				page,
			});

			await setSegmentName({
				page,
				segmentName: 'Test Commented on Blog Segment',
			});

			await saveSegment(page);
		});

		await test.step('Run the Segment Nanite', async () => {
			await runNanites({
				apiHelpers,
				naniteNames: [Nanites.UpdateMembershipsNanite],
				page,
			});
		});

		await test.step('Reload the segment page to clear the cache', async () => {
			await waitForLoading(page);

			await page.reload();

			await waitForLoading(page);
		});

		await test.step('Check the segment member count in the membership', async () => {
			await navigateTo({
				page,
				pageName: 'Membership',
			});

			await expect(
				page
					.locator('li')
					.filter({hasText: 'Known Members:'})
					.locator('b')
			).toHaveText('1');

			await expect(
				page
					.locator('li')
					.filter({hasText: 'Anonymous Members:'})
					.locator('b')
			).toHaveText('1');

			await expect(
				page
					.locator('li')
					.filter({hasText: 'Total Members:'})
					.locator('b')
			).toHaveText('2');
		});

		await test.step('Check that the correct known member appears in the membership tab', async () => {
			await viewNameOnTableList({
				itemNames: `${knownIndividualName} Smith`,
				page,
			});
		});

		await test.step('delete channel', async () => {
			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${channel.id}]`,
				project.groupId
			);
		});
	}
);

test(
	'Segment distribution chart can be filtered by date property',
	{
		tag: '@Legacy',
	},
	async ({apiHelpers, page}) => {
		const channelName = 'My Property - ' + getRandomString();
		const {channel, project} = await createChannel({
			apiHelpers,
			channelName,
		});

		const knownIndividualName = 'ac';
		const knownIndividual = [
			generateIndividual({
				name: knownIndividualName,
			}),
		];

		await test.step('Create the known individuals directly in the AC database', async () => {
			await createIndividuals({
				apiHelpers,
				individuals: knownIndividual,
			});
		});

		const date = new Date();

		await test.step('Create an event for the individual to appear in AC', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createEvents(
				knownIndividual.map((individual) => ({
					applicationId: 'Page',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'pageViewed',
					title: 'Liferay',
					userId: individual.id,
				}))
			);
		});

		await test.step('Create a session for the known individual', async () => {
			await apiHelpers.jsonWebServicesOSBAsah.createSessions(
				knownIndividual.map((individual) => ({
					channelId: channel.id,
					id: individual.id,
					sessionEnd: date.toISOString(),
					sessionStart: date.toISOString(),
					userId: individual.id,
				}))
			);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACSitesPageViaURL({
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Go to Segments > Create a Static Segment', async () => {
			await navigateTo({page, pageName: 'Segments'});

			await createStaticSegment(page);

			await setSegmentName({page, segmentName: 'Test Static Segment'});

			await addStaticMember({
				memberNames: `${knownIndividualName}@liferay.com`,
				page,
			});

			await saveSegment(page);
		});

		await test.step('Click on distribution tab and select birthDate attribute', async () => {
			await goToDistributionTabAndSelectAttribute({
				attributeName: 'birthDate',
				page,
			});
		});

		await test.step('Check if the correct results appear (birthdate and maximum count)', async () => {
			await viewBreakdownRechartsData({
				attributeValue: '1970-01-01',
				maxCount: '1',
				page,
			});
		});

		await test.step('delete channel', async () => {
			await apiHelpers.jsonWebServicesOSBFaro.deleteChannel(
				`[${channel.id}]`,
				project.groupId
			);
		});
	}
);
