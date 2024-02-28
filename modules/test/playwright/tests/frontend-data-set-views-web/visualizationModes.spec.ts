/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {loginTest} from '../../fixtures/loginTest';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {visualizationModesPageTest} from './fixtures/visualizationModesPageTest';
import {DEFAULT_LABEL} from './utils/constants';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	visualizationModesPageTest,
	loginTest()
);

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.createDataSet({});
	await dataSetManagerApiHelpers.createDataSetView({});
});

test('Assign field to a cards section @LPD-10735', async ({
	visualizationModesPage,
}) => {
	await test.step('Navigate to cards visualization mode', async () => {
		await visualizationModesPage.goto({
			dataSetLabel: DEFAULT_LABEL.DATA_SET,
			viewLabel: DEFAULT_LABEL.VIEW,
		});

		await visualizationModesPage.selectTab('Cards');

		await visualizationModesPage.page
			.getByText('Cards Element', {exact: true})
			.isVisible();
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
			.check();

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

		expect(
			visualizationModesPage.page.getByLabel(oldFieldName)
		).toBeChecked();

		await visualizationModesPage.fieldSelectModalContainer
			.getByLabel(newFieldName)
			.check();

		await visualizationModesPage.saveFieldSelection();

		const assignedFieldLocator =
			await visualizationModesPage.getAssignedFieldLocator({
				container,
				sectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(newFieldName);
	});
});

test('Assign field to a list section @LPD-10735', async ({
	visualizationModesPage,
}) => {
	await test.step('Navigate to list visualization mode', async () => {
		await visualizationModesPage.goto({
			dataSetLabel: DEFAULT_LABEL.DATA_SET,
			viewLabel: DEFAULT_LABEL.VIEW,
		});

		await visualizationModesPage.selectTab('List');

		await visualizationModesPage.page
			.getByText('List Element', {exact: true})
			.isVisible();
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
			.check();

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

		await visualizationModesPage.openAssignFieldModal({
			container,
			sectionLabel,
		});

		expect(
			visualizationModesPage.page.getByLabel(oldFieldName)
		).toBeChecked();

		await visualizationModesPage.fieldSelectModalContainer
			.getByLabel(newFieldName)
			.check();

		await visualizationModesPage.saveFieldSelection();

		const assignedFieldLocator =
			await visualizationModesPage.getAssignedFieldLocator({
				container,
				sectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(newFieldName);
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({});
});
