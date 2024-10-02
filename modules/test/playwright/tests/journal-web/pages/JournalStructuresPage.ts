/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import fillAndClickOutside from '../../../utils/fillAndClickOutside';
import {PORTLET_URLS} from '../../../utils/portletUrls';

export class JournalStructuresPage {
	readonly page: Page;

	readonly importButton: Locator;
	readonly importStructureOptionsMenuItem: Locator;
	readonly newButton: Locator;
	readonly optionsMenu: Locator;
	readonly selectButton: Locator;

	constructor(page: Page) {
		this.page = page;

		this.importButton = page.getByRole('button', {name: 'Import'});
		this.importStructureOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Import Structure',
		});
		this.newButton = page.getByText('New', {exact: true});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');

		this.selectButton = page.getByRole('button', {name: 'Select'});
	}

	async goto(siteUrl?: Site['friendlyUrlPath']) {
		await this.page.goto(
			`/group${siteUrl || '/guest'}${PORTLET_URLS.journalStructures}`
		);
	}

	async goToCreateNewStructure() {
		await this.goto();
		await this.newButton.click();
	}

	async goToJournalStructureAction(action: string, title: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {
				exact: true,
				name: action,
			}),
			trigger: this.page
				.getByRole('row', {name: `${title}`})
				.getByLabel('Show Actions', {exact: true}),
		});
	}

	async import(filePath: string, title: string) {
		await this.importStructureOptionsMenuItem.click();

		const textField = this.page.getByRole('textbox', {
			name: 'Name',
		});

		await fillAndClickOutside(this.page, textField, title);

		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.selectButton.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(filePath);

		await this.importButton.click();
	}

	async importAndOverride(filePath: string, title: string) {
		await this.goToJournalStructureAction('Import and Override', title);

		const fileChooserPromise = this.page.waitForEvent('filechooser');

		await this.selectButton.click();

		const fileChooser = await fileChooserPromise;

		await fileChooser.setFiles(filePath);

		await this.importButton.click();
	}

	async openOptionsMenu() {
		await this.optionsMenu
			.and(this.page.locator('[aria-haspopup]'))
			.click();
	}
}
