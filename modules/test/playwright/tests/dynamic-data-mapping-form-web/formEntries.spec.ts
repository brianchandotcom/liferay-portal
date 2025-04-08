/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page, expect, mergeTests} from '@playwright/test';

import {formsPagesTest} from '../../fixtures/formsPagesTest';
import {loginTest} from '../../fixtures/loginTest';
import getRandomString from '../../utils/getRandomString';
import {deleteItems} from './utils/deleteItems';

export const test = mergeTests(loginTest(), formsPagesTest);

let formPreviewPage: Page;

test.afterEach(async ({formsPage}) => {
	if (formPreviewPage) {
		await formPreviewPage.close();

		formPreviewPage = null;
	}

	await formsPage.goTo();

	await deleteItems(formsPage);
});

test.beforeEach(({page}) => {
	page.setViewportSize({height: 1080, width: 1920});
});

test.describe('Accessibility', () => {
	test('aria-errormessage is applied for invalid multiple selection', async ({
		formBuilderPage,
		formBuilderSidePanelPage,
		formsPage,
		page,
	}) => {
		await formsPage.goTo();

		await formsPage.clickManagementToolbarNewButton();

		await formBuilderSidePanelPage.addMultipleSelectionButton.dblclick();

		await formBuilderSidePanelPage.requiredFieldToggleSwitch.click();

		await page.waitForTimeout(1000);

		await formBuilderPage.clickPublishFormButton();

		const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

		await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

		await page.getByRole('button', {name: 'Submit'}).click();

		const singleSelect = page.getByRole('checkbox');

		await singleSelect.waitFor();

		const fieldFeedbackElement = page.locator('.form-feedback-item');

		await expect(fieldFeedbackElement).toBeVisible();

		await expect(fieldFeedbackElement).toHaveText(
			'This field is required.'
		);

		const fieldFeedbackId = await fieldFeedbackElement.getAttribute('id');

		await expect(singleSelect).toHaveAttribute(
			'aria-errormessage',
			fieldFeedbackId
		);
	});
});

test('can interact with a large list of fields on the form entries page', async ({
	formBuilderPage,
	formBuilderSidePanelPage,
	formsPage,
	page,
}) => {
	await formBuilderPage.goToNew();

	for (const index of Array.from(Array(30).keys())) {
		await formBuilderSidePanelPage.addFieldByDoubleClick('Text');

		await formBuilderSidePanelPage.label.fill(`Text ${index}`);

		await formBuilderSidePanelPage.clickBackButton();
	}

	await formBuilderPage.clickPublishFormButton();

	const formSubmissionURL = await formBuilderPage.getFormSubmissionURL();

	await page.goto(formSubmissionURL, {waitUntil: 'networkidle'});

	const formEntry = getRandomString();

	await page.getByLabel('Text 29').fill(formEntry);

	await page.getByRole('button', {name: 'Submit'}).click();

	await expect(
		page.getByText(
			'Your information was successfully received. Thank you for filling out the form.'
		)
	).toBeVisible();

	await formsPage.goTo();

	await formsPage.openForm('Untitled Form');

	await formBuilderPage.entriesTab.click();

	await page.locator('a').filter({hasText: 'Text 29'}).click();

	await expect(page.getByText(formEntry)).toBeVisible();
});

test('can interact with Single Selection options using only keys', async ({
	formBuilderFieldSettingsSidePanelPage,
	formBuilderPage,
	formBuilderSidePanelPage,
	formsPage,
	page,
}) => {
	await formsPage.goTo();

	await formsPage.clickManagementToolbarNewButton();

	for (let index = 0; index < 2; index++) {
		await formBuilderSidePanelPage.addSingleSelectionButton.dblclick();

		await formBuilderFieldSettingsSidePanelPage.addOptions(3);

		await formBuilderFieldSettingsSidePanelPage.advancedTabButton.click();

		await formBuilderFieldSettingsSidePanelPage.inlineToggle.click();

		await formBuilderSidePanelPage.backButton.click();
	}

	const formPreviewPagePromise = page.waitForEvent('popup');

	await formBuilderPage.previewButton.click();

	formPreviewPage = await formPreviewPagePromise;

	// We need to ensure option elements are enabled before tabbing through them.

	await expect(formPreviewPage.getByLabel('Option0').first()).toBeEnabled();

	for (let index = 0; index < 2; index++) {

		// On the second loop iteration, pressing Tab should focus the second group.

		await formPreviewPage.keyboard.press('Tab');

		await expect(
			formPreviewPage.getByLabel('Option0').nth(index)
		).toBeChecked();

		await formPreviewPage.keyboard.press('ArrowDown');

		await expect(
			formPreviewPage.getByLabel('Option1').nth(index)
		).toBeChecked();

		await formPreviewPage.keyboard.press('ArrowDown');

		await expect(
			formPreviewPage.getByLabel('Option2').nth(index)
		).toBeChecked();

		await formPreviewPage.keyboard.press('ArrowDown');

		// After pressing Arrow Down while being in the last option,
		// we should have the first option selected again.

		await expect(
			formPreviewPage.getByLabel('Option0').nth(index)
		).toBeChecked();

		// Then, pressing Arrow Up twice should select the middle option.

		await formPreviewPage.keyboard.press('ArrowUp');

		await formPreviewPage.keyboard.press('ArrowUp');

		await expect(
			formPreviewPage.getByLabel('Option1').nth(index)
		).toBeChecked();
	}
});
