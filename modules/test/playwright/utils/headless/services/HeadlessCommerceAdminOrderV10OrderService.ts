/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Order} from '../models/HeadlessCommerceAdminOrder_v1_0_Order';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrder} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrder';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_Order default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Order> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminOrderV10PatchOrderByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_Order
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrder default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrdersPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrder> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders',
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
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_Order default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrder(
		requestBody?: HeadlessCommerceAdminOrder_v1_0_Order
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Order> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/orders',
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
	public static headlessCommerceAdminOrderV10PostOrderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/orders/batch',
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
	public static headlessCommerceAdminOrderV10DeleteOrderBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/orders/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_Order default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrder(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Order> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrder(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}',
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
	public static headlessCommerceAdminOrderV10PatchOrder(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_Order
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrdersPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/orders/export-batch',
			query: {
				filter: filter,
				search: search,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}
}
