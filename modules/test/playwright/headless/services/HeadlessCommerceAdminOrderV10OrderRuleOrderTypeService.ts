/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrderRuleOrderType} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrderRuleOrderType';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderRuleOrderTypeService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/order-rule-order-types/batch',
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
	public static headlessCommerceAdminOrderV10DeleteOrderRuleOrderTypeBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-order-types/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param orderRuleOrderTypeId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderRuleOrderType(
		orderRuleOrderTypeId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-order-types/{orderRuleOrderTypeId}',
			path: {
				orderRuleOrderTypeId: orderRuleOrderTypeId,
			},
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @param search
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleIdOrderRuleOrderTypesPage(
		id: string,
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-order-types',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleIdOrderRuleOrderType(
		id: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-order-types',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleOrderType> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-order-types',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleByExternalReferenceCodeOrderRuleOrderType(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleOrderType> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-order-types',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
