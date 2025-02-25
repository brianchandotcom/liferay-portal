/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {jsComponentsSamplePageTest} from './fixtures/jsComponentsSamplePageTest';

export const test = mergeTests(
	dataApiHelpersTest,
	apiHelpersTest,
	jsComponentsSamplePageTest,
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(
	'Setup site and JS Components Sample widget',
	async ({apiHelpers, jsComponentsSamplePage, page, site}) => {
		await test.step('Create a content site and the js components sample widget', async () => {
			await jsComponentsSamplePage.setupJSComponentsSampleWidget({
				apiHelpers,
				site,
			});
		});

		await test.step('Select Translation Manager tab', async () => {
			await jsComponentsSamplePage.selectTab(
				'Translation Manager',
				page.getByRole('heading', {name: 'AUI Tag'})
			);
		});
	}
);

test.describe('Translation Manager', () => {
	test(
		'Assert the localization dropdown is rendered alongside the localized input',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const localizedInputContainer = page
				.locator('.input-localized.input-localized-input')
				.first();

			await test.step('Assert the localized input is rendered', async () => {
				expect(localizedInputContainer).toBeVisible();
			});

			await test.step('Assert the localization dropdown is rendered', async () => {
				const localizedTriggerButton =
					localizedInputContainer.getByRole('button');

				expect(localizedTriggerButton).toBeVisible();

				const translationDropdown = page.getByRole('menuitem', {
					name: 'Not translated into Catalan.',
				});

				await clickAndExpectToBeVisible({
					target: translationDropdown,
					trigger: localizedTriggerButton,
				});
			});
		}
	);

	test(
		'Assert the click on a translation updates the localized input',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const localizedInputContainer = page
				.locator('.input-localized.input-localized-input')
				.first();

			const localizedInput = localizedInputContainer.getByRole('textbox');

			await test.step('Fill the localized input with a translation', async () => {
				await expect(localizedInputContainer).toBeVisible();

				await localizedInput.fill('Translation');
			});

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					localizedInputContainer.getByRole('button');

				const translationManagerCatalanChoice = page.getByRole(
					'menuitem',
					{
						name: 'Not translated into Catalan.',
					}
				);

				await clickAndExpectToBeVisible({
					target: translationManagerCatalanChoice,
					trigger: translationManagerTriggerButton,
				});

				await translationManagerCatalanChoice.click();

				const translationText = page
					.locator('text=Translation')
					.first();

				await expect(translationText).toBeVisible();
				await expect(localizedInput).not.toContainText('Translation');
			});
		}
	);

	test(
		'Assert the click on a translation changes the translation manager trigger text',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const localizedInputContainer = page
				.locator('.input-localized.input-localized-input')
				.first();

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					localizedInputContainer.getByRole('button');

				const translationManagerCatalanChoice = page.getByRole(
					'menuitem',
					{
						name: 'Not translated into Catalan.',
					}
				);

				await clickAndExpectToBeVisible({
					target: translationManagerCatalanChoice,
					trigger: translationManagerTriggerButton,
				});

				await translationManagerCatalanChoice.click();

				await expect(translationManagerTriggerButton).toHaveText(
					'ca-ES'
				);
			});
		}
	);

	test(
		'Assert translation manager button exists for Admin users',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const adminLocalizedInputContainer = page
				.getByText('Admin English (United States')
				.first()
				.locator('.input-localized.input-localized-input')
				.first();

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				const translationManageButton = page.getByRole('button', {
					name: 'Manage Translations',
				});

				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerTriggerButton,
				});
			});
		}
	);

	test(
		'Assert the translation manager is opened when Manage Translations is clicked',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const adminLocalizedInputContainer = page
				.getByText('Admin English (United States')
				.first()
				.locator('.input-localized.input-localized-input')
				.first();

			const translationManageButton = page.getByRole('button', {
				name: 'Manage Translations',
			});

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerTriggerButton,
				});
			});

			await test.step('Assert the translation manager is opened', async () => {
				const translationManagerDialog = page.getByRole('dialog', {
					name: 'Manage Translations',
				});

				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});
		}
	);

	test(
		'Assert that clicking on cancel closes the translation manager and discards the changes',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const adminLocalizedInputContainer = page
				.getByText('Admin English (United States)')
				.first()
				.locator('.input-localized.input-localized-input')
				.first();

			const translationManageButton = page.getByRole('button', {
				name: 'Manage Translations',
			});

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerTriggerButton,
				});
			});

			const translationManagerDialog = page.getByRole('dialog', {
				name: 'Manage Translations',
			});

			await test.step('Assert the translation manager is opened', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});

			await test.step('Delete a translation', async () => {
				const translationRow = translationManagerDialog.getByRole(
					'row',
					{
						name: 'Catalan (Spain)',
					}
				);

				const deleteButton = translationRow.getByRole('button', {
					name: 'Delete',
				});

				await deleteButton.click();
			});

			await test.step('Click on cancel', async () => {
				const cancelButton = translationManagerDialog.getByRole(
					'button',
					{
						name: 'Cancel',
					}
				);

				await cancelButton.click();

				await expect(translationManagerDialog).not.toBeVisible();
			});

			await test.step('Assert the translation was not deleted', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				const translationManagerCatalanChoice = page.getByRole(
					'menuitem',
					{
						name: 'Not translated into Catalan.',
					}
				);

				await clickAndExpectToBeVisible({
					target: translationManagerCatalanChoice,
					trigger: translationManagerTriggerButton,
				});

				await translationManagerCatalanChoice.click();

				await expect(translationManagerTriggerButton).toHaveText(
					'ca-ES'
				);
			});
		}
	);

	test(
		'Assert that a new language can be added to the translation manager',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const adminLocalizedInputContainer = page
				.getByText('Admin English (United States)')
				.first()
				.locator('.input-localized.input-localized-input')
				.first();

			const translationManageButton = page.getByRole('button', {
				name: 'Manage Translations',
			});

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerTriggerButton,
				});
			});

			const translationManagerDialog = page.getByRole('dialog', {
				name: 'Manage Translations',
			});

			await test.step('Assert the translation manager is opened', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});

			const newLanguageOption = page.getByRole('menuitem', {
				name: 'de-DE',
			});

			test.step('Add a new language', async () => {
				const addNewLanguageButton = page.getByLabel('Add', {
					exact: true,
				});

				await clickAndExpectToBeVisible({
					target: newLanguageOption,
					trigger: addNewLanguageButton,
				});

				await newLanguageOption.click();
			});

			test.step('Assert the new language is added to the translation manager', async () => {
				const newLanguageTranslateOption = page.getByRole('menuitem', {
					name: 'Not translated into German.',
				});

				await clickAndExpectToBeVisible({
					target: newLanguageTranslateOption,
					trigger: newLanguageOption,
				});

				const doneButton = translationManagerDialog.getByRole(
					'button',
					{
						name: 'Done',
					}
				);

				await expect(doneButton).toBeVisible();

				clickAndExpectToBeHidden({
					target: translationManagerDialog,
					trigger: doneButton,
				});
			});

			test.step('Assert the new language is listed as an option', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				await expect(translationManagerTriggerButton).toBeVisible();

				const newLanguageTranslateOption = page.getByRole('menuitem', {
					name: 'Not translated into German.',
				});

				await clickAndExpectToBeVisible({
					target: newLanguageTranslateOption,
					trigger: translationManagerTriggerButton,
				});
			});
		}
	);

	test(
		'Assert that languages can be searched in the translation manager',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const adminLocalizedInputContainer = page
				.getByText('Admin English (United States)')
				.first()
				.locator('.input-localized.input-localized-input')
				.first();

			const translationManageButton = page.getByRole('button', {
				name: 'Manage Translations',
			});

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerTriggerButton,
				});
			});

			const translationManagerDialog = page.getByRole('dialog', {
				name: 'Manage Translations',
			});

			await test.step('Assert the translation manager is opened', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});

			await test.step('Search for a language', async () => {
				const searchInput = page.getByPlaceholder('Search');

				await searchInput.fill('Catalan');

				const searchResult = page.getByRole('row', {
					name: 'Catalan (Spain)',
				});

				await expect(searchResult).toBeVisible();
			});

			await test.step('Assert languages not matching are hidden', async () => {
				const searchResult = page.getByRole('row', {
					name: 'English (United States)',
				});

				await expect(searchResult).not.toBeVisible();
			});
		}
	);

	test(
		'Assert that a language can be deleted from the translation manager',
		{tag: '@LPD-47235'},
		async ({page}) => {
			const adminLocalizedInputContainer = page
				.getByText('Admin English (United States)')
				.first()
				.locator('.input-localized.input-localized-input')
				.first();

			const translationManageButton = page.getByRole('button', {
				name: 'Manage Translations',
			});

			await test.step('Click on a translation', async () => {
				const translationManagerTriggerButton =
					adminLocalizedInputContainer.getByRole('button');

				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerTriggerButton,
				});
			});

			const translationManagerDialog = page.getByRole('dialog', {
				name: 'Manage Translations',
			});

			await test.step('Assert the translation manager is opened', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});

			await test.step('Delete a language', async () => {
				const translationRow = translationManagerDialog.getByRole(
					'row',
					{
						name: 'French (France)',
					}
				);

				const deleteButton = translationRow.getByRole('button', {
					name: 'Delete',
				});

				await clickAndExpectToBeHidden({
					target: translationRow,
					trigger: deleteButton,
				});
			});

			await test.step('Save and close the translation manager', async () => {
				const doneButton = translationManagerDialog.getByRole(
					'button',
					{
						name: 'Done',
					}
				);

				await doneButton.click();

				await expect(translationManagerDialog).not.toBeVisible();
			});
		}
	);
});
