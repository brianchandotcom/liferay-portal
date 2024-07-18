/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Page} from '@playwright/test';

import {ApiHelpers} from '../../../../helpers/ApiHelpers';

type AssignUserToAccountRole = {
	accountId: number;
	accountRole: string;
};

type CreateAccount = {
    accountName: string;
    accountType: string;
}

type CreateMDFRequest = {
    data: {};
}

type TAccount = {
	externalReferenceCode?: string;
	id?: number;
	name: string;
	type?: string;
};

type TMyAccountType = TAccount & {
		currency: string,
		externalReferenceCode: string,
		marketingPerformance: number,
		marketingPlan: boolean,
		name: string,
		newProjectExistingBusiness: number,
		partnerCountry: string,
		r_prtLvlToAcc_c_partnerLevelERC: string,
		solutionDeliveryCertification: boolean,
};
export class PartnerHelper {
	readonly apiHelpers: ApiHelpers;

	constructor(page: Page) {
		this.apiHelpers = new ApiHelpers(page);
	}

	async assignUserToAccountRole({
		accountId,
		accountRole,
	}: AssignUserToAccountRole) {
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
		accountName,
	}: CreateAccount) {
		const partnerAccount: TMyAccountType = {
			currency: "USD",
			externalReferenceCode: "0017000000b3ScRAAU",
			marketingPerformance: 3,
			marketingPlan: true,
			name: accountName,
			newProjectExistingBusiness: 1,
			partnerCountry: "US",
			r_prtLvlToAcc_c_partnerLevelERC: "GOLD-PRTLVL",
			solutionDeliveryCertification: true,
		};

		try {
			const account = await this.apiHelpers.headlessAdminUser.postAccount(
				partnerAccount
			);

			await this.apiHelpers.headlessAdminUser.assignUserToAccountByEmailAddress(
				account.id,
				['test@liferay.com']
			);

			return {account};
		}
		catch (error) {
			console.error('Error when trying to create account', error);
			throw error;
		}
	}

    async createMDFRequest({
        data,
    }: CreateMDFRequest) {
        try {
            const mdfRequest = await this.apiHelpers.post('/o/c/mdfrequests', {
                data
            });

            return mdfRequest   
        }
        catch (error) {
            console.error('Error when trying to create an MDF Request', error);
            throw error;
        }
    }
}
