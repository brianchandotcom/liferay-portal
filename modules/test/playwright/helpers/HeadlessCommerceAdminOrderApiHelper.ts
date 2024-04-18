/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

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

	async getOrdersPage() {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/orders`
		);
	}
}
