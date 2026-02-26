/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TShipment = {
	carrier?: string;
	createDate?: string;
	expectedDate?: string;
	id?: number;
	orderId: number;
	shipmentItems?: TShipmentItem[];
	shippingAddressId?: number;
	shippingDate?: string;
	trackingNumber?: string;
	trackingURL?: string;
};

type TShipmentItem = {
	id?: number;
	orderItemId?: number;
	quantity: number;
	warehouseId?: number;
};

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

	async postShipmentStatusDelivered(shipmentId: number) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/shipments/${shipmentId}/status-delivered`
		);
	}

	async postShipment(shipment: TShipment): Promise<TShipment> {
		const postShipment = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/shipments?nestedFields=shipmentItems`,
			{data: {orderId: 0, shipmentItems: [], ...shipment}}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({
				id: postShipment.id,
				type: 'shipment',
			});
		}

		return postShipment;
	}
}
