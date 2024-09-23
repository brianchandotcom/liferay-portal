/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Locator, Page} from '@playwright/test';

import {CUSTOMER_SITE_FRIENLY_URL_PATH} from '../utils/constants';

export class ProjectTeamMembersPage {
	readonly applyButton: Locator;
	readonly emailField: Locator;
	readonly firstNameField: Locator;
	readonly inviteButton: Locator;
	readonly lastNameField: Locator;
	readonly page: Page;
	readonly roleSelect: Locator;
	readonly sendInvitationsButton: Locator;
	readonly userActionColumnHeader: Locator;
	readonly userRoleOption: Locator;

	constructor(page: Page) {
		this.applyButton = page.getByRole('button', {name: 'Apply'});
		this.emailField = page.getByLabel('Email');
		this.firstNameField = page.getByLabel('First Name');
		this.inviteButton = page.getByRole('button', {name: 'invite'});
		this.lastNameField = page.getByLabel('Last Name');
		this.page = page;
		this.roleSelect = page
			.locator('div.role-selector-container')
			.getByRole('button');
		this.sendInvitationsButton = page.getByRole('button', {
			name: 'Send Invitations',
		});
		this.userActionColumnHeader = page.locator('th:nth-child(6)');
		this.userRoleOption = page
			.locator('div.dropdown-menu')
			.getByText('User', {exact: true});
	}

	async goto(accountExternalReferenceCode: String) {
		await this.page.goto(
			`${CUSTOMER_SITE_FRIENLY_URL_PATH}/project/#/${accountExternalReferenceCode}/team-members`
		);
	}
}
