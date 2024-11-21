/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {clickAndExpectToBeVisible} from '../../../utils/clickAndExpectToBeVisible';
import {waitForAlert} from '../../../utils/waitForAlert';

export class VocabulariesEditPage {
	readonly deleteButton: Locator;
	readonly nameInput: Locator;
	readonly saveButton: Locator;
	readonly page: Page;

	constructor(page: Page) {
		this.deleteButton = page.getByRole('button', {name: 'Delete'});
		this.nameInput = page.getByPlaceholder('Name');
		this.saveButton = page.getByRole('button', {
			name: 'Save',
		});
		this.page = page;
	}

	async add(name: string) {
		await this.fillName(name);
		await this.page.on('dialog', (dialog) => dialog.accept());
		await this.saveButton.click();
		await waitForAlert(this.page);
	}

	async delete(name: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Delete'}),
			trigger: this.page
				.getByRole('heading', {name})
				.getByLabel('Show Actions'),
		});

		await this.deleteButton.click();
	}

	async fillName(name: string) {
		await this.nameInput.fill(name);
	}

	async goto(name: string) {
		await clickAndExpectToBeVisible({
			autoClick: true,
			target: this.page.getByRole('menuitem', {name: 'Edit'}),
			trigger: this.page
				.getByRole('heading', {name})
				.getByLabel('Show Actions'),
		});
	}
}
