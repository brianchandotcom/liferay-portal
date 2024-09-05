/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import {createObjectField} from './utils/mockObjectFields';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);

const createdEntities = {
	objectDefinitionIds: [],
	objectFolderIds: [],
	objectRelationshipIds: [],
} as {
	objectDefinitionIds: number[];
	objectFolderIds: number[];
	objectRelationshipIds: number[];
};

test.afterEach(async ({apiHelpers}) => {
	for (const id of createdEntities.objectRelationshipIds) {
		await apiHelpers.objectAdmin.deleteObjectRelationship(id);
	}

	for (const id of createdEntities.objectDefinitionIds) {
		await apiHelpers.objectAdmin.deleteObjectDefinition(id);
	}

	for (const id of createdEntities.objectFolderIds) {
		await apiHelpers.objectAdmin.deleteObjectFolder(id);
	}
});

test.describe('Manage object relationships through Model Builder', () => {
	test.beforeEach(({page}) => {
		page.setViewportSize({height: 1080, width: 1920});
	});

	test('can create one to many relationship with object field by dragging node handles', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderObjectDefinitionNodePage,
		viewObjectDefinitionsPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		createdEntities.objectFolderIds.push(objectFolder.id);

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});
		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		createdEntities.objectDefinitionIds.push(
			objectDefinition1.id,
			objectDefinition2.id
		);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder(
			objectFolder.label['en_US']
		);

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		await modelBuilderDiagramPage.toggleSidebarsButton.click();

		await modelBuilderDiagramPage.fitViewButton.click();

		await modelBuilderDiagramPage.connectObjectDefinitionsNodeHandles(
			objectDefinition1.id,
			objectDefinition2.id
		);

		const objectRelationshipLabel = 'objectRelationship' + getRandomInt();

		const objectRelationship =
			await modelBuilderObjectDefinitionNodePage.handleObjectRelationshipModal(
				{
					objectRelationshipLabel,
					type: 'One to Many',
				}
			);

		createdEntities.objectRelationshipIds.push(objectRelationship.id);

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).toBeVisible();

		await modelBuilderObjectDefinitionNodePage.clickShowAllFieldsButton(
			objectDefinition2.label['en_US'],
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		await modelBuilderDiagramPage.fitViewButton.click();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({hasText: objectDefinition2.label['en_US']})
				.getByText(objectRelationshipLabel)
		).toBeVisible();
	});

	test('can delete object relationship from different folders', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderRightSidebarPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		await page.goto('/');

		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		createdEntities.objectFolderIds.push(objectFolder.id);

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		createdEntities.objectDefinitionIds.push(
			objectDefinition1.id,
			objectDefinition2.id
		);

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const objectRelationshipData: Partial<ObjectRelationship> = {
			label: {
				en_US: objectRelationshipLabel,
			},
			name: objectRelationshipName,
			objectDefinitionExternalReferenceCode1:
				objectDefinition1.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition2.externalReferenceCode,
			objectDefinitionId1: objectDefinition1.id,
			objectDefinitionId2: objectDefinition2.id,
			objectDefinitionName2: objectDefinition2.name,
			type: 'oneToMany' as ObjectRelationshipType,
		};

		await apiHelpers.objectAdmin.postObjectRelationship(
			objectRelationshipData
		);

		createdEntities.objectRelationshipIds.push(objectRelationshipData.id);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder(
			objectFolder.label['en_US']
		);

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		await modelBuilderDiagramPage.clickObjectRelationshipEdge(
			objectRelationshipLabel
		);

		await modelBuilderRightSidebarPage.deleteObjectRelationship(
			objectRelationshipName
		);

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).not.toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).not.toBeVisible();
	});

	test('can edit object relationship details in Right Sidebar', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderRightSidebarPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});
		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		createdEntities.objectDefinitionIds.push(
			objectDefinition1.id,
			objectDefinition2.id
		);

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const objectRelationshipData: Partial<ObjectRelationship> = {
			label: {
				en_US: objectRelationshipLabel,
			},
			name: objectRelationshipName,
			objectDefinitionExternalReferenceCode1:
				objectDefinition1.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition2.externalReferenceCode,
			objectDefinitionId1: objectDefinition1.id,
			objectDefinitionId2: objectDefinition2.id,
			objectDefinitionName2: objectDefinition2.name,
			type: 'oneToMany' as ObjectRelationshipType,
		};

		await apiHelpers.objectAdmin.postObjectRelationship(
			objectRelationshipData
		);

		createdEntities.objectRelationshipIds.push(objectRelationshipData.id);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder(
			objectFolder.label['en_US']
		);

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		await modelBuilderDiagramPage.clickObjectRelationshipEdge(
			objectRelationshipLabel
		);

		await modelBuilderRightSidebarPage.sidebarLabelInput.fill('Value Test');

		await modelBuilderRightSidebarPage.objectRelationshipDeletionType.click();
		await page.getByRole('option', {name: 'Cascade'}).click();

		await modelBuilderLeftSidebarPage.sidebarItems
			.filter({hasText: objectDefinition1.label['en_US']})
			.click();

		await modelBuilderDiagramPage.clickObjectRelationshipEdge('Value Test');

		await expect(
			modelBuilderRightSidebarPage.sidebarLabelInput
		).toHaveValue('Value Test');
		await expect(
			modelBuilderRightSidebarPage.objectRelationshipDeletionType
		).toContainText('Cascade');
	});

	test('cannot create relationship between the postal address object and objects without an one-to-many relationship with the account object', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		const postalAddress =
			await apiHelpers.objectAdmin.getObjectDefinitionByExternalReferenceCode(
				'L_POSTAL_ADDRESS'
			);

		createdEntities.objectDefinitionIds.push(objectDefinition1.id);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder('Default');

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		await modelBuilderDiagramPage.toggleSidebarsButton.click();

		await modelBuilderDiagramPage.fitViewButton.click();

		await modelBuilderDiagramPage.connectObjectDefinitionsNodeHandles(
			postalAddress.id,
			objectDefinition1.id
		);

		await expect(
			modelBuilderDiagramPage.postalAddressObjectRelationshipWarning
		).toBeVisible();

		const pagePromise = page.waitForEvent('popup');

		await page.getByRole('link', {name: 'Learn more.'}).click();

		const liferayLearnPage = await pagePromise;

		await liferayLearnPage.waitForLoadState();

		await expect(
			liferayLearnPage.getByRole('heading', {
				name: 'Accessing Accounts Data from Custom Object',
			})
		).toBeVisible();
	});

	test('cannot delete the object relationship that is the only custom object field from the published object definition', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderRightSidebarPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		createdEntities.objectFolderIds.push(objectFolder.id);

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [],
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 1},
			});

		createdEntities.objectDefinitionIds.push(
			objectDefinition1.id,
			objectDefinition2.id
		);

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const objectRelationshipData: Partial<ObjectRelationship> = {
			label: {
				en_US: objectRelationshipLabel,
			},
			name: objectRelationshipName,
			objectDefinitionExternalReferenceCode1:
				objectDefinition1.externalReferenceCode,
			objectDefinitionExternalReferenceCode2:
				objectDefinition2.externalReferenceCode,
			objectDefinitionId1: objectDefinition1.id,
			objectDefinitionId2: objectDefinition2.id,
			objectDefinitionName2: objectDefinition2.name,
			type: 'oneToMany' as ObjectRelationshipType,
		};

		const objectRelationship =
			await apiHelpers.objectAdmin.postObjectRelationship(
				objectRelationshipData
			);

		createdEntities.objectRelationshipIds.push(objectRelationship.id);

		const publishedObjectDefinition2 =
			await apiHelpers.objectAdmin.postObjectDefinitionPublish(
				objectDefinition2.id
			);

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder(
			objectFolder.label['en_US']
		);

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		await modelBuilderDiagramPage.fitViewButton.click();

		await modelBuilderDiagramPage.clickObjectRelationshipEdge(
			objectRelationshipLabel
		);

		await modelBuilderRightSidebarPage.deleteObjectRelationship(
			objectRelationshipData.name
		);

		await expect(modelBuilderDiagramPage.deletionNotAllowed).toBeVisible();

		const objectFieldObjectRelationship =
			publishedObjectDefinition2.objectFields.find(
				(objectField: ObjectField) =>
					objectField.businessType === 'Relationship'
			);

		await expect(
			page.getByText(
				`The object field "${objectFieldObjectRelationship.name}" cannot be deleted because it is the only custom object field of the published object definition "${objectDefinition2.name}". Add at least one object field to the object definition.`
			)
		).toBeVisible();

		await apiHelpers.objectAdmin.postObjectFieldByExternalReferenceCode(
			publishedObjectDefinition2.externalReferenceCode,
			createObjectField('text', {
				label: 'textField',
				name: 'textField',
			})
		);
	});
});
