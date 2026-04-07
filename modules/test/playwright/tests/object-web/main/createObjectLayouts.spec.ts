/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';
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

test.describe('Layouts Tab', () => {
	test(
		'Verify it is possible to add a Block',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await objectLayoutsPage.createObjectLayoutBlock({
				objectLayoutRegularBlockName: 'Block Name',
			});

			await expect(
				objectLayoutsPage.iframeLocator.getByText('Block Name')
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to add a Field for the Block with 1 column',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label!['en_US']],
				objectLayoutName: layoutName,
				objectLayoutRegularBlockName: 'Block Name',
				objectLayoutTabName: 'Field Tab',
			});

			await expect(
				objectLayoutsPage.layoutTabPanel.getByText(
					objectFields[0].label!['en_US']
				)
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to add a Field for the Block with 2 columns',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label!['en_US']],
				objectLayoutName: layoutName,
				objectLayoutRegularBlockName: 'Block Name',
				objectLayoutTabName: 'Field Tab',
			});

			await expect(
				objectLayoutsPage.layoutTabPanel.getByText(
					objectFields[0].label!['en_US']
				)
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to add a Field for the Block with 3 columns',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label!['en_US']],
				objectLayoutName: layoutName,
				objectLayoutRegularBlockName: 'Block Name',
				objectLayoutTabName: 'Field Tab',
			});

			await expect(
				objectLayoutsPage.layoutTabPanel.getByText(
					objectFields[0].label!['en_US']
				)
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to add a Tab with Fields Type',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await expect(
				objectLayoutsPage.iframeLocator.getByText('Field Tab')
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to collapse and expand a block of fields',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label!['en_US']],
				objectLayoutName: layoutName,
				objectLayoutRegularBlockName: 'Block 1',
				objectLayoutTabName: 'Field Tab',
			});

			await objectLayoutsPage.toggleCollapsible('Block 1');

			await expect(
				objectLayoutsPage.iframeLocator.getByRole('switch', {
					name: 'Collapsible',
				})
			).toBeChecked();
		}
	);

	test.fixme(
		'Verify it is possible to add Entries with Custom Layout Created',
		{tag: '@LPS-135397'},
		async ({
			apiHelpers,
			objectLayoutsPage,
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await objectLayoutsPage.createObjectLayoutBlock({
				objectLayoutRegularBlockName: 'Block',
			});

			await objectLayoutsPage.openObjectLayoutObjectField();

			await objectLayoutsPage.addObjectLayoutObjectField(
				objectFields[0].label!['en_US']
			);

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			await waitForAlert(page);

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await waitForAlert(page);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await expect(page.getByText('Block')).toBeVisible();

			await expect(
				page.getByLabel(objectFields[0].label!['en_US'])
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to cancel the update of a Layout',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.iframeLocator
				.getByLabel('Name')
				.fill('Layout Updated');

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Cancel'})
				.click();

			await page.reload();

			await expect(page.getByText(layoutName)).toBeVisible();
			await expect(page.getByText('Layout Updated')).toBeHidden();
		}
	);

	test(
		'Verify it is possible to create a Layout for an Object',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await expect(page.getByText(layoutName)).toBeVisible();
		}
	);

	test(
		'Verify it is possible to delete a Field on Layout',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label!['en_US']],
				objectLayoutName: layoutName,
				objectLayoutRegularBlockName: 'Block 1',
				objectLayoutTabName: 'Field Tab',
			});

			await objectLayoutsPage.iframeLocator
				.getByText(objectFields[0].label!['en_US'])
				.hover();

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Delete'})
				.first()
				.click();

			await expect(
				objectLayoutsPage.iframeLocator.getByText(
					objectFields[0].label!['en_US']
				)
			).toBeHidden();
		}
	);

	test(
		'Verify it is possible to delete a Layout for an Object',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'LayoutToDelete' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await page
				.getByRole('row')
				.filter({hasText: layoutName})
				.locator('.dropdown-toggle')
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();

			await page.getByRole('button', {name: 'Delete'}).click();

			await waitForAlert(page);

			await expect(page.getByText(layoutName)).toBeHidden();
		}
	);

	test(
		'Verify it is not possible to add a Tab with Relationship Type in an Object without Relationship',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await objectLayoutsPage.addTab.click();

			await expect(
				objectLayoutsPage.iframeLocator.getByRole('menuitem', {
					name: 'Relationship Tab',
				})
			).toBeHidden();
		}
	);

	test(
		'Verify that the Relationship tab cannot be added first',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
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

			const {ObjectRelationshipAPI} = await import(
				'@liferay/object-admin-rest-client-js'
			);

			const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
				ObjectRelationshipAPI
			);

			await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
				objectDefinition1.externalReferenceCode!,
				{
					label: {en_US: 'Relationship' + getRandomInt()},
					name: 'relationship' + getRandomInt(),
					objectDefinitionExternalReferenceCode2:
						objectDefinition2.externalReferenceCode,
					objectDefinitionId2: objectDefinition2.id,
					objectDefinitionName2: objectDefinition2.name,
					type: 'oneToMany',
				}
			);

			await objectLayoutsPage.goto(objectDefinition1.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.addTab.click();

			await expect(
				objectLayoutsPage.iframeLocator.getByRole('menuitem', {
					name: 'Relationship Tab',
				})
			).toBeHidden();
		}
	);

	test(
		'Verify it is not possible to set a layout as default without all the required fields on the first tab',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage, page}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: [{businessType: 'Text', required: true}],
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await objectLayoutsPage.createObjectLayoutBlock({
				objectLayoutRegularBlockName: 'Block',
			});

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			await waitForAlert(page);

			await page.goBack();

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await expect(
				page.getByText('All mandatory fields must be on the first tab.')
			).toBeVisible();
		}
	);

	test(
		'Verify it is possible to update a Custom Layout Created',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage, page}) => {
			const objectDefinition =
				await apiHelpers.objectAdmin.postRandomObjectDefinition({
					status: {code: 0},
				});

			apiHelpers.data.push({
				id: objectDefinition.id,
				type: 'objectDefinition',
			});

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			const newLayoutName = 'UpdatedLayout' + getRandomInt();

			await objectLayoutsPage.iframeLocator
				.getByLabel('Name')
				.fill(newLayoutName);

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			await waitForAlert(page);

			await page.goBack();

			await expect(page.getByText(newLayoutName)).toBeVisible();
		}
	);

	test.fixme(
		'Verify it is possible to view the Entry with one column',
		{tag: '@LPS-135397'},
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await objectLayoutsPage.createObjectLayoutBlock({
				objectLayoutRegularBlockName: 'Block',
			});

			await objectLayoutsPage.openObjectLayoutObjectField();

			await objectLayoutsPage.addObjectLayoutObjectField(
				objectFields[0].label!['en_US']
			);

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			await waitForAlert(page);

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await waitForAlert(page);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			await expect(page.getByText('Block')).toBeVisible();

			await expect(
				page.getByLabel(objectFields[0].label!['en_US'])
			).toBeVisible();
		}
	);

	test.fixme(
		'Verify it is possible to view the Entry with three columns',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text', 'Integer', 'Boolean'],
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await objectLayoutsPage.createObjectLayoutBlock({
				objectLayoutRegularBlockName: 'Block',
			});

			for (const field of objectFields) {
				await objectLayoutsPage.openObjectLayoutObjectField();

				await objectLayoutsPage.addObjectLayoutObjectField(
					field.label!['en_US']
				);
			}

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			await waitForAlert(page);

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await waitForAlert(page);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			for (const field of objectFields) {
				await expect(page.getByLabel(field.label!['en_US'])).toBeVisible();
			}
		}
	);

	test.fixme(
		'Verify it is possible to view the Entry with two columns',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage, page, viewObjectEntriesPage}) => {
			const objectFields = generateObjectFields({
				objectFieldBusinessTypes: ['Text', 'Integer'],
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutTab('Field Tab');

			await objectLayoutsPage.createObjectLayoutBlock({
				objectLayoutRegularBlockName: 'Block',
			});

			for (const field of objectFields) {
				await objectLayoutsPage.openObjectLayoutObjectField();

				await objectLayoutsPage.addObjectLayoutObjectField(
					field.label!['en_US']
				);
			}

			await objectLayoutsPage.iframeLocator
				.getByRole('button', {name: 'Save'})
				.first()
				.click();

			await waitForAlert(page);

			await objectLayoutsPage.setObjectLayoutAsDefault();

			await waitForAlert(page);

			await viewObjectEntriesPage.goto(objectDefinition.className);

			await viewObjectEntriesPage.clickAddObjectEntry(
				objectDefinition.label['en_US']
			);

			for (const field of objectFields) {
				await expect(page.getByLabel(field.label!['en_US'])).toBeVisible();
			}
		}
	);

	test(
		'Verify it is possible to set the block as Collapsible',
		{tag: '@LPS-135397'},
		async ({apiHelpers, objectLayoutsPage}) => {
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

			await objectLayoutsPage.goto(objectDefinition.label['en_US']);

			const layoutName = 'Layout' + getRandomInt();

			await objectLayoutsPage.createObjectLayout(layoutName);

			await objectLayoutsPage.openObjectLayoutConfiguration(layoutName);

			await objectLayoutsPage.layoutTab.click();

			await objectLayoutsPage.createObjectLayoutContent({
				objectFieldNames: [objectFields[0].label!['en_US']],
				objectLayoutName: layoutName,
				objectLayoutRegularBlockName: 'Block 1',
				objectLayoutTabName: 'Field Tab',
			});

			await objectLayoutsPage.toggleCollapsible('Block 1');

			await expect(
				objectLayoutsPage.iframeLocator.getByRole('switch', {
					name: 'Collapsible',
				})
			).toBeChecked();
		}
	);
});
