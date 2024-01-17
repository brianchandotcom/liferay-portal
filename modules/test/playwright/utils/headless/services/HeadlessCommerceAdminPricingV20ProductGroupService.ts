/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_ProductGroup} from '../models/HeadlessCommerceAdminPricing_v2_0_ProductGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20ProductGroupService {

	/**
	 * @param discountProductGroupId
	 * @returns HeadlessCommerceAdminPricing_v2_0_ProductGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountProductGroupProductGroup(
		discountProductGroupId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_ProductGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-product-groups/{discountProductGroupId}/product-group',
			path: {
				discountProductGroupId: discountProductGroupId,
			},
		});
	}

	/**
	 * @param priceModifierProductGroupId
	 * @returns HeadlessCommerceAdminPricing_v2_0_ProductGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierProductGroupProductGroup(
		priceModifierProductGroupId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_ProductGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-product-groups/{priceModifierProductGroupId}/product-group',
			path: {
				priceModifierProductGroupId: priceModifierProductGroupId,
			},
		});
	}
}
