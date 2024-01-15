/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrder} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrder';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryOrderV10PlacedOrderService {

	/**
	 * Retrieve information of the given Placed Order.
	 * @param placedOrderId
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrder(
		placedOrderId: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}',
			path: {
				placedOrderId: placedOrderId,
			},
		});
	}

	/**
	 * Retrieves placed orders for specific account in the given channel.
	 * @param accountId
	 * @param channelId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrder default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetChannelAccountPlacedOrdersPage(
		accountId: string,
		channelId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/channels/{channelId}/accounts/{accountId}/placed-orders',
			path: {
				accountId: accountId,
				channelId: channelId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param placedOrderId
	 * @param callbackUrl
	 * @returns string default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderPaymentUrl(
		placedOrderId: string,
		callbackUrl?: string
	): CancelablePromise<string> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/payment-url',
			path: {
				placedOrderId: placedOrderId,
			},
			query: {
				callbackURL: callbackUrl,
			},
		});
	}
}
