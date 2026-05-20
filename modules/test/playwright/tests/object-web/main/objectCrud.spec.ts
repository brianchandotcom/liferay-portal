/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinition,
	ObjectRelationship,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
import {localizationPagesTest} from '../../site-admin-web/main/fixtures/localizationPagesTest';
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	localizationPagesTest,
	loginTest(),
	objectPagesTest
);

test(
	'Verify it is possible to delete a published object',
	{tag: '@LPS-150886'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			objectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

		await page
			.getByPlaceholder('Confirm Object Definition Name')
			.fill(objectDefinition.name);

		await page.getByRole('button', {exact: true, name: 'Delete'}).click();

		apiHelpers.data.splice(
			apiHelpers.data.findIndex(
				(object) =>
					object.id === objectDefinition.id &&
					object.type === 'objectDefinition'
			),
			1
		);

		await expect(
			viewObjectDefinitionsPage.frontendDataSetEntries.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeHidden();
	}
);

test(
	'Verify it is possible to filter object entries by API',
	{tag: '@LPS-158615'},
	async ({apiHelpers}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{businessType: 'Text', indexedAsKeyword: true},
			],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Apple'},
			applicationName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Apple Sauce'},
			applicationName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Banana'},
			applicationName
		);

		const searchParams = new URLSearchParams();

		searchParams.append('filter', `${fieldName} eq 'Apple'`);

		const {items} =
			await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
				applicationName,
				searchParams
			);

		expect(items).toHaveLength(1);
		expect(items[0][fieldName]).toBe('Apple');
	}
);

test(
	'Verify the custom object label cannot be used to confirm the deletion',
	{tag: '@LPS-150886'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			objectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

		// Type a wrong value (not the name)

		await page
			.getByPlaceholder('Confirm Object Definition Name', {
				exact: false,
			})
			.fill('WrongValue');

		const deleteButton = page.getByRole('button', {
			exact: true,
			name: 'Delete',
		});

		await expect(deleteButton).toBeDisabled();
	}
);

test('Verify it is possible to search object entries by API', async ({
	apiHelpers,
}) => {
	const objectFields = generateObjectFields({
		objectFieldBusinessTypes: ['Text'],
	});

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFields,
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';
	const fieldName = objectFields[0].name!;

	const entryA = await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'EntryA'},
		applicationName
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'EntryB'},
		applicationName
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'EntryC'},
		applicationName
	);

	const searchParams = new URLSearchParams();

	searchParams.append('search', 'EntryA');

	const {items} =
		await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
			applicationName,
			searchParams
		);

	expect(items.length).toBeGreaterThan(0);
	expect(items[0].id).toBe(entryA.id);
});

test('Verify it is possible to sort object entries by API', async ({
	apiHelpers,
}) => {
	const objectFields = generateObjectFields({
		objectFieldBusinessTypes: [
			{businessType: 'Text', indexedAsKeyword: true},
		],
	});

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFields,
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';
	const fieldName = objectFields[0].name!;

	await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'EntryC'},
		applicationName
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'EntryZ'},
		applicationName
	);

	await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'EntryA'},
		applicationName
	);

	const searchParams = new URLSearchParams();

	searchParams.append('sort', `${fieldName}:asc`);

	const {items} =
		await apiHelpers.objectEntry.getObjectDefinitionObjectEntries(
			applicationName,
			searchParams
		);

	expect(items[0][fieldName]).toBe('EntryA');
	expect(items[1][fieldName]).toBe('EntryC');
	expect(items[2][fieldName]).toBe('EntryZ');
});

test('Verify it is possible to update Custom Object when changing the localization on Instance Settings', async ({
	apiHelpers,
	localizationInstanceSettingsPage,
	page,
	viewObjectDefinitionsPage,
}) => {
	let objectDefinition: ObjectDefinition;

	await test.step('Create a custom object', async () => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});
	});

	try {
		await test.step('Change default language to Portuguese', async () => {
			await localizationInstanceSettingsPage.goto('Language');

			await localizationInstanceSettingsPage.defaultLanguageSelect.selectOption(
				'pt_BR'
			);

			await localizationInstanceSettingsPage.saveSettings();
		});

		await test.step('Navigate to Object Admin and update the object label', async () => {
			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
				objectDefinition.label['en_US']
			);

			const newLabel = 'Objeto Personalizado ' + getRandomInt();
			const newPluralLabel = 'Objetos Personalizados ' + getRandomInt();

			await page
				.getByLabel('LabelMandatory', {exact: true})
				.pressSequentially(newLabel);
			await page
				.getByLabel('Plural LabelMandatory', {exact: true})
				.pressSequentially(newPluralLabel);

			await page.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(page, 'The object was saved successfully.');

			await page.waitForLoadState('networkidle');

			await expect(
				page.getByLabel('LabelMandatory', {exact: true})
			).toHaveValue(newLabel);
			await expect(
				page.getByLabel('Plural LabelMandatory', {exact: true})
			).toHaveValue(newPluralLabel);
		});
	}
	finally {
		await test.step('Restore default language to English', async () => {
			await localizationInstanceSettingsPage.goto('Language');

			await localizationInstanceSettingsPage.defaultLanguageSelect.selectOption(
				'en_US'
			);

			await localizationInstanceSettingsPage.saveSettings();
		});
	}
});

test(
	'Verify it is possible to update the label of relationship field of custom object from a native object',
	{tag: '@LPS-135406'},
	async ({apiHelpers, objectFieldsPage, page}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const relationshipLabel = 'Relationship' + getRandomInt();

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_USER',
				{
					label: {en_US: relationshipLabel},
					name: 'relationship' + getRandomInt(),
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		// Navigate to the custom object's Fields tab to update the relationship field label

		await objectFieldsPage.goto(objectDefinition.label['en_US']);

		await objectFieldsPage.openObjectField(relationshipLabel);

		const iframeLocator = page.frameLocator('iframe');
		const newLabel = 'New Relationship' + getRandomInt();

		await iframeLocator.getByLabel('LabelMandatory').clear();
		await iframeLocator.getByLabel('LabelMandatory').fill(newLabel);

		await objectFieldsPage.editFieldSaveButton.click();

		await waitForAlert(page, 'The object field was updated successfully');

		await page.reload();

		await expect(page.getByText(newLabel)).toBeVisible();
	}
);

test(
	'Verify an error message is shown when the user enters the wrong value in the confirmation field',
	{tag: '@LPS-162024'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			objectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

		await page
			.getByPlaceholder('Confirm Object Definition Name')
			.fill('wrongvalue');

		await expect(page.getByText('Input does not match')).toBeVisible();
	}
);

test('Verify it is possible to view the custom object after restarting portal', async ({
	apiHelpers,
	page,
	viewObjectDefinitionsPage,
}) => {
	const objectFields = generateObjectFields({
		objectFieldBusinessTypes: ['Text'],
	});

	const objectDefinition =
		await apiHelpers.objectAdmin.postRandomObjectDefinition({
			objectFields,
			status: {code: 0},
		});

	apiHelpers.data.push({
		id: objectDefinition.id,
		type: 'objectDefinition',
	});

	const applicationName = 'c/' + objectDefinition.name.toLowerCase() + 's';
	const fieldName = objectFields[0].name!;

	await apiHelpers.objectEntry.postObjectEntry(
		{[fieldName]: 'Entry Test'},
		applicationName
	);

	// Simulate "restart" by navigating away and back

	await page.goto('/');

	await viewObjectDefinitionsPage.goto();

	await expect(
		viewObjectDefinitionsPage.frontendDataSetEntries.filter({
			hasText: objectDefinition.label['en_US'],
		})
	).toBeVisible();
});

test.fixme(
	'Verify it is possible to add an Object Entry Title Field when changing the localization on Instance Settings',
	async ({
		apiHelpers,
		localizationInstanceSettingsPage,
		page,
		viewObjectDefinitionsPage,
		viewObjectEntriesPage,
	}) => {
		let objectDefinition: ObjectDefinition;
		let relationshipLabel: string;
		let relationshipName: string;

		await test.step('Create an Account and a Custom Object with a Relationship 1toM of Account to Custom Object', async () => {
			await apiHelpers.headlessAdminUser.postAccount();

			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text'],
			});

			objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFields,
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			const objectRelationshipAPIClient =
				await apiHelpers.buildRestClient(ObjectRelationshipAPI);

			relationshipLabel = 'Relationship';
			relationshipName = 'relationship' + getRandomInt();

			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_ACCOUNT',
				{
					label: {en_US: relationshipLabel},
					name: relationshipName,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);
		});

		try {
			await test.step('Change the default language in the virtual instance to Portuguese', async () => {
				await localizationInstanceSettingsPage.goto('Language');

				await localizationInstanceSettingsPage.defaultLanguageSelect.selectOption(
					'pt_BR'
				);

				await localizationInstanceSettingsPage.saveSettings();
			});

			await test.step('Change the title field in the System Object and publish the Custom Object', async () => {
				await viewObjectDefinitionsPage.goto();

				await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
					'Account'
				);

				await page
					.getByRole('combobox', {name: 'Entry Title Field'})
					.click();

				await page.getByRole('option', {name: 'Type'}).click();

				await viewObjectEntriesPage.saveObjectEntryButton.click();

				await waitForAlert(page, 'The object was saved successfully.');

				await page.waitForLoadState('networkidle');
			});

			await test.step('Create an entry using the title field selected', async () => {
				await viewObjectEntriesPage.goto(objectDefinition.className);

				await viewObjectEntriesPage.clickAddObjectEntry(
					objectDefinition.label['en_US']
				);

				await page.getByLabel(relationshipLabel).click();

				await page
					.getByRole('option', {name: /business/i})
					.first()
					.click();

				await viewObjectEntriesPage.saveObjectEntryButton.click();

				await waitForAlert(page, 'The object was saved successfully.');

				await page.waitForLoadState('networkidle');
			});

			await test.step('Verify the title field of System Object is present', async () => {
				await viewObjectEntriesPage.backButton.click();

				await expect(
					page.getByRole('cell', {name: /business/i}).first()
				).toBeVisible();

				await page
					.getByRole('cell', {name: /business/i})
					.first()
					.click();

				await expect(page.getByText(/business/i).first()).toBeVisible();
			});
		}
		finally {
			await test.step('Restore default language to English', async () => {
				await localizationInstanceSettingsPage.goto('Language');

				await localizationInstanceSettingsPage.defaultLanguageSelect.selectOption(
					'en_US'
				);

				await localizationInstanceSettingsPage.saveSettings();
			});

			await test.step('Restore Account Entry Title Field', async () => {
				await viewObjectDefinitionsPage.goto();

				await viewObjectDefinitionsPage.clickEditObjectDefinitionLink(
					'Account'
				);

				await page
					.getByRole('combobox', {name: 'Entry Title Field'})
					.click();

				await page.getByRole('option', {name: 'Name'}).click();

				await viewObjectEntriesPage.saveObjectEntryButton.click();

				await waitForAlert(page, 'The object was saved successfully.');
			});
		}
	}
);

test(
	'Verify the delete modal contains a warning message with the number of entries that will be deleted',
	{tag: '@LPS-150886'},
	async ({apiHelpers, page, viewObjectDefinitionsPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';
		const fieldName = objectFields[0].name!;

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry A'},
			applicationName
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[fieldName]: 'Entry B'},
			applicationName
		);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.clickObjectDefinitionActionButton(
			objectDefinition.label['en_US']
		);

		await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

		await expect(page.getByText('Delete Object Definition')).toBeVisible();

		await expect(page.getByText('2 object entries')).toBeVisible();
	}
);
