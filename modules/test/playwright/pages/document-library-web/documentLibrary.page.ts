/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import {Locator, Page} from '@playwright/test';

export class DocumentLibraryPage {
	readonly optionsMenu: Locator;
	readonly page: Page;
	readonly exportImportOptionsMenuItem: Locator;

	constructor(page: Page) {
		this.exportImportOptionsMenuItem = page.getByRole('menuitem', {
			name: 'Export / Import',
		});
		this.optionsMenu = page
			.getByTestId('headerOptions')
			.getByLabel('Options');
		this.page = page;
	}

	async goto() {
		await this.page.goto(
			'/group/guest/~/control_panel/manage?p_p_id=com_liferay_document_library_web_portlet_DLAdminPortlet'
		);
	}

	async editEntry(entryTitle: string) {
		await this.page
			.locator(`.card-body:has-text('${entryTitle}')`)
			.getByLabel('More actions')
			.click();
		await this.page.getByRole('menuitem', {name: 'Edit'}).click();
	}

	async openOptionsMenu() {
		await this.page.waitForLoadState('networkidle');
		await this.optionsMenu.click();
	}
}
