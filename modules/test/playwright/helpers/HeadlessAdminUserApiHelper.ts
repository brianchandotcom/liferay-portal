/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/getRandomInt';
import {ApiHelpers} from './ApiHelpers';

type TAccount = {
	externalReferenceCode?: string;
	id?: number;
	name: string;
	type?: string;
};

type THoursAvailable = {
	closes: string;
	dayOfWeek?: string;
	opens: string;
};

type TOrganization = {
	id?: string;
	name: string;
	services?: TServices[];
};

type TServices = {
	hoursAvailable: THoursAvailable[];
	serviceType: string;
};

export class HeadlessAdminUserApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-admin-user/v1.0/';
	}

	async assignUserToAccountByEmailAddress(
		accountId: number,
		emails: string[]
	) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts/${accountId}/user-accounts/by-email-address`,
			emails || []
		);
	}

	async deleteAccount(accountId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts/${accountId}`
		);
	}

	async deleteOrganization(organizationId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/organizations/${organizationId}`
		);
	}

	async getSiteByFriendlyUrlPath(friendlyUrlPath: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/by-friendly-url-path/${friendlyUrlPath}`
		);
	}

	async postAccount(account?: TAccount): Promise<TAccount> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts`,
			{name: 'Account' + getRandomInt(), ...(account || {})}
		);
	}

	async postOrganization(
		organization?: TOrganization
	): Promise<TOrganization> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/organizations`,
			{name: 'Organization' + getRandomInt(), ...(organization || {})}
		);
	}

	async getAccountRoles(accountId: number) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts/${accountId}/account-roles`
		);
	}

	async assignAccountRoles(
		accountERC: string,
		roleId: number,
		userEmail: string
	) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts/by-external-reference-code/${accountERC}/account-roles/${roleId}/user-accounts/by-email-address/${userEmail}`,
			{}
		);
	}
}
