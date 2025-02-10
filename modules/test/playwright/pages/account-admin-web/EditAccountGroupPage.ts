/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {DataApiHelpers} from '../../helpers/ApiHelpers';
import getRandomString from '../../utils/getRandomString';
import {waitForAlert} from '../../utils/waitForAlert';

export class EditAccountGroupPage {
	readonly accountGroupNameInput: Locator;
	readonly accountsLink: Locator;
	readonly backButton: Locator;
	readonly descriptionInput: Locator;
	readonly detailsLink: Locator;
	readonly externalReferenceCodeInput: Locator;
	readonly page: Page;
	readonly saveButton: Locator;

	constructor(page: Page) {
		this.accountGroupNameInput = page.getByLabel('Account Group Name');
		this.accountsLink = page.getByRole('link', {
			exact: true,
			name: 'Accounts',
		});
		this.backButton = page.getByRole('link', {exact: true, name: 'Back'});
		this.descriptionInput = page.getByLabel('Description');
		this.detailsLink = page.getByRole('link', {
			exact: true,
			name: 'Details',
		});
		this.externalReferenceCodeInput = page.getByLabel(
			'External Reference Code'
		);
		this.page = page;
		this.saveButton = page.getByRole('button', {name: 'Save'});
	}

	async addAccountGroup(
		apiHelpers: DataApiHelpers,
		{
			description = '',
			externalReferenceCode = getRandomString(),
			name = getRandomString(),
		}: {
			description?: string;
			externalReferenceCode?: string;
			name?: string;
		}
	) {
		await expect(this.accountGroupNameInput).toBeEnabled();

		await this.accountGroupNameInput.fill(name);
		await this.descriptionInput.fill(description);
		await this.externalReferenceCodeInput.fill(externalReferenceCode);

		await this.saveButton.click();

		await waitForAlert(this.page);

		const accountGroup =
			await apiHelpers.headlessAdminUser.getAccountGroupByExternalReferenceCode(
				externalReferenceCode
			);

		apiHelpers.data.push({
			id: accountGroup.id,
			type: 'accountGroup',
		});
	}
}
