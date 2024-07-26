/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers} from './ApiHelpers';

export class HeadlessCommerceAdminShipmentApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;
	constructor(apiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-shipment/v1.0';
	}

	async deleteShipment(shipmentId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/shipments/${shipmentId}`
		);
	}

	async getShipments() {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/shipments`
		);
	}
}
