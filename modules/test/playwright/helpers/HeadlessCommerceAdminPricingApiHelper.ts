/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getRandomString from '../utils/getRandomString';
import {ApiHelpers, DataApiHelpers} from './ApiHelpers';

type TDiscount = {
	active?: boolean;
	couponCode?: string;
	discountCategories?: string;
	discountProductGroups?: string;
	discountProducts?: string;
	id?: number;
	level?: string;
	limitationType?: string;
	maximumDiscountAmount?: number;
	neverExpire?: boolean;
	percentageLevel1?: number;
	percentageLevel2?: number;
	percentageLevel3?: number;
	percentageLevel4?: number;
	target?: string;
	title?: string;
	useCouponCode?: boolean;
	usePercentage?: boolean;
};

class TPriceEntry {
	id?: number;
	skuId: number;
	price: number;
	priceListId: number;
}

export class HeadlessCommerceAdminPricingApiHelper {
	readonly apiHelpers: ApiHelpers;
	readonly basePath: string;

	constructor(apiHelpers: ApiHelpers) {
		this.apiHelpers = apiHelpers;
		this.basePath = 'headless-commerce-admin-pricing/v2.0';
	}

	async deleteDiscount(discountId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/discounts/${discountId}`
		);
	}

	async deletePriceEntry(priceEntryId: number) {
		return this.apiHelpers.delete(
			`${this.apiHelpers.baseUrl}${this.basePath}/price-entries/${priceEntryId}`
		);
	}

	async getBasePriceLists(catalogId: number) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/price-lists?filter=catalogId/any(x:(x eq ${catalogId})) and catalogBasePriceList eq true`
		);
	}

	async getBasePriceListId(catalogId: number) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/price-lists?filter=catalogId/any(x:(x eq ${catalogId})) and catalogBasePriceList eq true and type eq 'price-list'&fields=id`
		);
	}

	async getBasePromoPriceListId(catalogId: number) {
		return this.apiHelpers.get(
			`${this.apiHelpers.baseUrl}${this.basePath}/price-lists?filter=catalogId/any(x:(x eq ${catalogId})) and catalogBasePriceList eq true and type eq 'promotion'&fields=id`
		);
	}

	async postPriceEntry(priceEntry?: TPriceEntry) {
		priceEntry = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/price-lists/${priceEntry.priceListId}/price-entries`,
			{
				data: priceEntry,
				failOnStatusCode: true,
			}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({id: priceEntry.id, type: 'price-entry'});
		}

		return priceEntry;
	}

	async postDiscount(discount?: TDiscount) {
		discount = {
			active: true,
			level: 'L1',
			limitationType: 'unlimited',
			maximumDiscountAmount: 0,
			neverExpire: true,
			percentageLevel1: 20,
			percentageLevel2: 0,
			percentageLevel3: 0,
			percentageLevel4: 0,
			target: 'products',
			title: getRandomString(),
			useCouponCode: false,
			usePercentage: false,
			...(discount || {}),
		};

		discount = await this.apiHelpers.post(
			`${this.apiHelpers.baseUrl}${this.basePath}/discounts`,
			{
				data: discount,
				failOnStatusCode: true,
			}
		);

		if (this.apiHelpers instanceof DataApiHelpers) {
			this.apiHelpers.data.push({id: discount.id, type: 'discount'});
		}

		return discount;
	}
}
