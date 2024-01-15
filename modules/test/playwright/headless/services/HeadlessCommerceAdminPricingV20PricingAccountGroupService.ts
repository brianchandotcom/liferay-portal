/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_PricingAccountGroup} from '../models/HeadlessCommerceAdminPricing_v2_0_PricingAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20PricingAccountGroupService {

	/**
	 * @param discountAccountGroupId
	 * @returns HeadlessCommerceAdminPricing_v2_0_PricingAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountAccountGroupAccountGroup(
		discountAccountGroupId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PricingAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-account-groups/{discountAccountGroupId}/account-group',
			path: {
				discountAccountGroupId: discountAccountGroupId,
			},
		});
	}

	/**
	 * @param priceListAccountGroupId
	 * @returns HeadlessCommerceAdminPricing_v2_0_PricingAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListAccountGroupAccountGroup(
		priceListAccountGroupId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_PricingAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-account-groups/{priceListAccountGroupId}/account-group',
			path: {
				priceListAccountGroupId: priceListAccountGroupId,
			},
		});
	}
}
