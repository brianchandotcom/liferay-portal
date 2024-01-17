/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_ChannelAccount} from '../models/HeadlessCommerceAdminChannel_v1_0_ChannelAccount';
import type {HeadlessCommerceAdminChannel_v1_0_PageChannelAccount} from '../models/HeadlessCommerceAdminChannel_v1_0_PageChannelAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10ChannelAccountService {

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteChannelAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/channel-accounts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param channelAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteChannelAccount(
		channelAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/channel-accounts/{channelAccountId}',
			path: {
				channelAccountId: channelAccountId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageChannelAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetChannelByExternalReferenceCodeChannelAccountsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageChannelAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/channels/by-externalReferenceCode/{externalReferenceCode}/channel-accounts',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_ChannelAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostChannelByExternalReferenceCodeChannelAccount(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_ChannelAccount
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_ChannelAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/channels/by-externalReferenceCode/{externalReferenceCode}/channel-accounts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageChannelAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetChannelIdChannelAccountsPage(
		id: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageChannelAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/channels/{id}/channel-accounts',
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
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminChannel_v1_0_ChannelAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostChannelIdChannelAccount(
		id: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_ChannelAccount
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_ChannelAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/channels/{id}/channel-accounts',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
