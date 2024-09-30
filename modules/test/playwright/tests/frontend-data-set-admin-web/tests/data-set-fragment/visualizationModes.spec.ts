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

export const test = mergeTests(
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': true,
	}),
	loginTest(),
	fdsFragmentPageTest,
	isolatedLayoutTest({publish: false})
);

let dataSetERC: string;

const dataSetLabel: string = getRandomString();

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		additionalAPIURLParameters:
			'&nestedFields=dataSetToDataSetTableSections',
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({
		erc: dataSetERC,
	});
});

test.describe('Visualization Modes in Data Set fragment', () => {
	test('Show mapped fields in the fragment', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		const SAMPLE_SCALAR_FIELD = 'id';
		const SAMPLE_OBJECT_FIELD = 'dataSetToDataSetTableSections';
		const SAMPLE_OBJECT_CHILD_FIELD = 'label';

		await test.step('Create table fields', async () => {
			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC,
				fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				label_i18n: {en_US: 'Label'},
				type: 'string',
			});
			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC,
				fieldName: `${SAMPLE_SCALAR_FIELD}`,
				label_i18n: {en_US: 'Id'},
				type: 'string',
			});
		});

		await test.step('Create cards section fields', async () => {
			await dataSetManagerApiHelpers.createDataSetCardsSection({
				dataSetERC,
				fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				name: 'title',
			});
			await dataSetManagerApiHelpers.createDataSetCardsSection({
				dataSetERC,
				fieldName: `${SAMPLE_SCALAR_FIELD}`,
				name: 'description',
			});
		});

		await test.step('Create list section fields', async () => {
			await dataSetManagerApiHelpers.createDataSetListSection({
				dataSetERC,
				fieldName: `${SAMPLE_OBJECT_FIELD}.${SAMPLE_OBJECT_CHILD_FIELD}`,
				name: 'title',
			});
			await dataSetManagerApiHelpers.createDataSetListSection({
				dataSetERC,
				fieldName: `${SAMPLE_SCALAR_FIELD}`,
				name: 'description',
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Check Data Set Cards are present', async () => {
			await fdsFragmentPage.fdsCardsWrapper.waitFor({
				state: 'visible',
			});

			await expect(fdsFragmentPage.fdsCardsWrapper).toBeInViewport();

			await fdsFragmentPage.page.locator('.card').first().waitFor();

			const firstCard = fdsFragmentPage.page.locator('.card').first();

			await expect(firstCard.locator('.card-title')).toContainText(
				dataSetLabel
			);

			await expect(firstCard.locator('.card-subtitle')).not.toBeEmpty();
		});

		await test.step('Change visualization mode to List', async () => {
			await fdsFragmentPage.changeVisualizationMode('List');
		});

		await test.step('Check Data Set List is present', async () => {
			await fdsFragmentPage.fdsListWrapper.waitFor({
				state: 'visible',
			});

			await expect(fdsFragmentPage.fdsListWrapper).toBeInViewport();

			await fdsFragmentPage.page
				.locator('.list-group-item')
				.first()
				.waitFor();

			const firstListItem = fdsFragmentPage.page
				.locator('.list-group-item')
				.first();

			await expect(
				firstListItem.locator('.list-group-title')
			).toContainText(dataSetLabel);

			await expect(
				firstListItem.locator('.list-group-text')
			).not.toBeEmpty();
		});

		await test.step('Change visualization mode to Table', async () => {
			await fdsFragmentPage.changeVisualizationMode('Table');
		});

		await test.step('Data Set Table is in the page', async () => {
			await fdsFragmentPage.fdsTableWrapper.waitFor({
				state: 'visible',
			});

			await expect(
				await fdsFragmentPage.fdsTableWrapper
			).toBeInViewport();

			expect(
				await page
					.locator('.dnd-thead > div')
					.first()
					.locator('.dnd-th')
					.allInnerTexts()
			).toEqual(['Label', 'Id', '']);
		});
	});

	test('Show mapped scalar array field in the fragment @LPD-11769', async ({
		dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
		page,
	}) => {
		const SAMPLE_SCALAR_ARRAY_FIELD = 'keywords';
		const SAMPLE_SCALAR_ARRAY_CONTENT = ['one', 'two', 'three'];

		await test.step('Create table fields', async () => {
			await dataSetManagerApiHelpers.createDataSetTableSection({
				dataSetERC,
				extraBodyParams: {
					keywords: SAMPLE_SCALAR_ARRAY_CONTENT,
				},
				fieldName: SAMPLE_SCALAR_ARRAY_FIELD,
				label_i18n: {en_US: SAMPLE_SCALAR_ARRAY_FIELD},
				type: 'array',
			});
		});

		await test.step('Configure Data Set in the page', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

		await test.step('Data Set Table is in the page', async () => {
			await fdsFragmentPage.fdsTableWrapper.waitFor({
				state: 'visible',
			});

			await expect(fdsFragmentPage.fdsTableWrapper).toBeInViewport();

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
		});
	});
});
