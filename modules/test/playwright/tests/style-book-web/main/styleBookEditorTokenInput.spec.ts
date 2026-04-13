/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {isolatedSiteTest} from '../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../fixtures/loginTest';
import {styleBookPageTest} from '../../../fixtures/styleBookPageTest';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../utils/fillAndClickOutside';
import getRandomString from '../../../utils/getRandomString';

const test = mergeTests(isolatedSiteTest, loginTest(), styleBookPageTest);

test.beforeEach(async ({site, styleBooksPage}) => {
	await styleBooksPage.goto(site.friendlyUrlPath);

	await styleBooksPage.create(getRandomString());
});

test(
	'A token with a custom unit identifies the unit from the input',
	{tag: '@LPD-66976'},
	async ({page, styleBooksPage}) => {
		const formGroup = page.locator('.form-group').filter({
			has: page.getByLabel('Spacer 1', {exact: true}),
		});

		const valueInput = formGroup.locator('input');
		const unitButton = formGroup.getByTitle('Select a Unit');

		await test.step('Assert that a token with unit only accepts numeric values', async () => {
			await styleBooksPage.selectTokenCategory('Spacing');

			await expect(valueInput).toHaveValue('0.25');
			await expect(unitButton).toHaveText('REM');
		});

		const testValues = [
			{expectedUnit: 'PX', expectedValue: '8'},
			{expectedUnit: '%', expectedValue: '10'},
			{expectedUnit: 'EM', expectedValue: '90'},
			{expectedUnit: 'REM', expectedValue: '72'},
			{expectedUnit: 'VW', expectedValue: '12'},
			{expectedUnit: 'VH', expectedValue: '14'},
		];

		for (const {expectedUnit, expectedValue} of testValues) {
			const inputValue = `${expectedValue}${expectedUnit.toLowerCase()}`;

			await test.step(`Change unit to "CUSTOM", set value to "${inputValue}", and assert it identifies the unit`, async () => {
				await clickAndExpectToBeVisible({
					autoClick: true,
					target: page.getByRole('option', {name: 'CUSTOM'}),
					trigger: unitButton,
				});

				await fillAndClickOutside(page, valueInput, inputValue);

				await styleBooksPage.waitForAutoSave();

				await expect(valueInput).toHaveValue(expectedValue);
				await expect(unitButton).toHaveText(expectedUnit);
				await expect(valueInput.fill(inputValue)).rejects.toThrow();
			});
		}
	}
);
