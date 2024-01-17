/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItemShipment} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItemShipment';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryOrderV10PlacedOrderItemShipmentService {

	/**
	 * Retrieve shipments of the given Placed Order Item.
	 * @param placedOrderItemId
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItemShipment default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderItemPlacedOrderItemShipmentsPage(
		placedOrderItemId: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PagePlacedOrderItemShipment> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-order-items/{placedOrderItemId}/placed-order-item-shipments',
			path: {
				placedOrderItemId: placedOrderItemId,
			},
		});
	}

	/**
	 * @param placedOrderItemId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10PostPlacedOrderItemPlacedOrderItemShipmentsPageExportBatch(
		placedOrderItemId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-delivery-order/v1.0/placed-order-items/{placedOrderItemId}/placed-order-item-shipments/export-batch',
			path: {
				placedOrderItemId: placedOrderItemId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
