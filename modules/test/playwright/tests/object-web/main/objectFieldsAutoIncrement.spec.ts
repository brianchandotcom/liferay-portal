/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {dataApiHelpersTest} from '../../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {objectPagesTest} from '../../../fixtures/objectPagesTest';
import {waitForAlert} from '../../../utils/waitForAlert';
import {generateObjectFields} from './utils/generateObjectFields';

const test = mergeTests(
	dataApiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest(),
	objectPagesTest
);

test.skip(
	'LPD-78504 Can add and view object entries with auto increment fields via page builder',
	{tag: '@LPD-78504'},
	async () => {
		// This test requires widget page builder infrastructure to map a custom
		// object portlet to a widget page, which is not available in the
		// current test fixtures.
		//
		// Intended flow based on Poshi test (CanAddObjectEntryViaPageBuilder):
		// 1. Create an object "Shirt" with an auto increment field
		//    (initialValue=24680, prefix="T-Shirt-", suffix="-Brazil")
		//    and a text field "Size"
		// 2. Create two entries via API with sizes "Small" and "Large"
		// 3. Create a widget page and add the "Shirts" portlet to it
		// 4. Navigate to the page and add a new entry with size "Medium"
		// 5. Verify all three entries are present with auto increment values:
		//    "T-Shirt-24680-Brazil", "T-Shirt-24681-Brazil", "T-Shirt-24682-Brazil"
	}
);

test.skip(
	'LPD-78504 Can increment auto increment field after importing an object entry with modified value',
	{tag: '@LPD-78504'},
	async () => {
		// This test requires batch import/export infrastructure (Export/Import
		// admin portlet, file download/modification, and re-import), which is
		// not available in the current test fixtures.
		//
		// Intended flow based on Poshi test (CanIncrementAfterImportingObjectEntry):
		// 1. Create an object "Shoe" with an auto increment field
		//    (initialValue=001, prefix="", suffix="-Sneakers")
		//    and a required text field "Color"
		// 2. Create an entry via API with color "Blue" and ERC "shoe001"
		// 3. Export the object entry as JSON
		// 4. Modify the exported JSON: change ERC from "shoe001" to "shoe020"
		//    and auto increment value from "001-Sneakers" to "020-Sneakers"
		// 5. Import the modified JSON file
		// 6. Create a new entry with color "Black"
		// 7. Verify three entries exist with auto increment values:
		//    "001-Sneakers", "020-Sneakers", "021-Sneakers"
		//    (the new entry increments from the highest imported value)
	}
);

test(
	'LPD-78504 Can verify auto increment field is read only in object entries',
	{tag: '@LPD-78504'},
	async ({apiHelpers, page, viewObjectEntriesPage}) => {
		const objectFields = generateObjectFields({
			objectFieldBusinessTypes: [
				{
					businessType: 'AutoIncrement',
					objectFieldSettings: [
						{name: 'prefix', value: 'HAT-'} as any,
						{name: 'initialValue', value: '1'},
						{name: 'suffix', value: ''} as any,
					],
				},
				'Text',
			],
		});

		const objectDefinition =
			await apiHelpers.objectAdmin.postRandomObjectDefinition({
				objectFields,
				status: {code: 0},
			});

		apiHelpers.data.push({
			id: objectDefinition.id,
			type: 'objectDefinition',
		});

		const autoIncrementFieldLabel = objectFields[0].label['en_US'];

		await viewObjectEntriesPage.goto(objectDefinition.className);

		await viewObjectEntriesPage.clickAddObjectEntry(
			objectDefinition.label['en_US']
		);

		await expect(
			page
				.locator('.ddm-field')
				.filter({hasText: autoIncrementFieldLabel})
		).not.toBeVisible();

		await viewObjectEntriesPage.saveObjectEntryButton.click();

		await waitForAlert(page);

		await viewObjectEntriesPage.backButton.click();

		await viewObjectEntriesPage.frontendDatasetItems.first().click();

		const autoIncrementInput = page
			.locator('.ddm-field')
			.filter({hasText: autoIncrementFieldLabel})
			.locator('input[readonly]');

		await expect(autoIncrementInput).toBeVisible();
		await expect(autoIncrementInput).toHaveAttribute('readonly', '');
		await expect(autoIncrementInput).toHaveValue('HAT-1');
	}
);
