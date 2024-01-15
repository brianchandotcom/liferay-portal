/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_PageShippingMethod} from '../models/HeadlessCommerceDeliveryCart_v1_0_PageShippingMethod';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCartV10ShippingMethodService {

	/**
	 * Retrive payment methods available for the Cart.
	 * @param cartId
	 * @returns HeadlessCommerceDeliveryCart_v1_0_PageShippingMethod default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartShippingMethodsPage(
		cartId: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_PageShippingMethod> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/shipping-methods',
			path: {
				cartId: cartId,
			},
		});
	}

	/**
	 * @param cartId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PostCartShippingMethodsPageExportBatch(
		cartId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/shipping-methods/export-batch',
			path: {
				cartId: cartId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
