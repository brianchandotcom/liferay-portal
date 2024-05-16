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

type TAccountGroup = {
	description?: string;
	externalReferenceCode?: string;
	id?: number;
	name: string;
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

	async assignAccountToAccountGroup(
		accountExternalReferenceCode: string,
		accountGroupExternalReferenceCode: string
	) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/account-groups/by-external-reference-code/${accountExternalReferenceCode}/accounts/by-external-reference-code/${accountGroupExternalReferenceCode}`
		);
	}

	async assignUserToAccountByEmailAddress(
		accountId: number,
		emails: string[]
	) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts/${accountId}/user-accounts/by-email-address`,
			{data: emails || []}
		);
	}

	async deleteAccount(accountId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts/${accountId}`
		);
	}

	async deleteAccountGroup(accountGroupId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/account-groups/${accountGroupId}`
		);
	}

	async deleteOrganization(organizationId: string) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/organizations/${organizationId}`
		);
	}

	async deleteRoleUserAccountAssociation(
		roleId: number,
		userAccountId: number
	) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/roles/${roleId}/association/user-account/${userAccountId}`
		);
	}

	async getSiteByFriendlyUrlPath(friendlyUrlPath: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/sites/by-friendly-url-path/${friendlyUrlPath}`
		);
	}

	async getRoles(search: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/roles?search=${search}`
		);
	}

	async getUserAccountByEmailAddress(emailAddress: string) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/user-accounts/by-email-address/${emailAddress}`
		);
	}

	async postAccount(account?: TAccount): Promise<TAccount> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/accounts`,
			{data: {name: 'Account' + getRandomInt(), ...(account || {})}}
		);
	}

	async postAccountGroup(
		accountGroup?: TAccountGroup
	): Promise<TAccountGroup> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/account-groups`,
			{
				data: {
					name: 'AccountGroup' + getRandomInt(),
					...(accountGroup || {}),
				},
			}
		);
	}

	async postRoleUserAccountAssociation(
		roleId: number,
		userAccountId: number
	) {
		return this.apiHelpers.postResponse(
			`${this.apiHelpers.baseUrl}${this.basePath}/roles/${roleId}/association/user-account/${userAccountId}`,
			{data: {}}
		);
	}

	async postOrganization(
		organization?: TOrganization
	): Promise<TOrganization> {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/organizations`,
			{
				data: {
					name: 'Organization' + getRandomInt(),
					...(organization || {}),
				},
			}
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
			{data: {}}
		);
	}
}
