/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class InputLocalizedPage {
	readonly content: {
		container: Locator;
		editor: Locator;
		languageButton: Locator;
	};
	readonly default: {
		container: Locator;
		editor: Locator;
		languageButton: Locator;
	};
	readonly englishOption: Locator;
	readonly editable: Locator;
	readonly inputOnly: {
		container: Locator;
		editor: Locator;
	};
	readonly page: Page;
	readonly spanishOption: Locator;

	constructor(page: Page) {
		const contentContainer = page.locator('#contentInputLocalized');

		this.content = {
			container: contentContainer,
			editor: contentContainer.locator('.ck-editor__editable'),
			languageButton: contentContainer.getByTitle('Select a Language'),
		};

		const defaultContainer = page.locator('#defaultInputLocalized');

		this.default = {
			container: defaultContainer,
			editor: defaultContainer.locator('.ck-editor__editable'),
			languageButton: defaultContainer.getByTitle('Select a Language'),
		};

		const inputOnlyContainer = page.locator('#inputOnlyInputLocalized');

		this.inputOnly = {
			container: inputOnlyContainer,
			editor: inputOnlyContainer.locator('.ck-editor__editable'),
		};

		this.englishOption = page.locator('.dropdown-menu.show #en_US');
		this.spanishOption = page.locator('.dropdown-menu.show #es_ES');

		this.page = page;
	}

	/**
	 * Opens the editor's language dropdown, selects the given option, and
	 * verifies the language button reflects the new locale. The whole flow is
	 * retried as a unit: on slower environments the option click can register
	 * as a highlight without committing the selection (the menu is still
	 * rendering when the click lands), leaving the dropdown open and the locale
	 * unchanged. Re-running open + click until the button text updates recovers
	 * from that missed click instead of polling a value that never changes.
	 */
	async switchLanguage(
		languageButton: Locator,
		option: Locator,
		expectedLanguageId: string
	) {
		await expect(async () => {
			const expanded = await languageButton.getAttribute('aria-expanded');

			if (expanded !== 'true') {
				await languageButton.click({timeout: 1000});
			}

			await option.click({timeout: 1000});

			await expect(languageButton).toContainText(expectedLanguageId, {
				timeout: 1000,
			});
		}).toPass();
	}
}
