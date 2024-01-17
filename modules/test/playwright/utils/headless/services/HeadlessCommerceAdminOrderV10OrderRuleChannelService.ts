/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrderRuleChannel} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrderRuleChannel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderRuleChannelService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleByExternalReferenceCodeOrderRuleChannelsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-channels',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleByExternalReferenceCodeOrderRuleChannel(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-channels',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleIdOrderRuleChannelsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-channels',
			path: {
				id: id,
			},
			query: {
				filter: filter,
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleChannel(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-channels',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param orderRuleChannelId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderRuleChannel(
		orderRuleChannelId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-channels/{orderRuleChannelId}',
			path: {
				orderRuleChannelId: orderRuleChannelId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderRuleChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-channels/batch',
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
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/order-rule-channels/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
