/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_OrderType} from '../models/HeadlessCommerceAdminPricing_v2_0_OrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20OrderTypeService {

	/**
	 * @param priceListOrderTypeId
	 * @returns HeadlessCommerceAdminPricing_v2_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListOrderTypeOrderType(
		priceListOrderTypeId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-order-types/{priceListOrderTypeId}/order-type',
			path: {
				priceListOrderTypeId: priceListOrderTypeId,
			},
		});
	}

	/**
	 * @param discountOrderTypeId
	 * @returns HeadlessCommerceAdminPricing_v2_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountOrderTypeOrderType(
		discountOrderTypeId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-order-types/{discountOrderTypeId}/order-type',
			path: {
				discountOrderTypeId: discountOrderTypeId,
			},
		});
	}
}
