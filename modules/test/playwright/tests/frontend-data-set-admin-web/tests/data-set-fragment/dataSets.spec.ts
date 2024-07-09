/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../fixtures/featureFlagsTest';
import {isolatedLayoutTest} from '../../../../fixtures/isolatedLayoutTest';
import {loginTest} from '../../../../fixtures/loginTest';
import getRandomString from '../../../../utils/getRandomString';
import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {fdsFragmentPageTest} from './fixtures/fdsFragmentPageTest';

export const test = mergeTests(
	apiHelpersTest,
	dataSetManagerApiHelpersTest,
	featureFlagsTest({
		'LPS-164563': true,
		'LPS-178052': true,
	}),
	isolatedLayoutTest({publish: false}),
	loginTest(),
	fdsFragmentPageTest
);

let dataSetERC: string;
let dataSetLabel: string;

test.beforeEach(async ({dataSetManagerApiHelpers}) => {
	dataSetERC = getRandomString();
	dataSetLabel = getRandomString();

	await dataSetManagerApiHelpers.createDataSet({
		erc: dataSetERC,
		label: dataSetLabel,
	});
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
	await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Data Set Fragment', () => {
    test('Data Set can be added to the fragment', async({
		dataSetManagerApiHelpers,
        fdsFragmentPage,
		layout,
        page
    }) => {
        await test.step('Add fields, so data is displayed', async () => {
			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {
					en_US: 'ID',
				},
				name: 'id',
				sortable: true,
				type: 'string',
			});

			await dataSetManagerApiHelpers.createDataSetField({
				dataSetERC,
				label_i18n: {en_US: 'Name'},
				name: 'name',
				sortable: true,
				type: 'string',
			});
		});

        await test.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

        await test.step('Assert that the Data Set is available on the page', async() => {
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
			).toEqual(['ID', 'Name', '']);
        });
    });

    test('Data Set selection modal shows a "No results found" message when there are no Data Sets created', async({
        dataSetManagerApiHelpers,
		fdsFragmentPage,
		layout,
    }) => {
        test.step('Remove Data Set', async() => {
            await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
        });

        await test.step('Configure Data Set fragment', async () => {
			await fdsFragmentPage.configureEmptyDataSetFragment({
				dataSetLabel,
				layout,
			});
		});

        test.step('Assert that there are no Data Sets available to select', async() => {
            await expect(
                fdsFragmentPage.page
                .frameLocator('iframe[title="Select"]')
                .locator('.c-empty-state-title')
            ).toContainText('No Results Found');
        });
    });
});