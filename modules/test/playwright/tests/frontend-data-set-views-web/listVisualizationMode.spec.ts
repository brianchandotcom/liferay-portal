/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {listVisualizationModePageTest} from './fixtures/listVisualizationModePageTest';
import {DEFAULT_LABEL} from './utils/constants';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-10735': true,
		'LPS-164563': true,
	}),
	listVisualizationModePageTest,
	loginTest()
);

test('Assign a field to a list section @LPD-10735', async ({
	dataSetManagerApiHelpers,
	listVisualizationModePage,
}) => {
	await test.step('Create sample data', async () => {
		await dataSetManagerApiHelpers.createDataSet({});
		await dataSetManagerApiHelpers.createDataSetView({});
	});

	await test.step('Navigate to list visualization mode', async () => {
		await listVisualizationModePage.goto({
			dataSetLabel: DEFAULT_LABEL.DATA_SET,
			viewLabel: DEFAULT_LABEL.VIEW,
		});
	});

	await test.step('Assign a field to title section', async () => {
		const listSectionLabel = 'Title';
		const fieldName = 'name';

		await listVisualizationModePage.openAssignFieldModal({
			listSectionLabel,
		});

		await listVisualizationModePage.fieldSelectModalContainer
			.getByLabel(fieldName)
			.check();

		await listVisualizationModePage.saveFieldSelection();

		const assignedFieldLocator =
			await listVisualizationModePage.getAssignedFieldLocator({
				listSectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(fieldName);
	});

	await test.step('Edit field to title section', async () => {
		const listSectionLabel = 'Title';
		const oldFieldName = 'name';
		const newFieldName = 'rendererType';

		await listVisualizationModePage.openAssignFieldModal({
			listSectionLabel,
		});

		expect(
			listVisualizationModePage.page.getByLabel(oldFieldName)
		).toBeChecked();

		await listVisualizationModePage.fieldSelectModalContainer
			.getByLabel(newFieldName)
			.check();

		await listVisualizationModePage.saveFieldSelection();

		const assignedFieldLocator =
			await listVisualizationModePage.getAssignedFieldLocator({
				listSectionLabel,
			});

		expect(assignedFieldLocator).toHaveText(newFieldName);
	});

	await test.step('Delete all sample data', async () => {
		await dataSetManagerApiHelpers.deleteDataSet({});
	});
});
