/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {isolatedChannelTest} from '../../../fixtures/isolatedChannelTest';
import {loginAnalyticsCloudTest} from '../../../fixtures/loginAnalyticsCloudTest';
import {loginTest} from '../../../fixtures/loginTest';
import getRandomString from '../../../utils/getRandomString';
import {selectAndExpectToHaveValue} from '../../../utils/selectAndExpectToHaveValue';
import {waitForAlert} from '../../../utils/waitForAlert';
import {faroConfig} from './faro.config';
import {
	ACPage,
	navigateToACPageViaURL,
	navigateToACSettingsViaURL,
} from './utils/navigation';
import {CardSelectors} from './utils/selectors';
import {changeTimeFilter} from './utils/time-filter';

const test = mergeTests(
	apiHelpersTest,
	isolatedChannelTest,
	loginAnalyticsCloudTest(),
	loginTest()
);

async function createCustomEvent({
	apiHelpers,
	attributes = [],
	channelId,
	eventName,
}: {
	apiHelpers: any;
	attributes?: Array<{dataType: string; name: string; value: string}>;
	channelId: string;
	eventName: string;
}) {
	await apiHelpers.jsonWebServicesOSBAsah.createEvents([
		{
			applicationId: 'CustomEvent',
			canonicalUrl: 'https://www.liferay.com',
			channelId,
			eventDate: new Date().toISOString(),
			eventId: eventName,
			properties: attributes.map(({name, value}) => ({name, value})),
			title: 'Liferay',
			userId: '1',
		},
	]);

	await apiHelpers.jsonWebServicesOSBAsah.createEventDefinition([
		{
			applicationId: 'CustomEvent',
			displayName: eventName,
			eventAttributeDefinitions: attributes.map(({dataType, name}) => ({
				dataType,
				displayName: name,
				name,
				type: 'LOCAL',
			})),
			name: eventName,
			type: 'CUSTOM',
		},
	]);
}

async function openCustomEventEditor({
	eventName,
	page,
	projectGroupId,
}: {
	eventName: string;
	page: Page;
	projectGroupId: string;
}) {
	await navigateToACSettingsViaURL({
		acPage: ACPage.definitionsEventsCustomPage,
		page,
		projectID: projectGroupId,
	});

	await page.getByRole('link', {name: eventName}).click();

	await page.getByRole('button', {name: 'Edit'}).click();
}

async function saveCustomEventEditor(page: Page) {

	// Click Save and wait for the edit mutation to persist before navigating
	// away; navigating too early aborts the in-flight request.

	await Promise.all([
		page.waitForResponse(
			(response) =>
				response.url().includes('opname=UpdateEventDefinition') &&
				response.ok()
		),
		page.getByRole('button', {name: 'Save'}).click(),
	]);
}

async function saveAttributeEditor(page: Page) {

	// The attribute editor persists through a different mutation than the event
	// editor; wait for it before navigating away.

	await Promise.all([
		page.waitForResponse(
			(response) =>
				response
					.url()
					.includes('opname=UpdateEventAttributeDefinition') &&
				response.ok()
		),
		page.getByRole('button', {name: 'Save'}).click(),
	]);
}

async function resetSearchQueriesPreference(page: Page, projectID: string) {
	const response = await page.request.post(
		`${faroConfig.environment.baseUrl}/o/cerebro/graphql?opname=Preference&projectGroupId=${projectID}`,
		{
			data: {
				operationName: 'Preference',
				query: 'mutation Preference($key: String!, $value: String!) { preference(key: $key, value: $value) { key value } }',
				variables: {
					key: 'search-query-strings',
					value: '[]',
				},
			},
		}
	);

	expect(response.ok()).toBe(true);
}

test.beforeEach(async ({page, project}) => {
	await resetSearchQueriesPreference(page, project.groupId);
});

test(
	'Add, edit, and delete a search query parameter',
	{tag: ['@LRAC-8779', '@LRAC-8780', '@LRAC-8781']},
	async ({page, project}) => {
		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsSearchPage,
			page,
			projectID: project.groupId,
		});

		// Add a search query parameter

		await page.getByRole('button', {name: 'Add'}).click();

		await page
			.locator('input[name="queryStringList.0"]')
			.fill('Test Query');

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page, 'Search query definition has been saved', {
			autoClose: false,
		});

		// Verify the parameter persists after reload

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsSearchPage,
			page,
			projectID: project.groupId,
		});

		await expect(
			page.locator('input[name="queryStringList.0"]')
		).toHaveValue('Test Query');

		// Edit the parameter

		await page.locator('input[name="queryStringList.0"]').fill('Test Edit');

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page, 'Search query definition has been saved', {
			autoClose: false,
		});

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsSearchPage,
			page,
			projectID: project.groupId,
		});

		await expect(
			page.locator('input[name="queryStringList.0"]')
		).toHaveValue('Test Edit');

		// Add a second parameter so deleting the first leaves a valid form

		await page.getByRole('button', {name: 'Add'}).click();

		await page.locator('input[name="queryStringList.1"]').fill('Keep This');

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page, 'Search query definition has been saved', {
			autoClose: false,
		});

		// Delete the first parameter

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsSearchPage,
			page,
			projectID: project.groupId,
		});

		await page.getByRole('button', {name: 'Delete'}).first().click();

		// Re-fill the remaining input and blur to force Formik to re-run
		// validation after the FieldArray remove; otherwise Save stays disabled.

		await page.locator('input[name="queryStringList.0"]').fill('Keep This');

		await page.keyboard.press('Tab');

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page, 'Search query definition has been saved', {
			autoClose: false,
		});

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsSearchPage,
			page,
			projectID: project.groupId,
		});

		await expect(
			page.locator('input[name="queryStringList.0"]')
		).toHaveValue('Keep This');

		await expect(
			page.locator('input[name="queryStringList.1"]')
		).toHaveCount(0);
	}
);

test(
	'Connected DXP data source appears as the source of every default individual attribute',
	{tag: '@Legacy'},
	async ({apiHelpers, page, project}) => {
		const connectionToken =
			await apiHelpers.jsonWebServicesOSBFaro.fetchDataSourceConnectionToken(
				project.groupId
			);

		await apiHelpers.analyticsSettingsRest.postDataSource(connectionToken);

		try {
			await navigateToACSettingsViaURL({
				acPage: ACPage.dataSourcePage,
				page,
				projectID: project.groupId,
			});

			const connectedRow = page
				.locator('table tbody tr')
				.filter({hasNotText: 'DISCONNECTED'})
				.filter({hasText: 'CONNECTED'});

			const dataSourceName =
				(await connectedRow.locator('td').first().textContent()) || '';

			expect(dataSourceName.trim()).not.toBe('');

			await navigateToACSettingsViaURL({
				acPage: ACPage.definitionsIndividualAttributesPage,
				page,
				projectID: project.groupId,
			});

			// Open the 'email' attribute and verify its source is the
			// freshly-connected DXP data source

			await page.getByRole('button', {name: 'email'}).click();

			await expect(
				page.getByRole('cell', {name: dataSourceName}).first()
			).toBeVisible();
		}
		finally {
			await apiHelpers.analyticsSettingsRest.deleteDataSource();
		}
	}
);

test(
	'Add button is hidden after reaching the 5-query limit',
	{tag: '@LRAC-8782'},
	async ({page, project}) => {
		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsSearchPage,
			page,
			projectID: project.groupId,
		});

		// Add 5 query strings

		for (let i = 0; i < 5; i++) {
			await page.getByRole('button', {name: 'Add'}).click();

			await page
				.locator(`input[name="queryStringList.${i}"]`)
				.fill(`Test Query ${i + 1}`);
		}

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page, 'Search query definition has been saved', {
			autoClose: false,
		});

		// Assert no Add button is rendered when the limit is reached

		await expect(page.getByRole('button', {name: 'Add'})).not.toBeVisible();
	}
);

test(
	'A customized search query string parameter collects searched terms in the Search Terms report',
	{tag: '@LRAC-14680'},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const queryParameter = 'query';

		const searchTerm = 'test parameter';

		// Define a customized search query string parameter

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsSearchPage,
			page,
			projectID: project.groupId,
		});

		await page.getByRole('button', {name: 'Add'}).click();

		await page
			.locator('input[name="queryStringList.0"]')
			.fill(queryParameter);

		await page.getByRole('button', {name: 'Save'}).click();

		await waitForAlert(page, 'Search query definition has been saved', {
			autoClose: false,
		});

		// Seed a search event carrying the customized keyword parameter

		const date = new Date();

		const searchUrl = `https://www.liferay.com/search?${queryParameter}=${encodeURIComponent(
			searchTerm
		)}`;

		await apiHelpers.jsonWebServicesOSBAsah.createEvents([
			{
				applicationId: 'Page',
				canonicalUrl: searchUrl,
				channelId: channel.id,
				eventDate: date.toISOString(),
				eventId: 'pageViewed',
				title: searchTerm,
				url: searchUrl,
				userId: 'searchUser',
			},
		]);

		// The searched term surfaces in the Site overview Search Terms report

		await navigateToACPageViaURL({
			acPage: ACPage.sitePage,
			channelID: channel.id,
			page,
			projectID: project.groupId,
		});

		await changeTimeFilter({
			cardSelector: CardSelectors.SearchTerms,
			page,
			timeFilterPeriod: 'Last 24 hours',
		});

		await expect(
			page
				.locator(CardSelectors.SearchTerms)
				.getByText(searchTerm)
				.first()
		).toBeVisible();
	}
);

test(
	'A custom event description can be set and cleared, the event renamed, and a name over 255 characters is rejected',
	{tag: '@LRAC-9865'},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		const customEventName = 'event' + getRandomString();

		await createCustomEvent({
			apiHelpers,
			channelId: channel.id,
			eventName: customEventName,
		});

		// The custom event is listed

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsEventsCustomPage,
			page,
			projectID: project.groupId,
		});

		await expect(
			page.getByRole('link', {name: customEventName})
		).toBeVisible();

		// A description can be set and it appears in the list

		const description = `${customEventName} Description`;

		await openCustomEventEditor({
			eventName: customEventName,
			page,
			projectGroupId: project.groupId,
		});

		await page.getByLabel('Description').fill(description);

		await saveCustomEventEditor(page);

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsEventsCustomPage,
			page,
			projectID: project.groupId,
		});

		await expect(page.getByText(description)).toBeVisible();

		// The description can be cleared

		await openCustomEventEditor({
			eventName: customEventName,
			page,
			projectGroupId: project.groupId,
		});

		await page.getByLabel('Description').fill('');

		await saveCustomEventEditor(page);

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsEventsCustomPage,
			page,
			projectID: project.groupId,
		});

		await expect(page.getByText(description)).toHaveCount(0);

		// The display name can be renamed

		const displayName = `${customEventName} Display Name`;

		await openCustomEventEditor({
			eventName: customEventName,
			page,
			projectGroupId: project.groupId,
		});

		await page.getByLabel('Display Name').fill(displayName);

		await saveCustomEventEditor(page);

		await navigateToACSettingsViaURL({
			acPage: ACPage.definitionsEventsCustomPage,
			page,
			projectID: project.groupId,
		});

		await expect(page.getByText(displayName)).toBeVisible();

		// A display name longer than 255 characters is rejected. The row link
		// keeps showing the event name, so reopen the editor by that name.

		await openCustomEventEditor({
			eventName: customEventName,
			page,
			projectGroupId: project.groupId,
		});

		await page.getByLabel('Display Name').fill('a'.repeat(256));

		// Blur so Formik runs validation and surfaces the field error

		await page.getByLabel('Display Name').blur();

		await expect(page.getByText('Exceeds maximum length.')).toBeVisible();
	}
);

test(
	'An event attribute data type can be changed to Number, Date, Boolean and Duration',
	{
		tag: ['@LRAC-10200', '@LRAC-10201', '@LRAC-10203', '@LRAC-10205'],
	},
	async ({analyticsChannel: channel, apiHelpers, page, project}) => {
		await createCustomEvent({
			apiHelpers,
			attributes: [
				{dataType: 'STRING', name: 'animal', value: 'dog'},
				{dataType: 'STRING', name: 'phone', value: '87900809'},
				{dataType: 'STRING', name: 'job', value: 'QA'},
				{dataType: 'STRING', name: 'portal', value: 'ac'},
			],
			channelId: channel.id,
			eventName: 'event' + getRandomString(),
		});

		async function searchAttribute(attributeName: string) {
			await navigateToACSettingsViaURL({
				acPage: ACPage.eventAttributesPage,
				page,
				projectID: project.groupId,
			});

			await page
				.getByRole('link', {exact: true, name: 'Attributes'})
				.click();

			await page.getByPlaceholder('Search').fill(attributeName);

			await page.keyboard.press('Enter');
		}

		const dataTypeByAttribute: Array<[string, string]> = [
			['animal', 'NUMBER'],
			['phone', 'DATE'],
			['job', 'BOOLEAN'],
			['portal', 'DURATION'],
		];

		for (const [attributeName, dataType] of dataTypeByAttribute) {
			await searchAttribute(attributeName);

			await page
				.getByRole('link', {exact: true, name: attributeName})
				.click();

			await page.getByRole('button', {name: 'Edit'}).click();

			await selectAndExpectToHaveValue({
				optionValue: dataType,
				select: page.getByLabel('Default Data Typecast'),
			});

			await saveAttributeEditor(page);

			// The new data type is reflected in the attributes list

			await searchAttribute(attributeName);

			await expect(page.getByText(dataType)).toBeVisible();
		}
	}
);
