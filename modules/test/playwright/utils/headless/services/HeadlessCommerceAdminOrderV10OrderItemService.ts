/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderItem} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderItem';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrderItem} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrderItem';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderItemService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PutOrderItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-order/v1.0/orderItems/batch',
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
	public static headlessCommerceAdminOrderV10DeleteOrderItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/orderItems/batch',
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
	public static headlessCommerceAdminOrderV10PostOrderIdOrderItemBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/orders/orderItems/batch',
			query: {
				callbackURL: callbackUrl,
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderItemsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orderItems',
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
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderItemsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/orderItems/export-batch',
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

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderItemByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PutOrderItemByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderItem
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderItem> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderItemByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminOrderV10PatchOrderItemByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderItem
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderItem(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PutOrderItem(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderItem
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderItem> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderItem(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
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
	public static headlessCommerceAdminOrderV10PatchOrderItem(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderItem
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderByExternalReferenceCodeOrderItemsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/orderItems',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderByExternalReferenceCodeOrderItem(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderItem
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/orderItems',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderIdOrderItemsPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderItem> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/orderItems',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderItem default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderIdOrderItem(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderItem
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderItem> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/orderItems',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
