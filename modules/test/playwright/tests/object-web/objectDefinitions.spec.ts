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

test.describe('Manage object definitions through Model Builder', () => {
	test('can create an object definition inside a folder and see if it renders correctly in the model builder', async ({
		apiHelpers,
		modalAddObjectDefinitionPage,
		modelBuilderPage,
		page,
		viewObjectDefinitionsPage,
	}) => {
		await viewObjectDefinitionsPage.goto();

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		viewObjectDefinitionsPage.createObjectDefinitionButton.click();

		const objectDefinition =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				objectDefinitionLabel
			);

		expect(page.getByText(objectDefinitionLabel)).toBeVisible();

		await viewObjectDefinitionsPage.viewInModelBuilder();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});

	test('can create an object definition by model builder', async ({
		apiHelpers,
		modalAddObjectDefinitionPage,
		modelBuilderPage,
	}) => {
		await modelBuilderPage.goto({objectFolderName: 'Default'});

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		modelBuilderPage.createNewObjectDefinitionButton.click();

		const objectDefinition =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				objectDefinitionLabel
			);

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.label['en_US'],
			})
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});

	test('linked object definitions are created when object definitions are related and put into different folders', async ({
		apiHelpers,
		modelBuilderPage,
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
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

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

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition1.label['en_US']
			)
		).toBeVisible();

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition2.label['en_US']
			)
		).toBeHidden();

		await modelBuilderPage.leftSidebarItems
			.filter({hasText: objectFolder.name})
			.hover();

		await modelBuilderPage.clickGoToFolderButton();

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition1.label['en_US']
			)
		).toBeHidden();

		await expect(
			modelBuilderPage.getLinkedObjectDefinitionIconLocator(
				objectDefinition2.label['en_US']
			)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition1.id
		);
		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition2.id
		);

		await apiHelpers.objectAdmin.deleteObjectFolder(objectFolder.id);
	});

	test('show object definition details in "RightSidebar" after create object definition', async ({
		apiHelpers,
		modalAddObjectDefinitionPage,
		modelBuilderPage,
	}) => {
		await modelBuilderPage.goto({objectFolderName: 'Default'});

		const objectDefinitionLabel = 'ObjectDefinitionLabel' + getRandomInt();

		modelBuilderPage.createNewObjectDefinitionButton.click();

		const objectDefinition =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				objectDefinitionLabel
			);

		await expect(
			modelBuilderPage.rightSidebar.getByTitle(
				objectDefinitionLabel + ' Details'
			)
		).toBeVisible();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});

	test('see object definition details', async ({
		apiHelpers,
		modelBuilderPage,
	}) => {
		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		await modelBuilderPage.leftSidebarItems
			.filter({hasText: objectDefinition.name})
			.click();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition.name,
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.rightSidebar.getByTitle(
				objectDefinition.name + ' Details'
			)
		).toBeVisible();

		const details = [
			'Label',
			'Plural Label',
			'Table Name',
			'Activate Object',
			'Entry Title Field',
			'Scope',
			'Panel Link',
		];

		for (let i = 0; i < details.length; i++) {
			const detail = details[i];

			await expect(
				modelBuilderPage.rightSidebar
					.filter({hasText: detail})
					.filter({hasText: objectDefinition.name})
			).toBeVisible();
		}

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition.id
		);
	});

	test('can delete an published object definition by model builder', async ({
		apiHelpers,
		modalAddObjectDefinitionPage,
		modelBuilderPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 0},
			});

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.createNewObjectDefinitionButton.click();

		const objectDefinition2 =
			await modalAddObjectDefinitionPage.createObjectDefinition(
				'ObjectDefinition' + getRandomInt()
			);

		await modelBuilderPage.clickToggleSidebarsButton();

		await modelBuilderPage.clickFitViewButton();

		await modelBuilderPage.clickObjectDefinitionActionsButton(
			objectDefinition1.label['en_US']
		);

		await modelBuilderPage.deleteObjectDefinition(objectDefinition1.name);

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.objectDefinitionNodes.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeHidden();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition2.id
		);
	});

	test('can delete an object definition by model builder leftsidebar', async ({
		apiHelpers,
		modelBuilderPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		await modelBuilderPage.goto({objectFolderName: 'Default'});

		await modelBuilderPage.clickLeftSideBarItem(
			objectDefinition1.label['en_US']
		);

		await modelBuilderPage.clickObjectDefinitionActionsButtonInLeftSidebar(
			objectDefinition1.label['en_US']
		);

		await modelBuilderPage.clickDeleteObjectDefinition();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderPage.leftSidebarItems.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeHidden();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition2.id
		);
	});
});

test.describe('Manage object definitions through View Object Definitions', () => {
	test('can delete an object definition by FDS action', async ({
		apiHelpers,
		page,
		viewObjectDefinitionsPage,
	}) => {
		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFolderExternalReferenceCode: 'default',
				status: {code: 2},
			});

		await viewObjectDefinitionsPage.goto();

		await page.locator('.dnd-td.item-actions').first().waitFor();

		await page
			.locator('.dnd-td.item-actions')
			.last()
			.locator('.dropdown-toggle')
			.click();

		await viewObjectDefinitionsPage.clickDeleteObjectDefinition();

		await expect(
			viewObjectDefinitionsPage.frontendDataSetEntries.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			viewObjectDefinitionsPage.frontendDataSetEntries.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeHidden();

		// Clean up

		await apiHelpers.objectAdmin.deleteObjectDefinition(
			objectDefinition1.id
		);
	});
});
