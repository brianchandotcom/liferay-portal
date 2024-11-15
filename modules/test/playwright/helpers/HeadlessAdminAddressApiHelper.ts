/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../utils/getRandomString';
import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TCountry = {
	a2: string;
	a3: string;
	active: boolean;
	billingAllowed: boolean;
	groupFilterEnabled: boolean;
	id: number;
	idd: number;
	name: string;
	number: number;
	position: number;
	regions?: TRegion[];
	shippingAllowed: boolean;
	subjectToVat: boolean;
};

type TRegion = {
	active: boolean;
	name: string;
	position?: number;
	regionCode: string;
};

export class HeadlessAdminAddressApiHelper {
	readonly apiHelpers: ApiHelpers | DataApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-admin-address/v1.0/';
	}

	async getCountryByName(name: string): Promise<TCountry> {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/countries/by-name/${name}`
		);
	}

	async postCountryRegion(countryId: number, region?: TRegion) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/countries/${countryId}/regions`,
			{
				data: {
					active: true,
					name: getRandomString(),
					regionCode: getRandomString(),
					...(region || {}),
				},
				failOnStatusCode: true,
			}
		);
	}
}
