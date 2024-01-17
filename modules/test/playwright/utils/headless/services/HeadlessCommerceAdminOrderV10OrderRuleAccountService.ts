/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccount} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderRuleAccountService {

	/**
	 * @param orderRuleAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderRuleAccount(
		orderRuleAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-accounts/{orderRuleAccountId}',
			path: {
				orderRuleAccountId: orderRuleAccountId,
			},
		});
	}

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleIdOrderRuleAccountsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-accounts',
			path: {
				id: id,
			},
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderRuleAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-accounts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleByExternalReferenceCodeOrderRuleAccountsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-accounts',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleByExternalReferenceCodeOrderRuleAccount(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-accounts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
