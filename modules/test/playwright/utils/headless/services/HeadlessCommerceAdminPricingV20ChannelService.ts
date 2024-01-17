/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminPricing_v2_0_Channel} from '../models/HeadlessCommerceAdminPricing_v2_0_Channel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminPricingV20ChannelService {

	/**
	 * @param priceListChannelId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetPriceListChannelChannel(
		priceListChannelId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/price-list-channels/{priceListChannelId}/channel',
			path: {
				priceListChannelId: priceListChannelId,
			},
		});
	}

	/**
	 * @param discountChannelId
	 * @returns HeadlessCommerceAdminPricing_v2_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminPricingV20GetDiscountChannelChannel(
		discountChannelId: string
	): CancelablePromise<HeadlessCommerceAdminPricing_v2_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-pricing/v2.0/discount-channels/{discountChannelId}/channel',
			path: {
				discountChannelId: discountChannelId,
			},
		});
	}
}
