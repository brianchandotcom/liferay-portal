/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectDefinitionAPI,
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
import {generateObjectFields} from '../utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test(
	'LPS-146754 Can create Many-to-Many relationship with System Object',
	{tag: '@LPS-146754'},
	async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectRelationshipsPage,
		page,
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

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await objectRelationshipsPage.addObjectRelationshipButton.click();

		const objectRelationship =
			await addNewObjectRelationshipModalPage.handleForm({
				manyRecordsOf: 'User',
				objectRelationshipLabel: 'Relationship',
				type: 'Many to Many',
			});

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await waitForAlert(
			page,
			'Success:Relationship was created successfully.'
		);

		await expect(
			page.getByRole('link', {exact: true, name: 'Relationship'})
		).toBeVisible();
	}
);

test(
	'LPS-163661 Can delete entries using Cascade deletion type',
	{tag: '@LPS-163661'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const textFieldName = objectFields[0].name;

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 2},
				titleObjectFieldName: textFieldName,
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					deletionType: 'cascade',
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		await objectDefinitionAPIClient.postObjectDefinitionPublish(
			objectDefinition.id
		);

		const restPath = `c/${objectDefinition.name.toLowerCase()}s`;

		const entryA = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry A'},
			restPath
		);

		const entryB = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry B'},
			restPath
		);

		const entryC = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry C'},
			restPath
		);

		await apiHelpers.objectEntry.putByExternalReferenceCodeCurrentExternalReferenceCodeObjectRelationshipNameRelatedExternalReferenceCode(
			{
				applicationName: restPath,
				currentExternalReferenceCode: entryA.externalReferenceCode,
				objectRelationshipName,
				relatedExternalReferenceCode: entryB.externalReferenceCode,
			}
		);

		await apiHelpers.objectEntry.putByExternalReferenceCodeCurrentExternalReferenceCodeObjectRelationshipNameRelatedExternalReferenceCode(
			{
				applicationName: restPath,
				currentExternalReferenceCode: entryB.externalReferenceCode,
				objectRelationshipName,
				relatedExternalReferenceCode: entryC.externalReferenceCode,
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const entryBRow = page.getByRole('row', {name: /Entry B/});

		await expect(entryBRow).toBeVisible();

		await entryBRow.getByRole('button', {name: 'Actions'}).click();
		await page.getByRole('menuitem', {name: 'Delete'}).click();
		await page.getByRole('button', {name: 'Delete'}).click();

		await expect(page.getByRole('row', {name: /Entry B/})).toBeHidden();
		await expect(page.getByRole('row', {name: /Entry C/})).toBeHidden();
		await expect(page.getByRole('row', {name: /Entry A/})).toBeVisible();
	}
);

test(
	'LPS-146754 Can delete Many-to-Many relationship between Custom Object entry and System Object entry',
	{tag: '@LPS-146754'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const userAccount1 =
			await apiHelpers.headlessAdminUser.postUserAccount();

		const userAccount2 =
			await apiHelpers.headlessAdminUser.postUserAccount();

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2: 'L_USER',
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionName2: 'User',
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		const objectDefinitionAPIClient =
			await apiHelpers.buildRestClient(ObjectDefinitionAPI);

		await objectDefinitionAPIClient.postObjectDefinitionPublish(
			objectDefinition.id
		);

		const {body: publishedObjectDefinition} =
			await objectDefinitionAPIClient.getObjectDefinition(
				objectDefinition.id
			);

		const objectEntriesClassName = publishedObjectDefinition.className;

		const textFieldName = objectFields[0].name;

		await objectLayoutsPage.goto(objectDefinition.label['en_US']);

		const layoutName = 'Layout' + getRandomInt();

		await objectLayoutsPage.createObjectLayout(layoutName);

		await objectLayoutsPage.createObjectLayoutContent({
			objectFieldNames: [objectFields[0].label['en_US']],
			objectLayoutName: layoutName,
			objectLayoutRegularBlockName: 'Block 1',
			objectLayoutTabName: 'Field Tab',
		});

		await objectLayoutsPage.addRelationshipTab(
			'Relationship Tab',
			'Relationship'
		);
		await objectLayoutsPage.setObjectLayoutAsDefault();

		const saveButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'})
			.first();

		await expect(saveButton).toBeVisible();
		await saveButton.dispatchEvent('click');
		await page.waitForLoadState('domcontentloaded');

		const restPath = `c/${objectDefinition.name.toLowerCase()}s`;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry A'},
			restPath
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry B'},
			restPath
		);

		for (const entryLabel of ['Entry A', 'Entry B']) {
			await viewObjectEntriesPage.goto(objectEntriesClassName);

			const entryRow = page.getByRole('row', {
				name: new RegExp(entryLabel),
			});

			await expect(entryRow).toBeVisible();

			await entryRow.getByRole('button', {name: 'Actions'}).click();
			await page.getByRole('menuitem', {name: 'View'}).click();

			const relationshipTab = page.getByText('Relationship Tab', {
				exact: true,
			});

			await expect(relationshipTab).toBeVisible();
			await relationshipTab.click();

			await page.getByLabel('Select Existing One').first().click();

			await page
				.frameLocator('iframe[title="Select"]')
				.getByText(String(userAccount1.id), {exact: true})
				.first()
				.click();
			await page.waitForTimeout(1000);

			await page.reload();

			const relationshipTabLink = page.getByRole('link', {
				exact: true,
				name: 'Relationship Tab',
			});

			await expect(relationshipTabLink).toBeVisible();
			await relationshipTabLink.click();

			await page.getByLabel('Select Existing One').first().click();

			await page
				.frameLocator('iframe[title="Select"]')
				.getByText(String(userAccount2.id), {exact: true})
				.click();

			await page.waitForTimeout(1000);
		}

		for (const entryLabel of ['Entry A', 'Entry B']) {
			await viewObjectEntriesPage.goto(objectEntriesClassName);

			const entryRow = page.getByRole('row', {
				name: new RegExp(entryLabel),
			});

			await expect(entryRow).toBeVisible();

			await entryRow.getByRole('button', {name: 'Actions'}).click();
			await page.getByRole('menuitem', {name: 'View'}).click();

			const relationshipTab = page.getByText('Relationship Tab', {
				exact: true,
			});

			await expect(relationshipTab).toBeVisible();
			await relationshipTab.click();

			const actionsButtons = page.getByRole('button', {name: 'Actions'});

			const trashLinks = page.locator('tbody tr [aria-label="Delete"]');

			await page
				.locator('tbody tr')
				.first()
				.waitFor({state: 'visible', timeout: 15000});

			while (
				(await actionsButtons
					.first()
					.isVisible({timeout: 1000})
					.catch(() => false)) ||
				(await trashLinks
					.first()
					.isVisible({timeout: 1000})
					.catch(() => false))
			) {
				if (
					await actionsButtons
						.first()
						.isVisible()
						.catch(() => false)
				) {
					await actionsButtons.first().click();
					await page.getByRole('menuitem', {name: 'Delete'}).click();
					await page.getByRole('button', {name: 'Delete'}).click();
				}
				else {
					await trashLinks.first().click();
				}

				await page.waitForTimeout(1500);
			}
		}

		await expect(
			page.getByText(userAccount1.givenName.toLowerCase())
		).toBeHidden();
		await expect(
			page.getByText(userAccount2.givenName.toLowerCase())
		).toBeHidden();
	}
);

test(
	'LPS-152508 Can delete Many-to-Many relationship from parent side',
	{tag: '@LPS-152508'},
	async ({apiHelpers, objectRelationshipsPage, page}) => {
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

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_USER',
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1: 'L_USER',
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectRelationshipsPage.goto('User');

		await objectRelationshipsPage.actionsButton.first().click();

		await objectRelationshipsPage.deleteObjectRelationshipOption.click();

		const deleteDialog = page.getByRole('dialog');

		await expect(deleteDialog).toBeVisible();

		await deleteDialog.getByRole('textbox').fill(objectRelationshipName);

		await deleteDialog.getByRole('button', {name: 'Delete'}).click();

		await expect(
			page.getByRole('link', {exact: true, name: 'Relationship'})
		).toBeHidden();

		apiHelpers.data.splice(
			apiHelpers.data.findIndex(
				(item) =>
					item.id === objectRelationship.id &&
					item.type === 'objectRelationship'
			),
			1
		);
	}
);

test(
	'LPS-147906 Can relate two entries bidirectionally in a self-referencing One-to-Many relationship',
	{tag: '@LPS-147906'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const textFieldName = objectFields[0].name;

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
				titleObjectFieldName: textFieldName,
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectLayoutsPage.goto(objectDefinition.label['en_US']);

		const layoutName = 'Layout' + getRandomInt();

		await objectLayoutsPage.createObjectLayout(layoutName);

		await objectLayoutsPage.createObjectLayoutContent({
			objectFieldNames: [objectFields[0].label['en_US'], 'Relationship'],
			objectLayoutName: layoutName,
			objectLayoutRegularBlockName: 'Block 1',
			objectLayoutTabName: 'Field Tab',
		});

		await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);
		await objectLayoutsPage.setObjectLayoutAsDefault();
		await objectLayoutsPage.layoutTab.click();

		await objectLayoutsPage.addRelationshipTab(
			'Relationship Tab',
			'Relationship'
		);

		const saveButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'})
			.first();

		await expect(saveButton).toBeVisible();
		await saveButton.dispatchEvent('click');
		await waitForAlert(
			page,
			'Success:The object layout was updated successfully'
		);

		const restPath = `c/${objectDefinition.name.toLowerCase()}s`;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry A'},
			restPath
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry B'},
			restPath
		);

		const openEntryRelationshipTab = async (entryLabel: string) => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			const entryRow = page.getByRole('row', {
				name: new RegExp(entryLabel),
			});

			await expect(entryRow).toBeVisible();

			await entryRow.getByRole('button', {name: 'Actions'}).click();
			await page.getByRole('menuitem', {name: 'View'}).click();

			await page.waitForLoadState('domcontentloaded');

			const relationshipTab = page.getByRole('link', {
				exact: true,
				name: 'Relationship Tab',
			});

			await expect(relationshipTab).toBeVisible();
			await relationshipTab.click();
		};

		const selectExistingRelationshipEntry = async (entryLabel: string) => {
			const selectExistingOneButton = page
				.getByRole('button', {name: 'Select Existing One'})
				.first();

			try {
				await expect(selectExistingOneButton).toBeVisible({
					timeout: 3000,
				});
				await selectExistingOneButton.click();
			}
			catch {
				await page.getByRole('button', {name: 'New'}).first().click();
				await page
					.getByRole('menuitem', {name: 'Select Existing One'})
					.click();
			}

			const relationshipEntry = page
				.frameLocator('iframe[title="Select"]')
				.getByText(entryLabel, {exact: true})
				.first();

			await expect(relationshipEntry).toBeVisible();
			await relationshipEntry.click();
		};

		await openEntryRelationshipTab('Entry A');

		await selectExistingRelationshipEntry('Entry B');

		await page.waitForTimeout(1000);

		await openEntryRelationshipTab('Entry B');

		await selectExistingRelationshipEntry('Entry A');

		await page.waitForTimeout(1000);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const tableBody = page.locator('tbody');

		await expect(tableBody.locator('tr')).toHaveCount(2);
		await expect(tableBody.getByText('Entry A', {exact: true})).toHaveCount(
			2
		);
		await expect(tableBody.getByText('Entry B', {exact: true})).toHaveCount(
			2
		);
	}
);

test(
	'LPS-157229 Can edit Many-to-Many relationship of Custom Object entries',
	{tag: '@LPS-157229'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectFields1 = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectFields2 = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const textFieldName1 = objectFields1[0].name;
		const textFieldName2 = objectFields2[0].name;

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: objectFields1,
				status: {code: 0},
				titleObjectFieldName: textFieldName1,
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: objectFields2,
				status: {code: 0},
				titleObjectFieldName: textFieldName2,
			});

		apiHelpers.data.push({
			id: objectDefinition1.id,
			type: 'objectDefinition',
		});

		apiHelpers.data.push({
			id: objectDefinition2.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition1.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition1.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition2.externalReferenceCode,
					objectDefinitionId1: objectDefinition1.id,
					objectDefinitionId2: objectDefinition2.id,
					objectDefinitionName2: objectDefinition2.name,
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectLayoutsPage.goto(objectDefinition1.label['en_US']);

		const layoutName = 'Layout' + getRandomInt();

		await objectLayoutsPage.createObjectLayout(layoutName);

		await objectLayoutsPage.createObjectLayoutContent({
			objectFieldNames: [objectFields1[0].label['en_US']],
			objectLayoutName: layoutName,
			objectLayoutRegularBlockName: 'Block 1',
			objectLayoutTabName: 'Field Tab',
		});

		await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);
		await objectLayoutsPage.setObjectLayoutAsDefault();
		await objectLayoutsPage.layoutTab.click();

		await objectLayoutsPage.addRelationshipTab(
			'Relationship Tab',
			'Relationship'
		);

		const saveButton = objectLayoutsPage.iframeLocator
			.getByRole('button', {name: 'Save'})
			.first();

		await expect(saveButton).toBeVisible();
		await saveButton.dispatchEvent('click');
		await waitForAlert(
			page,
			'Success:The object layout was updated successfully'
		);

		const restPath1 = `c/${objectDefinition1.name.toLowerCase()}s`;
		const restPath2 = `c/${objectDefinition2.name.toLowerCase()}s`;

		const entryA = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName1]: 'Entry A'},
			restPath1
		);

		const entryB = await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName2]: 'Entry B'},
			restPath2
		);

		await apiHelpers.objectEntry.putByExternalReferenceCodeCurrentExternalReferenceCodeObjectRelationshipNameRelatedExternalReferenceCode(
			{
				applicationName: restPath1,
				currentExternalReferenceCode: entryA.externalReferenceCode,
				objectRelationshipName,
				relatedExternalReferenceCode: entryB.externalReferenceCode,
			}
		);

		const openObjectEntry = async (
			className: string,
			entryLabel: string
		) => {
			await viewObjectEntriesPage.goto(className);

			const entryRow = page.getByRole('row', {
				name: new RegExp(entryLabel),
			});

			await expect(entryRow).toBeVisible();

			await entryRow.getByRole('button', {name: 'Actions'}).click();
			await page.getByRole('menuitem', {name: 'View'}).click();

			await page.waitForLoadState('domcontentloaded');
		};

		const openRelationshipTab = async () => {
			const relationshipTab = page.getByRole('link', {
				exact: true,
				name: 'Relationship Tab',
			});

			await expect(relationshipTab).toBeVisible();
			await relationshipTab.click();
		};

		await openObjectEntry(objectDefinition1.className, 'Entry A');
		await openRelationshipTab();
		await expect(
			page.getByRole('row').filter({hasText: 'Entry B'}).first()
		).toBeVisible();

		await openObjectEntry(objectDefinition2.className, 'Entry B');
		await page
			.getByLabel(objectFields2[0].label['en_US'], {exact: true})
			.fill('Entry C');

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await openObjectEntry(objectDefinition1.className, 'Entry A');
		await openRelationshipTab();

		await expect(
			page.getByRole('row').filter({hasText: 'Entry C'}).first()
		).toBeVisible();
	}
);

test(
	'LPS-163654 Can identify Parent and Child relationship through labels',
	{tag: '@LPS-163654'},
	async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectRelationshipsPage,
		page,
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

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await objectRelationshipsPage.addObjectRelationshipButton.click();

		const objectRelationship =
			await addNewObjectRelationshipModalPage.handleForm({
				manyRecordsOf: objectDefinition.label['en_US'],
				objectRelationshipLabel: 'Relationship',
				type: 'Many to Many',
			});

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await waitForAlert(
			page,
			'Success:Relationship was created successfully.'
		);

		await expect(page.getByText('Parent')).toBeVisible();
		await expect(page.getByText('Child')).toBeVisible();
	}
);

test(
	'LPS-152508 Cannot delete Many-to-Many relationship from child side',
	{tag: '@LPS-152508'},
	async ({apiHelpers, objectRelationshipsPage, page}) => {
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

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_USER',
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1: 'L_USER',
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await objectRelationshipsPage.actionsButton.first().click();

		await objectRelationshipsPage.deleteObjectRelationshipOption.click();

		await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
		await expect(
			page.getByText(
				'You do not have permission to delete this relationship.'
			)
		).toBeVisible();
		await expect(
			page.getByText(
				'You cannot delete a relationship from here. To delete the relationship, you must delete it from the parent object definition.'
			)
		).toBeVisible();
	}
);

test(
	'LPS-152508 Cannot delete relationship with incorrect input',
	{tag: '@LPS-152508'},
	async ({apiHelpers, objectRelationshipsPage, page}) => {
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

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await objectRelationshipsPage.actionsButton.first().click();

		await objectRelationshipsPage.deleteObjectRelationshipOption.click();

		const deleteRelationshipDialog = page.getByRole('dialog', {
			name: 'Delete Relationship',
		});

		const confirmRelationshipNameInput =
			deleteRelationshipDialog.getByPlaceholder(
				'Confirm Relationship Name'
			);
		const incorrectRelationshipName = `${objectRelationshipName}-wrong`;

		await expect(confirmRelationshipNameInput).toBeVisible();
		await confirmRelationshipNameInput.fill(incorrectRelationshipName);

		await expect(
			deleteRelationshipDialog.getByText(
				'Input and relationship name do not match.'
			)
		).toBeVisible();
		await expect(
			deleteRelationshipDialog.getByRole('button', {
				exact: true,
				name: 'Delete',
			})
		).toBeDisabled();
	}
);

test(
	'LPS-163658 Cannot relate an entry with itself',
	{tag: '@LPS-163658'},
	async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectLayoutsPage,
		objectRelationshipsPage,
		page,
		viewObjectEntriesPage,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const textFieldName = objectFields[0].name;

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
				titleObjectFieldName: textFieldName,
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await objectRelationshipsPage.addObjectRelationshipButton.click();

		const objectRelationship =
			await addNewObjectRelationshipModalPage.handleForm({
				manyRecordsOf: objectDefinition.label['en_US'],
				objectRelationshipLabel: 'Relationship',
				type: 'Many to Many',
			});

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectLayoutsPage.goto(objectDefinition.label['en_US']);

		const layoutName = 'Layout' + getRandomInt();

		await objectLayoutsPage.createObjectLayout(layoutName);

		await objectLayoutsPage.createObjectLayoutContent({
			objectFieldNames: [objectFields[0].label['en_US']],
			objectLayoutName: layoutName,
			objectLayoutRegularBlockName: 'Block 1',
			objectLayoutTabName: 'Field Tab',
		});

		await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);
		await objectLayoutsPage.setObjectLayoutAsDefault();
		await objectLayoutsPage.layoutTab.click();

		await objectLayoutsPage.addRelationshipTab(
			'Relationship Tab',
			'Relationship'
		);

		const saveButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'})
			.first();

		await expect(saveButton).toBeVisible();
		await saveButton.dispatchEvent('click');
		await waitForAlert(
			page,
			'Success:The object layout was updated successfully'
		);

		const restPath = `c/${objectDefinition.name.toLowerCase()}s`;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry Test'},
			restPath
		);

		const openEntryRelationshipTab = async (entryLabel: string) => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			const entryRow = page.getByRole('row', {
				name: new RegExp(entryLabel),
			});

			await expect(entryRow).toBeVisible();

			await entryRow.getByRole('button', {name: 'Actions'}).click();
			await page.getByRole('menuitem', {name: 'View'}).click();

			await page.waitForLoadState('domcontentloaded');

			const relationshipTab = page.getByRole('link', {
				exact: true,
				name: 'Relationship Tab',
			});

			await expect(relationshipTab).toBeVisible();
			await relationshipTab.click();
		};

		await openEntryRelationshipTab('Entry Test');

		const selectExistingOneButton = page
			.getByRole('button', {name: 'Select Existing One'})
			.first();

		try {
			await expect(selectExistingOneButton).toBeVisible({
				timeout: 3000,
			});
			await selectExistingOneButton.click();
		}
		catch {
			await page.getByRole('button', {name: 'New'}).first().click();
			await page
				.getByRole('menuitem', {name: 'Select Existing One'})
				.click();
		}

		const selectFrame = page.frameLocator('iframe[title="Select"]');

		await expect(
			selectFrame.getByText('Entry Test', {exact: true})
		).toBeHidden();
	}
);

test(
	'LPS-139803 Cannot select Relationship field for Object Entry Title',
	{tag: '@LPS-139803'},
	async ({apiHelpers, editObjectDetailsPage, page}) => {
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

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await editObjectDetailsPage.goto(objectDefinition.label['en_US']);

		await editObjectDetailsPage.goToDetailsTab();

		await editObjectDetailsPage.entryTitleField.click();

		const titleFieldOptions = page.getByRole('listbox');

		await expect(
			titleFieldOptions
				.getByRole('option')
				.filter({hasText: /^Relationship\b/})
		).toHaveCount(0);
	}
);

test(
	'LPS-163655 Cannot update Name, Type or Object from parent Relationship',
	{tag: '@LPS-163655'},
	async ({apiHelpers, objectRelationshipsPage, page}) => {
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

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await page
			.getByRole('link', {exact: true, name: 'Relationship'})
			.first()
			.click();

		const iframe = page.frameLocator('iframe');

		await expect(iframe.getByLabel('NameMandatory')).toBeDisabled();

		const objectDefinitionInputs = iframe.locator(
			`input[value="${objectDefinition.label['en_US']}"]`
		);
		const objectDefinitionSelect = iframe.getByPlaceholder(
			'Search for an object definition'
		);

		await expect(objectDefinitionInputs).toHaveCount(2);
		await expect(objectDefinitionSelect).toBeDisabled();
		await expect(objectDefinitionSelect).toHaveValue(
			objectDefinition.label['en_US']
		);

		const typeField = iframe.getByRole('combobox', {
			exact: true,
			name: 'Type Mandatory',
		});

		await expect(typeField).toBeDisabled();
		await expect(typeField).toHaveText('Many to Many');
	}
);

test(
	'LPS-146754 Can relate Many-to-Many Custom Object entry with System Object entries',
	{tag: '@LPS-146754'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
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

		const userAccount1 =
			await apiHelpers.headlessAdminUser.postUserAccount();

		const userAccount2 =
			await apiHelpers.headlessAdminUser.postUserAccount();

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2: 'L_USER',
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionName2: 'User',
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		const textFieldName = objectFields[0].name;

		await objectLayoutsPage.goto(objectDefinition.label['en_US']);

		const layoutName = 'Layout' + getRandomInt();

		await objectLayoutsPage.createObjectLayout(layoutName);

		await objectLayoutsPage.createObjectLayoutContent({
			objectFieldNames: [objectFields[0].label['en_US']],
			objectLayoutName: layoutName,
			objectLayoutRegularBlockName: 'Block 1',
			objectLayoutTabName: 'Field Tab',
		});

		await objectLayoutsPage.addRelationshipTab(
			'Relationship Tab',
			'Relationship'
		);
		await objectLayoutsPage.setObjectLayoutAsDefault();

		const saveButton = page
			.frameLocator('iframe')
			.getByRole('button', {name: 'Save'})
			.first();

		await expect(saveButton).toBeVisible();
		await saveButton.click();
		await page.waitForLoadState('domcontentloaded');

		const restPath = `c/${objectDefinition.name.toLowerCase()}s`;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry A'},
			restPath
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry B'},
			restPath
		);

		const openEntryRelationshipTab = async (entryLabel: string) => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			const entryRow = page.getByRole('row', {
				name: new RegExp(entryLabel),
			});

			await expect(entryRow).toBeVisible();

			await entryRow.getByRole('button', {name: 'Actions'}).click();
			await page.getByRole('menuitem', {name: 'View'}).click();

			await page.waitForLoadState('domcontentloaded');

			const relationshipTab = page.getByRole('link', {
				exact: true,
				name: 'Relationship Tab',
			});

			await expect(relationshipTab).toBeVisible();
			await relationshipTab.click();
		};

		const selectExistingRelationshipEntry = async (
			userAccount: TUserAccount
		) => {
			const addRelationshipButton = page
				.getByLabel('Add Relationship')
				.first();
			const selectExistingOneButton = page
				.getByLabel('Select Existing One')
				.first();

			try {
				await expect(addRelationshipButton).toBeVisible({
					timeout: 3000,
				});
				await addRelationshipButton.click();
			}
			catch {
				try {
					await expect(selectExistingOneButton).toBeVisible({
						timeout: 3000,
					});
					await selectExistingOneButton.click();
				}
				catch {
					await page
						.getByRole('button', {name: 'New'})
						.first()
						.click();
					await page
						.getByRole('menuitem', {name: 'Select Existing One'})
						.click();
				}
			}

			const relationshipEntry = page
				.frameLocator('iframe[title="Select"]')
				.getByText(String(userAccount.id), {exact: true})
				.first();

			await expect(relationshipEntry).toBeVisible();
			await relationshipEntry.click();
			await page.waitForTimeout(1000);
		};

		const getRelatedUserRow = (userAccount: TUserAccount) =>
			page
				.getByRole('row')
				.filter({
					hasText: new RegExp(
						`${userAccount.givenName}|${userAccount.id}`,
						'i'
					),
				})
				.first();

		for (const entryLabel of ['Entry A', 'Entry B']) {
			await openEntryRelationshipTab(entryLabel);

			await selectExistingRelationshipEntry(userAccount1);

			await page.waitForTimeout(1000);

			await openEntryRelationshipTab(entryLabel);

			await expect(getRelatedUserRow(userAccount1)).toBeVisible();

			await selectExistingRelationshipEntry(userAccount2);

			await page.waitForTimeout(1000);

			await openEntryRelationshipTab(entryLabel);

			await expect(getRelatedUserRow(userAccount1)).toBeVisible();
			await expect(getRelatedUserRow(userAccount2)).toBeVisible();
		}
	}
);

test(
	'LPS-145393 Can relate One-to-Many System Object with Custom site scoped Object',
	{tag: '@LPS-145393'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				scope: 'site',
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const userAccount =
			await apiHelpers.headlessAdminUser.postUserAccount();

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				'L_USER',
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1: 'L_USER',
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

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await viewObjectEntriesPage.selectDropdownItemWithSearch(
			userAccount.givenName!
		);

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await expect(viewObjectEntriesPage.successMessage).toBeVisible();

		expect(
			(await page.getByPlaceholder('Search').inputValue()).toLowerCase()
		).toBe(userAccount.givenName!.toLowerCase());
	}
);

test('Can see label when editing Relationship', async ({
	apiHelpers,
	objectRelationshipsPage,
	page,
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

	const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
		ObjectRelationshipAPI
	);

	const objectRelationshipName =
		'objectRelationshipName' + Math.floor(Math.random() * 99);

	const {body: objectRelationship} =
		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			objectDefinition.externalReferenceCode,
			{
				label: {en_US: 'Relationship'},
				name: objectRelationshipName,
				objectDefinitionExternalReferenceCode1:
					objectDefinition.externalReferenceCode,
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId1: objectDefinition.id,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'manyToMany',
			}
		);

	apiHelpers.data.push({
		id: objectRelationship.id,
		type: 'objectRelationship',
	});

	await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

	await expect(page.getByText('Parent')).toBeVisible();
	await expect(page.getByText('Child')).toBeVisible();

	await page
		.getByRole('row')
		.filter({hasText: 'Parent'})
		.getByRole('link', {exact: true, name: 'Relationship'})
		.click();

	const iframe = page.frameLocator('iframe');

	await expect(iframe.getByText('Parent')).toBeVisible();

	await objectRelationshipsPage.cancelButton.click();

	await page
		.getByRole('row')
		.filter({hasText: 'Child'})
		.getByRole('link', {exact: true, name: 'Relationship'})
		.click();

	await expect(iframe.getByText('Child')).toBeVisible();
});

test(
	'LPS-158478 Can see related entries on Relationship tab',
	{tag: '@LPS-158478'},
	async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const textFieldName = objectFields[0].name;

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
				titleObjectFieldName: textFieldName,
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition.externalReferenceCode,
				{
					label: {en_US: 'Relationship'},
					name: objectRelationshipName,
					objectDefinitionExternalReferenceCode1:
						objectDefinition.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition.externalReferenceCode,
					objectDefinitionId1: objectDefinition.id,
					objectDefinitionId2: objectDefinition.id,
					objectDefinitionName2: objectDefinition.name,
					type: 'manyToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await objectLayoutsPage.goto(objectDefinition.label['en_US']);

		const layoutName = 'Layout' + getRandomInt();

		await objectLayoutsPage.createObjectLayout(layoutName);

		await objectLayoutsPage.createObjectLayoutContent({
			objectFieldNames: [objectFields[0].label['en_US']],
			objectLayoutName: layoutName,
			objectLayoutRegularBlockName: 'Block 1',
			objectLayoutTabName: 'Field Tab',
		});

		await objectLayoutsPage.addRelationshipTab(
			'Relationship Tab',
			'Relationship'
		);
		await objectLayoutsPage.setObjectLayoutAsDefault();

		const saveButton = objectLayoutsPage.iframeLocator
			.getByRole('button', {name: 'Save'})
			.first();

		await expect(saveButton).toBeVisible();
		await saveButton.dispatchEvent('click');

		const restPath = `c/${objectDefinition.name.toLowerCase()}s`;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry Test A'},
			restPath
		);

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry Test B'},
			restPath
		);

		const openEntryRelationshipTab = async (entryLabel: string) => {
			await viewObjectEntriesPage.goto(objectDefinition.className);

			const entryRow = page.getByRole('row', {
				name: new RegExp(entryLabel),
			});

			await expect(entryRow).toBeVisible();

			await entryRow.getByRole('button', {name: 'Actions'}).click();
			await page.getByRole('menuitem', {name: 'View'}).click();

			await page.waitForLoadState('domcontentloaded');

			const relationshipTab = page.getByRole('link', {
				exact: true,
				name: 'Relationship Tab',
			});

			await expect(relationshipTab).toBeVisible();
			await relationshipTab.click();
		};

		await openEntryRelationshipTab('Entry Test A');

		const selectExistingOneButton = page
			.getByRole('button', {name: 'Select Existing One'})
			.first();

		try {
			await expect(selectExistingOneButton).toBeVisible({
				timeout: 3000,
			});
			await selectExistingOneButton.click();
		}
		catch {
			await page.getByRole('button', {name: 'New'}).first().click();
			await page
				.getByRole('menuitem', {name: 'Select Existing One'})
				.click();
		}

		const relationshipEntry = page
			.frameLocator('iframe[title="Select"]')
			.getByText('Entry Test B', {exact: true})
			.first();

		await expect(relationshipEntry).toBeVisible();
		await relationshipEntry.click();

		await page.waitForTimeout(1000);
		await openEntryRelationshipTab('Entry Test A');

		await expect(
			page.getByRole('row').filter({hasText: 'Entry Test B'}).first()
		).toBeVisible();
	}
);

test(
	'LPS-193697 Can switch relationship order between parent and child',
	{tag: '@LPS-193697'},
	async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectRelationshipsPage,
		page,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition1.id,
			type: 'objectDefinition',
		});

		apiHelpers.data.push({
			id: objectDefinition2.id,
			type: 'objectDefinition',
		});

		await objectRelationshipsPage.goto(objectDefinition1.label['en_US']);

		await objectRelationshipsPage.addObjectRelationshipButton.click();

		await expect(
			addNewObjectRelationshipModalPage.modalHeader
		).toBeVisible();

		await addNewObjectRelationshipModalPage.objectRelationshipFormPage.labelInput.fill(
			'Relationship'
		);

		await addNewObjectRelationshipModalPage.objectRelationshipFormPage.selectType(
			'One to Many'
		);

		await addNewObjectRelationshipModalPage.objectRelationshipFormPage.selectManyRecordsOf(
			objectDefinition2.label['en_US']
		);

		await addNewObjectRelationshipModalPage.objectRelationshipFormPage.reverseOrderButton.click();

		const modalContent = page.locator('.modal-content');

		await expect(modalContent.getByLabel('One Record Of')).toHaveValue(
			objectDefinition2.label['en_US']
		);

		await expect(modalContent.getByLabel('Many Records Of')).toHaveValue(
			objectDefinition1.label['en_US']
		);

		const objectRelationship =
			await addNewObjectRelationshipModalPage.objectRelationshipFormPage.saveButton
				.click()
				.then(async () => {
					const response = await page.waitForResponse(
						'**/object-relationships'
					);

					return response.json();
				});

		if (objectRelationship?.id) {
			apiHelpers.data.push({
				id: objectRelationship.id,
				type: 'objectRelationship',
			});
		}
	}
);

test(
	'LPS-143021 Can view and add Object entries after creating a Relationship',
	{tag: '@LPS-143021'},
	async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectRelationshipsPage,
		page,
		viewObjectEntriesPage,
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

		const textFieldName = objectFields[0].name;
		const restPath = `c/${objectDefinition.name.toLowerCase()}s`;

		await apiHelpers.objectEntry.postObjectEntry(
			{[textFieldName]: 'Entry Test'},
			restPath
		);

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await objectRelationshipsPage.addObjectRelationshipButton.click();

		const objectRelationship =
			await addNewObjectRelationshipModalPage.handleForm({
				manyRecordsOf: objectDefinition.label['en_US'],
				objectRelationshipLabel: 'Relationship',
				type: 'One to Many',
			});

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByRole('row').filter({hasText: 'Entry Test'}).first()
		).toBeVisible();
	}
);

test(
	'LPS-193697 Switch button is not present when M:M relationship is selected',
	{tag: '@LPS-193697'},
	async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectRelationshipsPage,
		page: _page,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectRelationshipsPage.goto(objectDefinition.label['en_US']);

		await objectRelationshipsPage.addObjectRelationshipButton.click();

		await expect(
			addNewObjectRelationshipModalPage.modalHeader
		).toBeVisible();

		await addNewObjectRelationshipModalPage.objectRelationshipFormPage.selectType(
			'Many to Many'
		);

		await addNewObjectRelationshipModalPage.objectRelationshipFormPage.selectManyRecordsOf(
			objectDefinition.label['en_US']
		);

		await expect(
			addNewObjectRelationshipModalPage.objectRelationshipFormPage
				.reverseOrderButton
		).toBeHidden();
	}
);
