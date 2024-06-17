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
import {dataSetManagerSetupTest} from '../data-set-manager/fixtures/dataSetManagerSetupTest';
import {fdsFragmentPageTest} from '../data-set-fragment/fixtures/fdsFragmentPageTest';

export const fragmentTest = mergeTests(
	dataSetManagerApiHelpersTest,
	fdsFragmentPageTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	dataSetManagerSetupTest,
	isolatedLayoutTest({publish: false})
);

let dataSetERC: string;

const dataSetLabel: string = getRandomString();

fragmentTest.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

fragmentTest.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({
		erc: dataSetERC,
	});
});

fragmentTest.describe('Visualization Modes in Data Set fragment', () => {
	fragmentTest(
		'Show mapped fields in the fragment',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
			const SAMPLE_SCALAR_FIELD = 'id';
			const SAMPLE_OBJECT_FIELD = 'fdsViewFDSFieldRelationship';
			const SAMPLE_OBJECT_CHILD_FIELD = 'label';

			await fragmentTest.step('Create table fields', async () => {
				await dataSetManagerApiHelpers.createDataSetField({
					label_i18n: {en_US: 'Label'},
					name: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					type: 'string',
				});
				await dataSetManagerApiHelpers.createDataSetField({
					label_i18n: {en_US: 'Id'},
					name: `${SAMPLE_SCALAR_FIELD}`,
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					type: 'string',
				});
			});

			await fragmentTest.step('Create cards section fields', async () => {
				await dataSetManagerApiHelpers.createDataSetCardsSection({
					fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
					name: 'title',
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
						dataSetERC,
				});
				await dataSetManagerApiHelpers.createDataSetCardsSection({
					fieldName: `${SAMPLE_SCALAR_FIELD}`,
					name: 'description',
					r_fdsViewFDSCardsSectionRelationship_c_fdsViewERC:
						dataSetERC,
				});
			});

			await fragmentTest.step('Create list section fields', async () => {
				await dataSetManagerApiHelpers.createDataSetListSection({
					fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
					name: 'title',
					r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
						dataSetERC,
				});
				await dataSetManagerApiHelpers.createDataSetListSection({
					fieldName: `${SAMPLE_SCALAR_FIELD}`,
					name: 'description',
					r_fdsViewFDSListSectionRelationship_c_fdsViewERC:
						dataSetERC,
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
				'Check Data Set Cards are present',
				async () => {
					await fdsFragmentPage.fdsCardsWrapper.waitFor({
						state: 'visible',
					});

					expect(
						await fdsFragmentPage.fdsCardsWrapper
					).toBeInViewport();

					await fdsFragmentPage.page
						.locator('.card')
						.first()
						.waitFor();

					const firstCard = await fdsFragmentPage.page
						.locator('.card')
						.first();

					expect(firstCard.locator('.card-title')).toContainText(
						dataSetLabel
					);

					expect(firstCard.locator('.card-subtitle')).not.toBeEmpty();
				}
			);

			await fragmentTest.step(
				'Change visualization mode to List',
				async () => {
					await fdsFragmentPage.changeVisualizationMode('List');
				}
			);

			await fragmentTest.step(
				'Check Data Set List is present',
				async () => {
					await fdsFragmentPage.fdsListWrapper.waitFor({
						state: 'visible',
					});

					expect(
						await fdsFragmentPage.fdsListWrapper
					).toBeInViewport();

					await fdsFragmentPage.page
						.locator('.list-group-item')
						.first()
						.waitFor();

					const firstListItem = await fdsFragmentPage.page
						.locator('.list-group-item')
						.first();

					expect(
						firstListItem.locator('.list-group-title')
					).toContainText(dataSetLabel);

					expect(
						firstListItem.locator('.list-group-text')
					).not.toBeEmpty();
				}
			);

			await fragmentTest.step(
				'Change visualization mode to Table',
				async () => {
					await fdsFragmentPage.changeVisualizationMode('Table');
				}
			);

			await fragmentTest.step(
				'Data Set Table is in the page',
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
		}
	);

	fragmentTest(
		'Show mapped scalar array field in the fragment @LPD-11769',
		async ({dataSetManagerApiHelpers, fdsFragmentPage, layout, page}) => {
			const SAMPLE_SCALAR_ARRAY_FIELD = 'keywords';
			const SAMPLE_SCALAR_ARRAY_CONTENT = ['one', 'two', 'three'];

			await fragmentTest.step('Create table fields', async () => {
				await dataSetManagerApiHelpers.createDataSetField({
					extraBodyParams: {
						keywords: SAMPLE_SCALAR_ARRAY_CONTENT,
					},
					label_i18n: {en_US: SAMPLE_SCALAR_ARRAY_FIELD},
					name: SAMPLE_SCALAR_ARRAY_FIELD,
					r_fdsViewFDSFieldRelationship_c_fdsViewERC: dataSetERC,
					type: 'array',
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
				'Data Set Table is in the page',
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
					).toEqual([SAMPLE_SCALAR_ARRAY_FIELD, '']);

					expect(
						await page
							.locator('.dnd-tbody > div')
							.first()
							.locator('.dnd-td')
							.allInnerTexts()
					).toEqual(['one, two, three', '']);
				}
			);
		}
	);
});
