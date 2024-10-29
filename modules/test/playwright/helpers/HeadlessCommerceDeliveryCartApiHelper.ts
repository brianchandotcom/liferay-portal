/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TCartItem = {
	deliveryGroupName?: string;
	id?: number;
	options?: string;
	quantity: number;
	replacedSkuId?: number;
	requestedDeliveryDate?: string;
	shippingAddressId?: string;
	skuId: number;
	skuUnitOfMeasure?: TCartItemUOM;
};

type TCartItemUOM = {
	key: string;
};

type TCart = {
	accountId: number;
	author?: string;
	billingAddressId?: number;
	cartItems?: TCartItem[];
	createDate?: string;
	currencyCode?: string;
	id?: number;
	modifiedDate?: string;
	paymentMethod?: string;
	shippingAddressId?: number;
	shippingMethod?: string;
	shippingOption?: string;
};

export class HeadlessCommerceDeliveryCartApiHelper {
	readonly apiHelpers: ApiHelpers | DataApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers | DataApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-delivery-cart/v1.0/';
	}

	async checkoutCart(cartId: number) {
		return this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/carts/${cartId}/checkout`
		);
	}

	async getCartItems(cartId: number) {
		return await this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/carts/${cartId}/items`
		);
	}

	async getComments(cartId: number) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/carts/${cartId}/comments`
		);
	}

	async deleteCart(cartId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/carts/${cartId}`
		);
	}

	async patchCart(cart: TCart, id: number): Promise<TCart> {
		const patchCart = await this.apiHelpers.patch(
			`${this.apiHelpers.baseUrl}${this.basePath}/carts/${id}`,
			cart
		);

		return patchCart;
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
