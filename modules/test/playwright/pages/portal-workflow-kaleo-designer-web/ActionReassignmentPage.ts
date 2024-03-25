/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page, expect} from '@playwright/test';

import {RoleTypePage} from './RoleTypePage';

export class ActionReassignmentPage {
	readonly inputActionType: Locator;
	readonly inputReassignmentType: Locator;
	page: Page;
	roleTypePage: RoleTypePage;

	constructor(page: Page) {
		this.inputActionType = page.locator('#action-type');
		this.inputReassignmentType = page.locator('#reassignment-type');
		this.page = page;
	}

	async assertRoleTypeReassignmentType(roleTypes: RoleType[]) {
		await expect(this.inputReassignmentType).toHaveValue('roleType');

		for (let i = 0; i < roleTypes.length; i++) {
			this.roleTypePage = new RoleTypePage(i, this.page);

			const {autocreate, roleName, roleType} = roleTypes[i];

			await this.roleTypePage.assertSectionFields(
				autocreate,
				roleName,
				roleType
			);
		}
	}

	async fillRoleTypeReassignmentType(roleTypes: RoleType[]) {
		await this.inputActionType.selectOption('reassignments');

		await this.inputReassignmentType.selectOption('roleType');

		for (let i = 0; i < roleTypes.length; i++) {
			this.roleTypePage = new RoleTypePage(i, this.page);

			const {autocreate, roleName, roleType} = roleTypes[i];

			await this.roleTypePage.fillSectionFields(
				autocreate,
				roleName,
				roleType
			);

			if (i === roleTypes.length - 1) {
				return;
			}
			await this.roleTypePage.clickNewSectionButton();
		}
	}
}
