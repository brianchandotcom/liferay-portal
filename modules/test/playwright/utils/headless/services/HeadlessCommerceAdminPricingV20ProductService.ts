/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Product} from '../models/HeadlessCommerceAdminPricing_v2_0_Product';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20ProductService {

	/**
	 * @param priceEntryId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Product default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceEntryIdProduct(
		priceEntryId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Product> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-entries/{priceEntryId}/product',
			path: {
				priceEntryId: priceEntryId,
			},
		});
	}

	/**
	 * @param discountProductId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Product default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountProductProduct(
		discountProductId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Product> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-products/{discountProductId}/product',
			path: {
				discountProductId: discountProductId,
			},
		});
	}

	/**
	 * @param priceModifierProductId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Product default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierProductProduct(
		priceModifierProductId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Product> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-products/{priceModifierProductId}/product',
			path: {
				priceModifierProductId: priceModifierProductId,
			},
		});
	}
}
