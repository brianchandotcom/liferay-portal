/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectRelationship,
	ObjectRelationshipApi,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';
import {pushToApiHelpersData} from '../../utils/pushToApiHelpersData';

export const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPD-34594': {enabled: true},
	}),
	loginTest(),
	objectPagesTest
);

test.describe('Manage root models elements through Objects Admin', () => {
	test('cannot delete an object definition with inheritance enabled on its relationship', async ({
		apiHelpers,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectRelationships: ObjectRelationship[] = [];

		try {
			const objectDefinition1 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFolderExternalReferenceCode: 'default',
					status: {code: 0},
				});

			const objectDefinition2 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFolderExternalReferenceCode: 'default',
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

			const objectRelationshipLabel =
				'objectRelationshipLabel' + getRandomInt();
			const objectRelationshipName =
				'objectRelationshipName' + Math.floor(Math.random() * 99);

			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			const {body: objectRelationship} =
				await objectRelationshipApiClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
					objectDefinition1.externalReferenceCode,
					{
						edge: true,
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
						type: ObjectRelationship.TypeEnum.OneToMany,
					}
				);

			objectRelationships.push(objectRelationship);

			apiHelpers.data.push({
				id: objectRelationship.id,
				type: 'objectRelationship',
			});

			await viewObjectDefinitionsPage.goto();

			await viewObjectDefinitionsPage.actionsButton.first().waitFor();

			await viewObjectDefinitionsPage.actionsButton.last().click();

			await viewObjectDefinitionsPage.deleteObjectDefinitionOption.click();

			await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
			await expect(
				page.getByText(
					'To delete this object, you must first disable inheritance and delete its relationships.'
				)
			).toBeVisible();

			await page.getByRole('button', {name: 'Done'}).click();
		}
		finally {
			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			for (const objectRelationship of objectRelationships) {
				await objectRelationshipApiClient.putObjectRelationship(
					objectRelationship.id,
					{
						...objectRelationship,
						edge: false,
					}
				);
			}
		}
	});

	test('cannot delete an object relationship with inheritance enabled', async ({
		apiHelpers,
		objectRelationshipsPage,
		page,
	}) => {
		const objectRelationships: ObjectRelationship[] = [];

		try {
			const objectDefinition1 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFolderExternalReferenceCode: 'default',
					status: {code: 0},
				});

			const objectDefinition2 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFolderExternalReferenceCode: 'default',
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

			const objectRelationshipLabel =
				'objectRelationshipLabel' + getRandomInt();
			const objectRelationshipName =
				'objectRelationshipName' + Math.floor(Math.random() * 99);

			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			const {body: objectRelationship} =
				await objectRelationshipApiClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
					objectDefinition1.externalReferenceCode,
					{
						edge: true,
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
						type: ObjectRelationship.TypeEnum.OneToMany,
					}
				);

			objectRelationships.push(objectRelationship);

			apiHelpers.data.push({
				id: objectRelationship.id,
				type: 'objectRelationship',
			});

			await objectRelationshipsPage.goto(
				objectDefinition1.label['en_US']
			);

			await objectRelationshipsPage.actionsButton.click();

			await objectRelationshipsPage.deleteObjectRelationshipOption.click();

			await expect(page.getByText('Deletion Not Allowed')).toBeVisible();
			await page
				.getByText(
					'You cannot delete a relationship with inheritance enabled. Disable inheritance before deleting the relationship.'
				)
				.click();

			await page.getByRole('button', {name: 'Done'}).click();
		}
		finally {
			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			for (const objectRelationship of objectRelationships) {
				await objectRelationshipApiClient.putObjectRelationship(
					objectRelationship.id,
					{
						...objectRelationship,
						edge: false,
					}
				);
			}
		}
	});

	test('creating multiple inheritance relationships between the same objects should trigger a warning message', async ({
		apiHelpers,
		objectRelationshipsPage,
		page,
	}) => {
		const objectRelationships: ObjectRelationship[] = [];

		try {
			const objectDefinition1 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFolderExternalReferenceCode: 'default',
					status: {code: 1},
				});
			const objectDefinition2 =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFolderExternalReferenceCode: 'default',
					status: {code: 1},
				});

			apiHelpers.data.push({
				id: objectDefinition1.id,
				type: 'objectDefinition',
			});

			apiHelpers.data.push({
				id: objectDefinition2.id,
				type: 'objectDefinition',
			});

			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			const objectRelationshipLabel1 =
				'objectRelationshipLabel' + getRandomInt();

			const objectRelationshipName1 =
				'objectRelationshipName' + Math.floor(Math.random() * 99);

			const {body: objectRelationship1} =
				await objectRelationshipApiClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
					objectDefinition1.externalReferenceCode,
					{
						edge: true,
						label: {
							en_US: objectRelationshipLabel1,
						},
						name: objectRelationshipName1,
						objectDefinitionExternalReferenceCode1:
							objectDefinition1.externalReferenceCode,
						objectDefinitionExternalReferenceCode2:
							objectDefinition2.externalReferenceCode,
						objectDefinitionId1: objectDefinition1.id,
						objectDefinitionId2: objectDefinition2.id,
						objectDefinitionName2: objectDefinition2.name,
						type: ObjectRelationship.TypeEnum.OneToMany,
					}
				);

			objectRelationships.push(objectRelationship1);

			apiHelpers.data.push({
				id: objectRelationship1.id,
				type: 'objectRelationship',
			});

			const objectRelationshipLabel2 =
				'objectRelationshipLabel' + getRandomInt();

			const objectRelationshipName2 =
				'objectRelationshipName' + Math.floor(Math.random() * 99);

			const {body: objectRelationship2} =
				await objectRelationshipApiClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
					objectDefinition1.externalReferenceCode,
					{
						edge: false,
						label: {
							en_US: objectRelationshipLabel2,
						},
						name: objectRelationshipName2,
						objectDefinitionExternalReferenceCode1:
							objectDefinition1.externalReferenceCode,
						objectDefinitionExternalReferenceCode2:
							objectDefinition2.externalReferenceCode,
						objectDefinitionId1: objectDefinition1.id,
						objectDefinitionId2: objectDefinition2.id,
						objectDefinitionName2: objectDefinition2.name,
						type: ObjectRelationship.TypeEnum.OneToMany,
					}
				);

			objectRelationships.push(objectRelationship2);

			apiHelpers.data.push({
				id: objectRelationship2.id,
				type: 'objectRelationship',
			});

			// fail enabling inheritance in object relationship 2

			await objectRelationshipsPage.goto(
				objectDefinition1.label['en_US']
			);

			await page
				.getByRole('link', {name: objectRelationshipLabel2})
				.click();

			await objectRelationshipsPage.inheritanceCheckbox.check();

			await objectRelationshipsPage.saveObjectRelationshipButton.click();

			await expect(
				objectRelationshipsPage.inheritanceWarningMessage
			).toBeVisible();

			await objectRelationshipsPage.cancelButton.click();

			// remove inheritance from object relationship 1

			await page
				.getByRole('link', {name: objectRelationshipLabel1})
				.click();

			await objectRelationshipsPage.inheritanceCheckbox.click();

			await expect(
				objectRelationshipsPage.inheritanceModalHeader
			).toBeVisible();

			await objectRelationshipsPage.inheritanceModalDisableButton.click();

			// enable inheritance in object relationship 2

			await page
				.getByRole('link', {name: objectRelationshipLabel2})
				.click();

			await objectRelationshipsPage.inheritanceCheckbox.check();

			await objectRelationshipsPage.saveObjectRelationshipButton.click();

			await page.waitForLoadState('load');

			await page
				.getByRole('link', {name: objectRelationshipLabel2})
				.click();

			await expect(
				objectRelationshipsPage.inheritanceCheckbox
			).toBeChecked();
		}
		finally {
			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			for (const objectRelationship of objectRelationships) {
				await objectRelationshipApiClient.putObjectRelationship(
					objectRelationship.id,
					{
						...objectRelationship,
						edge: false,
						objectField: {
							...objectRelationship.objectField,
							required: true,
						},
					}
				);
			}
		}
	});

	test('can create relationship with inheritance enabled using the add relationship modal', async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		objectRelationshipsPage,
		page,
	}) => {
		const {objectRelationshipFormPage} = addNewObjectRelationshipModalPage;
		const objectRelationships: ObjectRelationship[] = [];

		try {
			const objectFolder =
				await apiHelpers.objectAdmin.postRandomObjectFolder();

			apiHelpers.data.push({id: objectFolder.id, type: 'objectFolder'});

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

			const siteScopedObjectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					objectFolderExternalReferenceCode:
						objectFolder.externalReferenceCode,
					scope: 'site',
					status: {code: 0},
				});

			pushToApiHelpersData(
				apiHelpers,
				[
					objectDefinition1.id,
					objectDefinition2.id,
					siteScopedObjectDefinition.id,
				],
				'objectDefinition'
			);

			await objectRelationshipsPage.goto(
				objectDefinition1.name,
				objectFolder.label['en_US']
			);

			// Add object relationship trough modal.

			await objectRelationshipsPage.addObjectRelationshipButton.click();

			const objectRelationshipLabel = 'Relationship' + getRandomInt();

			await objectRelationshipFormPage.labelInput.fill(
				objectRelationshipLabel
			);

			await objectRelationshipFormPage.selectType('One to Many');

			await objectRelationshipFormPage.selectManyRecordsOf(
				siteScopedObjectDefinition.name
			);

			await objectRelationshipFormPage.inheritanceCheckbox.check();

			// Check if info alert is displayed in the modal when the inheritance is enabled.

			await expect(page.getByText('Info:When enabled,')).toBeVisible();

			await objectRelationshipFormPage.saveButton.click();

			// Check if error alert is displayed in the modal.

			await expect(
				page.getByText('Error:Unable to bind the')
			).toBeVisible();

			await objectRelationshipFormPage.selectManyRecordsOf(
				objectDefinition2.name
			);

			await objectRelationshipFormPage.reverseOrderButton.click();

			const responsePromise = page.waitForResponse(
				`**/${objectDefinition2.externalReferenceCode}/object-relationships`
			);

			await objectRelationshipFormPage.saveButton.click();

			const response = await responsePromise;

			objectRelationships.push(await response.json());

			// Check if success toast is displayed after creating a relationship.

			await expect(
				page.getByText('Success:Relationship was')
			).toBeVisible();

			const viewRelationshipLink = page.getByRole('link', {
				name: 'View Relationship',
			});

			// Check if success toast includes a link to the other side of the relationship.

			await expect(viewRelationshipLink).toBeVisible();

			await viewRelationshipLink.click();

			// Check if the link works and the relationship was really created with inheritance enabled;

			await expect(
				page.locator('h3', {hasText: objectDefinition2.name})
			).toBeVisible();

			const cellClassSufix = 'relationshipInheritance';

			await expect(
				page
					.locator(`.dnd-td.cell-${cellClassSufix}`)
					.or(page.locator(`td.cell-${cellClassSufix}`))
			).toHaveText('Inherited');
		}
		finally {
			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			for (const objectRelationship of objectRelationships) {
				await objectRelationshipApiClient.putObjectRelationship(
					objectRelationship.id,
					{
						...objectRelationship,
						edge: false,
					}
				);
			}
		}
	});
});

test.describe('Manage root models elements through Model Builder', () => {
	test('assert inherited relationship styles on nodes and edges', async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderRightSidebarPage,
		objectRelationshipsPage,
		page,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		apiHelpers.data.push({id: objectFolder.id, type: 'objectFolder'});

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

		apiHelpers.data.push({
			id: objectDefinition1.id,
			type: 'objectDefinition',
		});
		apiHelpers.data.push({
			id: objectDefinition2.id,
			type: 'objectDefinition',
		});

		const objectRelationshipApiClient = await apiHelpers.buildRestClient(
			ObjectRelationshipApi
		);

		const {body: objectRelationship} =
			await objectRelationshipApiClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition1.externalReferenceCode,
				{
					edge: true,
					label: {
						en_US: 'objectRelationshipLabel' + getRandomInt(),
					},
					name:
						'objectRelationshipName' +
						Math.floor(Math.random() * 99),
					objectDefinitionExternalReferenceCode1:
						objectDefinition1.externalReferenceCode,
					objectDefinitionExternalReferenceCode2:
						objectDefinition2.externalReferenceCode,
					objectDefinitionId1: objectDefinition1.id,
					objectDefinitionId2: objectDefinition2.id,
					objectDefinitionName2: objectDefinition2.name,
					type: ObjectRelationship.TypeEnum.OneToMany,
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await modelBuilderDiagramPage.goto({
			objectFolderName: objectFolder.name,
		});

		await modelBuilderDiagramPage.clickObjectRelationshipEdge(
			objectRelationship.label['en_US']
		);

		await expect(
			modelBuilderRightSidebarPage.inheritanceCheckbox
		).toBeChecked();

		await expect(
			page
				.getByRole('button', {name: objectRelationship.label['en_US']})
				.getByRole('presentation')
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({
					hasText: objectDefinition1.label['en_US'],
				})
				.locator('.lfr-objects__model-builder-node-container')
		).toHaveClass(/treeItem/);

		await modelBuilderRightSidebarPage.inheritanceCheckbox.waitFor();

		await modelBuilderRightSidebarPage.inheritanceCheckbox.click();

		await expect(
			objectRelationshipsPage.inheritanceModalHeader
		).toBeVisible();

		await objectRelationshipsPage.inheritanceModalDisableButton.click();

		await modelBuilderRightSidebarPage.inheritanceCheckbox.waitFor();

		await expect(
			modelBuilderRightSidebarPage.inheritanceCheckbox
		).not.toBeChecked();

		await expect(
			page
				.getByRole('button', {name: objectRelationship.label['en_US']})
				.getByRole('presentation')
		).not.toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({
					hasText: objectDefinition2.label['en_US'],
				})
				.locator('.lfr-objects__model-builder-node-container')
		).not.toHaveClass(/treeItem/);
	});

	test('can create relationship with inheritance enabled using the add relationship modal', async ({
		addNewObjectRelationshipModalPage,
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderRightSidebarPage,
		page,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		apiHelpers.data.push({id: objectFolder.id, type: 'objectFolder'});

		const objectRelationships: ObjectRelationship[] = [];

		try {
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

			apiHelpers.data.push({
				id: objectDefinition1.id,
				type: 'objectDefinition',
			});
			apiHelpers.data.push({
				id: objectDefinition2.id,
				type: 'objectDefinition',
			});

			await modelBuilderDiagramPage.goto({
				objectFolderName: objectFolder.name,
			});

			await modelBuilderDiagramPage.connectObjectDefinitionsNodeHandles(
				objectDefinition1.id,
				objectDefinition2.id
			);

			const objectRelationship =
				await addNewObjectRelationshipModalPage.handleForm({
					inherited: true,
					objectRelationshipLabel:
						'objectRelationship' + getRandomInt(),
					type: 'One to Many',
				});

			objectRelationships.push(objectRelationship);

			apiHelpers.data.push({
				id: objectRelationship.id,
				type: 'objectRelationship',
			});

			await expect(
				page.locator('#ToastAlertContainer .alert-success')
			).toBeVisible();

			await expect(
				modelBuilderRightSidebarPage.inheritanceCheckbox
			).toBeChecked();
		}
		finally {
			const objectRelationshipApiClient =
				await apiHelpers.buildRestClient(ObjectRelationshipApi);

			for (const objectRelationship of objectRelationships) {
				await objectRelationshipApiClient.putObjectRelationship(
					objectRelationship.id,
					{
						...objectRelationship,
						edge: false,
					}
				);
			}
		}
	});
});
