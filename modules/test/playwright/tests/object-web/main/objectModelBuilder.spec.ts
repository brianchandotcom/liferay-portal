/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ObjectRelationshipAPI} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test.beforeEach(({page}) => {
	page.setViewportSize({height: 1080, width: 1920});
});

test(
	'LPD-78504 Cannot publish object definitions with errors in model builder view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderRightSidebarPage,
	}) => {
		// Create an object with no custom fields - the activate toggle should be disabled

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [],
				status: {code: 2},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			objectDefinition.label['en_US']
		);

		// Verify the activate toggle is disabled (cannot publish object without custom fields)

		await expect(
			modelBuilderRightSidebarPage.objectDefinitionActivateObject
		).toBeDisabled();

		await expect(
			modelBuilderRightSidebarPage.objectDefinitionActivateObject
		).not.toBeChecked();
	}
);

test(
	'LPD-78504 Can publish multiple object definitions in model builder view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderRightSidebarPage,
	}) => {
		// Create two published objects and verify they both show as active in model builder

		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectDefinition1 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		const objectDefinition2 =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
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

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		// Verify the first object definition is active

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			objectDefinition1.label['en_US']
		);

		await expect(
			modelBuilderRightSidebarPage.objectDefinitionActivateObject
		).toBeChecked();

		// Verify the second object definition is active

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			objectDefinition2.label['en_US']
		);

		await expect(
			modelBuilderRightSidebarPage.objectDefinitionActivateObject
		).toBeChecked();
	}
);

test(
	'LPD-78504 Can search for object definition in model builder view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
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

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		// Verify both objects appear in the sidebar before searching

		await expect(
			modelBuilderLeftSidebarPage.sidebarItems.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderLeftSidebarPage.sidebarItems.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		// Search for only the first object definition

		const searchInput = page.getByPlaceholder('Search');

		await searchInput.fill(objectDefinition1.label['en_US']);

		// Verify only the matching object appears

		await expect(
			modelBuilderLeftSidebarPage.sidebarItems.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderLeftSidebarPage.sidebarItems.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeHidden();

		// Clear search and verify both appear again

		await searchInput.clear();

		await expect(
			modelBuilderLeftSidebarPage.sidebarItems.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderLeftSidebarPage.sidebarItems.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can search for object definition with relationship in model builder view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
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

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition1.externalReferenceCode,
				{
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
					type: 'oneToMany',
				}
			);

		apiHelpers.data.push({
			id: objectRelationship.id,
			type: 'objectRelationship',
		});

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		// Verify the relationship edge is visible

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).toBeVisible();

		// Search for the first object definition

		const searchInput = page.getByPlaceholder('Search');

		await searchInput.fill(objectDefinition1.label['en_US']);

		// Verify the searched object is visible in the sidebar

		await expect(
			modelBuilderLeftSidebarPage.sidebarItems.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		// Verify both object definition nodes are still visible (related objects stay visible)

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can update field in model builder view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		modelBuilderDiagramPage,
		modelBuilderLeftSidebarPage,
		modelBuilderObjectDefinitionNodePage,
		page,
	}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: ['Text'],
		});

		const objectFieldLabel = objectFields[0].label['en_US'];

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			objectDefinition.label['en_US']
		);

		// Show all fields in the object definition node

		await modelBuilderObjectDefinitionNodePage.clickShowAllFieldsButton(
			objectDefinition.label['en_US'],
			modelBuilderDiagramPage.objectDefinitionNodes
		);

		// Click the field to select it

		await modelBuilderDiagramPage.objectDefinitionNodes
			.filter({hasText: objectDefinition.label['en_US']})
			.getByText(objectFieldLabel, {exact: true})
			.click();

		// Update the field label in the right sidebar

		const updatedFieldLabel = 'UpdatedField' + getRandomInt();

		await page
			.getByPlaceholder('Text to translate...')
			.fill(updatedFieldLabel);

		// Click another item to trigger save

		await modelBuilderLeftSidebarPage.clickSideBarItem(
			objectDefinition.label['en_US']
		);

		// Verify the updated label is visible in the node

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.label['en_US']})
				.getByText(updatedFieldLabel, {exact: true})
		).toBeVisible();

		// Verify the old label is no longer visible

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes
				.filter({hasText: objectDefinition.label['en_US']})
				.getByText(objectFieldLabel, {exact: true})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can view card after relationship deletion in model builder view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, modelBuilderDiagramPage}) => {
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

		const objectRelationshipLabel =
			'objectRelationshipLabel' + getRandomInt();
		const objectRelationshipName =
			'objectRelationshipName' + Math.floor(Math.random() * 99);

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const {body: objectRelationship} =
			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition1.externalReferenceCode,
				{
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
					type: 'oneToMany',
				}
			);

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		// Verify both nodes and the relationship edge are visible

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).toBeVisible();

		// Delete the relationship via API

		await objectRelationshipAPIClient.deleteObjectRelationship(
			objectRelationship.id
		);

		// Reload the model builder to reflect the deletion

		await modelBuilderDiagramPage.goto({objectFolderName: 'Default'});

		// Verify the relationship edge is no longer visible

		await expect(
			modelBuilderDiagramPage.objectRelationshipEdges.filter({
				hasText: objectRelationshipLabel,
			})
		).not.toBeVisible();

		// Verify both object definition cards/nodes are still visible after deletion

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition1.label['en_US'],
			})
		).toBeVisible();

		await expect(
			modelBuilderDiagramPage.objectDefinitionNodes.filter({
				hasText: objectDefinition2.label['en_US'],
			})
		).toBeVisible();
	}
);
