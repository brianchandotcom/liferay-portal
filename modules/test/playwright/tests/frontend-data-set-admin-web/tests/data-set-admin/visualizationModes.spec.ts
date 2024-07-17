/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import saveFromModal from '../../utils/saveFromModal';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';
import {visualizationModesPageTest} from './fixtures/visualizationModesPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	visualizationModesPageTest,
	loginTest(),
	dataSetManagerSetupTest
);

let dataSetERC: string;

const dataSetLabel: string = getRandomString();

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({
		erc: dataSetERC,
	});
});

const clickActionInRow = async ({
	actionName,
	rowName,
	visualizationModesPage,
}) => {
	await visualizationModesPage
		.getRowByText(rowName)
		.locator('.actions-cell button')
		.click();

	const actionButton = visualizationModesPage.page.getByRole('menuitem', {
		name: actionName,
	});

	await expect(actionButton).toBeInViewport();

	await actionButton.click();
};

test.describe('Visualization Modes in Data Set Manager', () => {
	test('Configure cards visualization mode @LPD-10735', async ({
		page,
		visualizationModesPage,
	}) => {
		await test.step('Navigate to cards visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('Cards');

			await expect(
				visualizationModesPage.cardsVisualizationModeContainer
			).toBeVisible();
		});

		await test.step('Check if cards sections are correct', async () => {
			await expect(
				visualizationModesPage.cardsVisualizationModeContainer.locator(
					'.cards-section-label'
				)
			).toHaveText([
				'Card Element',
				'Title',
				'Description',
				'Image',
				'Symbol',
			]);
		});

		await test.step('Assign a field to title section', async () => {
			const fieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.cardsVisualizationModeContainer;

			await visualizationModesPage.openAssignFieldModal({
				container,
				sectionLabel,
			});

			await visualizationModesPage.selectField({fieldName});

			await saveFromModal({
				page,
			});

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			await expect(assignedFieldLocator).toHaveText(fieldName);
		});

		await test.step('Edit field to title section', async () => {
			const newFieldName = 'rendererType';
			const oldFieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.cardsVisualizationModeContainer;

			await visualizationModesPage.openChangeFieldModal({
				container,
				sectionLabel,
			});

			const oldCheckbox =
				visualizationModesPage.getFieldCheckboxByLabel(oldFieldName);

			await expect(oldCheckbox).toBeChecked();

			await visualizationModesPage.selectField({fieldName: newFieldName});

			await expect(oldCheckbox).not.toBeChecked();

			await saveFromModal({
				page,
			});

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			await expect(assignedFieldLocator).toHaveText(newFieldName);
		});
	});

	test('Configure list visualization mode @LPD-10735', async ({
		page,
		visualizationModesPage,
	}) => {
		await test.step('Navigate to list visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('List');

			await expect(
				visualizationModesPage.listVisualizationModeContainer
			).toBeVisible();
		});

		await test.step('Check if list sections are correct', async () => {
			await expect(
				visualizationModesPage.listVisualizationModeContainer.locator(
					'.list-section-label'
				)
			).toHaveText([
				'List Element',
				'Title',
				'Description',
				'Image',
				'Symbol',
			]);
		});

		await test.step('Assign a field to title section', async () => {
			const fieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.listVisualizationModeContainer;

			await visualizationModesPage.openAssignFieldModal({
				container,
				sectionLabel,
			});

			await visualizationModesPage.selectField({fieldName});

			await saveFromModal({
				page,
			});

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			await expect(assignedFieldLocator).toHaveText(fieldName);
		});

		await test.step('Edit field to title section', async () => {
			const newFieldName = 'rendererType';
			const oldFieldName = 'name';
			const sectionLabel = 'Title';

			const container =
				visualizationModesPage.listVisualizationModeContainer;

			await visualizationModesPage.openChangeFieldModal({
				container,
				sectionLabel,
			});

			const oldCheckbox =
				visualizationModesPage.getFieldCheckboxByLabel(oldFieldName);

			await expect(oldCheckbox).toBeChecked();

			await visualizationModesPage.selectField({fieldName: newFieldName});

			await expect(oldCheckbox).not.toBeChecked();

			await saveFromModal({
				page,
			});

			const assignedFieldLocator =
				await visualizationModesPage.getAssignedFieldLocator({
					container,
					sectionLabel,
				});

			await expect(assignedFieldLocator).toHaveText(newFieldName);
		});
	});

	test('Configure table visualization mode @LPD-11049', async ({
		page,
		visualizationModesPage,
	}) => {
		const SAMPLE_SCALAR_FIELD = 'id';
		const SAMPLE_OBJECT_FIELD = 'fdsViewFDSFieldRelationship';
		const SAMPLE_OBJECT_CHILD_FIELD = 'id';
		const SORTABLE_COLUMN_INDEX = 5;

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Add fields', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_SCALAR_FIELD,
			});

			await visualizationModesPage.selectField({
				dataId: `${SAMPLE_OBJECT_FIELD}.*`,
				fieldName: SAMPLE_OBJECT_FIELD,
			});

			await visualizationModesPage.selectField({
				dataId: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				fieldName: SAMPLE_OBJECT_CHILD_FIELD,
			});

			await saveFromModal({
				page,
			});
		});

		await test.step('Check if field defaults are correct', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('true');

			await expect(
				visualizationModesPage
					.getRowByText(`${SAMPLE_OBJECT_FIELD}.*`)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('false');

			await expect(
				visualizationModesPage
					.getRowByText(
						`${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`
					)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('false');
		});

		await test.step('Edit a field', async () => {
			await clickActionInRow({
				actionName: 'Edit',
				rowName: SAMPLE_SCALAR_FIELD,
				visualizationModesPage,
			});

			const sortableInput =
				visualizationModesPage.page.getByLabel('Sortable');

			await expect(sortableInput).toBeInViewport();
			await expect(sortableInput).toBeEnabled();
			await expect(sortableInput).toBeChecked();

			await sortableInput.click();

			await expect(sortableInput).not.toBeChecked();

			await saveFromModal({
				page,
			});

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('false');
		});

		await test.step('Check if object field has disabled sortable option', async () => {
			await clickActionInRow({
				actionName: 'Edit',
				rowName: `${SAMPLE_OBJECT_FIELD}.*`,
				visualizationModesPage,
			});

			const sortableLabel =
				visualizationModesPage.page.getByLabel('Sortable');

			await expect(sortableLabel).toBeInViewport();

			await expect(sortableLabel).toBeDisabled();

			await visualizationModesPage.cancelAddFieldsModal();
		});
	});

	test('Add a field and assert its added to the last position in the table and assert fields can be reordered using a keyboard', async ({
		page,
		visualizationModesPage,
	}) => {
		const SAMPLE_FIELD = 'name';
		const SAMPLE_SCALAR_FIELD = 'id';
		const SAMPLE_OBJECT_FIELD = 'fdsViewFDSFieldRelationship';
		const SAMPLE_OBJECT_CHILD_FIELD = 'id';

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Add fields', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_SCALAR_FIELD,
			});

			await visualizationModesPage.selectField({
				dataId: `${SAMPLE_OBJECT_FIELD}.*`,
				fieldName: SAMPLE_OBJECT_FIELD,
			});

			await visualizationModesPage.selectField({
				dataId: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				fieldName: SAMPLE_OBJECT_CHILD_FIELD,
			});

			await saveFromModal({
				page,
			});
		});

		await test.step('Add a new field', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_FIELD,
			});

			await saveFromModal({
				page,
			});
		});

		await test.step('Check if field is added to the last position', async () => {
			const lastTableRow = visualizationModesPage.page.locator(
				'table.orderable-table > tbody tr:last-child'
			);

			await expect(lastTableRow.locator('td').nth(1)).toHaveText(
				SAMPLE_FIELD
			);

			await visualizationModesPage.assertTableFieldRowCount(4);
		});

		await test.step('Focus the last field', async () => {
			const lastTableRow = visualizationModesPage.page.locator(
				'table.orderable-table > tbody tr:last-child'
			);

			await expect(lastTableRow).toBeVisible();

			const firstCell = lastTableRow.locator('td > button').first();

			await expect(firstCell).toBeVisible();

			await firstCell.focus();

			await expect(firstCell).toBeFocused();
		});

		await test.step('Move the field one place up', async () => {
			await page.keyboard.press('Enter');

			await page.keyboard.press('ArrowUp');

			await page.keyboard.press('Enter');
		});

		await test.step('Assert that the field has moved one place up', async () => {
			const tableRows = visualizationModesPage.page.locator(
				'table.orderable-table > tbody tr'
			);

			const tableRowsCount = await tableRows.count();

			expect(tableRowsCount).toEqual(4);

			const expectedTexts = [
				SAMPLE_SCALAR_FIELD,
				`${SAMPLE_OBJECT_FIELD}.*`,
				SAMPLE_FIELD,
				`${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
			];

			for (let i = 0; i < expectedTexts.length; i++) {
				const row = tableRows.nth(i);

				await expect(row).toBeVisible();

				const secondColumn = row.locator('td').nth(1);

				await expect(secondColumn).toBeVisible();

				const text = await secondColumn.innerText();

				expect(text).toBe(expectedTexts[i]);
			}
		});
	});

	test('Configure table visualization mode using search with array fields @LPD-11769, @LPS-185231, LPS-185227', async ({
		page,
		visualizationModesPage,
	}) => {
		const SAMPLE_COMPLEX_ARRAY_FIELD = 'auditEvents[]*';
		const SAMPLE_COMPLEX_ARRAY_CHILD_FIELD = 'auditEvents[]creator.name';
		const SAMPLE_SCALAR_ARRAY_FIELD = 'keywords';
		const SAMPLE_FULL_COMPLEX_FIELD = 'creator.*';
		const SAMPLE_COMPLEX_OBJECT_CHILD_FIELD = 'creator.givenName';
		const SORTABLE_COLUMN_INDEX = 5;
		const TYPE_COLUMN_INDEX = 3;

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Add scalar array field', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.searchAndSelecteField(
				SAMPLE_SCALAR_ARRAY_FIELD
			);
			await visualizationModesPage.searchAndSelecteField(
				SAMPLE_COMPLEX_ARRAY_FIELD
			);
			await visualizationModesPage.searchAndSelecteField(
				SAMPLE_COMPLEX_ARRAY_CHILD_FIELD
			);
			await visualizationModesPage.searchAndSelecteField(
				SAMPLE_COMPLEX_OBJECT_CHILD_FIELD
			);
			await visualizationModesPage.searchAndSelecteField(
				SAMPLE_FULL_COMPLEX_FIELD
			);

			await saveFromModal({
				page,
			});
		});

		await test.step('Check if fields show the correct type', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_ARRAY_FIELD)
					.locator('td')
					.nth(TYPE_COLUMN_INDEX)
			).toHaveText('array');

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_ARRAY_FIELD)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('false');

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_COMPLEX_ARRAY_FIELD)
					.locator('td')
					.nth(TYPE_COLUMN_INDEX)
			).toHaveText('array');

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_COMPLEX_ARRAY_FIELD)
					.locator('td')
					.nth(SORTABLE_COLUMN_INDEX)
			).toHaveText('false');

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_COMPLEX_OBJECT_CHILD_FIELD)
					.locator('td')
					.nth(TYPE_COLUMN_INDEX)
			).toHaveText('string');

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_FULL_COMPLEX_FIELD)
					.locator('td')
					.nth(TYPE_COLUMN_INDEX)
			).toHaveText('object');
		});
	});

	test('Check cancel in table visualization mode', async ({
		page,
		visualizationModesPage,
	}) => {
		const SAMPLE_SCALAR_FIELD = 'id';
		const SAMPLE_OBJECT_FIELD = 'fdsViewFDSFieldRelationship';
		const LABEL_COLUMN_INDEX = 2;

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Add one field, but cancel the operation @LPS-185230', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_SCALAR_FIELD,
			});

			await visualizationModesPage.cancelAddFieldsModal();

			await visualizationModesPage.assertTableFieldRowCount(0);
		});

		await test.step('Add one field, save', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_SCALAR_FIELD,
			});

			await saveFromModal({
				page,
			});
		});

		await test.step('Unselect selected field. Select another. Cancel @LPS-185230', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.unSelectField({
				fieldName: SAMPLE_SCALAR_FIELD,
			});

			await visualizationModesPage.selectField({
				dataId: `${SAMPLE_OBJECT_FIELD}.*`,
				fieldName: SAMPLE_OBJECT_FIELD,
			});

			await visualizationModesPage.cancelAddFieldsModal();
		});

		await test.step('Check there is one field and is the one just added', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(LABEL_COLUMN_INDEX)
			).toHaveText(SAMPLE_SCALAR_FIELD);

			await visualizationModesPage.assertTableFieldRowCount(1);
		});

		await test.step('Edit a field, change its label, cancel @LPS-176051 @LPS-178736 @LPS-179151', async () => {
			await clickActionInRow({
				actionName: 'Edit',
				rowName: SAMPLE_SCALAR_FIELD,
				visualizationModesPage,
			});

			const labelInput = visualizationModesPage.page.getByLabel('Label');

			await expect(labelInput).toBeInViewport();

			await expect(labelInput).toBeEnabled();

			await labelInput.fill('New label for field');

			await visualizationModesPage.cancelAddFieldsModal();
		});

		await test.step('Check there is one field and is the one just added', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(LABEL_COLUMN_INDEX)
			).toHaveText(SAMPLE_SCALAR_FIELD);

			await visualizationModesPage.assertTableFieldRowCount(1);
		});

		await test.step('Delete a field, cancel @LPS-185500', async () => {
			await clickActionInRow({
				actionName: 'Delete',
				rowName: SAMPLE_SCALAR_FIELD,
				visualizationModesPage,
			});

			await visualizationModesPage.cancelAddFieldsModal();
		});

		await test.step('Check there is one field and is the one just added', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(LABEL_COLUMN_INDEX)
			).toHaveText(SAMPLE_SCALAR_FIELD);

			await visualizationModesPage.assertTableFieldRowCount(1);
		});
	});

	test('Check field edition in table visualization mode @LPS-176051, @LPS-178736', async ({
		page,
		visualizationModesPage,
	}) => {
		const SAMPLE_SCALAR_FIELD = 'id';
		const SAMPLE_FIELD = 'name';
		const LABEL_COLUMN_INDEX = 2;
		const RENDERER_COLUMN_INDEX = 4;

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Add one field, save', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_SCALAR_FIELD,
			});

			await saveFromModal({
				page,
			});
		});

		await test.step('Check there is one field and is the one just added', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(LABEL_COLUMN_INDEX)
			).toHaveText(SAMPLE_SCALAR_FIELD);

			await visualizationModesPage.assertTableFieldRowCount(1);
		});

		await test.step('Add another field, save', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_FIELD,
			});

			await saveFromModal({
				page,
			});
		});

		await test.step('Check there are two fields', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(LABEL_COLUMN_INDEX)
			).toHaveText(SAMPLE_SCALAR_FIELD);

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_FIELD)
					.locator('td')
					.nth(LABEL_COLUMN_INDEX)
			).toHaveText(SAMPLE_FIELD);

			await visualizationModesPage.assertTableFieldRowCount(2);
		});

		await test.step('Delete field', async () => {
			await clickActionInRow({
				actionName: 'Delete',
				rowName: SAMPLE_FIELD,
				visualizationModesPage,
			});

			const deleteModal =
				await visualizationModesPage.page.getByRole('dialog');

			await expect(deleteModal).toContainText(
				'Are you sure you want to delete this field? It will be removed immediately. Fragments using it will be affected. This action cannot be undone.'
			);

			await deleteModal.getByRole('button', {name: 'Delete'}).click();

			const toastContainer = page.locator('.alert-container');

			await expect(toastContainer.getByText('Success')).toBeInViewport();

			await toastContainer
				.getByRole('button', {
					name: 'Close',
				})
				.click();
		});

		await test.step('Check that there is only one field', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(LABEL_COLUMN_INDEX)
			).toHaveText(SAMPLE_SCALAR_FIELD);

			await visualizationModesPage.assertTableFieldRowCount(1);
		});

		await test.step('Open field edition modal, check that name field is not editable', async () => {
			await clickActionInRow({
				actionName: 'Edit',
				rowName: SAMPLE_SCALAR_FIELD,
				visualizationModesPage,
			});

			const editModal =
				await visualizationModesPage.page.getByRole('dialog');

			await expect(editModal.getByRole('heading')).toContainText(
				`Edit ${SAMPLE_SCALAR_FIELD}`
			);

			const nameInput = visualizationModesPage.page.getByLabel('Name');

			await expect(nameInput).toBeInViewport();

			await expect(nameInput).toBeDisabled();

			await visualizationModesPage.cancelAddFieldsModal();
		});

		await test.step('Open field edition modal, check that the user can change the renderer', async () => {
			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(RENDERER_COLUMN_INDEX)
			).toHaveText('Default');

			await clickActionInRow({
				actionName: 'Edit',
				rowName: SAMPLE_SCALAR_FIELD,
				visualizationModesPage,
			});

			const rendererButton = page.getByRole('button', {name: 'Default'});
			await expect(rendererButton).toBeInViewport();

			const rendererDropdownId = await rendererButton.evaluate((node) => {
				return node.getAttribute('aria-controls');
			});
			await rendererButton.click();

			await page.locator(`#${rendererDropdownId}`).waitFor();

			const availbleRenderersCount = await page
				.locator(`#${rendererDropdownId}`)
				.getByRole('option')
				.count();
			await expect(availbleRenderersCount).toBeGreaterThanOrEqual(10);

			await page
				.locator(`#${rendererDropdownId}`)
				.getByRole('option', {name: 'Boolean'})
				.click();

			await saveFromModal({
				page,
			});

			await expect(
				visualizationModesPage
					.getRowByText(SAMPLE_SCALAR_FIELD)
					.locator('td')
					.nth(RENDERER_COLUMN_INDEX)
			).toHaveText('Boolean');
		});
	});

	test('Check modal field selection allows check and uncheck fields @LPS-174141, @LPS-185228, @LPS-179282', async ({
		page,
		visualizationModesPage,
	}) => {
		const SAMPLE_SCALAR_FIELD = 'externalReferenceCode';
		const SAMPLE_FIELD = 'name';

		await test.step('Navigate to table visualization mode page', async () => {
			await visualizationModesPage.goto({
				dataSetLabel,
			});

			await visualizationModesPage.selectTab('Table');

			await expect(
				visualizationModesPage.page.getByPlaceholder('Search')
			).toBeVisible();
		});

		await test.step('Can check and uncheck fields in the field selection modal', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({fieldName: SAMPLE_FIELD});

			const checkbox =
				visualizationModesPage.getFieldCheckboxByLabel(SAMPLE_FIELD);

			await expect(checkbox).toBeChecked();

			await visualizationModesPage.unSelectField({
				fieldName: SAMPLE_FIELD,
			});

			await expect(checkbox).not.toBeChecked();

			await saveFromModal({
				page,
			});
		});

		await test.step('Can check some fields and uncheck all selected fields using Deselect All button', async () => {
			await visualizationModesPage.openAddFieldsModal();

			await visualizationModesPage.selectField({fieldName: SAMPLE_FIELD});

			const sampleFieldCheckbox =
				visualizationModesPage.getFieldCheckboxByLabel(SAMPLE_FIELD);

			await expect(sampleFieldCheckbox).toBeChecked();

			await visualizationModesPage.selectField({
				fieldName: SAMPLE_SCALAR_FIELD,
			});

			const sampleScalarFieldCheckbox =
				visualizationModesPage.getFieldCheckboxByLabel(
					SAMPLE_SCALAR_FIELD
				);

			await expect(sampleScalarFieldCheckbox).toBeChecked();

			await visualizationModesPage.unSelectSelectedFields();

			await expect(sampleFieldCheckbox).not.toBeChecked();
			await expect(sampleScalarFieldCheckbox).not.toBeChecked();

			await saveFromModal({
				page,
			});
		});

		await test.step('Check there is no field added', async () => {
			await visualizationModesPage.assertTableFieldRowCount(0);
		});
	});
});
