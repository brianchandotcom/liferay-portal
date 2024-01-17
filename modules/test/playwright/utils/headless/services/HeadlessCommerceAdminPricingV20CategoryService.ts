/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Category} from '../models/HeadlessCommerceAdminPricing_v2_0_Category';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20CategoryService {

	/**
	 * @param priceModifierCategoryId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Category default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceModifierCategoryCategory(
		priceModifierCategoryId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Category> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-modifier-categories/{priceModifierCategoryId}/category',
			path: {
				priceModifierCategoryId: priceModifierCategoryId,
			},
		});
	}

	/**
	 * @param discountCategoryId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Category default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountCategoryCategory(
		discountCategoryId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Category> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-categories/{discountCategoryId}/category',
			path: {
				discountCategoryId: discountCategoryId,
			},
		});
	}
}
