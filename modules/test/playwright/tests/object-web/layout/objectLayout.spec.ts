/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
	ObjectField,
	ObjectRelationshipAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {editObjectDefinitionPagesTest} from '../../../fixtures/editObjectDefinitionPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import getRandomString from '../../../utils/getRandomString';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectEntryValues} from '../utils/generateObjectEntry';
import {generateObjectFields} from '../utils/generateObjectFields';
import getRandomObjectFieldText from '../utils/getRandomObjectFieldText';

export const test = mergeTests(
	apiHelpersTest,
	dataApiHelpersTest,
	editObjectDefinitionPagesTest,
	loginTest(),
	objectPagesTest
);

test.describe('Manage custom layouts through object layout tab', () => {
	test('can add seo block when creating its layout', async ({
		apiHelpers,
		objectLayoutsPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				enableFriendlyURLCustomization: true,
				status: {code: 0},
				titleObjectFieldName: 'textField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await test.step('create layout with SEO block and set it as default', async () => {
			await objectLayoutsPage.goto(objectDefinition.name);

			const objectLayoutName = getRandomString();

			await objectLayoutsPage.createObjectLayout(objectLayoutName);

			await objectLayoutsPage.createObjectLayoutContent({
				hasSeoBlock: true,
				objectFieldNames: ['textField'],
				objectLayoutName,
				objectLayoutRegularBlockName: getRandomString(),
				objectLayoutTabName: getRandomString(),
			});

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await page
				.frameLocator('iframe')
				.getByRole('button', {name: 'Save'})
				.first()
				.click();
		});

		await test.step('add object entry with custom friendly URL', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldBusinessType: 'Text',
				objectFieldLabel: 'textField',
				objectFieldValue: 'Entry A',
			});

			await viewObjectEntriesPage.friendlyUrlInput.fill(
				'Entry A friendlyURL'
			);

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await expect(page.getByLabel('Friendly URL')).toHaveValue(
				'entry-a-friendlyurl'
			);
		});
	});

	test('can create, read, update and delete relationship child entry when selected', async ({
		apiHelpers,
		objectLayoutsPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const objectDefinitionLabel1 = 'ObjectDefinitionLabel' + getRandomInt();
		const objectDefinitionName1 = 'ObjectDefinitionName' + getRandomInt();

		const objectDefinitionLabel2 = 'ObjectDefinitionLabel' + getRandomInt();
		const objectDefinitionName2 = 'ObjectDefinitionName' + getRandomInt();

		const objectFields1: Partial<ObjectField>[] = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const {objectEntry: objectEntry1} = await generateObjectEntryValues({
			objectEntryFormat: 'UI',
			objectFields: objectFields1,
		});

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		const {body: objectDefinition} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				label: {
					en_US: objectDefinitionLabel1,
				},
				name: objectDefinitionName1,
				objectFields: objectFields1,
				pluralLabel: {
					en_US: objectDefinitionLabel1,
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		const parentObjectEntry = await apiHelpers.objectEntry.postObjectEntry(
			objectEntry1,
			applicationName
		);

		const objectFields2: Partial<ObjectField>[] = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const {objectEntry: objectEntry2} = await generateObjectEntryValues({
			objectEntryFormat: 'UI',
			objectFields: objectFields2,
		});

		const {body: objectDefinition2} =
			await objectDefinitionAPIClient.postObjectDefinition({
				active: true,
				label: {
					en_US: objectDefinitionLabel2,
				},
				name: objectDefinitionName2,
				objectFields: objectFields2,
				pluralLabel: {
					en_US: objectDefinitionLabel2,
				},
				portlet: true,
				scope: 'company',
				status: {
					code: 0,
				},
			});

		apiHelpers.data.push({
			id: objectDefinition2.id,
			type: 'objectDefinition',
		});

		const applicationName2 =
			'c/' + objectDefinition2.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			objectEntry2,
			applicationName2
		);

		const objectRelationshipApiClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const {body: objectRelationship} =
			await objectRelationshipApiClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {
						en_US: 'objectRelationshipLabel' + getRandomInt(),
					},
					name:
						'objectRelationshipName' +
						Math.floor(Math.random() * 99),
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition2.externalReferenceCode,
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionId2: objectDefinition2.id,
					objectDefinitionName2: objectDefinition2.name,
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectLayoutsPage.goto(objectDefinitionLabel1);

		const objectLayoutName = getRandomString();

		await objectLayoutsPage.createObjectLayout(objectLayoutName);

		await objectLayoutsPage.createObjectLayoutContent({
			objectFieldNames: [objectFields1[0].label.en_US],
			objectLayoutName,
			objectLayoutRegularBlockName: getRandomString(),
			objectLayoutTabName: getRandomString(),
		});

		await objectLayoutsPage.setObjectLayoutAsDefault();

		const objectLayoutRelTabName = getRandomString();

		await objectLayoutsPage.createObjectRelationshipTab(
			objectLayoutName,
			objectLayoutRelTabName,
			objectRelationship.label.en_US
		);

		await waitForAlert(
			page,
			'Success:The object layout was updated successfully'
		);

		const objectChildEntry = 'ChildEntry' + getRandomInt();

		await test.step('Create relationship entry', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await page
				.getByRole('link', {name: parentObjectEntry.id.toString()})
				.click();

			await page
				.getByRole('link')
				.filter({hasText: objectLayoutRelTabName})
				.click();

			await page.getByRole('button', {name: 'New'}).first().click();

			await page.getByRole('menuitem', {name: 'Create New'}).click();

			await page
				.getByLabel(objectFields2[0].label.en_US)
				.fill(objectChildEntry);

			await page.getByRole('button', {name: 'Save'}).click();

			await waitForAlert(page);
		});

		const relationshipInput = page.getByPlaceholder('Search');

		await test.step('Read relationship input value', async () => {
			await viewObjectEntriesPage.backButton.click();

			await page.getByRole('cell').getByRole('link').click();

			await page
				.getByLabel(objectFields2[0].label.en_US)
				.waitFor({state: 'visible'});

			await expect(relationshipInput).toHaveValue(
				parentObjectEntry.externalReferenceCode
			);

			await expect(
				page.getByLabel(objectFields2[0].label.en_US)
			).toHaveValue(objectChildEntry);
		});

		await test.step('Update relationship input value', async () => {
			const newEntry = await generateObjectEntryValues({
				objectEntryFormat: 'UI',
				objectFields: objectFields1,
			});

			const newRelationshipEntry =
				await apiHelpers.objectEntry.postObjectEntry(
					newEntry,
					applicationName
				);

			await page.reload();

			await relationshipInput.click();

			await page
				.getByRole('menuitem', {
					name: newRelationshipEntry.externalReferenceCode,
				})
				.click();

			await page.getByRole('button', {name: 'Save'}).click();

			await expect(relationshipInput).toHaveValue(
				newRelationshipEntry.externalReferenceCode
			);
		});

		await test.step('Delete relationship input value', async () => {
			await relationshipInput.fill('');

			await page.keyboard.press('Enter');

			await page.getByRole('button', {name: 'Save'}).click();

			await expect(relationshipInput).toHaveValue('');
		});
	});

	test('can view all fields of an object when creating its layout', async ({
		apiHelpers,
		objectLayoutsPage,
		page,
	}) => {
		const objectFields = getRandomObjectFieldText({
			objectFieldsQuantity: 20,
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		const objectLayoutName = getRandomString();

		await objectLayoutsPage.goto(objectDefinition.name);

		await objectLayoutsPage.createObjectLayout(objectLayoutName);

		await objectLayoutsPage.openObjectLayoutConfiguration(objectLayoutName);

		await objectLayoutsPage.createObjectLayoutTab(getRandomString());

		await objectLayoutsPage.createObjectLayoutBlock({
			objectLayoutRegularBlockName: getRandomString(),
		});

		await objectLayoutsPage.openObjectLayoutObjectField();

		objectFields.forEach(({label}) => {
			expect(
				page
					.frameLocator('iframe')
					.getByRole('option', {name: label.en_US})
			).toBeVisible();
		});

		// Clean up

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		await objectDefinitionAPIClient.deleteObjectDefinition(
			objectDefinition.id
		);
	});

	test('can view entries with custom layout created', async ({
		apiHelpers,
		objectLayoutsPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
				titleObjectFieldName: 'textField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const blockName = getRandomString();

		await test.step('create layout and set it as default', async () => {
			await objectLayoutsPage.goto(objectDefinition.name);

			const objectLayoutName = getRandomString();

			await objectLayoutsPage.createObjectLayout(objectLayoutName);

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: ['textField'],
				objectLayoutName,
				objectLayoutRegularBlockName: blockName,
				objectLayoutTabName: getRandomString(),
			});

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await page
				.frameLocator('iframe')
				.getByRole('button', {name: 'Save'})
				.first()
				.click();
		});

		await test.step('add object entry and assert that blockname is visible', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await viewObjectEntriesPage.fillObjectEntry({
				objectFieldBusinessType: 'Text',
				objectFieldLabel: 'textField',
				objectFieldValue: 'Entry A',
			});

			await viewObjectEntriesPage.saveObjectEntryButton.click();

			await waitForAlert(page);

			await viewObjectEntriesPage.backButton.click();

			await page.waitForLoadState('networkidle');

			await page
				.getByRole('row', {name: 'Entry A'})
				.getByRole('link')
				.click();

			await expect(
				page.locator('label').filter({hasText: blockName})
			).toBeVisible();

			await expect(page.getByLabel('textField')).toBeVisible();
		});
	});

	test(
		'cannot save layout as default when other is already set',
		{tag: ['@LPS-165850']},
		async ({apiHelpers, objectLayoutsPage, page}) => {
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

			await objectLayoutsPage.goto(objectDefinition.name);

			await objectLayoutsPage.goto(objectDefinition.label.en_US);

			const objectLayout1Name = getRandomString();

			await objectLayoutsPage.createObjectLayout(objectLayout1Name);

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label.en_US],
				objectLayoutName: objectLayout1Name,
				objectLayoutRegularBlockName: getRandomString(),
				objectLayoutTabName: getRandomString(),
			});

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await page
				.frameLocator('iframe')
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			const objectLayout2Name = getRandomString();

			await objectLayoutsPage.createObjectLayout(objectLayout2Name);

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label.en_US],
				objectLayoutName: objectLayout2Name,
				objectLayoutRegularBlockName: getRandomString(),
				objectLayoutTabName: getRandomString(),
			});

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await page
				.frameLocator('iframe')
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			await waitForAlert(
				page,
				'Error:There can only be one default object layout',
				{type: 'danger'}
			);
		}
	);

	test('seo and categorization blocks can be added and removed from layout', async ({
		apiHelpers,
		objectLayoutsPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				enableFriendlyURLCustomization: true,
				status: {code: 0},
				titleObjectFieldName: 'textField',
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectLayoutName = getRandomString();

		await test.step('create layout with SEO and Categorization collapsible blocks and set it as default', async () => {
			await objectLayoutsPage.goto(objectDefinition.name);

			await objectLayoutsPage.createObjectLayout(objectLayoutName);

			await objectLayoutsPage.createObjectLayoutContent({
				hasCategorizationBlock: true,
				hasSeoBlock: true,
				objectFieldNames: ['textField'],
				objectLayoutName,
				objectLayoutRegularBlockName: getRandomString(),
				objectLayoutTabName: getRandomString(),
			});

			await objectLayoutsPage.toggleCollapsible('Categorization');

			await objectLayoutsPage.toggleCollapsible('SEO');

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await page
				.frameLocator('iframe')
				.getByRole('button', {name: 'Save'})
				.first()
				.click();
		});

		await test.step('verify SEO and Categorization blocks are visible and collapsible', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await expect(
				page.getByRole('button', {name: 'Categorization'})
			).toBeVisible();

			await expect(page.getByRole('button', {name: 'SEO'})).toBeVisible();
		});

		await test.step('edit layout and remove SEO and Categorization blocks', async () => {
			await objectLayoutsPage.goto(objectDefinition.name);

			await page.getByRole('link', {name: objectLayoutName}).click();

			await objectLayoutsPage.layoutTab.click();

			const frame = page.locator('iframe').contentFrame();
			const itemsToDelete = 2;

			for (let i = 0; i < itemsToDelete; i++) {
				await frame
					.getByRole('button', {name: 'More Actions'})
					.last()
					.click();
				await frame.getByRole('menuitem', {name: 'Delete'}).click();
			}

			await objectLayoutsPage.saveUpdateLayoutButton.click();
		});

		await test.step('verify that blocks are not visible anymore', async () => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await expect(
				page.getByRole('button', {name: 'Categorization'})
			).not.toBeVisible();

			await expect(
				page.getByRole('button', {name: 'SEO'})
			).not.toBeVisible();
		});
	});
});
