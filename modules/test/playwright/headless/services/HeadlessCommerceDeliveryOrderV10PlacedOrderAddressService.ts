/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress} from '../models/HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceDeliveryOrderV10PlacedOrderAddressService {

	/**
	 * Retrieve placed order shipping address.
	 * @param placedOrderId
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderPlacedOrderShippingAddres(
		placedOrderId: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-shipping-address',
			path: {
				placedOrderId: placedOrderId,
			},
		});
	}

	/**
	 * Retrieve placed order billing address.
	 * @param placedOrderId
	 * @returns HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceDeliveryOrderV10GetPlacedOrderPlacedOrderBillingAddres(
		placedOrderId: string
	): CancelablePromise<HeadlessCommerceDeliveryOrder_v1_0_PlacedOrderAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-billing-address',
			path: {
				placedOrderId: placedOrderId,
			},
		});
	}
}
