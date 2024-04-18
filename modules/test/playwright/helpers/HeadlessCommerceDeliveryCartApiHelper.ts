/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TCartItem = {
	options?: string;
	quantity: number;
	replacedSkuId?: number;
	skuId: number;
	skuUnitOfMeasure?: TCartItemUOM;
};

type TCartItemUOM = {
	key: string;
};

type TCart = {
	accountId: number;
	cartItems?: TCartItem[];
	currencyCode?: string;
	id?: number;
};

export class HeadlessCommerceDeliveryCartApiHelper {
	readonly apiHelpers: ApiHelpers | DataApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers | DataApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-delivery-cart/v1.0/';
	}

	async deleteCart(cartId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/carts/${cartId}`
		);
	}

	async postCart(cart: TCart, channelId: number): Promise<TCart> {
		const postCart = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/channels/${channelId}/carts?nestedFields=cartItems`,
			{data: {accountId: 0, cartItems: [], currencyCode: 'USD', ...cart}}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({
				id: postCart.id,
				type: 'order',
			});
		}

		return postCart;
	}
}
