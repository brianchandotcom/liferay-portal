/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getRandomInt} from '../utils/getRandomInt';
import getRandomString from '../utils/getRandomString';
import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TTerms = {
	active?: boolean;
	id?: number;
	label?: {
		[key: string]: string;
	};
	name?: string;
	priority?: number;
	type: string;
};

export class HeadlessCommerceAdminOrderApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-order/v1.0/';
	}

	async deleteOrder(orderId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/orders/${orderId}`
		);
	}

	async deleteTerms(termsId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/terms/${termsId}`
		);
	}

	async getOrdersPage() {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/orders`
		);
	}

	async postTerms(terms: TTerms) {
		terms = {
			active: true,
			label: {
				en_US: getRandomString(),
			},
			name: getRandomString(),
			priority: getRandomInt(),
			type: '',
			...(terms || {}),
		};

		terms = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/terms`,
			{
				data: terms,
				failOnStatusCode: true,
			}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({id: terms.id, type: 'terms'});
		}

		return terms;
	}
}
