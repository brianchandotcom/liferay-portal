/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminOrder_v1_0_OrderAccountGroup} from '../models/HeadlessCommerceAdminOrder_v1_0_OrderAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminOrderV10OrderAccountGroupService {

	/**
	 * @param orderRuleAccountGroupId
	 * @returns HeadlessCommerceAdminOrder_v1_0_OrderAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminOrderV10GetOrderRuleAccountGroupAccountGroup(
		orderRuleAccountGroupId: string
	): CancelablePromise<HeadlessCommerceAdminOrder_v1_0_OrderAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-order/v1.0/order-rule-account-groups/{orderRuleAccountGroupId}/account-group',
			path: {
				orderRuleAccountGroupId: orderRuleAccountGroupId,
			},
		});
	}
}
