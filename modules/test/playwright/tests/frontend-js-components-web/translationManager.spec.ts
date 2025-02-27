/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../fixtures/apiHelpersTest';
import {dataApiHelpersTest} from '../../fixtures/dataApiHelpersTest';
import {featureFlagsTest} from '../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../fixtures/isolatedSiteTest';
import {loginTest} from '../../fixtures/loginTest';
import {clickAndExpectToBeHidden} from '../../utils/clickAndExpectToBeHidden';
import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {jsComponentsSamplePageTest} from './fixtures/jsComponentsSamplePageTest';

export const test = mergeTests(
	dataApiHelpersTest,
	apiHelpersTest,
	featureFlagsTest({
		'LPS-178052': {enabled: true},
	}),
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
		async ({jsComponentsSamplePage}) => {
			await test.step('Assert the localized input is rendered', async () => {
				expect(
					jsComponentsSamplePage.adminLocalizedInputContainer
				).toBeVisible();
			});

			await test.step('Assert the localization dropdown is rendered', async () => {
				const {
					translationManagerCatalanChoice,
					translationManagerTriggerButton,
				} = jsComponentsSamplePage;

				expect(translationManagerTriggerButton).toBeVisible();

				await clickAndExpectToBeVisible({
					target: translationManagerCatalanChoice,
					trigger: translationManagerTriggerButton,
				});
			});
		}
	);

	test(
		'Assert the click on a translation updates the localized input',
		{tag: '@LPD-47235'},
		async ({jsComponentsSamplePage, page}) => {
			const {
				adminLocalizedInputContainer,
				localizedInput,
				translationManagerCatalanChoice,
				translationManagerTriggerButton,
			} = jsComponentsSamplePage;

			await test.step('Fill the localized input with a translation', async () => {
				await expect(adminLocalizedInputContainer).toBeVisible();

				await localizedInput.fill('Translation');
			});

			await test.step('Click on a translation', async () => {
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
		async ({jsComponentsSamplePage}) => {
			await test.step('Click on a translation', async () => {
				const {
					translationManagerCatalanChoice,
					translationManagerTriggerButton,
				} = jsComponentsSamplePage;

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
		async ({jsComponentsSamplePage}) => {
			await test.step('Click on a translation', async () => {
				const {
					translationManageButton,
					translationManagerEnglishTriggerButton,
				} = jsComponentsSamplePage;

				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerEnglishTriggerButton,
				});
			});
		}
	);

	test(
		'Assert the translation manager is opened when Manage Translations is clicked',
		{tag: '@LPD-47235'},
		async ({jsComponentsSamplePage}) => {
			const {
				translationManageButton,
				translationManagerDialog,
				translationManagerEnglishTriggerButton,
			} = jsComponentsSamplePage;

			await test.step('Click on a translation', async () => {
				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerEnglishTriggerButton,
				});
			});

			await test.step('Assert the translation manager is opened', async () => {
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
		async ({jsComponentsSamplePage}) => {
			const {
				translationManageButton,
				translationManagerCancelButton,
				translationManagerCatalanChoice,
				translationManagerCatalanRow,
				translationManagerDialog,
				translationManagerEnglishTriggerButton,
			} = jsComponentsSamplePage;

			await test.step('Click on a translation', async () => {
				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerEnglishTriggerButton,
				});
			});

			await test.step('Assert the translation manager is opened', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});

			await test.step('Delete a translation', async () => {
				const deleteButton = translationManagerCatalanRow.getByRole(
					'button',
					{
						name: 'Delete',
					}
				);

				await deleteButton.click();
			});

			await test.step('Click on cancel', async () => {
				await translationManagerCancelButton.click();

				await expect(translationManagerDialog).not.toBeVisible();
			});

			await test.step('Assert the translation was not deleted', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerCatalanChoice,
					trigger: translationManagerEnglishTriggerButton,
				});

				await translationManagerCatalanChoice.click();

				await expect(translationManagerEnglishTriggerButton).toHaveText(
					'ca-ES'
				);
			});
		}
	);

	test(
		'Assert that a new language can be added to the translation manager',
		{tag: '@LPD-47235'},
		async ({jsComponentsSamplePage, page}) => {
			const {
				translationManageButton,
				translationManagerAddButton,
				translationManagerDialog,
				translationManagerDoneButton,
				translationManagerGermanChoice,
				translationManagerEnglishTriggerButton,
			} = jsComponentsSamplePage;

			await test.step('Click on a translation', async () => {
				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerEnglishTriggerButton,
				});
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
				await clickAndExpectToBeVisible({
					target: newLanguageOption,
					trigger: translationManagerAddButton,
				});

				await newLanguageOption.click();
			});

			test.step('Assert the new language is added to the translation manager', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerGermanChoice,
					trigger: newLanguageOption,
				});

				await expect(translationManagerDoneButton).toBeVisible();

				clickAndExpectToBeHidden({
					target: translationManagerDialog,
					trigger: translationManagerDoneButton,
				});
			});

			test.step('Assert the new language is listed as an option', async () => {
				await expect(
					translationManagerEnglishTriggerButton
				).toBeVisible();

				await clickAndExpectToBeVisible({
					target: translationManagerGermanChoice,
					trigger: translationManagerEnglishTriggerButton,
				});
			});
		}
	);

	test(
		'Assert that languages can be searched in the translation manager',
		{tag: '@LPD-47235'},
		async ({jsComponentsSamplePage, page}) => {
			const {
				translationManageButton,
				translationManagerCatalanRow,
				translationManagerDialog,
				translationManagerEnglishTriggerButton,
				translationManagerSearchInput,
			} = jsComponentsSamplePage;

			await test.step('Click on a translation', async () => {
				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerEnglishTriggerButton,
				});
			});

			await test.step('Assert the translation manager is opened', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});

			await test.step('Search for a language', async () => {
				await translationManagerSearchInput.fill('Catalan');

				await expect(translationManagerCatalanRow).toBeVisible();
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
		async ({jsComponentsSamplePage}) => {
			const {
				translationManageButton,
				translationManagerDialog,
				translationManagerDoneButton,
				translationManagerEnglishTriggerButton,
				translationManagerFrenchRow,
			} = jsComponentsSamplePage;

			await test.step('Click on a translation', async () => {
				await clickAndExpectToBeVisible({
					target: translationManageButton,
					trigger: translationManagerEnglishTriggerButton,
				});
			});

			await test.step('Assert the translation manager is opened', async () => {
				await clickAndExpectToBeVisible({
					target: translationManagerDialog,
					trigger: translationManageButton,
				});
			});

			await test.step('Delete a language', async () => {
				const deleteButton = translationManagerFrenchRow.getByRole(
					'button',
					{
						name: 'Delete',
					}
				);

				await clickAndExpectToBeHidden({
					target: translationManagerFrenchRow,
					trigger: deleteButton,
				});
			});

			await test.step('Save and close the translation manager', async () => {
				await translationManagerDoneButton.click();

				await expect(translationManagerDialog).not.toBeVisible();
			});
		}
	);
});
