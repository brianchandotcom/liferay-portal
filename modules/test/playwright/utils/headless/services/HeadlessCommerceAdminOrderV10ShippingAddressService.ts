/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_ShippingAddress} from '../models/HeadlessCommerceAdminOrder_v1_0_ShippingAddress';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10ShippingAddressService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_ShippingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderByExternalReferenceCodeShippingAddress(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_ShippingAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/shippingAddress',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PatchOrderByExternalReferenceCodeShippingAddress(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_ShippingAddress
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/shippingAddress',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_ShippingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderItemShippingAddress(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_ShippingAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orderItems/{id}/shippingAddress',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_ShippingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderIdShippingAddress(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_ShippingAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/shippingAddress',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PatchOrderIdShippingAddress(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_ShippingAddress
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/shippingAddress',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
