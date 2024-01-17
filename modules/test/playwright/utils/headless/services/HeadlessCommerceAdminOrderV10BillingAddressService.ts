/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_BillingAddress} from '../models/HeadlessCommerceAdminOrder_v1_0_BillingAddress';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10BillingAddressService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_BillingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderByExternalReferenceCodeBillingAddress(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_BillingAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/billingAddress',
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
	public static headlessCommerceAdminOrderV10PatchOrderByExternalReferenceCodeBillingAddress(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_BillingAddress
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/billingAddress',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_BillingAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderIdBillingAddress(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_BillingAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/billingAddress',
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
	public static headlessCommerceAdminOrderV10PatchOrderIdBillingAddress(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_BillingAddress
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/billingAddress',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
