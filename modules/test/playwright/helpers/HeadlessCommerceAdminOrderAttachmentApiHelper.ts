/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TOrderAttachment = {
	attachment?: {
		fileBase64: string;
		name: string;
	};
	attachmentType?: {
		key: string;
	};
	id?: number;
	priority?: number;
	private?: boolean;
	r_accountToCommerceOrderAttachments_accountEntryId: number;
	r_commerceOrderToCommerceOrderAttachments_commerceOrderId: number;
	title: string;
};

export class HeadlessCommerceAdminOrderAttachmentApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly applicationName: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.applicationName = 'commerce/order-attachments';
	}

	async deleteOrderAttachment(orderAttachmentId: number) {
		return this.apiHelpers.objectEntry.deleteObjectEntry(
			this.applicationName,
			String(orderAttachmentId)
		);
	}

	async postOrderAttachment(
		orderAttachment: TOrderAttachment
	): Promise<ObjectEntry> {
		const postOrderAttachment =
			await this.apiHelpers.objectEntry.postObjectEntry(
				orderAttachment,
				this.applicationName
			);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({
				id: postOrderAttachment.id,
				type: 'orderAttachment',
			});
		}

		return postOrderAttachment;
	}
}
