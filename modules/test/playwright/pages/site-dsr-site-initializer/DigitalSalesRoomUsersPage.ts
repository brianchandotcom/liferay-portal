/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

export class DigitalSalesRoomUsersPage {
	readonly doneButton: Locator;
	readonly inviteButton: Locator;
	readonly page: Page;
	readonly shareButton: Locator;
	readonly shareModal: Locator;
	readonly shareModalEmailInput: Locator;
	readonly shareModalHeading: Locator;
	readonly shareModalInviteButton: Locator;
	readonly userEmailAddressesInput: Locator;

	constructor(page: Page) {
		this.doneButton = page.getByRole('button', {name: 'Done'});
		this.inviteButton = page.locator('[data-testid="inviteButton"]');
		this.page = page;
		this.shareButton = page.getByRole('button', {name: 'share'});
		this.shareModal = page.locator('.modal-dialog');
		this.shareModalEmailInput = this.shareModal.locator(
			'[data-testid="emailAddressesInput"]'
		);
		this.shareModalHeading = this.shareModal.getByRole('heading', {
			name: 'Share',
		});
		this.shareModalInviteButton = this.shareModal.locator(
			'[data-testid="inviteButton"]'
		);
		this.userEmailAddressesInput = page.locator(
			'[data-testid="emailAddressesInput"]'
		);
	}

	removeUserButton(nameOrEmail: string): Locator {
		return this.userRow(nameOrEmail)
			.getByRole('button')
			.filter({has: this.page.locator('.lexicon-icon-trash')});
	}

	roleDropdown(nameOrEmail: string): Locator {
		return this.userRow(nameOrEmail).locator('.dropdown-toggle');
	}

	roleText(nameOrEmail: string, role: string): Locator {
		return this.userRow(nameOrEmail).getByText(role, {exact: true});
	}

	userRow(nameOrEmail: string): Locator {
		return this.page.locator('.user-row').filter({hasText: nameOrEmail});
	}
}
