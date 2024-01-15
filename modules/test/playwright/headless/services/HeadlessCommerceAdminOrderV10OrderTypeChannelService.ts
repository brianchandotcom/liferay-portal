/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrderTypeChannel} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrderTypeChannel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderTypeChannelService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderTypeIdOrderTypeChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-types/order-type-channels/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderTypeChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderTypeIdOrderTypeChannelsPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderTypeChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-types/{id}/order-type-channels',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderTypeIdOrderTypeChannel(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-types/{id}/order-type-channels',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param orderTypeChannelId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderTypeChannel(
		orderTypeChannelId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-type-channels/{orderTypeChannelId}',
			path: {
				orderTypeChannelId: orderTypeChannelId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderTypeChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-type-channels/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderTypeChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderTypeByExternalReferenceCodeOrderTypeChannelsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderTypeChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}/order-type-channels',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderTypeByExternalReferenceCodeOrderTypeChannel(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderTypeChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}/order-type-channels',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
