/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_Address} from '../models/HeadlessCommerceDeliveryCart_v1_0_Address';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCartV10AddressService {

	/**
	 * Retrive cart billing address.
	 * @param cartId
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Address default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartBillingAddres(
		cartId: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Address> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/billing-address',
			path: {
				cartId: cartId,
			},
		});
	}

	/**
	 * Retrive cart billing address.
	 * @param cartId
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Address default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartShippingAddres(
		cartId: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Address> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/shipping-address',
			path: {
				cartId: cartId,
			},
		});
	}
}
