/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_Account} from '../models/HeadlessCommerceAdminOrder_v1_0_Account';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10AccountService {

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminOrder_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderByExternalReferenceCodeAccount(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/account',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param orderRuleAccountId
	 * @returns HeadlessCommerceAdminOrder_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleAccountAccount(
		orderRuleAccountId: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rule-accounts/{orderRuleAccountId}/account',
			path: {
				orderRuleAccountId: orderRuleAccountId,
			},
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminOrder_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderIdAccount(
		id: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/orders/{id}/account',
			path: {
				id: id,
			},
		});
	}
}
