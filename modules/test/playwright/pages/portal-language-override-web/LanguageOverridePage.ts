/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../utils/clickAndExpectToBeVisible';
import {PORTLET_URLS} from '../../utils/portletUrls';
import {waitForAlert} from '../../utils/waitForAlert';

export type TLanguageKey = {
	key: string;
	translations: {
		languageId: string;
		value: string;
	}[];
};

export class LanguageOverridePage {
	readonly filterButton: Locator;
	readonly newButton: Locator;
	readonly optionsButton: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.filterButton = page.getByRole('button', {
			exact: true,
			name: 'Filter',
		});
		this.newButton = page.getByRole('link', {name: 'Add Language Key'});
		this.optionsButton = page.getByRole('button', {name: 'Options'});
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async addLanguageKey({key, translations}: TLanguageKey) {
		await this.newButton.click();

		await this.page.getByLabel('key required').fill(key);

		for (const {languageId, value} of translations) {
			await this.page.getByLabel(languageId).click();
			await this.page.getByLabel(languageId).fill(value);
		}

		await this.saveButton.click();

		await waitForAlert(this.page);
	}

	async addLanguageKeys(languageOverrides: TLanguageKey[]) {
		for (const languageOverride of languageOverrides) {
			await this.addLanguageKey(languageOverride);
		}
	}

	async assertLanguageKeyInListView({key, translations}: TLanguageKey) {
		if (translations.length) {
			const normalizedLanguageIds = translations.map(({languageId}) =>
				languageId.replace('-', '_')
			);

			await expect(
				this.page.locator(
					`a:has-text("${key}"):has-text("Languages With Override: ${normalizedLanguageIds.join(', ')}")`
				)
			).toBeVisible();
		}
		else {
			await expect(
				this.page.getByRole('link', {name: key})
			).toBeAttached();
		}
	}

	async assertLanguageKeyNotInListView(key: string) {
		await expect(this.page.getByRole('link', {name: key})).toBeHidden();
	}

	async assertLanguageKeyTranslationIsEmpty(languageId: string) {
		await expect(this.page.getByLabel(languageId)).toHaveValue('');
	}

	async assertLanguageKeyTranslations({key, translations}: TLanguageKey) {
		await this.page.getByRole('link', {name: key}).click();

		await this.page.waitForLoadState();

		for (const {languageId, value} of translations) {
			await expect(this.page.getByLabel(languageId)).toHaveValue(value);
		}
	}

	async changeFilter(option: 'Any Language' | 'Selected Language') {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: option}),
			trigger: this.filterButton,
		});

		await this.page
			.getByText('Search Results', {exact: true})
			.waitFor({state: 'visible'});
	}

	async changeLocale(currentLanguageId: string, languageId: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: languageId}),
			trigger: this.page.getByRole('button', {name: currentLanguageId}),
		});

		await this.page.waitForLoadState();
	}

	async exportOverridenTranslations() {
		await this.optionsButton.click();

		await this.page
			.getByRole('menuitem', {name: 'Export Overridden Translations'})
			.click();
	}

	async goto() {
		await this.page.goto(`/group/guest${PORTLET_URLS.languageOverride}`);
	}

	async searchLanguageKey(key: string) {
		await this.page.getByRole('searchbox').click();
		await this.page.getByRole('searchbox').fill(key);

		await this.page
			.getByRole('button', {exact: true, name: 'Search for'})
			.click();

		await this.page.waitForLoadState();

		await this.page.getByText('Search Results').waitFor({state: 'visible'});
	}
}
