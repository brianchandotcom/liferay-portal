/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItem} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItem';
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryOrderV10PlacedOrderItemService {

	/**
	 * @param placedOrderId
	 * @param skuId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10PostPlacedOrderPlacedOrderItemsPageExportBatch(
		placedOrderId: string,
		skuId?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-items/export-batch',
			path: {
				placedOrderId: placedOrderId,
			},
			query: {
				skuId: skuId,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Retrieve information of the given Placed Order.
	 * @param placedOrderItemId
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderItem(
		placedOrderItemId: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-order-items/{placedOrderItemId}',
			path: {
				placedOrderItemId: placedOrderItemId,
			},
		});
	}

	/**
	 * Retrieve placed order items.
	 * @param placedOrderId
	 * @param skuId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderPlacedOrderItemsPage(
		placedOrderId: string,
		skuId?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-items',
			path: {
				placedOrderId: placedOrderId,
			},
			query: {
				skuId: skuId,
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
