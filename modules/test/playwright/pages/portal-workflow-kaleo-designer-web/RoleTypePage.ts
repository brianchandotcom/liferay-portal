/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

export class RoleTypePage {
	readonly InputAutoCreate: Locator;
	readonly inputRoleName: Locator;
	readonly inputRoleType: Locator;
	readonly newSectionButton: Locator;
	readonly page: Page;

	constructor(index: number, page: Page) {
		this.InputAutoCreate = page.getByRole('checkbox').nth(index);
		this.inputRoleName = page.locator('#role-name').nth(index);
		this.inputRoleType = page.locator('#role-type').nth(index);
		this.newSectionButton = page
			.getByRole('button', {name: 'New Section'})
			.nth(index);
		this.page = page;
	}

	async assertSectionFields(
		autocreate: boolean,
		roleName: string,
		roleType: string
	) {
		if (autocreate) {
			await expect(this.InputAutoCreate).toBeChecked();
		}

		await expect(this.inputRoleName).toHaveValue(roleName);

		await expect(this.inputRoleType).toHaveValue(roleType);
	}

	async fillSectionFields(
		autocreate: boolean,
		roleName: string,
		roleType: string
	) {
		await this.inputRoleType.click();

		await this.page
			.getByRole('menuitem', {exact: true, name: roleType})
			.click();

		await this.inputRoleName.click();

		await this.page
			.getByRole('menuitem', {exact: true, name: roleName})
			.click();

		if (autocreate) {
			await this.InputAutoCreate.check();
		}
	}

	async clickNewSectionButton() {
		await this.newSectionButton.click();
	}
}
