/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TWishList = {
	defaultWishList?: boolean;
	id?: number;
	name?: string;
	wishListItems?: TWishListItem[];
};

type TWishListItem = {
	id?: number;
	productId?: number;
	skuId?: number;
};

export class HeadlessCommerceDeliveryCatalogApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-delivery-catalog/v1.0/';
	}

	async getChannelProductAttachmentsPage(
		channelId: number,
		productId: number
	) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}/products/${productId}/attachments`
		);
	}

	async getChannelProductPinsPage(
		accountId: number,
		channelId: number,
		productId: number
	) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}/products/${productId}/pins?accountId=${accountId}`
		);
	}

	async postWishList(
		wishList: TWishList,
		channelId: number,
		accountId: number
	): Promise<TWishList> {
		const postWishList = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}/wishlists?nestedFields=wishListItems&wishListItems.accountId=${accountId}&accountId=${accountId}`,
			{
				data: {
					defaultWishList: true,
					name: wishList.name,
					wishListItems: wishList.wishListItems,
				},
			}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({
				id: postWishList.id,
				type: 'wishList',
			});
		}

		return postWishList;
	}

	async deleteWishList(wishListId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/wishlists/${wishListId}`
		);
	}
}
