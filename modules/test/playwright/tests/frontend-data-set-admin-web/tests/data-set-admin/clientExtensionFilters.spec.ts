/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataSetManagerApiHelpersTest} from '../../fixtures/dataSetManagerApiHelpersTest';
import {filtersPageTest} from './fixtures/filtersPageTest';
import getRandomString from '../../../../utils/getRandomString';
import {loginTest} from '../../../../fixtures/loginTest';



export const test = mergeTests(
    dataSetManagerApiHelpersTest,
	filtersPageTest,
	loginTest(),
);

let dataSetERC: string;
let dataSetLabel: string;

test.beforeEach(async ({dataSetManagerApiHelpers, filtersPage}) => {
    dataSetERC = getRandomString();
    dataSetLabel = getRandomString();

    await test.step('Create a data set', async () => {
        await dataSetManagerApiHelpers.createDataSet({
            erc: dataSetERC,
            label: dataSetLabel,
        });
    });

    await test.step('Navigate to Filters section', async () => {
        await filtersPage.goto({
            dataSetLabel,
        });
    });
});

test.afterEach(async ({dataSetManagerApiHelpers}) => {
    await dataSetManagerApiHelpers.deleteDataSet({erc: dataSetERC});
});

test.describe('Client Extension Filters in Data Set Manager', () => {
	test('Shows there is not client extension available', async ({filtersPage}) => {
        await test.step('Open add client extension filter modal', async () => {
			await expect(filtersPage.newFilterButton).toBeVisible();

		    await filtersPage.newFilterButton.click();

            const menuItem = filtersPage.page.getByRole('menuitem', {
                name: 'Client Extension',
            });

            await expect(menuItem).toBeVisible();

            await menuItem.click();
            });
        await test.step('Shows alert message', async () => {
			await expect(
				filtersPage.newClientExtensionFilterModal.noClientExtensionsAvailableAlert
			).toBeVisible();
		});
	});
});