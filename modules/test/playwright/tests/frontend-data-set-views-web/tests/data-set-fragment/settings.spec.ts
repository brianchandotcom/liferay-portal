/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

let settingsDataSetERC: string;
let dataSetLabel: string;

export const fragmentTest = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fdsFragmentPageTest,
	isolatedLayoutTest({publish: false}),
	loginTest()
);

fragmentTest.beforeEach(async ({dataSetManagerApiHelpers}) => {
	settingsDataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: settingsDataSetERC,
		label: dataSetLabel,
	});
});

fragmentTest.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: settingsDataSetERC});
});

fragmentTest.describe('Data Set Default Visualization Mode in fragment', () => {
	fragmentTest(
		'When there is only one visualization mode defined, that will be the default one. Cards',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
			await fragmentTest.step(
				'Assign a field to a Card title section',
				async () => {
					await dataSetManagerApiHelpers.createDataSetCardsSection({
						r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
							settingsDataSetERC,
					});
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						dataSetLabel,
						layout,
					});
				}
			);

			await fragmentTest.step('Data Set (Card) is present', async () => {
				await page
					.getByTestId('visualization-mode-cards')
					.waitFor({state: 'visible'});

				expect(await fdsFragmentPage.fdsCardsWrapper).toBeInViewport();
			});
		}
	);

	fragmentTest(
		'When there are more than one visualization mode defined (cards & list), the user could change the visualization option.',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
			await fragmentTest.step(
				'Assign a field to a Card and List title sections',
				async () => {
					await dataSetManagerApiHelpers.createDataSetCardsSection({
						r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
							settingsDataSetERC,
					});
					await dataSetManagerApiHelpers.createDataSetListSection({
						r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
							settingsDataSetERC,
					});
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						dataSetLabel,
						layout,
					});
				}
			);

			await fragmentTest.step('Check Data Set is present', async () => {
				await page
					.getByTestId('visualization-mode-cards')
					.waitFor({state: 'visible'});

				expect(await fdsFragmentPage.fdsCardsWrapper).toBeInViewport();
			});

			await fragmentTest.step(
				'Change Data Set Visualization option',
				async () => {
					await fdsFragmentPage.changeVisualizationMode('List');
				}
			);

			await fragmentTest.step(
				'Check Data Set Visualization option is list',
				async () => {
					await page
						.getByTestId('visualization-mode-list')
						.waitFor({state: 'visible'});

					expect(
						await fdsFragmentPage.fdsListWrapper
					).toBeInViewport();
				}
			);
		}
	);

	fragmentTest(
		'When there are more than one visualization modes defined, with a default selected (List), this will be the default one in the fragment.',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
			await fragmentTest.step(
				'Assign a field to a Card and List title sections',
				async () => {
					await dataSetManagerApiHelpers.createDataSetCardsSection({
						r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
							settingsDataSetERC,
					});
					await dataSetManagerApiHelpers.createDataSetListSection({
						r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
							settingsDataSetERC,
					});
				}
			);

			await fragmentTest.step(
				'Set List as default visualization mode',
				async () => {
					await dataSetManagerApiHelpers.updateDataSet({
						defaultVisualizationMode: 'list',
						erc: settingsDataSetERC,
					});
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						dataSetLabel,
						layout,
					});
				}
			);

			await fragmentTest.step(
				'Check Data Set is present (List)',
				async () => {
					await page
						.getByTestId('visualization-mode-list')
						.waitFor({state: 'visible'});

					await expect(
						fdsFragmentPage.fdsListWrapper
					).toBeInViewport();
				}
			);

			await fragmentTest.step(
				'Check Default Visualization Mode option',
				async () => {
					await fdsFragmentPage.fdsActiveViewSelector.waitFor({
						state: 'visible',
					});
					await fdsFragmentPage.fdsActiveViewSelector.click();

					await page
						.getByRole('listbox', {name: 'View Options'})
						.getByRole('option', {name: 'Cards', selected: false})
						.isVisible();

					await page
						.getByRole('listbox', {name: 'View Options'})
						.getByRole('option', {name: 'List', selected: true})
						.isVisible();
				}
			);
		}
	);

	fragmentTest(
		'When the default visualization mode is changed in the Data Set Manager, the change is reflected in the fragment',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
			await fragmentTest.step(
				'Assign a field to a Card and List title sections',
				async () => {
					await dataSetManagerApiHelpers.createDataSetCardsSection({
						r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
							settingsDataSetERC,
					});
					await dataSetManagerApiHelpers.createDataSetListSection({
						r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
							settingsDataSetERC,
					});
				}
			);

			await fragmentTest.step(
				'Set List as default visualization mode',
				async () => {
					await dataSetManagerApiHelpers.updateDataSet({
						defaultVisualizationMode: 'list',
						erc: settingsDataSetERC,
					});
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						dataSetLabel,
						layout,
					});
				}
			);

			await fragmentTest.step(
				'Check Data Set is present (List)',
				async () => {
					await page
						.getByTestId('visualization-mode-list')
						.waitFor({state: 'visible'});

					expect(
						await fdsFragmentPage.fdsListWrapper
					).toBeInViewport();
				}
			);

			await fragmentTest.step(
				'Check default visualization mode option',
				async () => {
					await fdsFragmentPage.fdsActiveViewSelector.waitFor({
						state: 'visible',
					});
					await fdsFragmentPage.fdsActiveViewSelector.click();

					await page
						.getByRole('listbox', {name: 'View Options'})
						.getByRole('option', {name: 'Cards', selected: false})
						.isVisible();

					await page
						.getByRole('listbox', {name: 'View Options'})
						.getByRole('option', {name: 'List', selected: true})
						.isVisible();
				}
			);

			await fragmentTest.step(
				'Change default visualization mode to Cards',
				async () => {
					await dataSetManagerApiHelpers.updateDataSet({
						defaultVisualizationMode: 'cards',
						erc: settingsDataSetERC,
					});
				}
			);

			await fragmentTest.step(
				'Reload page and check the default visualization mode',
				async () => {
					await page.reload();

					await page
						.getByTestId('visualization-mode-cards')
						.waitFor({state: 'visible'});

					expect(fdsFragmentPage.fdsCardsWrapper).toBeInViewport();

					await fdsFragmentPage.fdsActiveViewSelector.waitFor({
						state: 'visible',
					});
					await fdsFragmentPage.fdsActiveViewSelector.click();

					await page
						.getByRole('listbox', {name: 'View Options'})
						.getByRole('option', {name: 'Cards', selected: true})
						.isVisible();
				}
			);
		}
	);
});
