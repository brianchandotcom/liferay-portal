/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import path from 'path';

import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import {deleteItems} from './utils/deleteItems';

export const test = mergeTests(loginTest(), formsPagesTest);

test.afterEach(async ({formsPage, page}) => {
	await formsPage.goTo();

	await deleteItems(formsPage, page);
});

test.describe('Manage fields through Form Preview page', () => {
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

			await newTabPage.close();
		});
	});

	test('make sure the aria-labelledby reference is present in the captcha form view', async ({
		formBuilderPage,
		formBuilderSidePanelPage,
	}) => {
		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderPage.formSettingsButton.click();

		await formBuilderPage.requireCaptchaToggle.click();

		await formBuilderPage.formSettingsDoneButton.click();

		const newTabPagePromise = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.previewButton.click();

		const newTabPage = await newTabPagePromise;

		await newTabPage.waitForLoadState('domcontentloaded');

		const captchaContainer = newTabPage.locator(
			"[data-field-reference='_CAPTCHA_']"
		);

		await expect(captchaContainer).toBeVisible();

		const captchaContainerAriaLabelledby =
			await captchaContainer.getAttribute('aria-labelledby');

		const screenReaderOnlyCaptchaSpan = newTabPage.locator(
			`span[id='${captchaContainerAriaLabelledby}']`
		);

		await expect(screenReaderOnlyCaptchaSpan).toHaveClass('sr-only');

		await expect(screenReaderOnlyCaptchaSpan).toContainText('captcha');

		await newTabPage.close();
	});
});

test.describe('Manage fields through Form Builder page', () => {
	test('assert edition of a rich text field predefined value that contains a rule', async ({
		formBuilderPage,
		formsPage,
		page,
	}) => {
		await formsPage.goTo();

		await formsPage.importForm(
			path.join(
				__dirname,
				'dependencies',
				'form-with-rich-text.portlet.lar'
			)
		);

		await formsPage.openForm('Form with rich text field');

		await expect(
			page.getByRole('textbox', {name: 'Rich Text'})
		).toBeVisible();

		await formBuilderPage.openFieldSettings('Rich Text');

		await formBuilderPage.settingsAdvancedTab.click();

		const richTextPredefinedValueIframe = page
			.getByRole('textbox', {name: 'Predefined Value'})
			.frameLocator('iframe');

		await richTextPredefinedValueIframe
			.getByText("Rich's text predefined value")
			.click();

		await page.keyboard.press('Control+A');

		await page.keyboard.press('Backspace');

		await page.keyboard.type(
			'Typing a new predefined value for the rich text field.'
		);

		await expect(
			richTextPredefinedValueIframe.getByText(
				'Typing a new predefined value for the rich text field.'
			)
		).toBeVisible();
	});
});
