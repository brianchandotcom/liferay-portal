/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class DocumentLibraryEditFolderPage {
	readonly page: Page;
	readonly title: Locator;
	readonly saveButton: Locator;
	constructor(page: Page) {
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
		this.title = page.getByLabel('Name Required');
	}
	async createNewFolder(name: string) {
		await this.fillTitle(name);
		await this.saveButton.click();
	}
	async getSelectedWorkflowDefinition() {
		return await this.page
			.getByTitle('Workflow Definition')
			.evaluate(
				(select: HTMLSelectElement) =>
					select.options[select.selectedIndex].value
			);
	}

	async fillTitle(name: string) {
		await this.title.fill(name);
	}

	async setWorkflow(workflowName: string) {
		await this.page.getByLabel('Set the default workflow for').click();

		const dropdown = this.page.getByLabel('Default Workflow for all');

		await dropdown.selectOption({label: workflowName});

		await this.saveButton.click();
	}
}
