/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {PagesAdminPage} from '../../../pages/layout-admin-web/PagesAdminPage';
import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../utils/fillAndClickOutside';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';

export class PageConfigurationPage {
	readonly page: Page;

	readonly canonicalURLCheckbox: Locator;
	readonly customCanonicalURLSettings: Locator;
	readonly friendlyURL: Locator;
	readonly name: Locator;
	readonly pagesAdminPage: PagesAdminPage;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.canonicalURLCheckbox = page.getByLabel('Use Custom Canonical URL');
		this.friendlyURL = page.getByLabel('Friendly URL');
		this.customCanonicalURLSettings = page.getByLabel('Canonical URL', {
			exact: true,
		});
		this.name = page.getByLabel('Name');
		this.pagesAdminPage = new PagesAdminPage(page);
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
	}

	async goToSection(pageTitle: string, section: string) {
		await this.pagesAdminPage.clickOnAction('Configure', pageTitle);

		await this.page
			.locator('li', {has: this.page.getByText(section)})
			.click();
	}

	async setCanonicalURL(canonicalURL: string) {
		await this.canonicalURLCheckbox.waitFor();

		await this.canonicalURLCheckbox.check();
		await this.customCanonicalURLSettings.fill(canonicalURL);

		await this.save();
	}

	async save() {
		await this.saveButton.click();

		await waitForSuccessAlert(
			this.page,
			'Success:The page was updated successfully.'
		);
	}

	async fillName(name: string) {
		await fillAndClickOutside(this.page, this.name, name);
	}

	async setFriendlyURL(friendlyURL: string, language: 'spanish' | 'english') {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: language}),
			trigger: this.page
				.getByLabel('Current translation')
				.nth(1)
				.locator('..'),
		});

		await this.friendlyURL.fill(friendlyURL);

		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page
				.getByRole('menuitem')
				.filter({hasText: 'default'}),
			trigger: this.page
				.getByLabel('Current translation')
				.nth(1)
				.locator('..'),
		});

		await this.save();
	}

	async setHTMLTitle(title: string) {
		await this.page.getByLabel('HTML Title').fill(title);

		await this.save();
	}
}
