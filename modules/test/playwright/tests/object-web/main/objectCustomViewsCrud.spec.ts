/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	ObjectRelationshipAPI,
	ObjectViewAPI,
} from '@liferay/object-admin-rest-client-js';
import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';

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
	'LPD-78504 Can add a column to the custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanAddColumn
		// LPS-135394 - Verify it is possible to add a column for the View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields([
			'Author',
			'Create Date',
		]);

		const sidePanel = editObjectViewPage.sidePanel;

		await expect(sidePanel.getByText('Author')).toBeVisible();
		await expect(sidePanel.getByText('Create Date')).toBeVisible();
	}
);

test(
	'LPD-78504 Can add metadata columns to default sort',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanAddMetadataColumnsToDefaultSort
		// LPS-144472 - Verify if the user can add metadata columns to Default Sort

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		const metadataColumns = [
			'Author',
			'Create Date',
			'External Reference Code',
			'Modified Date',
			'Status',
			'ID',
		];

		await editObjectViewPage.selectObjectFields(metadataColumns);

		const defaultSortTab = sidePanel.getByRole('tab', {
			name: 'Default Sort',
		});

		await defaultSortTab.click();

		for (const columnName of metadataColumns) {
			await sidePanel.getByRole('button', {name: 'Add'}).click();

			await sidePanel.getByLabel('Column' + 'Mandatory').last().click();

			await sidePanel
				.getByRole('option', {name: columnName})
				.last()
				.click();

			await sidePanel.getByLabel('Sorting' + 'Mandatory').last().click();

			await sidePanel
				.getByRole('option', {name: 'Ascending'})
				.last()
				.click();
		}

		for (const columnName of metadataColumns) {
			await expect(sidePanel.getByText(columnName).first()).toBeVisible();
		}
	}
);

test(
	'LPD-78504 Can add translation to column label in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanAddTranslationToColumnLabel
		// LPS-147792 - Verify it is possible to add any translation for any Column Label

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field', pt_BR: 'Campo Customizado'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Custom Field']);

		const defaultSortTab = sidePanel.getByRole('tab', {
			name: 'Default Sort',
		});

		await defaultSortTab.click();

		await sidePanel.getByRole('button', {name: 'Add'}).click();

		await sidePanel.getByLabel('Column' + 'Mandatory').last().click();

		await sidePanel
			.getByRole('option', {name: 'Custom Field'})
			.last()
			.click();

		await sidePanel.getByLabel('Sorting' + 'Mandatory').last().click();

		await sidePanel
			.getByRole('option', {name: 'Ascending'})
			.last()
			.click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await page.goto(
			`/pt/group/guest/~/control_panel/manage?p_p_id=com_liferay_object_web_internal_object_definitions_portlet_ObjectDefinitionsPortlet_${objectDefinition.name.toLowerCase()}s`
		);

		await expect(
			page.getByText('Campo Customizado')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel column addition in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanCancelColumnAddition
		// LPS-135394 - Verify it is possible to cancel the addition of a column for the View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		const addButton = editObjectViewPage.addColumnButton.or(
			editObjectViewPage.addButton
		);

		await addButton.click();

		await editObjectViewPage.addColumnsModal
			.getByRole('button', {name: 'Cancel'})
			.click();

		await expect(editObjectViewPage.addColumnsModal).toBeHidden();

		const sidePanel = editObjectViewPage.sidePanel;

		await expect(
			sidePanel.getByText('No columns added yet.')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel rename of a column label in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanCancelRenameColumnLabel
		// LPS-147792 - Verify it is possible to cancel the rename of a Column Label

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields(['Author']);

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Author'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Edit'}).click();

		const editModal = page.getByRole('dialog').filter({hasText: 'Label'});

		await editModal.getByLabel('Label').clear();
		await editModal.getByLabel('Label').fill('Publishing House');

		await editModal.getByRole('button', {name: 'Cancel'}).click();

		await expect(sidePanel.getByText('Author')).toBeVisible();
	}
);

test(
	'LPD-78504 Can cancel the creation of a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: CanCancelViewCreation
		// LPS-135394 - Verify it is possible to cancel the creation of a View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		await objectViewPage.addObjectViewButton.click();

		await page.getByRole('button', {name: 'Cancel'}).click();

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Can create a default sort with ascending or descending order',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanCreateDefaultSort
		// LPS-144472 - Verify it is possible to create a Default Sort when there is column (Ascending or Descending)

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Author']);

		const defaultSortTab = sidePanel.getByRole('tab', {
			name: 'Default Sort',
		});

		await defaultSortTab.click();

		await sidePanel.getByRole('button', {name: 'Add'}).click();

		await sidePanel.getByLabel('Column' + 'Mandatory').last().click();

		await sidePanel
			.getByRole('option', {name: 'Author'})
			.last()
			.click();

		await sidePanel.getByLabel('Sorting' + 'Mandatory').last().click();

		await sidePanel
			.getByRole('option', {name: 'Ascending'})
			.last()
			.click();

		await expect(sidePanel.getByText('Author')).toBeVisible();
		await expect(sidePanel.getByText('Ascending')).toBeVisible();
	}
);

test(
	'LPD-78504 Can create a filter in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanCreateFilter
		// LPS-144957 - Verify that it's possible to create the filter

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Custom Field',
			'Status',
		]);

		await editObjectViewPage.createFilter('Status', 'Includes', 'Approved');

		await expect(sidePanel.getByText('Status')).toBeVisible();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can create a filter by relationship column from system object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanCreateFilterByRelationshipColumnFromSystemObject
		// LPS-170529 - Verify if it is possible to create a filter by relationship columns made from the system objects to custom objects

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const relationshipName = 'relationship' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'AccountEntry',
			{
				label: {en_US: 'Relationship'},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1: 'AccountEntry',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Relationship']);

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await sidePanel
			.getByRole('option', {name: 'Relationship'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await expect(sidePanel.getByText('Relationship')).toBeVisible();
	}
);

test(
	'LPD-78504 Can create a filter using the relationship field of the object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanCreateFilterUsingRelationshipField
		// LPS-166585 - Verify that it's possible to create a filter using the relationship field of the object

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

		const relationshipName = 'relationship' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'User',
			{
				label: {en_US: 'Relationship'},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1: 'User',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel
			.getByRole('option', {name: 'Relationship'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await expect(sidePanel.getByText('Relationship')).toBeVisible();
	}
);

test(
	'LPD-78504 Can create a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: CanCreateView
		// LPS-135394 - Verify it is possible to create a View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await expect(
			page.getByRole('link', {name: viewName})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete a column by unselecting it in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanDeleteColumnByUnselect
		// LPS-135394 - Verify it is possible to delete a column for the View by unselecting it

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields(['Author']);

		const sidePanel = editObjectViewPage.sidePanel;

		await expect(sidePanel.getByText('Author')).toBeVisible();

		await editObjectViewPage.unselectObjectFields(['Author']);

		await expect(
			sidePanel.getByText('No columns added yet.')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete a column through the delete button in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanDeleteColumnThroughDeleteButton
		// LPS-135394 - Verify it is possible to delete a column for the View through the delete button

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Author']);

		await expect(sidePanel.getByText('Author')).toBeVisible();

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Author'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await expect(
			sidePanel.getByText('No columns added yet.')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete a column with relationship field filter in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanDeleteColumnWithRelationshipFieldFilter
		// LPS-166588 - Verify that it's possible to delete a default filter column with the relationship field in the custom view

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

		const relationshipName = 'relationship' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'User',
			{
				label: {en_US: 'Relationship'},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1: 'User',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Relationship']);

		await expect(sidePanel.getByText('Relationship')).toBeVisible();

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Relationship'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await expect(
			sidePanel.getByText('No columns added yet.')
		).toBeVisible();

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await expect(
			sidePanel.getByRole('option', {name: 'Relationship'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete a filter in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanDeleteFilter
		// LPS-144957 - Verify that it's possible to delete the filter

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Custom Field',
			'Status',
		]);

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await sidePanel
			.getByRole('option', {name: 'Create Date'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await sidePanel
			.getByRole('option', {name: 'Modified Date'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await expect(sidePanel.getByText('Create Date')).toBeVisible();
		await expect(sidePanel.getByText('Modified Date')).toBeVisible();

		for (let i = 0; i < 2; i++) {
			await sidePanel
				.locator('li[draggable="true"]')
				.first()
				.getByRole('button')
				.click();

			await page.getByRole('menuitem', {name: 'Delete'}).click();
		}

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);
	}
);

test(
	'LPD-78504 Can delete a filter with relationship field in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanDeleteFilterWithRelationshipField
		// LPS-166590 - Verify that it's possible to delete the configured filters from the object

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

		const relationshipName = 'relationship' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'User',
			{
				label: {en_US: 'Relationship'},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1: 'User',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Status']);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await sidePanel
			.getByRole('option', {name: 'Relationship'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Relationship'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await expect(
			sidePanel.getByText('Relationship')
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can delete a pre-order column in default sort',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanDeletePreOrderColumn
		// LPS-144472 - Verify it is possible the user to delete the pre-order column

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields(['Author']);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		const sidePanel = editObjectViewPage.sidePanel;

		await expect(sidePanel.getByText('Author')).toBeVisible();

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Author'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await expect(
			sidePanel.getByText('No columns added yet.')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can delete a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: CanDeleteView
		// LPS-135394 - Verify it is possible to delete a View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await expect(
			page.getByRole('link', {name: viewName})
		).toBeVisible();

		await page
			.getByRole('row', {name: viewName})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: 'Delete'}).click();

		await page.getByRole('button', {name: 'Delete'}).click();

		await waitForAlert(page);

		await expect(
			page.getByRole('link', {name: viewName})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can drag columns in custom view builder',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanDragColumns
		// LPS-135394 - Verify it is possible to drag the columns

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields([
			'Author',
			'Create Date',
			'Modified Date',
		]);

		const sidePanel = editObjectViewPage.sidePanel;
		const columns = sidePanel.locator('li[draggable="true"]');

		await expect(columns).toHaveCount(3);
		await expect(columns.nth(0)).toContainText('Author');
		await expect(columns.nth(1)).toContainText('Create Date');
		await expect(columns.nth(2)).toContainText('Modified Date');
	}
);

test(
	'LPD-78504 Can duplicate an object view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: CanDuplicateObjectView
		// LPS-146028 - Verify that the user can duplicate a object View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page
			.getByRole('row', {name: viewName})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: 'Duplicate'}).click();

		await waitForAlert(page);

		await expect(
			page.getByRole('link', {name: `${viewName} (Copy)`})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit a filter in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanEditFilter
		// LPS-144957 - Verify that it's possible to edit the filter

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Custom Field',
			'Status',
		]);

		await editObjectViewPage.createFilter('Status', 'Includes');

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await expect(sidePanel.getByText('Status')).toBeVisible();

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Status'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Edit'}).click();

		await expect(
			sidePanel.getByText('Edit Filter')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can edit a filter with relationship field in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanEditFilterWithRelationshipField
		// LPS-166587 - Verify that it's possible to edit a default filter column with the relationship field in the custom view

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

		const relationshipName = 'relationship' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'User',
			{
				label: {en_US: 'Relationship'},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1: 'User',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Relationship']);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Relationship'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Edit'}).click();

		const editModal = page.getByRole('dialog').filter({hasText: 'Label'});

		await editModal.getByLabel('Label').clear();
		await editModal.getByLabel('Label').fill('Relationship Edit');

		await editModal.getByRole('button', {name: 'Edit'}).click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		await expect(
			sidePanel.getByText('Relationship Edit')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can filter entries by create date in custom view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectViewPage,
		objectViewPage,
		page,
		viewObjectEntriesPage,
	}) => {
		// Migrated from: CanFilterEntriesByCreateDate
		// LPS-169019 - Verify it's possible to filter by Create Date

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Custom Field',
			'Create Date',
		]);

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await sidePanel
			.getByRole('option', {name: 'Create Date'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test(
	'LPD-78504 Can filter entries by modified date in custom view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectViewPage,
		objectViewPage,
		page,
		viewObjectEntriesPage,
	}) => {
		// Migrated from: CanFilterEntriesByModifiedDate
		// LPS-169018 - Verify it's possible to filter by Modified Date

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Custom Field',
			'Modified Date',
		]);

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await sidePanel
			.getByRole('option', {name: 'Modified Date'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test(
	'LPD-78504 Can filter entries by status in custom view',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectViewPage,
		objectViewPage,
		page,
		viewObjectEntriesPage,
	}) => {
		// Migrated from: CanFilterEntriesByStatus
		// LPS-169016 - Verify it's possible to filter by status

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Custom Field',
			'Status',
		]);

		await editObjectViewPage.createFilter('Status', 'Includes', 'Approved');

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test(
	'LPD-78504 Can filter entries using relationship field in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanFilterEntriesUsingRelationshipField
		// LPS-166586 - Verify that it's possible to filter entries using any relationship field of the object in the custom view

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const relationshipName = 'relationship' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'User',
			{
				label: {en_US: 'Relationship'},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1: 'User',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Custom Field']);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		await sidePanel
			.getByRole('option', {name: 'Relationship'})
			.click();

		await editObjectViewPage.filterType.click();

		await sidePanel
			.getByRole('option', {name: 'Excludes'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await expect(sidePanel.getByText('Relationship')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot leave name field empty when creating a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: CannotLeaveNameFieldEmpty
		// LPS-135394 - Verify the Name is required when creating a View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		await objectViewPage.addObjectViewButton.click();

		await objectViewPage.objectViewNameSaveModalButton.click();

		await expect(page.getByText('Required')).toBeVisible();
	}
);

test(
	'LPD-78504 Cannot save another view as default when one is already set',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CannotSaveAnotherViewAsDefault
		// LPS-135394 - Verify that it is not possible to save another View as default

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewNameA = 'CustomViewA' + getRandomInt();

		await objectViewPage.createObjectView(viewNameA);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewNameA}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Status']);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		const viewNameB = 'CustomViewB' + getRandomInt();

		await objectViewPage.createObjectView(viewNameB);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewNameB}).click();

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Author']);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page, 'There can only be one default object view', {
			type: 'danger',
		});
	}
);

test(
	'LPD-78504 Cannot save a view set as default when there are no columns selected',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CannotSaveNoColumnsView
		// LPS-135394 - Verify it is not possible to save a View set as default when there are no columns selected

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(
			page,
			'Default view must have at least one column.',
			{type: 'danger'}
		);
	}
);

test(
	'LPD-78504 Can prioritize columns in default sort',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanPrioritizeColumns
		// LPS-144472 - Verify it is possible to prioritize columns to Default Sort

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields([
			'Author',
			'Create Date',
		]);

		const sidePanel = editObjectViewPage.sidePanel;

		const defaultSortTab = sidePanel.getByRole('tab', {
			name: 'Default Sort',
		});

		await defaultSortTab.click();

		for (const columnName of ['Author', 'Create Date']) {
			await sidePanel.getByRole('button', {name: 'Add'}).click();

			await sidePanel.getByLabel('Column' + 'Mandatory').last().click();

			await sidePanel
				.getByRole('option', {name: columnName})
				.last()
				.click();

			await sidePanel
				.getByLabel('Sorting' + 'Mandatory')
				.last()
				.click();

			await sidePanel
				.getByRole('option', {name: 'Ascending'})
				.last()
				.click();
		}

		const sortColumns = sidePanel.locator('li[draggable="true"]');

		await expect(sortColumns.nth(0)).toContainText('Author');
		await expect(sortColumns.nth(1)).toContainText('Create Date');
	}
);

test(
	'LPD-78504 Can search for a column on the view builder tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanSearchColumn
		// LPS-135394 - Verify it is possible to search for a column on the View Builder tab

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields([
			'Author',
			'Create Date',
		]);

		const sidePanel = editObjectViewPage.sidePanel;

		const searchInput = sidePanel.getByPlaceholder('Search');

		await searchInput.fill('Author');

		await expect(
			sidePanel.locator('li[draggable="true"]').filter({hasText: 'Author'})
		).toBeVisible();

		await expect(
			sidePanel
				.locator('li[draggable="true"]')
				.filter({hasText: 'Create Date'})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can search for a column on the add columns modal',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanSearchColumnAddColumnModal
		// LPS-135394 - Verify it is possible to search for a column on the Add Columns modal

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		const addButton = editObjectViewPage.addColumnButton.or(
			editObjectViewPage.addButton
		);

		await addButton.click();

		const modal = editObjectViewPage.addColumnsModal;

		await modal.getByPlaceholder('Search').fill('Author');

		await expect(modal.getByText('Author')).toBeVisible();
		await expect(modal.getByText('Create Date')).toBeHidden();
	}
);

test(
	'LPD-78504 Can search columns in default sort',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanSearchColumns
		// LPS-144472 - Verify it is possible to search columns to Default Sort

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		const metadataColumns = [
			'Author',
			'Create Date',
			'External Reference Code',
			'Modified Date',
			'Status',
			'ID',
		];

		await editObjectViewPage.selectObjectFields(metadataColumns);

		const defaultSortTab = sidePanel.getByRole('tab', {
			name: 'Default Sort',
		});

		await defaultSortTab.click();

		for (const columnName of metadataColumns) {
			await sidePanel.getByRole('button', {name: 'Add'}).click();

			await sidePanel.getByLabel('Column' + 'Mandatory').last().click();

			await sidePanel
				.getByRole('option', {name: columnName})
				.last()
				.click();

			await sidePanel
				.getByLabel('Sorting' + 'Mandatory')
				.last()
				.click();

			await sidePanel
				.getByRole('option', {name: 'Ascending'})
				.last()
				.click();
		}

		for (const columnName of metadataColumns) {
			const searchInput = sidePanel.getByPlaceholder('Search');

			await searchInput.fill(columnName);

			await expect(
				sidePanel
					.locator('li[draggable="true"]')
					.filter({hasText: columnName})
					.first()
			).toBeVisible();

			await searchInput.clear();
		}
	}
);

test(
	'LPD-78504 Can search for a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: CanSearchView
		// LPS-135394 - Verify it is possible to search for a View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewNameA = 'ViewA' + getRandomInt();
		const viewNameB = 'CustomB' + getRandomInt();

		await objectViewPage.createObjectView(viewNameA);

		await waitForAlert(page);

		await objectViewPage.createObjectView(viewNameB);

		await waitForAlert(page);

		await page.getByPlaceholder('Search').fill(viewNameA);

		await page.keyboard.press('Enter');

		await expect(
			page.getByRole('link', {name: viewNameA})
		).toBeVisible();

		await expect(
			page.getByRole('link', {name: viewNameB})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can search with enter key on default sort',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanSearchWithEnterKey
		// LPS-144472 - Verify it is possible to search when press enter key on Default Sort

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields([
			'Author',
			'Create Date',
			'Modified Date',
		]);

		const sidePanel = editObjectViewPage.sidePanel;

		const defaultSortTab = sidePanel.getByRole('tab', {
			name: 'Default Sort',
		});

		await defaultSortTab.click();

		for (const columnName of ['Author', 'Create Date', 'Modified Date']) {
			await sidePanel.getByRole('button', {name: 'Add'}).click();

			await sidePanel.getByLabel('Column' + 'Mandatory').last().click();

			await sidePanel
				.getByRole('option', {name: columnName})
				.last()
				.click();

			await sidePanel
				.getByLabel('Sorting' + 'Mandatory')
				.last()
				.click();

			await sidePanel
				.getByRole('option', {name: 'Ascending'})
				.last()
				.click();
		}

		const searchInput = sidePanel.getByPlaceholder('Search');

		await searchInput.fill('Author');

		await searchInput.press('Enter');

		await expect(
			sidePanel
				.locator('li[draggable="true"]')
				.filter({hasText: 'Author'})
		).toBeVisible();

		await expect(
			sidePanel
				.locator('li[draggable="true"]')
				.filter({hasText: 'Create Date'})
		).toBeHidden();

		await expect(
			sidePanel
				.locator('li[draggable="true"]')
				.filter({hasText: 'Modified Date'})
		).toBeHidden();
	}
);

test(
	'LPD-78504 Can see entries with default return in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanSeeEntriesWithDefaultReturn
		// LPS-144472 - Verify it is possible to see the entries with default return

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		for (const letter of ['A', 'Z', 'B']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{customField: `Entry ${letter}`},
				applicationName
			);
		}

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{
						objectFieldName: 'customField',
						priority: 0,
					},
				],
				objectViewSortColumns: [
					{
						objectFieldName: 'customField',
						priority: 0,
						sortOrder: 'asc',
					},
				],
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const cells = page.getByRole('cell').getByRole('link');

		await expect(cells.first()).toHaveText('Entry A');
	}
);

test(
	'LPD-78504 Can see entries with filter applied in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanSeeEntriesWithFilterApplied
		// LPS-166589 - Verify that it's possible to view the object entries with the default filter applied

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const relationshipName = 'relationship' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			'User',
			{
				label: {en_US: 'Relationship'},
				name: relationshipName,
				objectDefinitionExternalReferenceCode1: 'User',
				objectDefinitionExternalReferenceCode2:
					objectDefinition.externalReferenceCode,
				objectDefinitionId2: objectDefinition.id,
				objectDefinitionName2: objectDefinition.name,
				type: 'oneToMany',
			}
		);

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields(['Custom Field']);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.filtersTab.click();

		await editObjectViewPage.newFilterButton.click();

		await editObjectViewPage.filterBy.click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel
			.getByRole('option', {name: 'Relationship'})
			.click();

		await editObjectViewPage.filterType.click();

		await sidePanel
			.getByRole('option', {name: 'Includes'})
			.click();

		await editObjectViewPage.saveFilter.click();

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await expect(sidePanel.getByText('Relationship')).toBeVisible();
	}
);

test(
	'LPD-78504 Can see renamed column name on object view entries list',
	{tag: '@LPD-78504'},
	async ({
		apiHelpers,
		editObjectViewPage,
		objectViewPage,
		page,
		viewObjectEntriesPage,
	}) => {
		// Migrated from: CanSeeRenamedColumnNameOnObjectView
		// LPS-147792 - Verify that the new column name will be displayed on the entries list

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Custom Field']);

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Custom Field'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Edit'}).click();

		const editModal = page.getByRole('dialog').filter({hasText: 'Label'});

		await editModal.getByLabel('Label').clear();
		await editModal.getByLabel('Label').fill('Column Label Renamed');

		await editModal.getByRole('button', {name: 'Edit'}).click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(
			page.getByRole('columnheader', {name: 'Column Label Renamed'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can see renamed column label as alias on view builder column list',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanSeeRenamedColumnOnViewBuilder
		// LPS-147792 - Verify it is possible to see the column label as the alias on the view builder column list

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.selectObjectFields(['Author']);

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel
			.locator('li[draggable="true"]')
			.filter({hasText: 'Author'})
			.getByRole('button')
			.click();

		await page.getByRole('menuitem', {name: 'Edit'}).click();

		const editModal = page.getByRole('dialog').filter({hasText: 'Label'});

		await editModal.getByLabel('Label').clear();
		await editModal.getByLabel('Label').fill('Publishing House');

		await editModal.getByRole('button', {name: 'Edit'}).click();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		await expect(
			sidePanel.getByText('Publishing House')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can sort column entries as ascending or descending',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanSortColumnEntries
		// LPS-144472 - Verify it is possible to sort the column entries as ascending or descending

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		for (const letter of ['A', 'B', 'C']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{customField: `Entry ${letter}`},
				applicationName
			);
		}

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{
						objectFieldName: 'customField',
						priority: 0,
					},
				],
				objectViewSortColumns: [
					{
						objectFieldName: 'customField',
						priority: 0,
						sortOrder: 'asc',
					},
				],
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const cells = page.getByRole('cell').getByRole('link');

		await expect(cells.first()).toHaveText('Entry A');
		await expect(cells.last()).toHaveText('Entry C');
	}
);

test(
	'LPD-78504 Can update a pre-order column in default sort',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanUpdatePreOrderColumn
		// LPS-144472 - Verify it is possible the user update the pre-order column

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields(['Author']);

		await expect(sidePanel.getByText('Author')).toBeVisible();

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		const nameInput = sidePanel.getByLabel('Name');

		await nameInput.clear();
		await nameInput.fill('Custom Views Edit');

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await expect(
			page.getByRole('link', {name: 'Custom Views Edit'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can update a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: CanUpdateView
		// LPS-135394 - Verify it is possible to update a View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		const nameInput = sidePanel.getByLabel('Name');

		await nameInput.clear();
		await nameInput.fill('New Custom Views');

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await expect(
			page.getByRole('link', {name: 'New Custom Views'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view values of two or more relationship fields for the same object',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanView2RelationshipFieldValues
		// LPS-148955 - Verify it is possible to view the values of 2 or more relationship fields for a same object

		const objectDefinitionA =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customFieldA',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field A'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customFieldA',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
				titleObjectFieldName: 'customFieldA',
			});

		apiHelpers.data.push({
			id: objectDefinitionA.id,
			type: 'objectDefinition',
		});

		const objectDefinitionB =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customFieldB',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field B'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customFieldB',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
				titleObjectFieldName: 'customFieldB',
			});

		apiHelpers.data.push({
			id: objectDefinitionB.id,
			type: 'objectDefinition',
		});

		const objectDefinitionC =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customFieldC',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field C'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customFieldC',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinitionC.id,
			type: 'objectDefinition',
		});

		const objectRelationshipAPIClient = await apiHelpers.buildRestClient(
			ObjectRelationshipAPI
		);

		const relNameA = 'relationshipA' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			objectDefinitionA.externalReferenceCode,
			{
				label: {en_US: 'Relationship A'},
				name: relNameA,
				objectDefinitionExternalReferenceCode1:
					objectDefinitionA.externalReferenceCode,
				objectDefinitionExternalReferenceCode2:
					objectDefinitionC.externalReferenceCode,
				objectDefinitionId1: objectDefinitionA.id,
				objectDefinitionId2: objectDefinitionC.id,
				objectDefinitionName2: objectDefinitionC.name,
				type: 'oneToMany',
			}
		);

		const relNameB = 'relationshipB' + getRandomInt();

		await objectRelationshipAPIClient.postObjectDefinitionByExternalReferenceCodeObjectRelationship(
			objectDefinitionB.externalReferenceCode,
			{
				label: {en_US: 'Relationship B'},
				name: relNameB,
				objectDefinitionExternalReferenceCode1:
					objectDefinitionB.externalReferenceCode,
				objectDefinitionExternalReferenceCode2:
					objectDefinitionC.externalReferenceCode,
				objectDefinitionId1: objectDefinitionB.id,
				objectDefinitionId2: objectDefinitionC.id,
				objectDefinitionName2: objectDefinitionC.name,
				type: 'oneToMany',
			}
		);

		const appNameA =
			'c/' + objectDefinitionA.name.toLowerCase() + 's';
		const appNameB =
			'c/' + objectDefinitionB.name.toLowerCase() + 's';

		const _entryA = await apiHelpers.objectEntry.postObjectEntry(
			{customFieldA: 'Entry A'},
			appNameA
		);

		const _entryB = await apiHelpers.objectEntry.postObjectEntry(
			{customFieldB: 'Entry B'},
			appNameB
		);

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinitionC.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{
						objectFieldName: relNameA,
						priority: 0,
					},
					{
						objectFieldName: relNameB,
						priority: 1,
					},
				],
			}
		);

		await viewObjectEntriesPage.goto(objectDefinitionC.className);

		await expect(
			page.getByRole('columnheader', {name: 'Relationship A'})
		).toBeVisible();
		await expect(
			page.getByRole('columnheader', {name: 'Relationship B'})
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can view entries from an object in a table view defined as default',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanViewEntries
		// LPS-144902 - Verify if the entries from an object in a table view defined as default are presented correctly

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{
						objectFieldName: 'customField',
						priority: 0,
					},
				],
			}
		);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await expect(page.getByText('Entry Test')).toBeVisible();
	}
);

test(
	'LPD-78504 Can view entries of object with default sort defined',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanViewEntriesWithDefaultSort
		// LPS-144472 - Verify it is possible the user see entries of object with default sort defined

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		for (const letter of ['A', 'B']) {
			await apiHelpers.objectEntry.postObjectEntry(
				{customField: `Entry ${letter}`},
				applicationName
			);
		}

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{
						objectFieldName: 'customField',
						priority: 0,
					},
				],
				objectViewSortColumns: [
					{
						objectFieldName: 'customField',
						priority: 0,
						sortOrder: 'asc',
					},
				],
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const cells = page.getByRole('cell').getByRole('link');

		await expect(cells.first()).toHaveText('Entry A');
		await expect(cells.last()).toHaveText('Entry B');
	}
);

test(
	'LPD-78504 Can view metadata values correctly displayed on a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanViewMetadataValues
		// LPS-143190 - Verify that the metadata values are correctly displayed on a Custom View

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{objectFieldName: 'author', priority: 0},
					{objectFieldName: 'createDate', priority: 1},
					{objectFieldName: 'modifiedDate', priority: 2},
					{objectFieldName: 'status', priority: 3},
					{objectFieldName: 'id', priority: 4},
				],
			}
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		for (const columnName of [
			'Author',
			'Create Date',
			'Modified Date',
			'Status',
			'ID',
		]) {
			await expect(
				page.getByRole('columnheader', {name: columnName})
			).toBeVisible();
		}
	}
);

test(
	'LPD-78504 Can view only selected columns on custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: CanViewOnlySelectedColumns
		// LPS-144902 - Verify if selected Columns on custom view are presented correctly during visualization

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{objectFieldName: 'author', priority: 0},
					{objectFieldName: 'createDate', priority: 1},
					{objectFieldName: 'customField', priority: 2},
				],
			}
		);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		for (const columnName of ['Author', 'Create Date', 'Custom Field']) {
			await expect(
				page.getByRole('columnheader', {name: columnName})
			).toBeVisible();
		}

		for (const columnName of [
			'External Reference Code',
			'Modified Date',
			'Status',
			'ID',
		]) {
			await expect(
				page.getByRole('columnheader', {name: columnName})
			).toBeHidden();
		}
	}
);

test(
	'LPD-78504 Can verify columns are ordered following the predefined order on custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		// Migrated from: ColumnsAreOrdered
		// LPS-144902 - Verify if the Columns on the custom view are presented following the predefined order during visualization

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customField',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customField',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const objectViewAPIClient =
			await apiHelpers.buildRestClient(ObjectViewAPI);

		await objectViewAPIClient.postObjectDefinitionObjectView(
			objectDefinition.id,
			{
				defaultObjectView: true,
				name: {en_US: 'CustomView' + getRandomInt()},
				objectViewColumns: [
					{objectFieldName: 'createDate', priority: 0},
					{objectFieldName: 'author', priority: 1},
					{objectFieldName: 'id', priority: 2},
				],
			}
		);

		const applicationName =
			'c/' + objectDefinition.name.toLowerCase() + 's';

		await apiHelpers.objectEntry.postObjectEntry(
			{customField: 'Entry Test'},
			applicationName
		);

		await viewObjectEntriesPage.goto(objectDefinition.className);

		const headers = page.getByRole('columnheader');

		const headerTexts: string[] = [];

		for (let i = 0; i < (await headers.count()); i++) {
			headerTexts.push(await headers.nth(i).textContent() || '');
		}

		const createDateIndex = headerTexts.findIndex((t) =>
			t.includes('Create Date')
		);
		const authorIndex = headerTexts.findIndex((t) =>
			t.includes('Author')
		);
		const idIndex = headerTexts.findIndex((t) => t.includes('ID'));

		expect(createDateIndex).toBeLessThan(authorIndex);
		expect(authorIndex).toBeLessThan(idIndex);
	}
);

test(
	'LPD-78504 Can verify duplicated object view columns are correctly ordered',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: DuplicatedObjectViewColumnsAreCorrectlyOrdered
		// LPS-146028 - Verify that the columns present at the View Builder are ordered correctly

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Author',
			'Create Date',
			'Modified Date',
			'Status',
			'ID',
		]);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page
			.getByRole('row', {name: viewName})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: 'Duplicate'}).click();

		await waitForAlert(page);

		await page
			.getByRole('link', {name: `${viewName} (Copy)`})
			.click();

		await editObjectViewPage.viewBuilderTab.click();

		const columns = sidePanel.locator('li[draggable="true"]');

		await expect(columns.nth(0)).toContainText('Author');
		await expect(columns.nth(1)).toContainText('Create Date');
		await expect(columns.nth(2)).toContainText('Modified Date');
		await expect(columns.nth(3)).toContainText('Status');
		await expect(columns.nth(4)).toContainText('ID');
	}
);

test(
	'LPD-78504 Can verify duplicated object view default sort fields are correctly ordered',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: DuplicatedObjectViewFieldsAreCorrectlyOrdered
		// LPS-146028 - Verify that the fields present at the Default Sort are ordered correctly

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields: [
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customFieldA',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field A'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customFieldA',
						required: false,
						system: false,
						type: 'String',
					},
					{
						DBType: 'String',
						businessType: 'Text',
						externalReferenceCode: 'customFieldB',
						indexed: true,
						indexedAsKeyword: false,
						indexedLanguageId: '',
						label: {en_US: 'Custom Field B'},
						listTypeDefinitionId: 0,
						localized: false,
						name: 'customFieldB',
						required: false,
						system: false,
						type: 'String',
					},
				],
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		const sidePanel = editObjectViewPage.sidePanel;

		await sidePanel.getByLabel('Mark as Default').check();

		await editObjectViewPage.selectObjectFields([
			'Custom Field A',
			'Custom Field B',
		]);

		await editObjectViewPage.saveButton.last().click();

		await waitForAlert(page);

		await page.reload();

		await page
			.getByRole('row', {name: viewName})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: 'Duplicate'}).click();

		await waitForAlert(page);

		await page
			.getByRole('link', {name: `${viewName} (Copy)`})
			.click();

		await editObjectViewPage.viewBuilderTab.click();

		const columns = sidePanel.locator('li[draggable="true"]');

		await expect(columns.nth(0)).toContainText('Custom Field A');
		await expect(columns.nth(1)).toContainText('Custom Field B');
	}
);

test(
	'LPD-78504 Can verify duplicated view has same original name with copy suffix',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: DuplicatedViewHasSameOriginalName
		// LPS-146028 - Verify that the view name is the same of the original, adding a (Copy) on the right side

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page
			.getByRole('row', {name: viewName})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: 'Duplicate'}).click();

		await waitForAlert(page);

		await page
			.getByRole('link', {name: `${viewName} (Copy)`})
			.click();

		const sidePanel = editObjectViewPage.sidePanel;

		await expect(sidePanel.getByLabel('Name')).toHaveValue(
			`${viewName} (Copy)`
		);
	}
);

test(
	'LPD-78504 Can verify duplicated view is not set as default',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: DuplicatedViewIsNotDefault
		// LPS-146028 - Verify that when the user duplicate a view, the Mark As Default option comes inactivated

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page
			.getByRole('row', {name: viewName})
			.getByRole('button', {name: 'Actions'})
			.click();

		await page.getByRole('menuitem', {name: 'Duplicate'}).click();

		await waitForAlert(page);

		await page
			.getByRole('link', {name: `${viewName} (Copy)`})
			.click();

		const sidePanel = editObjectViewPage.sidePanel;

		await expect(
			sidePanel.getByLabel('Mark as Default')
		).not.toBeChecked();
	}
);

test(
	'LPD-78504 Can verify empty state for the view builder tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: EmptyStateViewBuilder
		// LPS-135394 - Verify the empty state for the View Builder tab

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		const sidePanel = editObjectViewPage.sidePanel;

		await expect(
			sidePanel.getByText('No columns added yet.')
		).toBeVisible();

		await expect(
			sidePanel.getByText('Add columns to start creating a view.')
		).toBeVisible();
	}
);

test(
	'LPD-78504 Can verify empty state for the view tab',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: EmptyStateViewTab
		// LPS-135394 - Verify the empty state for the View tab

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);

test(
	'LPD-78504 Can verify metadata columns are displayed for selection in custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, editObjectViewPage, objectViewPage, page}) => {
		// Migrated from: MetadataColumnsDisplayed
		// LPS-135394 - Verify the Author, Create Date, Modified Date, Status, ID columns (Metadata columns) are displayed to be selected

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page.getByRole('link', {name: viewName}).click();

		await editObjectViewPage.viewBuilderTab.click();

		const addButton = editObjectViewPage.addColumnButton.or(
			editObjectViewPage.addButton
		);

		await addButton.click();

		const modal = editObjectViewPage.addColumnsModal;

		for (const columnOption of [
			'Author',
			'Create Date',
			'Modified Date',
			'Status',
			'ID',
		]) {
			await expect(modal.getByText(columnOption)).toBeVisible();
		}
	}
);

test(
	'LPD-78504 Can verify no result message when searching for a custom view',
	{tag: '@LPD-78504'},
	async ({apiHelpers, objectViewPage, page}) => {
		// Migrated from: NoResultMessageView
		// LPS-135394 - Verify the no result message when searching for a view

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		await objectViewPage.goto(objectDefinition.label['en_US']);

		const viewName = 'CustomView' + getRandomInt();

		await objectViewPage.createObjectView(viewName);

		await waitForAlert(page);

		await page
			.getByPlaceholder('Search')
			.fill('NonExistentView' + getRandomInt());

		await page.keyboard.press('Enter');

		await expect(page.getByText('No Results Found')).toBeVisible();
	}
);
