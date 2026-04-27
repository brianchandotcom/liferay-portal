/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TOrderAttachment = {
	dateModified?: string;
	extension?: string;
	externalReferenceCode?: string;
	id?: number;
	priority?: number;
	restricted?: boolean;
	title?: string;
	type?: string;
	url?: string;
};

type TOrderAttachmentBase64 = {
	attachment: string;
	externalReferenceCode?: string;
	priority?: number;
	restricted?: boolean;
	title: string;
	type?: string;
};

export class HeadlessCommerceAdminOrderAttachmentApiHelper {
	readonly apiHelpers: ApiHelpers | DataApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers | DataApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-delivery-order/v1.0';
	}

	async deleteOrderAttachment(orderAttachmentId: number, orderId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/placed-orders/${orderId}/attachments/${orderAttachmentId}`
		);
	}

	async postOrderAttachment(
		orderId: number,
		orderAttachment: TOrderAttachmentBase64
	): Promise<TOrderAttachment> {
		const postOrderAttachment = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/placed-orders/${orderId}/attachments/by-base64`,
			{data: orderAttachment, failOnStatusCode: true}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({
				id: `${orderId}_${postOrderAttachment.id}`,
				type: 'orderAttachment',
			});
		}

		return postOrderAttachment;
	}
}
