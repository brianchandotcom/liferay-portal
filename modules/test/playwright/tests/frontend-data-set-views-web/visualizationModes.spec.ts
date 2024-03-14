/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {visualizationModesPageTest} from './fixtures/visualizationModesPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
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

		await visualizationModesPage.openAssignFieldModal({
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
