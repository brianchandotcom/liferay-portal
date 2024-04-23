/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';

export const test = mergeTests(loginTest(), formsPagesTest);

test.describe('Can configure a HTML autocomplete attribute in Date, Numeric and Text field types', () => {
	test('LPD-12824 HTML autocomplete attribute is rendered and has the configured value limited to 20 non-special characters', async ({
		formBuilderPage,
		formBuilderSidePanelPage,
	}) => {
		const testData: {
			expectedValue: string;
			fieldTitle: FormFieldTypeTitle;
			inputValue: string;
		}[] = [
			{
				expectedValue: 'bday',
				fieldTitle: 'Date',
				inputValue: '+)(*&^%$#@ bday$__%  ',
			},
			{
				expectedValue: 'one-time-code',
				fieldTitle: 'Numeric',
				inputValue: '****[][one-time-code&&#()',
			},
			{
				expectedValue: 'transaction-currency',
				fieldTitle: 'Text',
				inputValue: 'transaction-currencyextracharacters',
			},
		];

		await formBuilderPage.goToNew();

		await expect(formBuilderPage.newFormHeading).toBeVisible();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		for (const data of testData) {
			await formBuilderSidePanelPage.addFieldByDoubleClick(
				data.fieldTitle
			);

			await formBuilderSidePanelPage.clickAdvancedTab();

			await expect(
				formBuilderSidePanelPage.htmlAutocompleteAttributeField
			).toBeVisible();

			await formBuilderSidePanelPage.htmlAutocompleteAttributeField.fill(
				data.inputValue
			);

			await formBuilderSidePanelPage.clickBackButton();
		}

		const newTabPagePromise = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.previewButton.click();

		const newTabPage = await newTabPagePromise;

		await newTabPage.waitForLoadState('domcontentloaded');

		for (const data of testData) {
			if (data.fieldTitle === 'Date') {
				await expect(
					newTabPage.getByPlaceholder('__/__/____')
				).toHaveAttribute('autocomplete', data.expectedValue);

				continue;
			}

			await expect(
				newTabPage.getByLabel(data.fieldTitle)
			).toHaveAttribute('autocomplete', data.expectedValue);
		}
	});
});
