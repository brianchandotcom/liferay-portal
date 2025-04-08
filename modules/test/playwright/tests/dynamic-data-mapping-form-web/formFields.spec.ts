/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';
import path from 'path';

import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import {getRandomInt} from '../../utils/getRandomInt';
import performLoginViaApi, {performLogout} from '../../utils/performLogin';
import {deleteItems} from './utils/deleteItems';

export const test = mergeTests(dataApiHelpersTest, loginTest(), formsPagesTest);

test.afterEach(async ({formsPage}) => {
	await formsPage.goTo();

	await deleteItems(formsPage);
});

test.describe('Manage fields through Form Preview page', () => {
	test('assert that it is possible to delete the predefined value of a text field', async ({
		formBuilderPage,
		formBuilderSidePanelPage,
	}) => {
		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.advancedTab.click();

		await formBuilderSidePanelPage.predefinedValueField.fill(
			'predefined value for text field.'
		);

		const newTabPagePromise = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.previewButton.click();

		const newTabPage = await newTabPagePromise;

		await newTabPage.waitForLoadState('domcontentloaded');

		await newTabPage.getByLabel('Text').click();

		await newTabPage.keyboard.press('Control+A');

		await newTabPage.keyboard.press('Backspace');

		// Wait a little bit before doing the assertion since useSyncValue hook takes a few miliseconds to set the value on the text field
		// Otherwise the test would always pass, even with the bug still present

		await newTabPage.waitForTimeout(1000);

		await expect(newTabPage.getByLabel('Text')).toHaveValue('');

		await newTabPage.close();
	});

	test('LPD-12824 HTML autocomplete attribute is rendered and has the configured value limited to 20 non-special characters in Date, Numeric and Text field types', async ({
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

	test('Verify if temporary files are removed', async ({
		apiHelpers,
		formBuilderPage,
		formBuilderSidePanelPage,
		formViewPage,
		page,
	}) => {
		await formBuilderPage.goToNew();

		await formBuilderPage.fillFormTitle('Form' + getRandomInt());

		await formBuilderSidePanelPage.addFieldByDoubleClick('Upload');

		await formBuilderSidePanelPage.allowGuestUsers.click();

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await performLogout(page);

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.waitForLoadState('domcontentloaded');

		// Verify that the first file is removed after the second file is uploaded

		await formViewPage.uploadFile(page, __dirname, 'sampleFile.txt');

		await expect(formViewPage.uploadInput).toHaveValue('sampleFile.txt');

		const firstFileEntryId = await formViewPage.getFileEntryId(page);

		const getDocumentUnauthenticated = async (documentId: string) => {
			const {Authorization} =
				await apiHelpers.getJSONWebServicesHeaders();

			return apiHelpers.get(
				`${apiHelpers.baseUrl}headless-delivery/v1.0/documents/${documentId}`,
				false,
				{Authorization}
			);
		};

		expect(await getDocumentUnauthenticated(firstFileEntryId)).toEqual(
			expect.objectContaining({
				id: Number(firstFileEntryId),
			})
		);

		await formViewPage.uploadFile(page, __dirname, 'loremIpsum.txt');

		await expect(formViewPage.uploadInput).toHaveValue('loremIpsum.txt');

		expect(await getDocumentUnauthenticated(firstFileEntryId)).toEqual({
			status: 'NOT_FOUND',
		});

		// Verify that the file is removed when reloading the page

		const secondFileEntryId = await formViewPage.getFileEntryId(page);

		expect(await getDocumentUnauthenticated(secondFileEntryId)).toEqual(
			expect.objectContaining({
				id: Number(secondFileEntryId),
			})
		);

		await page.reload();

		expect(await getDocumentUnauthenticated(secondFileEntryId)).toEqual({
			status: 'NOT_FOUND',
		});

		// Verify that the file is removed when clearing the upload field

		await formViewPage.uploadFile(page, __dirname, 'sampleFile.txt');

		await expect(formViewPage.uploadInput).toHaveValue('sampleFile.txt');

		const thirdFileEntryId = await formViewPage.getFileEntryId(page);

		expect(await getDocumentUnauthenticated(thirdFileEntryId)).toEqual(
			expect.objectContaining({
				id: Number(thirdFileEntryId),
			})
		);

		await formViewPage.unselectFile.click();

		expect(await getDocumentUnauthenticated(thirdFileEntryId)).toEqual({
			status: 'NOT_FOUND',
		});

		await performLoginViaApi(page, 'test');
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

	test('assert that a date field can be previewed', async ({
		formBuilderPage,
		formBuilderSidePanelPage,
	}) => {
		await formBuilderPage.goToNew();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Date');

		const newTabPagePromise = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.previewButton.click();

		const newTabPage = await newTabPagePromise;

		await newTabPage.waitForLoadState('domcontentloaded');

		await expect(
			newTabPage.getByLabel('Date', {exact: true})
		).toBeVisible();

		await newTabPage.getByRole('button', {name: 'Select Date'}).click();

		await newTabPage.getByLabel('Select Current Date').click();

		await newTabPage.keyboard.press('Escape');

		const currentDate = new Date();

		const formattedDate = new Intl.DateTimeFormat('en-US', {
			day: '2-digit',
			month: '2-digit',
			year: 'numeric',
		}).format(currentDate);

		await expect(newTabPage.getByText(formattedDate)).toBeVisible();
	});

	test('assert that a fields group can be previewed', async ({
		formBuilderPage,
		formBuilderSidePanelPage,
	}) => {
		await formBuilderPage.goToNew();

		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.backButton.click();

		await formBuilderSidePanelPage.addFieldToFieldGroup('Numeric', 0);

		const newTabPagePromise = new Promise<Page>((resolve) =>
			formBuilderPage.page.once('popup', resolve)
		);

		await formBuilderPage.previewButton.click();

		const newTabPage = await newTabPagePromise;

		await newTabPage.waitForLoadState('domcontentloaded');

		await expect(
			newTabPage.getByLabel('Fields Group', {exact: true})
		).toBeVisible();

		await expect(
			newTabPage.getByLabel('Text', {exact: true})
		).toBeVisible();

		await expect(
			newTabPage.getByLabel('Numeric', {exact: true})
		).toBeVisible();
	});
});
