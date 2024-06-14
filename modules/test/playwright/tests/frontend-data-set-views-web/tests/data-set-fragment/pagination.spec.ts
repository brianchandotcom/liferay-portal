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

export const fragmentTest = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	fdsFragmentPageTest,
	isolatedLayoutTest({publish: false}),
	loginTest()
);

let dataSetERC: string;
let dataSetLabel: string;

fragmentTest.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

fragmentTest.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

fragmentTest.describe(
	'Data Set Pagination configuration in the fragment',
	() => {
		fragmentTest(
			'FDS uses default pagination configuration after creating a Data Set',
			async ({
				dataSetManagerApiHelpers,
				fdsFragmentPage,
				layout,
				page,
			}) => {
				await fragmentTest.step('Create table fields', async () => {
					await dataSetManagerApiHelpers.createDataSetField({
						label_i18n: {en_US: 'Label'},
						name: 'id',
						r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
						type: 'string',
					});
					await dataSetManagerApiHelpers.createDataSetField({
						label_i18n: {en_US: 'Id'},
						name: 'label',
						r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
						type: 'string',
					});
				});

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
					'Frontend Data Set Table is in the page',
					async () => {
						await fdsFragmentPage.fdsTableWrapper.waitFor({
							state: 'visible',
						});

						expect(
							await fdsFragmentPage.fdsTableWrapper
						).toBeInViewport();

						expect(
							await page
								.locator('.dnd-thead > div')
								.first()
								.locator('.dnd-th')
								.allInnerTexts()
						).toEqual(['Label', 'Id', '']);
					}
				);

				await fragmentTest.step(
					'Check that the FDS Table pagination uses default configuration values',
					async () => {
						const paginatorWrapper =
							await fdsFragmentPage.fdsTableWrapper.locator(
								'.pagination-bar'
							);

						await expect(paginatorWrapper).toBeInViewport();

						const itemsPerPageButton =
							paginatorWrapper.getByLabel('Items Per Page');

						await expect(itemsPerPageButton).toContainText(
							'20 Items'
						);

						await itemsPerPageButton.click();

						// NOTE: strange behaviour. aria-controls is added after clicking the itemsPerPageButton

						const dropdownId = await itemsPerPageButton.evaluate(
							(node) => node.getAttribute('aria-controls')
						);

						await fdsFragmentPage.page
							.locator(`#${dropdownId}`)
							.waitFor();

						await expect(
							fdsFragmentPage.page
								.locator(`#${dropdownId}`)
								.getByRole('option')
						).toHaveCount(5);

						const paginationOptions = await fdsFragmentPage.page
							.locator(`#${dropdownId}`)
							.getByRole('option')
							.allInnerTexts();

						expect(paginationOptions).toEqual([
							'4 Items',
							'8 Items',
							'20 Items',
							'40 Items',
							'60 Items',
						]);
					}
				);
			}
		);

		fragmentTest(
			'FDS uses custom pagination configuration after creating a Data Set',
			async ({
				dataSetManagerApiHelpers,
				fdsFragmentPage,
				layout,
				page,
			}) => {
				await fragmentTest.step(
					'Update Data Set pagination configuration',
					async () => {
						await dataSetManagerApiHelpers.updateDataSet({
							defaultItemsPerPage: 10,
							erc: dataSetERC,
							label: dataSetLabel,
							listOfItemsPerPage: '5, 10, 15',
						});
					}
				);

				await fragmentTest.step('Create table fields', async () => {
					await dataSetManagerApiHelpers.createDataSetField({
						label_i18n: {en_US: 'Label'},
						name: 'id',
						r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
						type: 'string',
					});
					await dataSetManagerApiHelpers.createDataSetField({
						label_i18n: {en_US: 'Id'},
						name: 'label',
						r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
						type: 'string',
					});
				});

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
					'Frontend Data Set Table is in the page',
					async () => {
						await fdsFragmentPage.fdsTableWrapper.waitFor({
							state: 'visible',
						});

						expect(
							await fdsFragmentPage.fdsTableWrapper
						).toBeInViewport();

						expect(
							await page
								.locator('.dnd-thead > div')
								.first()
								.locator('.dnd-th')
								.allInnerTexts()
						).toEqual(['Label', 'Id', '']);
					}
				);

				await fragmentTest.step(
					'Check that the FDS Table pagination uses default configuration values',
					async () => {
						const paginatorWrapper =
							await fdsFragmentPage.fdsTableWrapper.locator(
								'.pagination-bar'
							);

						await expect(paginatorWrapper).toBeInViewport();

						const itemsPerPageButton =
							paginatorWrapper.getByLabel('Items Per Page');

						await expect(itemsPerPageButton).toContainText(
							'10 Items'
						);

						await itemsPerPageButton.click();

						// NOTE: strange behaviour. aria-controls is added after clicking the itemsPerPageButton

						const dropdownId = await itemsPerPageButton.evaluate(
							(node) => node.getAttribute('aria-controls')
						);

						await fdsFragmentPage.page
							.locator(`#${dropdownId}`)
							.waitFor();

						await expect(
							fdsFragmentPage.page
								.locator(`#${dropdownId}`)
								.getByRole('option')
						).toHaveCount(3);

						const paginationOptions = await fdsFragmentPage.page
							.locator(`#${dropdownId}`)
							.getByRole('option')
							.allInnerTexts();

						expect(paginationOptions).toEqual([
							'5 Items',
							'10 Items',
							'15 Items',
						]);
					}
				);
			}
		);
	}
);
