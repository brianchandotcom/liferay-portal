/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {loginTest} from '../../fixtures/loginTest';
import {objectPagesTest} from '../../fixtures/objectPagesTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(apiHelpersTest, loginTest(), objectPagesTest);

test.describe('Manage object relationships through Model Builder', () => {
	test('can create relationship by dragging node handles', async ({
		apiHelpers,
		modelBuilderPage,
		objectDefinitionsPage,
	}) => {
		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition(
				objectFolder.externalReferenceCode
			);
		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition(
				objectFolder.externalReferenceCode
			);

		await objectDefinitionsPage.goto();

		await objectDefinitionsPage.openObjectFolder(
			objectFolder.externalReferenceCode
		);

		await objectDefinitionsPage.viewInModelBuilder();

		await modelBuilderPage.clickToggleSidebarsButton();

		await modelBuilderPage.clickFitViewButton();

		const objectRelationshipLabel = 'objectRelationship' + getRandomInt();

		const objectRelationship =
			await modelBuilderPage.createObjectRelationship(
				objectDefinition1.id,
				objectDefinition2.id,
				objectRelationshipLabel,
				'One to Many'
			);

		await expect(
			modelBuilderPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).toBeVisible();

		await modelBuilderPage.clickObjectDefinitionShowAllFieldsButton(
			objectDefinition2.name
		);

		await modelBuilderPage.clickFitViewButton();

		await expect(
			modelBuilderPage.objectDefinitionNodes
				.filter({hasText: objectDefinition2.name})
				.getByText(objectRelationshipLabel)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectRelationship(
			objectRelationship.id
		);

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition1.id
		);
		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition2.id
		);

		await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
	});

	test('can delete object relationship from different folders', async ({
		apiHelpers,
		modelBuilderPage,
		objectDefinitionsPage,
		page,
	}) => {
		await page.goto('/');

		const objectFolder =
			await apiHelpers.objectAdmin.postRandomObjectFolder();

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition(
				objectFolder.externalReferenceCode
			);

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition('default');

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + getRandomInt();

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

		await objectDefinitionsPage.goto();

		await objectDefinitionsPage.openObjectFolder(
			objectFolder.externalReferenceCode
		);

		await objectDefinitionsPage.viewInModelBuilder();

		await expect(
			modelBuilderPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).not.toBeVisible();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.name,
			})
		).toBeVisible();

		await modelBuilderPage.clickObjectRelationshipEdge(
			objectRelationshipLabel
		);

		await modelBuilderPage.deleteObjectRelationship(objectRelationshipName);

		await expect(
			modelBuilderPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).toBeHidden();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.name,
			})
		).toBeHidden();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition1.id
		);
		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition2.id
		);

		await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
	});
});
