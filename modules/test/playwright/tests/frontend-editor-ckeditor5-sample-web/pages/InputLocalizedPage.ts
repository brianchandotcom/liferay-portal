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
	 * verifies the language button reflects the new locale.
	 *
	 * The dropdown menu is a portal aligned to its trigger. When the trigger
	 * sits low on the page the menu opens below the viewport fold, so clicking
	 * an option forces a page scroll that realigns the menu; on a slow CI
	 * machine the menu shifts in the gap between Playwright computing the click
	 * point and dispatching the event, and the click lands off the option
	 * without committing the selection. Scrolling the trigger up first lets the
	 * menu open fully on-screen, so the option click needs no scroll and lands
	 * cleanly. The open/click/verify flow is still retried as a unit as a
	 * safety net.
	 */
	async switchLanguage(
		languageButton: Locator,
		option: Locator,
		expectedLanguageId: string
	) {
		await languageButton.evaluate((element) =>
			element.scrollIntoView({block: 'start'})
		);

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
