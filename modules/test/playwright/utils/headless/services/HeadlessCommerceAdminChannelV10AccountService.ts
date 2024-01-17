/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Account} from '../models/HeadlessCommerceAdminChannel_v1_0_Account';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10AccountService {

	/**
	 * @param channelAccountId
	 * @returns HeadlessCommerceAdminChannel_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetChannelAccountAccount(
		channelAccountId: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/channel-accounts/{channelAccountId}/account',
			path: {
				channelAccountId: channelAccountId,
			},
		});
	}
}
