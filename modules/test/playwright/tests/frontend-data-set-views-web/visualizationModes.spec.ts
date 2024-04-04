/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {visualizationModesPageTest} from './fixtures/visualizationModesPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-186871': true,
	}),
	visualizationModesPageTest,
	loginTest()
);

let dataSetERC: string;
let viewERC: string;

const dataSetLabel: string = getRandomString();
const viewLabel: string = getRandomString();

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
	await dataSetManagerApiHelpers.createDataSetView({
		erc: viewERC,
		label: viewLabel,
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC: dataSetERC,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({
		erc: dataSetERC,
	});
});

test('Configure cards visualization mode @LPD-10735', async ({
	visualizationModesPage,
}) => {
	await test.step('Navigate to cards visualization mode page', async () => {
		await visualizationModesPage.goto({
			dataSetLabel,
			viewLabel,
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

		await visualizationModesPage.fieldSelectModalContainer
			.getByLabel(fieldName)
			.click();

		await expect(
			visualizationModesPage.page.getByLabel(fieldName)
		).toBeChecked();

		await visualizationModesPage.saveFieldSelection();

		const assignedFieldLocator =
			await visualizationModesPage.getAssignedFieldLocator({
				container,
				sectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(fieldName);
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

		await expect(
			visualizationModesPage.page.getByLabel(oldFieldName)
		).toBeChecked();

		await visualizationModesPage.fieldSelectModalContainer
			.getByLabel(newFieldName)
			.click();

		await expect(
			visualizationModesPage.page.getByLabel(newFieldName)
		).toBeChecked();

		await visualizationModesPage.saveFieldSelection();

		const assignedFieldLocator =
			await visualizationModesPage.getAssignedFieldLocator({
				container,
				sectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(newFieldName);
	});
});

test('Configure list visualization mode @LPD-10735', async ({
	visualizationModesPage,
}) => {
	await test.step('Navigate to list visualization mode page', async () => {
		await visualizationModesPage.goto({
			dataSetLabel,
			viewLabel,
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

		const container = visualizationModesPage.listVisualizationModeContainer;

		await visualizationModesPage.openAssignFieldModal({
			container,
			sectionLabel,
		});

		await visualizationModesPage.fieldSelectModalContainer
			.getByLabel(fieldName)
			.click();

		await expect(
			visualizationModesPage.page.getByLabel(fieldName)
		).toBeChecked();

		await visualizationModesPage.saveFieldSelection();

		const assignedFieldLocator =
			await visualizationModesPage.getAssignedFieldLocator({
				container,
				sectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(fieldName);
	});

	await test.step('Edit field to title section', async () => {
		const newFieldName = 'rendererType';
		const oldFieldName = 'name';
		const sectionLabel = 'Title';

		const container = visualizationModesPage.listVisualizationModeContainer;

		await visualizationModesPage.openChangeFieldModal({
			container,
			sectionLabel,
		});

		await expect(
			visualizationModesPage.page.getByLabel(oldFieldName)
		).toBeChecked();

		await visualizationModesPage.fieldSelectModalContainer
			.getByLabel(newFieldName)
			.click();

		await expect(
			visualizationModesPage.page.getByLabel(newFieldName)
		).toBeChecked();

		await visualizationModesPage.saveFieldSelection();

		const assignedFieldLocator =
			await visualizationModesPage.getAssignedFieldLocator({
				container,
				sectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(newFieldName);
	});
});

test('Configure table visualization mode @LPD-11049', async ({
	visualizationModesPage,
}) => {
	const SAMPLE_SCALAR_FIELD = 'id';
	const SAMPLE_OBJECT_FIELD = 'fdsViewFDSFieldRelationship';
	const SAMPLE_OBJECT_CHILD_FIELD = 'id';
	const SORTABLE_COLUMN_INDEX = 5;

	await test.step('Navigate to table visualization mode page', async () => {
		await visualizationModesPage.goto({
			dataSetLabel,
			viewLabel,
		});

		await visualizationModesPage.selectTab('Table');

		await expect(
			visualizationModesPage.page.getByPlaceholder('Search')
		).toBeVisible();
	});

	await test.step('Add fields', async () => {
		await visualizationModesPage.openAddFieldsModal();

		await visualizationModesPage.addRootField(SAMPLE_SCALAR_FIELD);
		await visualizationModesPage.addRootField(SAMPLE_OBJECT_FIELD);
		await visualizationModesPage.addChildField(
			[SAMPLE_OBJECT_FIELD],
			SAMPLE_OBJECT_CHILD_FIELD
		);

		await visualizationModesPage.saveAddFieldsModal();
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
		).toHaveText('true');
	});

	await test.step('Edit a field', async () => {
		await visualizationModesPage
			.getRowByText(SAMPLE_SCALAR_FIELD)
			.locator('.actions-cell button')
			.click();

		const editButton = visualizationModesPage.page.getByRole('menuitem', {
			name: 'Edit',
		});

		await expect(editButton).toBeInViewport();

		await editButton.click();

		const sortableInput =
			visualizationModesPage.page.getByLabel('Sortable');

		await expect(sortableInput).toBeInViewport();
		await expect(sortableInput).toBeEnabled();
		await expect(sortableInput).toBeChecked();

		await sortableInput.click();

		await expect(sortableInput).not.toBeChecked();

		await visualizationModesPage.saveAddFieldsModal();

		await expect(
			visualizationModesPage
				.getRowByText(SAMPLE_SCALAR_FIELD)
				.locator('td')
				.nth(SORTABLE_COLUMN_INDEX)
		).toHaveText('false');
	});

	await test.step('Check if object field has disabled sortable option', async () => {
		await visualizationModesPage
			.getRowByText(`${SAMPLE_OBJECT_FIELD}.*`)
			.locator('.actions-cell button')
			.click();

		const editButton = visualizationModesPage.page.getByRole('menuitem', {
			name: 'Edit',
		});

		await expect(editButton).toBeInViewport();

		await editButton.click();

		const sortableLabel =
			visualizationModesPage.page.getByLabel('Sortable');

		await expect(sortableLabel).toBeInViewport();

		await expect(sortableLabel).toBeDisabled();

		await visualizationModesPage.cancelAddFieldsModal();
	});
});
