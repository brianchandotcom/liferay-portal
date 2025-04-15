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
import {selectAndExpectToHaveValue} from '../../utils/selectAndExpectToHaveValue';
import {pagesPagesTest} from '../layout-admin-web/fixtures/pagesPagesTest';
import {createChannel} from './utils/channel';
import {
	addBreakdown,
	addCustomEvent,
	addFilter,
	removeAttribute,
	setEventAnalysisName,
} from './utils/events';
import {
	ACPage,
	navigateToACPageViaURL,
	navigateToACSettingsViaURL,
} from './utils/navigation';
import {changeTimeFilter} from './utils/time-filter';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	assetPublisherPagesTest,
	pageEditorPagesTest,
	pagesPagesTest,
	pagesAdminPagesTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	loginAnalyticsCloudTest(),
	loginTest()
);

const randomString = getRandomString();

const channelName = 'My Property ' + randomString;
const pageTitle = 'My Page';

let channel;
let project;

test.beforeEach(async ({apiHelpers}) => {
	const result = await createChannel({
		apiHelpers,
		channelName,
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
	});
});

test(
	'Change data type with event already in use',
	{
		tag: '@LRAC-6280',
	},

	async ({apiHelpers, page}) => {
		await test.step('Send a custom event', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'customEvent',
					properties: [
						{
							name: 'birthdate',
							value: '2021-11-25T14:36:30.685Z',
						},
						{
							name: 'category',
							value: 'wetsuit',
						},
						{
							name: 'duration',
							value: '3600000',
						},
						{
							name: 'like',
							value: 'true',
						},
						{
							name: 'price',
							value: '259.95',
						},
						{
							name: 'temp',
							value: '11',
						},
					],
					title: 'Liferay',
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: 'customEvent',
					eventAttributeDefinitions: [
						{
							dataType: 'DATE',
							displayName: 'birthdate',
							name: 'birthdate',
							type: 'LOCAL',
						},
						{
							dataType: 'STRING',
							displayName: 'category',
							name: 'category',
							type: 'LOCAL',
						},
						{
							dataType: 'DURATION',
							displayName: 'duration',
							name: 'duration',
							type: 'LOCAL',
						},
						{
							dataType: 'BOOLEAN',
							displayName: 'like',
							name: 'like',
							type: 'LOCAL',
						},
						{
							dataType: 'NUMBER',
							displayName: 'price',
							name: 'price',
							type: 'LOCAL',
						},
						{
							dataType: 'NUMBER',
							displayName: 'temp',
							name: 'temp',
							type: 'LOCAL',
						},
					],
					name: 'customEvent',
					type: 'CUSTOM',
				},
			]);
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
				customEventName: 'customEvent',
				page,
			});
		});

		const attributeNameList = ['category', 'duration', 'temp'];

		await test.step('Add a breakdown to the analysis', async () => {
			for (const attributeName of attributeNameList) {
				await addBreakdown({
					breakdownName: attributeName,
					page,
					tab: 'Event',
				});
			}
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Add a filter to the analysis', async () => {
			await addFilter({
				filterName: 'temp',
				input: '10',
				operator: 'is greater than',
				page,
			});
		});

		await test.step('Check the analysis result appears and save the analysis', async () => {
			await page.setViewportSize({height: 1080, width: 1920});

			for (const attributeName of attributeNameList) {
				await expect(
					page.getByRole('button', {
						exact: true,
						name: `EVENT ${attributeName}`,
					})
				).toBeVisible();
			}

			const tableInfo = page.getByRole('cell');

			await expect(tableInfo.getByText('wetsuit')).toBeVisible();
			await expect(
				tableInfo.getByText('Between 01:00:00 - 01:00:59')
			).toBeVisible();
			await expect(tableInfo.getByText('10 - 19')).toBeVisible();
			await expect(tableInfo.getByText('100%')).toBeVisible();

			await page.getByText('Save Analysis').click();
		});

		await test.step('Check the analysis result appears and save the', async () => {
			await expect(
				page.getByText(`Event Analysis ${randomString}`)
			).toBeVisible();
		});

		await test.step('Navigation to attributes tab', async () => {
			await navigateToACSettingsViaURL({
				acPage: ACPage.eventAttributesPage,
				page,
				projectID: project.groupId,
			});

			await page
				.getByRole('link', {exact: true, name: 'Attributes'})
				.click();
		});

		await test.step('Edit the new attribute', async () => {
			await page.getByPlaceholder('Search').fill('temp');

			await page.keyboard.press('Enter');

			await expect(page.getByText('NUMBER')).toBeVisible();

			await page.getByRole('link', {name: 'temp'}).click();

			await page.getByRole('button', {name: 'Edit'}).click();

			await page.getByLabel('Default Data Typecast').click();

			await selectAndExpectToHaveValue({
				optionValue: 'DATE',
				select: page.getByLabel('Default Data Typecast'),
			});

			await page.getByRole('button', {name: 'Save'}).click();
		});

		await test.step('Check that the type of the attribute has been changed', async () => {
			await navigateToACSettingsViaURL({
				acPage: ACPage.eventAttributesPage,
				page,
				projectID: project.groupId,
			});

			await page
				.getByRole('link', {exact: true, name: 'Attributes'})
				.click();

			await page.getByPlaceholder('Search').fill('temp');

			await page.keyboard.press('Enter');

			await expect(page.getByText('DATE')).toBeVisible();
		});

		await test.step('test', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.eventAnalysisPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});

			await page
				.getByRole('link', {exact: false, name: 'Event Analysis'})
				.click();
		});

		await test.step('Check the analysis result appears', async () => {
			for (const attributeName of attributeNameList) {
				await expect(
					page.getByRole('button', {
						exact: true,
						name: `EVENT ${attributeName}`,
					})
				).toBeVisible();
			}

			const tableInfo = page.getByRole('cell');

			await expect(tableInfo.getByText('wetsuit')).toBeVisible();
			await expect(
				tableInfo.getByText('Between 01:00:00 - 01:00:59')
			).toBeVisible();
			await expect(tableInfo.getByText('10 - 19')).toBeVisible();
			await expect(tableInfo.getByText('100%')).toBeVisible();
		});

		await test.step('Check that the attributes used have not had the attribute type changed', async () => {
			await expect(
				page.getByRole('button', {name: 'Event | temp is greater than'})
			).toBeVisible();
		});
	}
);

test(
	'Event Analysis breakdown filter provide auto complete suggestions for "contain" condition',
	{
		tag: '@LRAC-9481',
	},

	async ({apiHelpers, page}) => {
		await test.step('Send a custom event', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'customEvent',
					properties: [
						{
							name: 'city',
							value: 'rio de janeiro',
						},
					],
					title: 'Liferay',
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: 'customEvent',
					eventAttributeDefinitions: [
						{
							dataType: 'STRING',
							displayName: 'city',
							name: 'city',
							type: 'LOCAL',
						},
					],
					name: 'customEvent',
					type: 'CUSTOM',
				},
			]);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.eventAnalysisPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Add the custom event and create an analysis', async () => {
			await page.getByRole('link', {name: 'Create Analysis'}).click();
		});

		await test.step('Add a name to the analysis', async () => {
			await setEventAnalysisName({
				eventAnalysisName: `Event Analysis ${randomString}`,
				page,
			});
		});

		await test.step('Add the custom event to the analysis', async () => {
			await addCustomEvent({
				customEventName: 'customEvent',
				page,
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Add a filter to the analysis', async () => {
			await page
				.locator('.attribute-filter-section-root')
				.getByRole('button')
				.click();

			await page
				.getByRole('menuitem', {exact: true, name: 'city'})
				.click();
		});

		await test.step('Select the contain condition', async () => {
			await page.getByLabel('Condition').click();

			await selectAndExpectToHaveValue({
				optionLabel: 'contains',
				select: page.getByLabel('Condition'),
			});

			await page.waitForTimeout(1000);
		});

		await test.step('Check the auto complete filter', async () => {
			await page
				.locator(
					"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
				)
				.first()
				.fill('rio');

			await page.getByRole('option', {name: 'rio de janeiro'}).click();

			await page.keyboard.press('Enter');
		});

		await test.step('Check that the analysis result appears', async () => {
			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.nth(1)
			).toBeVisible();

			await page
				.locator('div')
				.filter({
					hasText: /^FilterEvent \| citycontains "rio de janeiro"$/,
				})
				.getByLabel('Close')
				.click();

			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.nth(1)
			).not.toBeVisible();
		});
	}
);

test(
	'Event Analysis breakdown filter provide auto complete suggestions for "not contain" condition',
	{
		tag: '@LRAC-9481',
	},

	async ({apiHelpers, page}) => {
		await test.step('Send a custom event', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'customEvent',
					properties: [
						{
							name: 'city',
							value: 'rio de janeiro',
						},
					],
					title: 'Liferay',
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: 'customEvent',
					eventAttributeDefinitions: [
						{
							dataType: 'STRING',
							displayName: 'city',
							name: 'city',
							type: 'LOCAL',
						},
					],
					name: 'customEvent',
					type: 'CUSTOM',
				},
			]);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.eventAnalysisPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Add the custom event and create an analysis', async () => {
			await page.getByRole('link', {name: 'Create Analysis'}).click();
		});

		await test.step('Add a name to the Analysis', async () => {
			await setEventAnalysisName({
				eventAnalysisName: `Event Analysis ${randomString}`,
				page,
			});
		});

		await test.step('Add the custom event to the analysis', async () => {
			await addCustomEvent({
				customEventName: 'customEvent',
				page,
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Add a filter to the analysis', async () => {
			await page
				.locator('.attribute-filter-section-root')
				.getByRole('button')
				.click();

			await page
				.getByRole('menuitem', {exact: true, name: 'city'})
				.click();
		});

		await test.step('Select the not contain condition', async () => {
			await page.getByLabel('Condition').click();

			await selectAndExpectToHaveValue({
				optionLabel: 'does not contain',
				select: page.getByLabel('Condition'),
			});

			await page.waitForTimeout(1000);
		});

		await test.step('Check the auto complete filter', async () => {
			await page
				.locator(
					"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
				)
				.first()
				.fill('rio');

			await page.getByRole('option', {name: 'rio de janeiro'}).click();

			await page.keyboard.press('Enter');
		});

		await test.step('Check that the analysis result appears', async () => {
			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.first()
			).toBeVisible();

			await page
				.locator('div')
				.filter({
					hasText:
						/^FilterEvent \| citydoes not contain "rio de janeiro"$/,
				})
				.getByLabel('Close')
				.click();

			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.first()
			).not.toBeVisible();
		});
	}
);

test(
	'Event Analysis breakdown filter provide auto complete suggestions for "is" condition',
	{
		tag: '@LRAC-9481',
	},

	async ({apiHelpers, page}) => {
		await test.step('Send a custom event', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'customEvent',
					properties: [
						{
							name: 'city',
							value: 'rio de janeiro',
						},
					],
					title: 'Liferay',
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: 'customEvent',
					eventAttributeDefinitions: [
						{
							dataType: 'STRING',
							displayName: 'city',
							name: 'city',
							type: 'LOCAL',
						},
					],
					name: 'customEvent',
					type: 'CUSTOM',
				},
			]);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.eventAnalysisPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Add the custom event and create an analysis', async () => {
			await page.getByRole('link', {name: 'Create Analysis'}).click();
		});

		await test.step('Add a name to the analysis', async () => {
			await setEventAnalysisName({
				eventAnalysisName: `Event Analysis ${randomString}`,
				page,
			});
		});

		await test.step('Add the custom event to the analysis', async () => {
			await addCustomEvent({
				customEventName: 'customEvent',
				page,
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Add a filter to the analysis', async () => {
			await page
				.locator('.attribute-filter-section-root')
				.getByRole('button')
				.click();

			await page
				.getByRole('menuitem', {exact: true, name: 'city'})
				.click();
		});

		await test.step('Select the not contain condition', async () => {
			await page.getByLabel('Condition').click();

			await selectAndExpectToHaveValue({
				optionLabel: 'is',
				select: page.getByLabel('Condition'),
			});

			await page.waitForTimeout(1000);
		});

		await test.step('Check the auto complete filter', async () => {
			await page
				.locator(
					"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
				)
				.first()
				.fill('rio');

			await page.getByRole('option', {name: 'rio de janeiro'}).click();

			await page.keyboard.press('Enter');
		});

		await test.step('Check that the analysis result appears', async () => {
			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.nth(1)
			).toBeVisible();

			await page
				.locator('div')
				.filter({hasText: /^FilterEvent \| cityis "rio de janeiro"$/})
				.getByLabel('Close')
				.click();

			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.nth(1)
			).not.toBeVisible();
		});
	}
);

test(
	'Event Analysis breakdown filter provide auto complete suggestions for "is not" condition',
	{
		tag: '@LRAC-9481',
	},

	async ({apiHelpers, page}) => {
		await test.step('Send a custom event', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'customEvent',
					properties: [
						{
							name: 'city',
							value: 'rio de janeiro',
						},
					],
					title: 'Liferay',
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: 'customEvent',
					eventAttributeDefinitions: [
						{
							dataType: 'STRING',
							displayName: 'city',
							name: 'city',
							type: 'LOCAL',
						},
					],
					name: 'customEvent',
					type: 'CUSTOM',
				},
			]);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.eventAnalysisPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Add the custom event and create an analysis', async () => {
			await page.getByRole('link', {name: 'Create Analysis'}).click();
		});

		await test.step('Add a name to the analysis', async () => {
			await setEventAnalysisName({
				eventAnalysisName: `Event Analysis ${randomString}`,
				page,
			});
		});

		await test.step('Add the custom event to the analysis', async () => {
			await addCustomEvent({
				customEventName: 'customEvent',
				page,
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Add a filter to the analysis', async () => {
			await page
				.locator('.attribute-filter-section-root')
				.getByRole('button')
				.click();

			await page
				.getByRole('menuitem', {exact: true, name: 'city'})
				.click();
		});

		await test.step('Select the not contain condition', async () => {
			await page.getByLabel('Condition').click();

			await selectAndExpectToHaveValue({
				optionLabel: 'is not',
				select: page.getByLabel('Condition'),
			});

			await page.waitForTimeout(1000);
		});

		await test.step('Check the auto complete filter', async () => {
			await page
				.locator(
					"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
				)
				.first()
				.fill('rio');

			await page.getByRole('option', {name: 'rio de janeiro'}).click();

			await page.keyboard.press('Enter');
		});

		await test.step('Check that the analysis result appears', async () => {
			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.first()
			).toBeVisible();

			await page
				.locator('div')
				.filter({
					hasText: /^FilterEvent \| cityis not "rio de janeiro"$/,
				})
				.getByLabel('Close')
				.click();

			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.first()
			).not.toBeVisible();
		});
	}
);

test(
	'Event Analysis creation with Filtered Attribute (String) and (Contains/Does not Contains) condition',
	{
		tag: '@LRAC-7868',
	},

	async ({apiHelpers, page}) => {
		await test.step('Send a custom event', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'customEvent',
					properties: [
						{
							name: 'city',
							value: 'rio de janeiro',
						},
					],
					title: 'Liferay',
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: 'customEvent',
					eventAttributeDefinitions: [
						{
							dataType: 'STRING',
							displayName: 'city',
							name: 'city',
							type: 'LOCAL',
						},
					],
					name: 'customEvent',
					type: 'CUSTOM',
				},
			]);
		});

		await test.step('Go to Analytics Cloud and Switch the property', async () => {
			await navigateToACPageViaURL({
				acPage: ACPage.eventAnalysisPage,
				channelID: channel.id,
				page,
				projectID: project.groupId,
			});
		});

		await test.step('Add the custom event and create an analysis', async () => {
			await page.getByRole('link', {name: 'Create Analysis'}).click();
		});

		await test.step('Add a name to the analysis', async () => {
			await setEventAnalysisName({
				eventAnalysisName: `Event Analysis ${randomString}`,
				page,
			});
		});

		await test.step('Add the custom event to the analysis', async () => {
			await addCustomEvent({
				customEventName: 'customEvent',
				page,
			});
		});

		await test.step('Change the time filter to Last 24 hours', async () => {
			await changeTimeFilter({
				page,
				timeFilterPeriod: 'Last 24 hours',
			});
		});

		await test.step('Add a filter to the analysis', async () => {
			await page
				.locator('.attribute-filter-section-root')
				.getByRole('button')
				.click();

			await page
				.getByRole('menuitem', {exact: true, name: 'city'})
				.click();
		});

		await test.step('Select the contain condition', async () => {
			await page.getByLabel('Condition').click();

			await selectAndExpectToHaveValue({
				optionLabel: 'contains',
				select: page.getByLabel('Condition'),
			});

			await page.waitForTimeout(1000);
		});

		await test.step('Check the auto complete filter', async () => {
			await page
				.locator(
					"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
				)
				.first()
				.fill('rio');

			await page.getByRole('option', {name: 'rio de janeiro'}).click();

			await page.keyboard.press('Enter');
		});

		await test.step('Check that the analysis result appears', async () => {
			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.nth(1)
			).toBeVisible();

			await page
				.locator('div')
				.filter({
					hasText: /^FilterEvent \| citycontains "rio de janeiro"$/,
				})
				.getByLabel('Close')
				.click();

			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.nth(1)
			).not.toBeVisible();
		});

		await test.step('Add a filter to the analysis', async () => {
			await page
				.locator('.attribute-filter-section-root')
				.getByRole('button')
				.click();

			await page
				.getByRole('menuitem', {exact: true, name: 'city'})
				.click();
		});

		await test.step('Select the not contain condition', async () => {
			await page.getByLabel('Condition').click();

			await selectAndExpectToHaveValue({
				optionLabel: 'does not contain',
				select: page.getByLabel('Condition'),
			});

			await page.waitForTimeout(1000);
		});

		await test.step('Check the auto complete filter', async () => {
			await page
				.locator(
					"xpath=//div[contains(@class,'event-analysis-editor-attribute-dropdown-root show')]//input"
				)
				.first()
				.fill('rio');

			await page.getByRole('option', {name: 'rio de janeiro'}).click();

			await page.keyboard.press('Enter');
		});

		await test.step('Check that the analysis result appears', async () => {
			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.first()
			).toBeVisible();

			await page
				.locator('div')
				.filter({
					hasText:
						/^FilterEvent \| citydoes not contain "rio de janeiro"$/,
				})
				.getByLabel('Close')
				.click();

			expect(
				page
					.getByRole('row', {name: 'customEvent'})
					.locator('div')
					.first()
			).not.toBeVisible();
		});
	}
);

test(
	'The analysis result should not appear if any of the attribute conditions are not contained within the result',
	{
		tag: '@LRAC-11746',
	},

	async ({apiHelpers, page}) => {
		await test.step('Send a custom event', async () => {
			const date = new Date();

			await apiHelpers.jsonWebServicesOSBAsah.createEvents([
				{
					applicationId: 'CustomEvent',
					canonicalUrl: 'https://www.liferay.com',
					channelId: channel.id,
					eventDate: date.toISOString(),
					eventId: 'pageTitleEvent',
					properties: [
						{
							name: 'pageTitleEvent',
							value: pageTitle,
						},
					],
					title: pageTitle,
					userId: '1',
				},
			]);

			await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
				{
					applicationId: 'CustomEvent',
					displayName: 'pageTitleEvent',
					eventAttributeDefinitions: [
						{
							dataType: 'STRING',
							displayName: 'pageTitleEvent',
							name: 'pageTitleEvent',
							type: 'LOCAL',
						},
						{
							dataType: 'STRING',
							displayName: 'pageTitle',
							name: 'pageTitle',
							type: 'GLOBAL',
						},
					],
					name: 'pageTitleEvent',
					type: 'CUSTOM',
				},
			]);
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

		await test.step('Add a name to the analysis', async () => {
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
				input: pageTitle,
				operator: 'contains',
				page,
			});
		});

		await test.step('View the information displayed', async () => {
			await expect(
				page.getByTestId(pageTitle.toLowerCase())
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
				input: pageTitle,
				operator: 'does not contain',
				page,
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
