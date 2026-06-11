/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';

export class TermsAndConditionDetailsPage {
	readonly activeToggle: Locator;
	readonly backButton: Locator;
	readonly descriptionInput: Locator;
	readonly nameInput: Locator;
	readonly nameInputLocaleSelector: Locator;
	readonly nameInputLocaleOption: (locale: string) => Locator;
	readonly page: Page;
	readonly publishButton: Locator;

	constructor(page: Page) {
		this.activeToggle = page.getByLabel('Active', {exact: true});
		this.backButton = page.getByRole('link', {exact: true, name: 'Back'});
		this.descriptionInput = page.locator(
			'textarea[id$="_descriptionMapAsXML"], iframe[title*="description"]'
		);
		this.nameInput = page
			.getByLabel('Title', {exact: true})
			.first()
			.or(page.getByRole('textbox', {name: 'Title'}));
		this.nameInputLocaleSelector = page
			.getByRole('button', {exact: false, name: 'Select Language'})
			.first()
			.or(page.getByRole('button', {name: 'Current translation'}))
			.first();
		this.nameInputLocaleOption = (locale) =>
			page.getByRole('menuitem', {exact: false, name: locale});
		this.page = page;
		this.publishButton = page
			.getByRole('button', {
				exact: true,
				name: 'Publish',
			})
			.or(page.getByRole('link', {name: 'Publish'}));
	}

	async changeNameInputLocale(locale: string) {
		const localeOption = this.nameInputLocaleOption(locale);

		await clickAndExpectToBeVisible({
			target: localeOption,
			trigger: this.nameInputLocaleSelector,
		});

		if (
			await localeOption.evaluate((node) =>
				node.classList.contains('active')
			)
		) {
			await this.page.keyboard.press('Escape');
		}
		else {
			await localeOption.click();
		}
	}

	async setActive(active: boolean) {
		const current = await this.activeToggle.isChecked();

		if (current !== active) {
			await this.activeToggle.click();
		}
	}
}
