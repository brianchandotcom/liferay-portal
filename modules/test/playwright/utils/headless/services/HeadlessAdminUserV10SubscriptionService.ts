/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PageSubscription} from '../models/HeadlessAdminUser_v1_0_PageSubscription';
import type {HeadlessAdminUser_v1_0_Subscription} from '../models/HeadlessAdminUser_v1_0_Subscription';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10SubscriptionService {

	/**
	 * @param subscriptionId
	 * @returns HeadlessAdminUser_v1_0_Subscription default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetMyUserAccountSubscription(
		subscriptionId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Subscription> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/my-user-account/subscriptions/{subscriptionId}',
			path: {
				subscriptionId: subscriptionId,
			},
		});
	}

	/**
	 * @param subscriptionId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteMyUserAccountSubscription(
		subscriptionId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/my-user-account/subscriptions/{subscriptionId}',
			path: {
				subscriptionId: subscriptionId,
			},
		});
	}

	/**
	 * @param contentType
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminUser_v1_0_PageSubscription default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetMyUserAccountSubscriptionsPage(
		contentType?: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageSubscription> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/my-user-account/subscriptions',
			query: {
				contentType: contentType,
				page: page,
				pageSize: pageSize,
			},
		});
	}
}
