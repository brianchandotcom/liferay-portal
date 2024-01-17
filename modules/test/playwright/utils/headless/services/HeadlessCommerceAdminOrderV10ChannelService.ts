/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Channel} from '../models/HeadlessCommerceAdminOrder_v1_0_Channel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10ChannelService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderByExternalReferenceCodeChannel(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/channel',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param orderTypeChannelId
	 * @returns HeadlessCommerceAdminOrder_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderTypeChannelChannel(
		orderTypeChannelId: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-type-channels/{orderTypeChannelId}/channel',
			path: {
				orderTypeChannelId: orderTypeChannelId,
			},
		});
	}

	/**
	 * @param orderRuleChannelId
	 * @returns HeadlessCommerceAdminOrder_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleChannelChannel(
		orderRuleChannelId: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rule-channels/{orderRuleChannelId}/channel',
			path: {
				orderRuleChannelId: orderRuleChannelId,
			},
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderIdChannel(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/channel',
			path: {
				id: id,
			},
		});
	}
}
