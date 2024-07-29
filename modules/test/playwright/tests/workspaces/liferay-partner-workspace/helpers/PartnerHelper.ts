/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import {TMDFRequest} from '../types/mdf';
import {TRole} from '../types/role';
import {TUserAccount} from '../types/user';

export class PartnerHelper {
	readonly apiHelpers: ApiHelpers;
	readonly page: Page;
	readonly site: Site;

	constructor(page: Page, site: Site) {
		this.page = page;
		this.site = site;

		this.apiHelpers = new ApiHelpers(this.page);
	}

	async assignUserToAccountRole(accountId: number, roleName: string, userId: number) {
		try {
			const rolesResponse =
				await this.apiHelpers.headlessAdminUser.getAccountRoles(
					accountId
				);

			const filteredAccountRole = rolesResponse?.items?.filter(
				(role: TRole) => role.name === roleName
			);

			await this.apiHelpers.headlessAdminUser.assignUserToAccountRole(
				accountId,
				filteredAccountRole[0].id,
				userId
			);
		}
		catch (error) {
			console.error('Error when trying to assign user to account role', error);

			throw error;
		}
	}

	async createMDFRequest(mdfRequest: TMDFRequest) {
		try {
			const mdfRequestData = await this.apiHelpers.post('/o/c/mdfrequests', {
				data: mdfRequest,
			});

			return mdfRequestData;
		}
		catch (error) {
			console.error('Error when trying to create an MDF Request', error);

			throw error;
		}
	}

	async deleteMDFRequest(mdfRequestId: number) {
		await this.apiHelpers.delete(`/o/c/mdfrequests/${mdfRequestId}`);
	}

	async performLogin(user: TUserAccount) {
		await this.page.goto('/c/portal/login');

		const signInButton = await this.page.getByRole('button', {
			name: 'Sign In',
		});

		await this.page.getByLabel('Email Address').fill(user.emailAddress);
		await this.page.getByLabel('Password').fill(user.password);

		await signInButton.click();
	}

	async performLogout() {
		await this.page.goto('/c/portal/logout');
	}
}
