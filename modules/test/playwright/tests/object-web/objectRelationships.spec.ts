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

	test('can create multiple object relationships between the same objects', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderObjectDefinitionNodePage,
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

		await modelBuilderDiagramPage.connectObjectDefinitionsNodeHandles(
			objectDefinition1.id,
			objectDefinition2.id
		);

		const objectRelationship1Label = 'objectRelationship' + getRandomInt();

		await modelBuilderObjectDefinitionNodePage.handleObjectRelationshipModal(
			{
				objectRelationshipLabel: objectRelationship1Label,
				type: 'One to Many',
			}
		);

		await modelBuilderDiagramPage.connectObjectDefinitionsNodeHandles(
			objectDefinition2.id,
			objectDefinition1.id,
			['top', 'bottom']
		);

		const objectRelationship2Label = 'objectRelationship' + getRandomInt();

		await modelBuilderObjectDefinitionNodePage.handleObjectRelationshipModal(
			{
				objectRelationshipLabel: objectRelationship2Label,
				type: 'One to Many',
			}
		);

		await page.waitForTimeout(500);

		await modelBuilderDiagramPage.clickObjectRelationshipEdge('2');

		expect(
			page.getByRole('menuitem', {name: objectRelationship1Label})
		).toBeVisible();

		expect(
			page.getByRole('menuitem', {name: objectRelationship2Label})
		).toBeVisible();
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

	test('can create relationship between definitions from different folders', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderObjectDefinitionNodePage,
		page,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		createdEntities.objectFolderIds.push(objectFolder.id);

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
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

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderDiagramPage.toggleSidebarsButton.click();

		await modelBuilderDiagramPage.fitViewButton.click();

		const objectRelationship =
			await modelBuilderObjectDefinitionNodePage.createObjectRelationship(
				{
					manyRecordsOf: objectDefinition2.label['en_US'],
					objectDefinitionLabel: objectDefinition1.label['en_US'],
					objectDefinitionNodes:
						modelBuilderDiagramPage.objectDefinitionNodes,
					objectRelationshipLabel:
						'objectRelationship' + getRandomInt(),
					objectRelationshipType: 'One to Many',
				}
			);

		createdEntities.objectRelationshipIds.push(objectRelationship.id);

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationship.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({
					hasText: objectDefinition2.label['en_US'],
				})
				.locator('.lfr-objects__model-builder-node-container')
		).toHaveClass(/link/);

		await modelBuilderDiagramPage.toggleSidebarsButton.click();

		await modelBuilderLeftSidebarPage.collapseOtherFoldersButton.click();

		await expect(
			page
				.getByRole('group', {name: 'Default'})
				.getByLabel(objectDefinition2.label['en_US'])
		).toBeVisible();
	});

	test('can create relationship by using add relationship button', async ({
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

		const objectRelationshipLabel = 'objectRelationship' + getRandomInt();

		const objectRelationship =
			await modelBuilderObjectDefinitionNodePage.createObjectRelationship(
				{
					manyRecordsOf: objectDefinition1.name,
					objectDefinitionLabel: objectDefinition2.label['en_US'],
					objectDefinitionNodes:
						modelBuilderDiagramPage.objectDefinitionNodes,
					objectRelationshipLabel,
					objectRelationshipType: 'One to Many',
				}
			);

		createdEntities.objectRelationshipIds.push(objectRelationship.id);

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).toBeVisible();
	});

	test('can create object relationship to linked object definition by drag and drop', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderObjectDefinitionNodePage,
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

		const objectDefinition3 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		createdEntities.objectDefinitionIds.push(
			objectDefinition1.id,
			objectDefinition2.id,
			objectDefinition3.id
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

		await modelBuilderDiagramPage.toggleSidebarsButton.click();

		await modelBuilderDiagramPage.fitViewButton.click();

		await modelBuilderDiagramPage.connectObjectDefinitionsNodeHandles(
			objectDefinition3.id,
			objectDefinition2.id
		);

		const objectRelationshipLabel2 = 'objectRelationship' + getRandomInt();

		const objectRelationship2 =
			await modelBuilderObjectDefinitionNodePage.handleObjectRelationshipModal(
				{
					objectRelationshipLabel: objectRelationshipLabel2,
					type: 'One to Many',
				}
			);

		createdEntities.objectRelationshipIds.push(objectRelationship2.id);

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel2,
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
				.getByText(objectRelationshipLabel2)
		).toBeVisible();
	});

	test('can create two self object relationship', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		createdEntities.objectFolderIds.push(objectFolder.id);

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode:
					objectFolder.externalReferenceCode,
				status: {code: 0},
			});

		createdEntities.objectDefinitionIds.push(objectDefinition.id);

		const objectRelationshipDetails: {
			label: string;
			type: ObjectRelationshipType;
		}[] = [
			{
				label: 'objectRelationshipLabel' + getRandomInt(),
				type: 'oneToMany',
			},
			{
				label: 'objectRelationshipLabel' + getRandomInt(),
				type: 'manyToMany',
			},
		];

		for (const {label, type} of objectRelationshipDetails) {
			const objectRelationshipName =
				'objectRelationshipName' + Math.floor(Math.random() * 99);
			const objectRelationshipData: Partial<ObjectRelationship> = {
				label: {
					en_US: label,
				},
				name: objectRelationshipName,
				objectDefinitionExternalReferenceCode1:
					objectDefinition.externalReferenceCode,
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId1: objectDefinition.id,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type,
			};
			await apiHelpers.objectAdmin.postObjectRelationship(
				objectRelationshipData
			);
			createdEntities.objectRelationshipIds.push(
				objectRelationshipData.id
			);
		}

		await viewObjectDefinitionsPage.goto();

		await viewObjectDefinitionsPage.openObjectFolder(
			objectFolder.label['en_US']
		);

		await viewObjectDefinitionsPage.viewInModelBuilderButton.click();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();

		await page
			.locator('svg')
			.filter({hasText: '2'})
			.locator('rect')
			.click();

		for (const {label, type} of objectRelationshipDetails) {
			await expect(
				page.getByRole('menuitem', {
					name: label,
				})
			).toBeVisible();
			await expect(
				page.getByRole('menuitem', {
					name: type,
				})
			).toBeVisible();
		}
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
