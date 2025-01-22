/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {createResourceURL, fetch} from 'frontend-js-web';

import {
	Cart,
	MarketplaceAuthorization,
	MarketplaceConfiguration,
	Product,
} from '../types';

class MarketplaceRestError extends Error {
	public info: any;
	public status?: number;

	constructor(message: string) {
		super(message);
	}
}

export class MarketplaceRest {
	private authorization?: MarketplaceAuthorization;

	constructor(
		protected baseResourceURL: string,
		protected marketplaceConfiguration: MarketplaceConfiguration
	) {}

	public async consoleProvisioningOrder(cart: Cart) {
		return this.fetchMarketplaceService(`/dxp/provisioning/${cart.id}`, {
			body: JSON.stringify({
				orderItemId: cart.cartItems[0]?.id,
				projectId: this.settings.cloudProject,
			}),
			method: 'POST',
		});
	}

	public async createCart(product: Product) {
		const {account, channelId} =
			this.marketplaceConfiguration.settings || {};

		const purchasableSKUs = product.skus.filter(
			({purchasable}) => purchasable
		);

		const baseCart = {
			accountId: account.id,
			cartItems: [
				{
					price: {
						currency: 'USD',
						discount: 0,
					},
					productId: product.productId,
					quantity: 1,
					settings: {
						maxQuantity: 1,
					},
					skuId: purchasableSKUs[0]?.id,
				},
			],
			currencyCode: 'USD',
			orderTypeExternalReferenceCode: 'CLOUDAPP',
		} as Partial<Cart>;

		let cart = await this.fetchMarketplace(
			`/o/headless-commerce-delivery-cart/v1.0/channels/${channelId}/carts`,
			{
				body: JSON.stringify(baseCart),
				method: 'POST',
			}
		);

		cart = await this.checkoutCart(cart);

		return cart;
	}

	private async checkoutCart(cart: Cart) {
		return this.fetchMarketplace(
			`/o/headless-commerce-delivery-cart/v1.0/carts/${cart.id}/checkout`,
			{
				method: 'POST',
			}
		);
	}

	private async getBaseFetch(url: string, options?: RequestInit) {
		const response = await fetch(url, options);

		if (!response.ok) {
			const error = new MarketplaceRestError(
				'An error occurred while fetching the data.'
			);

			error.info = await response.json();
			error.status = response.status;
			throw error;
		}

		return response.json();
	}

	static getBaseResourceURL() {
		return `/group/guest/~/control_panel/manage?p_p_id=${Liferay.PortletKeys.INSTANCE_SETTINGS}`;
	}

	public async fetchMarketplace(
		url: string,
		options?: RequestInit & {guestOperation?: boolean}
	) {
		const headers = {
			...options?.headers,
			'Authorization': '',
			'Content-Type': 'application/json',
		};

		if (!options?.guestOperation) {
			const {accessToken} = await this.getMarketplaceToken();

			headers.Authorization = `Bearer ${accessToken}`;
		}

		return this.getBaseFetch(`${this.marketplaceURL}${url}`, {
			...options,
			headers,
		});
	}

	public async fetchMarketplaceService(url: string, options?: RequestInit) {
		const {accessToken} = await this.getMarketplaceToken();

		return this.getBaseFetch(`${this.marketplaceServiceURL}${url}`, {
			...options,
			headers: {
				'Authorization': `Bearer ${accessToken}`,
				'Content-Type': 'application/json',
			},
		});
	}

	public async getMarketplaceToken() {
		if (this.authorization) {
			return this.authorization;
		}

		const response = await fetch(
			createResourceURL(this.baseResourceURL, {
				p_p_resource_id: '/marketplace_settings/get_authorization',
			}).toString()
		);

		if (response.ok) {
			const data = (await response.json()) as MarketplaceAuthorization;

			this.authorization = data;

			return data;
		}

		throw new Error('Unable to get authorization');
	}

	public async getProducts(urlSearchParams = new URLSearchParams()) {
		return this.fetchMarketplace(
			`/o/headless-commerce-delivery-catalog/v1.0/channels/${this.settings.channelId}/products?${urlSearchParams.toString()}`,
			{guestOperation: true}
		);
	}

	private get marketplaceServiceURL() {
		return this.marketplaceConfiguration.serviceURL;
	}

	private get marketplaceURL() {
		return this.marketplaceConfiguration.url;
	}

	public get settings() {
		return this.marketplaceConfiguration.settings || {};
	}
}
