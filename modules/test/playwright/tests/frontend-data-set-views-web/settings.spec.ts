/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from './fixtures/dataSetManagerApiHelpersTest';
import {dataSetManagerSetupTest} from './fixtures/dataSetManagerSetupTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';
import {settingsPageTest} from './fixtures/settingsPageTest';
import {visualizationModesPageTest} from './fixtures/visualizationModesPageTest';

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
	}),
	loginTest(),
	dataSetManagerSetupTest,
	settingsPageTest,
	visualizationModesPageTest
);

let settingsDataSetERC: string;
let settingsDataSetLabel: string;
let settingsDataSetViewERC: string;
let settingsDataSetViewLabel: string;

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	settingsDataSetERC = getRandomString();
	settingsDataSetLabel = getRandomString();
	settingsDataSetViewERC = getRandomString();
	settingsDataSetViewLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: settingsDataSetERC,
		label: settingsDataSetLabel,
	});
	await dataSetManagerApiHelpers.createDataSetView({
		erc: settingsDataSetViewERC,
		label: settingsDataSetViewLabel,
		r_fdsEntryFDSViewRelationship_c_fdsEntryERC: settingsDataSetERC,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: settingsDataSetERC});
});

test.describe('Data Set Settings', () => {
	test.describe('Default Visualization Mode', () => {
		test('If Default Visualization Mode is not configured allows user to navigate to Visualization Mode section', async ({
			settingsPage,
			visualizationModesPage,
		}) => {
			await test.step('Navigate to Settings section', async () => {
				await settingsPage.goto({
					dataSetLabel: settingsDataSetLabel,
					viewLabel: settingsDataSetViewLabel,
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
				await expect(
					settingsPage.goToVisualizationModesLink
				).toBeInViewport();

				await settingsPage.goToVisualizationModesLink.click();

				await expect(
					visualizationModesPage.page.getByText(
						'No fields added yet.'
					)
				).toBeInViewport();
			});
		});

		test('When there is only one visualization mode defined, that will be the default one.', async ({
			dataSetManagerApiHelpers,
			page,
			settingsPage,
		}) => {
			await test.step('Assign a field to a Card title section', async () => {
				await dataSetManagerApiHelpers.createDataSetViewCardsSection({
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
						settingsDataSetViewERC,
				});

				await page.reload();
			});

			await test.step('Navigate to Settings section', async () => {
				await settingsPage.goto({
					dataSetLabel: settingsDataSetLabel,
					viewLabel: settingsDataSetViewLabel,
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
		});

		test('When there are more than one visualization mode defined, the user could select the default one.', async ({
			dataSetManagerApiHelpers,
			page,
			settingsPage,
		}) => {
			await test.step('Assign a field to title section for Cards and List', async () => {
				await dataSetManagerApiHelpers.createDataSetViewCardsSection({
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
						settingsDataSetViewERC,
				});
				await dataSetManagerApiHelpers.createDataSetViewListSection({
					r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
						settingsDataSetViewERC,
				});

				await page.reload();
			});

			await test.step('Navigate to Settings section', async () => {
				await settingsPage.goto({
					dataSetLabel: settingsDataSetLabel,
					viewLabel: settingsDataSetViewLabel,
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
		});
	});
});

export const fragmentTest = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	fdsFragmentPageTest,
	isolatedSiteTest,
	loginTest()
);

fragmentTest.describe('Data Set Default Visualization Mode in fragment', () => {
	fragmentTest(
		'Default Visualization Mode is not configured (no field for any section). FDS does not appear.',
		async ({apiHelpers, fdsFragmentPage, page, site}) => {
			const layout = await fragmentTest.step(
				'Create a new page',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: settingsDataSetViewLabel,
					});
				}
			);

			fragmentTest.step('No Data Set is in the page', async () => {
				await expect(
					page.locator('.data-set-wrapper')
				).not.toBeVisible();
			});
		}
	);

	fragmentTest(
		'When there is only one visualization mode defined, that will be the default one. Cards',
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			await fragmentTest.step(
				'Assign a field to a Card title section',
				async () => {
					await dataSetManagerApiHelpers.createDataSetViewCardsSection(
						{
							r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
								settingsDataSetViewERC,
						}
					);
				}
			);

			const layout = await fragmentTest.step(
				'Create a page with a Data Set fragment',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: settingsDataSetViewLabel,
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
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			await fragmentTest.step(
				'Assign a field to a Card and List title sections',
				async () => {
					await dataSetManagerApiHelpers.createDataSetViewCardsSection(
						{
							r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
								settingsDataSetViewERC,
						}
					);
					await dataSetManagerApiHelpers.createDataSetViewListSection(
						{
							r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
								settingsDataSetViewERC,
						}
					);
				}
			);

			const layout = await fragmentTest.step(
				'Create a page with a Data Set fragment',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: settingsDataSetViewLabel,
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
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			await fragmentTest.step(
				'Assign a field to a Card and List title sections',
				async () => {
					await dataSetManagerApiHelpers.createDataSetViewCardsSection(
						{
							r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
								settingsDataSetViewERC,
						}
					);
					await dataSetManagerApiHelpers.createDataSetViewListSection(
						{
							r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
								settingsDataSetViewERC,
						}
					);
				}
			);

			await fragmentTest.step(
				'Set List as default visualization mode',
				async () => {
					await dataSetManagerApiHelpers.updateDataSetView({
						defaultVisualizationMode: 'list',
						erc: settingsDataSetViewERC,
					});
				}
			);

			const layout = await fragmentTest.step(
				'Create a page with a Data Set fragment',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: settingsDataSetViewLabel,
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
		async ({
			apiHelpers,
			dataSetManagerApiHelpers,
			fdsFragmentPage,
			page,
			site,
		}) => {
			await fragmentTest.step(
				'Assign a field to a Card and List title sections',
				async () => {
					await dataSetManagerApiHelpers.createDataSetViewCardsSection(
						{
							r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
								settingsDataSetViewERC,
						}
					);
					await dataSetManagerApiHelpers.createDataSetViewListSection(
						{
							r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
								settingsDataSetViewERC,
						}
					);
				}
			);

			await fragmentTest.step(
				'Set List as default visualization mode',
				async () => {
					await dataSetManagerApiHelpers.updateDataSetView({
						defaultVisualizationMode: 'list',
						erc: settingsDataSetViewERC,
					});
				}
			);

			const layout = await fragmentTest.step(
				'Create a page with a Data Set fragment',
				async () => {
					const pageLayout =
						await apiHelpers.headlessDelivery.createSitePage({
							siteId: site.id,
							title: getRandomString(),
						});

					return pageLayout;
				}
			);

			await fragmentTest.step(
				'Configure Data Set in the page',
				async () => {
					await fdsFragmentPage.configureDataSetFragment({
						layout,
						site,
						viewLabel: settingsDataSetViewLabel,
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
					await dataSetManagerApiHelpers.updateDataSetView({
						defaultVisualizationMode: 'cards',
						erc: settingsDataSetViewERC,
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

					expect(
						await fdsFragmentPage.fdsCardsWrapper
					).toBeInViewport();

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
