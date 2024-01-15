/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCart_v1_0_CartComment} from '../models/HeadlessCommerceDeliveryCart_v1_0_CartComment';
import type {HeadlessCommerceDeliveryCart_v1_0_PageCartComment} from '../models/HeadlessCommerceDeliveryCart_v1_0_PageCartComment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryCartV10CartCommentService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PutCartCommentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-delivery-cart/v1.0/cart-comments/batch',
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
	public static headlessCommerceDeliveryCartV10DeleteCartCommentBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-cart/v1.0/cart-comments/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param cartCommentId
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartComment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartComment(
		cartCommentId: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/cart-comments/{cartCommentId}',
			path: {
				cartCommentId: cartCommentId,
			},
		});
	}

	/**
	 * @param cartCommentId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartComment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PutCartComment(
		cartCommentId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_CartComment
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartComment> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-delivery-cart/v1.0/cart-comments/{cartCommentId}',
			path: {
				cartCommentId: cartCommentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param cartCommentId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10DeleteCartComment(
		cartCommentId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-delivery-cart/v1.0/cart-comments/{cartCommentId}',
			path: {
				cartCommentId: cartCommentId,
			},
		});
	}

	/**
	 * @param cartCommentId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartComment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PatchCartComment(
		cartCommentId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_CartComment
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartComment> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-delivery-cart/v1.0/cart-comments/{cartCommentId}',
			path: {
				cartCommentId: cartCommentId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param cartId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryCart_v1_0_PageCartComment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10GetCartCommentsPage(
		cartId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_PageCartComment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/comments',
			path: {
				cartId: cartId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param cartId
	 * @param requestBody
	 * @returns HeadlessCommerceDeliveryCart_v1_0_CartComment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryCartV10PostCartComment(
		cartId: string,
		requestBody?: HeadlessCommerceDeliveryCart_v1_0_CartComment
	): CancelablePromise<HeadlessCommerceDeliveryCart_v1_0_CartComment> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/comments',
			path: {
				cartId: cartId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
