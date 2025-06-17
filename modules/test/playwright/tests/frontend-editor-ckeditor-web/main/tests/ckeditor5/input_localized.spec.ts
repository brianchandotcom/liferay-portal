/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {expect, mergeTests} from '@playwright/test';

import {apiHelpersTest} from '../../../../../fixtures/apiHelpersTest';
import {featureFlagsTest} from '../../../../../fixtures/featureFlagsTest';
import {isolatedSiteTest} from '../../../../../fixtures/isolatedSiteTest';
import {loginTest} from '../../../../../fixtures/loginTest';
import {clickAndExpectToBeVisible} from '../../../../../utils/clickAndExpectToBeVisible';
import {ckeditorSamplePageTest} from '../../fixtures/ckeditorSamplePageTest';

export const test = mergeTests(
	apiHelpersTest,
	ckeditorSamplePageTest,
	featureFlagsTest({
		'LPD-11235': {enabled: true},
		'LPS-178052': {enabled: true},
	}),
	isolatedSiteTest,
	loginTest()
);

test.beforeEach(async ({ckeditorSamplePage, site}) => {
	await ckeditorSamplePage.createAndGotoSitePage({site});

	await ckeditorSamplePage.selectTab('CKEditor 5');
	await ckeditorSamplePage.selectTab('Input Localized');
});

test(
	'Editor and languages dropdown properly function',
	{tag: '@LPD-54165'},
	async ({page}) => {
		const contentInputLocalizedContainer = page.locator(
			'#contentInputLocalized'
		);
		const defaultInputLocalizedContainer = page.locator(
			'#defaultInputLocalized'
		);
		const inputOnlyInputLocalizedContainer = page.locator(
			'#inputOnlyInputLocalized'
		);

		test.step('Check all the editors are displayed', async () => {
			await expect(
				contentInputLocalizedContainer.locator('.ck-editor__editable')
			).toBeVisible();
			await expect(
				defaultInputLocalizedContainer.locator('.ck-editor__editable')
			).toBeVisible();
			await expect(
				inputOnlyInputLocalizedContainer.locator('.ck-editor__editable')
			).toBeVisible();
		});

		test.step('Check the initial content is displayed', async () => {
			const editor = contentInputLocalizedContainer.locator(
				'.ck-editor__editable'
			);
			const languageButton = contentInputLocalizedContainer.getByRole(
				'button',
				{
					name: /Select a language/,
				}
			);
			const spanishOption = page.getByRole('option', {name: 'es-ES'});

			await expect(editor).toHaveText('English');

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: spanishOption,
				trigger: languageButton,
			});

			await expect(editor).toHaveText('Spanish');
		});

		test.step('Check that multiple translations can be added', async () => {
			const editor = defaultInputLocalizedContainer.locator(
				'.ck-editor__editable'
			);
			const languageButton = defaultInputLocalizedContainer.getByRole(
				'button',
				{
					name: /Select a language/,
				}
			);

			const englishOption = page.getByRole('option', {name: 'en-US'});
			const spanishOption = page.getByRole('option', {name: 'es-ES'});

			await editor.fill('English');

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: spanishOption,
				trigger: languageButton,
			});

			await editor.fill('Spanish');

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: englishOption,
				trigger: languageButton,
			});

			await expect(editor).toHaveText('English');

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: spanishOption,
				trigger: languageButton,
			});

			await expect(editor).toHaveText('Spanish');

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: englishOption,
				trigger: languageButton,
			});
		});

		test.step('Check that the languages dropdown is hidden', async () => {
			await expect(
				inputOnlyInputLocalizedContainer.getByRole('button', {
					name: /Select a language/,
				})
			).toHaveCount(0);
		});

		test.step('Check the languages dropdown are synced', async () => {
			const defaultInputLanguageButton =
				defaultInputLocalizedContainer.getByRole('button', {
					name: /Select a language/,
				});

			const contentInputLanguageButton =
				contentInputLocalizedContainer.getByRole('button', {
					name: /Select a language/,
				});

			const spanishOption = page.getByRole('option', {name: 'es-ES'});
			const englishOption = page.getByRole('option', {name: 'en-US'});

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: spanishOption,
				trigger: defaultInputLanguageButton,
			});

			await expect(async () => {
				await expect(defaultInputLanguageButton).toContainText('es-ES');
			}).toPass();

			await expect(async () => {
				await expect(contentInputLanguageButton).toContainText('es-ES');
			}).toPass();

			await clickAndExpectToBeVisible({
				autoClick: true,
				target: englishOption,
				trigger: contentInputLanguageButton,
			});

			await expect(async () => {
				await expect(defaultInputLanguageButton).toContainText('en-US');
			}).toPass();

			await expect(async () => {
				await expect(contentInputLanguageButton).toContainText('en-US');
			}).toPass();
		});
	}
);
