/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {dataSetsPageTest} from './fixtures/dataSetsPageTest';
import {fieldsPageTest} from './fixtures/fieldsPageTest';
import {settingsPageTest} from './fixtures/settingsPageTest';
import {visualizationModesPageTest} from './fixtures/visualizationModesPageTest';
import {DEFAULT_LABEL} from './utils/constants';

export const test = mergeTests(
	dataSetsPageTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPD-10735': true,
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	fieldsPageTest,
	loginTest(),
	settingsPageTest,
	visualizationModesPageTest
);

// test.beforeEach(async ({dataSetManagerApiHelpers}) => {
// 	await dataSetManagerApiHelpers.createDataSet({erc: settingsDataSetERC});
// 	await dataSetManagerApiHelpers.createDataSetView({erc: settingsDataSetERC});
// });

test.describe('Data Set Settings', () => {
	test.describe('Default Visualization Mode', () => {
		test('If Default Visualization Mode is not configured allows user to navigate to Visualization Mode section', async ({
			dataSetManagerApiHelpers,
			settingsPage,
			visualizationModesPage,
		}) => {
			const customDataSetERC = getRandomString();

			await test.step('Navigate to Settings section', async () => {
				await dataSetManagerApiHelpers.createDataSet({
					erc: customDataSetERC,
				});
				await dataSetManagerApiHelpers.createDataSetView({
					r_fdsEntryFDSViewRelationship_c_fdsEntryERC:
						customDataSetERC,
				});
			});

			await test.step('Navigate to Settings section', async () => {
				await settingsPage.goto({
					dataSetLabel: DEFAULT_LABEL.DATA_SET,
					viewLabel: DEFAULT_LABEL.VIEW,
				});

				await expect(
					settingsPage.defaultVisualizationModeLabel
				).toBeInViewport();
			});

			await test.step('Check Default Visualization Mode', async () => {
				await expect(
					settingsPage.defaultVisualizationModeLabel
				).toContainText('Not Configured');
			});

			await test.step('Navigate to Visualization Mode section if  "Not Configured"', async () => {
				await settingsPage.defaultVisualizationModeLabel.click();

				await expect(
					settingsPage.configureNewLayoutButton
				).toBeInViewport();

				await settingsPage.configureNewLayoutButton.click();

				await expect(
					visualizationModesPage.page.getByText(
						'No fields added yet.'
					)
				).toBeInViewport();
			});

			await test.step('Clean data', async () => {
				await dataSetManagerApiHelpers.deleteDataSet({
					erc: customDataSetERC,
				});
			});
		});

		test('When there is only one visualization mode defined, that will be the default one.', async ({
			dataSetManagerApiHelpers,
			settingsPage,
		}) => {
			const customDataSetERC = getRandomString();

			const customViewERC =
				await test.step('Navigate to Settings section', async () => {
					await dataSetManagerApiHelpers.createDataSet({
						erc: customDataSetERC,
					});

					const viewERC =
						await dataSetManagerApiHelpers.createDataSetView({
							r_fdsEntryFDSViewRelationship_c_fdsEntryERC:
								customDataSetERC,
						});

					return viewERC.externalReferenceCode;
				});

			await test.step('Assign a field to a Card title section', async () => {
				await dataSetManagerApiHelpers.createDataSetViewCardsSection({
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
						customViewERC,
				});
			});

			await test.step('Navigate to Settings section', async () => {
				await settingsPage.goto({
					dataSetLabel: DEFAULT_LABEL.DATA_SET,
					viewLabel: DEFAULT_LABEL.VIEW,
				});

				await expect(
					settingsPage.defaultVisualizationModeLabel
				).toBeInViewport();
			});

			await test.step('Check Default Visualization Mode', async () => {
				await expect(
					settingsPage.defaultVisualizationModeLabel
				).toContainText('Cards');
			});

			await test.step('Clean data', async () => {
				await dataSetManagerApiHelpers.deleteDataSet({
					erc: customDataSetERC,
				});
			});
		});

		test('When there are more than one visualization mode defined, the user could select the default one.', async ({
			dataSetManagerApiHelpers,
			settingsPage,
		}) => {
			const customDataSetERC = getRandomString();

			const customViewERC =
				await test.step('Navigate to Settings section', async () => {
					await dataSetManagerApiHelpers.createDataSet({
						erc: customDataSetERC,
					});

					const viewERC =
						await dataSetManagerApiHelpers.createDataSetView({
							r_fdsEntryFDSViewRelationship_c_fdsEntryERC:
								customDataSetERC,
						});

					return viewERC.externalReferenceCode;
				});

			await test.step('Assign a field to title section for Cards and List', async () => {
				await dataSetManagerApiHelpers.createDataSetViewCardsSection({
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
						customViewERC,
				});
				await dataSetManagerApiHelpers.createDataSetViewListSection({
					r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
						customViewERC,
				});
			});

			await test.step('Navigate to Settings section', async () => {
				await settingsPage.goto({
					dataSetLabel: DEFAULT_LABEL.DATA_SET,
					viewLabel: DEFAULT_LABEL.VIEW,
				});

				await expect(
					settingsPage.defaultVisualizationModeLabel
				).toBeInViewport();
			});

			await test.step('Check Default Visualization Mode', async () => {
				await expect(
					settingsPage.defaultVisualizationModeLabel
				).toContainText('Cards');
			});

			await test.step('Change Default Visualization Mode', async () => {
				await settingsPage.defaultVisualizationModeLabel.click();

				await settingsPage.page
					.getByRole('option', {name: 'List'})
					.isVisible();

				await settingsPage.page
					.getByRole('option', {name: 'List'})
					.click();
			});

			await test.step('Save Default Visualization Mode', async () => {
				await settingsPage.saveButton.click();

				await settingsPage.toastContainer.isVisible();

				await settingsPage.page
					.getByText('Success:Your request completed successfully.')
					.waitFor();

				await settingsPage.toastContainer
					.getByRole('button', {
						name: 'Close',
					})
					.click();

				await settingsPage.toastContainer.isHidden();

				await expect(
					settingsPage.defaultVisualizationModeLabel
				).toContainText('List');
			});

			await test.step('Clean data', async () => {
				await dataSetManagerApiHelpers.deleteDataSet({
					erc: customDataSetERC,
				});
			});
		});
	});
});

// test.afterEach(async ({dataSetManagerApiHelpers}) => {
// 	await dataSetManagerApiHelpers.deleteDataSet({erc: settingsDataSetERC});
// });
