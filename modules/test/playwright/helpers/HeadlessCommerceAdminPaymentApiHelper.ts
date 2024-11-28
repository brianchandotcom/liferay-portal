/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TPayment = {
	amount?: number;
	channelId?: number;
	comment?: string;
	currencyCode?: string;
	id?: number;
	paymentIntegrationKey?: string;
	paymentIntegrationType?: number;
	paymentStatus?: number;
	reasonKey?: string;
	relatedItemId: number;
	relatedItemName?: string;
	transactionCode?: string;
	type?: number;
};

export class HeadlessCommerceAdminPaymentApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;
	constructor(apiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-payment/v1.0';
	}

	async deletePayment(paymentId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/payments/${paymentId}`
		);
	}

	async patchPayment(payment: TPayment, paymentId: number) {
		return this.apiHelpers.patch(
			`${this.apiHelpers.baseUrl}${this.basePath}/payments/${paymentId}`,
			payment
		);
	}

	async postPayment(payment: TPayment) {
		payment = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/payments`,
			{
				data: {
					amount: 0,
					channelId: 0,
					currencyCode: 'USD',
					paymentIntegrationKey: 'paypal-integration',
					paymentIntegrationType: 0,
					paymentStatus: 0,
					relatedItemName: 'com.liferay.commerce.model.CommerceOrder',
					type: 0,
					...payment,
				},
			}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({id: payment.id, type: 'payment'});
		}

		return payment;
	}
}
