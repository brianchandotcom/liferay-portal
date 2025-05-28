/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {kaleoFormsPagesTest} from '../../../fixtures/kaleoFormsPagesTest';
import {loginTest} from '../../../fixtures/loginTest';
import {getRandomInt} from '../../../utils/getRandomInt';
import {waitForAlert} from '../../../utils/waitForAlert';

export const test = mergeTests(loginTest(), kaleoFormsPagesTest);

test.afterEach(async ({kaleoFormsAdminPage, page}) => {
	await kaleoFormsAdminPage.goto();

	await page.waitForLoadState('networkidle');

	if (await kaleoFormsAdminPage.selectAllItemsCheckbox.isEnabled()) {
		await kaleoFormsAdminPage.selectAllItemsCheckbox.check();

		page.on('dialog', (dialog) => dialog.accept());

		await kaleoFormsAdminPage.deleteButton.click();

		await waitForAlert(page);
	}
});

test('Can navigate through pages and modify the amount of items per page', async ({
	kaleoFormModalPage,
	kaleoFormsAdminPage,
	page,
}) => {
	test.slow();

	const kaleoFormsProcessName = 'kaleoFormsProcess' + getRandomInt();
	const createdTaskFormName = 'createdTaskForm' + getRandomInt();

	await test.step('Go to Kaleo Forms Admin and add new process', async () => {
		await kaleoFormsAdminPage.goto();

		await kaleoFormsAdminPage.addNewProcessButton.click();

		// This waitForTimeout is needed so the event listener that copies the input value
		// from newProcessNameField to the hidden field is attached before filling it.

		await page.waitForTimeout(2000);

		await kaleoFormsAdminPage.newProcessNameField.fill(
			kaleoFormsProcessName
		);

		const newProcessNameFieldId =
			await kaleoFormsAdminPage.newProcessNameField.getAttribute('id');

		const newProcessNameHiddenField = page.locator(
			`#${newProcessNameFieldId}_en_US`
		);

		// Check that the hidden field has the expected value, otherwise the process
		// creation flow will fail.

		await expect(newProcessNameHiddenField).toHaveValue(
			kaleoFormsProcessName
		);

		await expect(kaleoFormsAdminPage.addNewProcessNextButton).toBeEnabled();

		await kaleoFormsAdminPage.addNewProcessNextButton.click();

		await kaleoFormsAdminPage.firstFieldSetActionMenu.click();

		await kaleoFormsAdminPage.actionMenuChooseOption.click();

		await kaleoFormsAdminPage.addNewProcessNextButton.click();

		await kaleoFormsAdminPage.firstWorkflowActionMenu.click();

		await kaleoFormsAdminPage.actionMenuChooseOption.click();

		await kaleoFormsAdminPage.addNewProcessNextButton.click();

		// This waitForTimeout is needed so the event listener that triggers the
		// dropdown menu is attached to the action menu button before clicking it.

		await page.waitForTimeout(2000);

		await kaleoFormsAdminPage.firstFormsActionMenu.click();

		await kaleoFormsAdminPage.assignFormButton.click();

		await kaleoFormModalPage.addNewFormButton.click();

		// This waitForTimeout is needed so the event listener that copies the input value
		// from formNameField to the hidden field is attached before filling it.

		await page.waitForTimeout(2000);

		await kaleoFormModalPage.formNameField.fill(createdTaskFormName);

		await kaleoFormModalPage.saveFormButton.click();

		await kaleoFormModalPage.chooseForm(createdTaskFormName);

		await kaleoFormsAdminPage.saveButton.click();
	});

	await test.step('Go to created process and add 21 entries', async () => {
		await kaleoFormsAdminPage.clickKaleoFormProcessLink(
			kaleoFormsProcessName
		);

		for (let index = 1; index <= 21; index++) {
			await kaleoFormsAdminPage.submitNewProcessEntryButton.click();

			// This waitForTimeout is needed so the event listener that copies the input value
			// from companyField to the hidden field is attached before filling it.

			await page.waitForTimeout(2000);

			const companyField = page.getByRole('textbox').first();

			await companyField.fill('Liferay' + index);

			await companyField.press('Enter');
		}
	});

	await test.step('Assert that the user can navigate to page 2', async () => {
		await expect(page.getByLabel('Page 2')).toBeVisible();

		await page.getByLabel('Page 2').click();

		await expect(
			page.getByRole('cell', {exact: true, name: 'Liferay1'})
		).not.toBeVisible();

		await expect(
			page.getByRole('cell', {exact: true, name: 'Liferay21'})
		).toBeVisible();
	});

	await test.step('Assert that the user can modify the amount of items per page', async () => {
		await page.getByLabel('Items per Page').click();

		await page.getByRole('option', {name: '40 Entries per Page'}).click();

		await expect(
			page.getByRole('cell', {exact: true, name: 'Liferay1'})
		).toBeVisible();

		await expect(
			page.getByRole('cell', {exact: true, name: 'Liferay21'})
		).toBeVisible();
	});
});
