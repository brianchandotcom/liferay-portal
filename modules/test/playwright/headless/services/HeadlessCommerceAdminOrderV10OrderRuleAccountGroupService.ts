/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup';
import type {HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccountGroup} from '../models/HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderRuleAccountGroupService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-account-groups',
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10PostOrderRuleByExternalReferenceCodeOrderRuleAccountGroup(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderRuleAccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-account-groups',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	public static headlessCommerceAdminOrderV10DeleteOrderRuleAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-account-groups/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param orderRuleAccountGroupId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10DeleteOrderRuleAccountGroup(
		orderRuleAccountGroupId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-order/v1.0/order-rule-account-groups/{orderRuleAccountGroupId}',
			path: {
				orderRuleAccountGroupId: orderRuleAccountGroupId,
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
	 * @returns HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleIdOrderRuleAccountGroupsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_PageOrderRuleAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-account-groups',
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
}
