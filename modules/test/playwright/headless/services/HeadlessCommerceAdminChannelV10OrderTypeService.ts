/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_OrderType} from '../models/HeadlessCommerceAdminChannel_v1_0_OrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10OrderTypeService {

	/**
	 * @param paymentMethodGroupRelOrderTypeId
	 * @returns HeadlessCommerceAdminChannel_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetPaymentMethodGroupRelOrderTypeOrderType(
		paymentMethodGroupRelOrderTypeId: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-order-types/{paymentMethodGroupRelOrderTypeId}/order-type',
			path: {
				paymentMethodGroupRelOrderTypeId:
					paymentMethodGroupRelOrderTypeId,
			},
		});
	}

	/**
	 * @param shippingFixedOptionOrderTypeId
	 * @returns HeadlessCommerceAdminChannel_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetShippingFixedOptionOrderTypeOrderType(
		shippingFixedOptionOrderTypeId: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-order-types/{shippingFixedOptionOrderTypeId}/order-type',
			path: {
				shippingFixedOptionOrderTypeId: shippingFixedOptionOrderTypeId,
			},
		});
	}
}
