/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class ShareModalPage {
	readonly collaboratorInput: Locator;
	readonly inviteExternalUserOption: Locator;
	readonly page: Page;
	readonly submitButton: Locator;

	constructor(page: Page) {
		this.collaboratorInput = page.getByPlaceholder(
			'Enter name, email, or groups'
		);
		this.inviteExternalUserOption = page.getByText('Invite external user', {
			exact: false,
		});
		this.page = page;
		this.submitButton = page
			.getByRole('dialog')
			.getByRole('button', {exact: true, name: 'Share'});
	}

	getHeader(title: string) {
		return this.page.getByText(`Share "${title}"`);
	}

	async submit() {
		await this.submitButton.click();
	}

	async typeInCollaboratorInput(value: string) {
		await this.collaboratorInput.click();

		await this.collaboratorInput.fill(value);
	}
}
