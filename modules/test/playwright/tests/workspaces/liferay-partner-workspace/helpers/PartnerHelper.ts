/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';
import {TPartnerAccount} from '../types/mdf';

export class PartnerHelper {
	readonly apiHelpers: ApiHelpers;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
	}

	async assignUserToAccountRole({accountId, accountRole}) {
		try {
			const user =
				await this.apiHelpers.headlessAdminUser.getUserAccountByEmailAddress(
					'test@liferay.com'
				);

			const rolesResponse =
				await this.apiHelpers.headlessAdminUser.getAccountRoles(
					accountId
				);

			const filteredAccountRole = rolesResponse?.items?.filter(
				(role) => role.name === accountRole
			);

			await this.apiHelpers.headlessAdminUser.assignUserToAccountRole(
				accountId,
				filteredAccountRole[0].id,
				user.id
			);
		}
		catch (error) {
			console.error('Error when trying to assign user to role', error);
			throw error;
		}
	}

	async createAccountUser({
		currency,
		externalReferenceCode,
		name,
		partnerCountry,
		type,
	}: TPartnerAccount) {
		const partnerAccount = {
			currency,
			externalReferenceCode,
			name,
			partnerCountry,
			type,
		};

		try {
			const account =
				await this.apiHelpers.headlessAdminUser.postAccount(
					partnerAccount
				);

			await this.apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
				account.id,
				['test@liferay.com']
			);

			return account;
		}
		catch (error) {
			console.error('Error when trying to create account', error);
			throw error;
		}
	}

	async createMDFRequest({data}) {
		try {
			const mdfRequest = await this.apiHelpers.post('/o/c/mdfrequests', {
				data,
			});

			return mdfRequest;
		}
		catch (error) {
			console.error('Error when trying to create an MDF Request', error);
			throw error;
		}
	}
}
