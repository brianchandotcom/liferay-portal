/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_Cart} from '../models/HeadlessCommerceDeliveryCart_v1_0_Cart';
import type {HeadlessCommerceDeliveryCart_v1_0_CouponCode} from '../models/HeadlessCommerceDeliveryCart_v1_0_CouponCode';
import type {HeadlessCommerceDeliveryCart_v1_0_PageCart} from '../models/HeadlessCommerceDeliveryCart_v1_0_PageCart';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCartV10CartService {

	/**
	 * Retrieves carts for specific account in the given channel.
	 * @param accountId
	 * @param channelId
	 * @param page
	 * @param pageSize
	 * @param search
	 * @returns HeadlessCommerceDeliveryCart_v1_0_PageCart default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetChannelCartsPage(
		accountId: string,
		channelId: string,
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_PageCart> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/channels/{channelId}/account/{accountId}/carts',
			path: {
				accountId: accountId,
				channelId: channelId,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}

	/**
	 * @param channelId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Cart default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PostChannelCart(
		channelId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_Cart
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Cart> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-cart/v1.0/channels/{channelId}/carts',
			path: {
				channelId: channelId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param cartId
	 * @param callbackUrl
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartPaymentUrl(
		cartId: string,
		callbackUrl?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/payment-url',
			path: {
				cartId: cartId,
			},
			query: {
				callbackURL: callbackUrl,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PutCartBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-delivery-cart/v1.0/carts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10DeleteCartBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-cart/v1.0/carts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrive information of the given Cart.
	 * @param cartId
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Cart default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCart(
		cartId: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Cart> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
			path: {
				cartId: cartId,
			},
		});
	}

	/**
	 * @param cartId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Cart default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PutCart(
		cartId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_Cart
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Cart> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
			path: {
				cartId: cartId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param cartId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10DeleteCart(
		cartId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
			path: {
				cartId: cartId,
			},
		});
	}

	/**
	 * @param cartId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Cart default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PatchCart(
		cartId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_Cart
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Cart> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}',
			path: {
				cartId: cartId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param cartId
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Cart default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PostCartCheckout(
		cartId: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Cart> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/checkout',
			path: {
				cartId: cartId,
			},
		});
	}

	/**
	 * Add new Items to a Cart, return the whole Cart updated.
	 * @param cartId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_Cart default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PostCartCouponCode(
		cartId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_CouponCode
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_Cart> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/coupon-code',
			path: {
				cartId: cartId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
