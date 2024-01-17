/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Sku} from '../models/HeadlessCommerceAdminPricing_v2_0_Sku';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20SkuService {

	/**
	 * @param priceEntryId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Sku default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceEntryIdSku(
		priceEntryId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Sku> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}/sku',
			path: {
				priceEntryId: priceEntryId,
			},
		});
	}

	/**
	 * @param discountSkuId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Sku default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountSkuSku(
		discountSkuId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Sku> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-skus/{discountSkuId}/sku',
			path: {
				discountSkuId: discountSkuId,
			},
		});
	}
}
