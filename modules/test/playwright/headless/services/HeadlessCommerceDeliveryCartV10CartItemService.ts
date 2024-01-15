/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_CartItem} from '../models/HeadlessCommerceDeliveryCart_v1_0_CartItem';
import type {HeadlessCommerceDeliveryCart_v1_0_PageCartItem} from '../models/HeadlessCommerceDeliveryCart_v1_0_PageCartItem';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCartV10CartItemService {

	/**
	 * Retrive cart items of a Cart.
	 * @param cartId
	 * @param skuId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCart_v1_0_PageCartItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartItemsPage(
		cartId: string,
		skuId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_PageCartItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/items',
			path: {
				cartId: cartId,
			},
			query: {
				skuId: skuId,
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Add new Items to a Cart, return the whole Cart updated.
	 * @param cartId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PostCartItem(
		cartId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_CartItem
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/items',
			path: {
				cartId: cartId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrive information of the given Cart
	 * @param cartItemId
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartItem(
		cartItemId: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
			path: {
				cartItemId: cartItemId,
			},
		});
	}

	/**
	 * update the given Cart.
	 * @param cartItemId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PutCartItem(
		cartItemId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_CartItem
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartItem> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
			path: {
				cartItemId: cartItemId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes an Cart Item by ID.
	 * @param cartItemId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10DeleteCartItem(
		cartItemId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
			path: {
				cartItemId: cartItemId,
			},
		});
	}

	/**
	 * Retrive information of the given Cart.
	 * @param cartItemId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PatchCartItem(
		cartItemId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_CartItem
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartItem> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-delivery-cart/v1.0/cart-items/{cartItemId}',
			path: {
				cartItemId: cartItemId,
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
	public static headlessCommerceDeliveryCartV10PutCartItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-delivery-cart/v1.0/cart-items/batch',
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
	public static headlessCommerceDeliveryCartV10DeleteCartItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-cart/v1.0/cart-items/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
