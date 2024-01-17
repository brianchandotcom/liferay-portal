/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Account} from '../models/HeadlessCommerceAdminPricing_v2_0_Account';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20AccountService {

	/**
	 * @param discountAccountId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountAccountAccount(
		discountAccountId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-accounts/{discountAccountId}/account',
			path: {
				discountAccountId: discountAccountId,
			},
		});
	}

	/**
	 * @param priceListAccountId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListAccountAccount(
		priceListAccountId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-accounts/{priceListAccountId}/account',
			path: {
				priceListAccountId: priceListAccountId,
			},
		});
	}
}
