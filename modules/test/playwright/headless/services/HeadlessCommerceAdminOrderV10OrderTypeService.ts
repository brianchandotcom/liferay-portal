/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderType} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderType';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrderType} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderTypeService {

	/**
	 * @param termOrderTypeId
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetTermOrderTypeOrderType(
		termOrderTypeId: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/term-order-types/{termOrderTypeId}/order-type',
			path: {
				termOrderTypeId: termOrderTypeId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderTypeByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminOrderV10DeleteOrderTypeByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PatchOrderTypeByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderType
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	public static headlessCommerceAdminOrderV10PostOrderTypesPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-types/export-batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-types/batch',
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
	public static headlessCommerceAdminOrderV10DeleteOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-types/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderType(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-types/{id}',
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
	public static headlessCommerceAdminOrderV10DeleteOrderType(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-types/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PatchOrderType(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderType
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-order/v1.0/order-types/{id}',
			path: {
				id: id,
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderTypesPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-types',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderType(
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderType
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-types',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param orderRuleOrderTypeId
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleOrderTypeOrderType(
		orderRuleOrderTypeId: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rule-order-types/{orderRuleOrderTypeId}/order-type',
			path: {
				orderRuleOrderTypeId: orderRuleOrderTypeId,
			},
		});
	}
}
