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
let dataSet: any;
let dataSetLabel: string;
const clientExtensionName = 'Liferay Sample FDS Filter';
const DATE_FIELD_NAME = 'dateCreated';
const NAME_FIELD_NAME = 'name';

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
        await test.step('Open the client extension filter modal', async () => {
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

    test('Can create a Client Extension Filter in DSM', async({
        dataSetManagerApiHelpers,
        filtersPage,
        page,
    }) => {
        const fieldLabel = getRandomString();
        const filterLabel = getRandomString();

        await test.step('"No default filters were created." message appears when there are no filters', async() => {
            await expect(
                page.getByText('No default filters were created')
            ).toBeVisible();
        });

        await test.step('Add a field, so FDS has something to show', async () => {
            await dataSetManagerApiHelpers.createDataSetField({
                dataSetERC,
                label_i18n: {en_US: fieldLabel},
                name: 'rendererType',
                type: 'string',
            });
        });

        await test.step('Can not create a client extension filter without mandatory fields', async () => {
			await expect(filtersPage.newFilterButton).toBeVisible();

		    await filtersPage.newFilterButton.click();

            const menuItem = filtersPage.page.getByRole('menuitem', {
                name: 'Client Extension',
            });

            await expect(menuItem).toBeVisible();

            await menuItem.click();

            await filtersPage.saveAddFilterModal();

            await expect(page.getByText('This field is required.')).toHaveCount(3);

            await filtersPage.newClientExtensionFilterModal.nameInput.click();
            await filtersPage.newClientExtensionFilterModal.nameInput.fill(filterLabel);
            await filtersPage.saveAddFilterModal();

            await expect(page.getByText('This field is required.')).toHaveCount(2);

            await filtersPage.newClientExtensionFilterModal.filterBySelect.click();
            await page.getByRole('option', {name: DATE_FIELD_NAME}).click();
            await filtersPage.saveAddFilterModal();

            await expect(page.getByText('This field is required.')).toHaveCount(1);

            await filtersPage.newClientExtensionFilterModal.clientExtensionDropdown.click();
            await page.getByRole('option', {name: clientExtensionName}).click();

            await filtersPage.saveAddFilterModal();

            await expect(page.getByText('This field is required.')).toHaveCount(0);

            await filtersPage.cancelAddFilterModal();
        });
    });

    test('Can create a Client Extension Filter in DSM', async({
        dataSetManagerApiHelpers,
        filtersPage,
        page,
    }) => {
        const fieldLabel = getRandomString();
        const filterLabel = getRandomString();

        await test.step('Add a field, so FDS has something to show', async () => {
            await dataSetManagerApiHelpers.createDataSetField({
                dataSetERC,
                label_i18n: {en_US: fieldLabel},
                name: 'rendererType',
                type: 'string',
            });
        });

        await test.step('Create a client extension filter', async () => {
            await filtersPage.createClientExtensionFilter({
                filterBy: DATE_FIELD_NAME,
                name: filterLabel,
                clientExtension: clientExtensionName
            });

            await filtersPage.saveAddFilterModal();
        });

        await test.step('Check that the client extension filter is in the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: DATE_FIELD_NAME,
				})
			).toBeVisible();
		}); 

        await test.step('Fill a client extension filter modal and close without saving', async () => {
            await filtersPage.createClientExtensionFilter({
                filterBy: NAME_FIELD_NAME,
                name: filterLabel,
                clientExtension: clientExtensionName
            });

            await filtersPage.closeAddFilterModal();
        });

        await test.step('Fill a client extension filter modal and cancel the creation', async () => {
            await filtersPage.createClientExtensionFilter({
                filterBy: NAME_FIELD_NAME,
                name: filterLabel,
                clientExtension: clientExtensionName
            });

            await filtersPage.cancelAddFilterModal();
        });

        await test.step('Check that only one client extension filter is in the list', async () => {
			await expect(
				page.getByRole('cell', {
					exact: true,
					name: DATE_FIELD_NAME,
				})
			).toBeVisible();

            await expect(
				page.getByRole('cell', {
					exact: true,
					name: NAME_FIELD_NAME,
				})
			).not.toBeVisible();
		}); 
    });
});