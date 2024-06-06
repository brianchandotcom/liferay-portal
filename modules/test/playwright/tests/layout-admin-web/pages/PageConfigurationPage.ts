/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {waitForSuccessAlert} from '../../../utils/waitForSuccessAlert';
import {LayoutPage} from '../../layout-admin-web/pages/LayoutPage';

export class PageConfigurationPage {
	readonly page: Page;

	readonly canonicalURLCheckbox: Locator;
	readonly customCanonicalURLSettings: Locator;
	readonly friendlyURL: Locator;
	readonly saveButton: Locator;
	readonly layoutPage: LayoutPage;

	constructor(page: Page) {
		this.page = page;

		this.canonicalURLCheckbox = page.getByLabel('Use Custom Canonical URL');
		this.friendlyURL = page.getByLabel('Friendly URL');
		this.saveButton = page.getByRole('button', {exact: true, name: 'Save'});
		this.customCanonicalURLSettings = page.getByLabel('Canonical URL', {
			exact: true,
		});
		this.layoutPage = new LayoutPage(page);
	}

	async goToSection(pageTitle: string, section: string) {
		await this.layoutPage.clickOnAction('Configure', pageTitle);

		await this.page
			.locator('li', {has: this.page.getByText(section)})
			.click();
	}

	async setCanonicalURL(canonicalURL: string) {
		await this.canonicalURLCheckbox.waitFor();

		await this.canonicalURLCheckbox.check();
		await this.customCanonicalURLSettings.fill(canonicalURL);

		await this.saveButton.click();

		await waitForSuccessAlert(
			this.page,
			'The page was updated successfully.'
		);
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
			target: this.page.getByRole('menuitem', {name: 'english'}),
			trigger: this.page
				.getByLabel('Current translation')
				.nth(1)
				.locator('..'),
		});

		await this.saveButton.click();

		await waitForSuccessAlert(
			this.page,
			'The page was updated successfully.'
		);
	}
}
