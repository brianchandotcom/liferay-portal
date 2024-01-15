/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_Channel} from '../models/HeadlessCommerceAdminChannel_v1_0_Channel';
import type {HeadlessCommerceAdminChannel_v1_0_PageChannel} from '../models/HeadlessCommerceAdminChannel_v1_0_PageChannel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10ChannelService {

	/**
	 * Retrieves channels.
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetChannelsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/channels',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostChannel(
		requestBody?: HeadlessCommerceAdminChannel_v1_0_Channel
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/channels',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrive information of the given Channel.
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetChannelByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/channels/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PutChannelByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_Channel
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-channel/v1.0/channels/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminChannelV10DeleteChannelByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/channels/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PatchChannelByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_Channel
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-channel/v1.0/channels/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminChannelV10PostChannelsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/channels/export-batch',
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
	 * Retrive information of the given Channel.
	 * @param channelId
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetChannel(
		channelId: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/channels/{channelId}',
			path: {
				channelId: channelId,
			},
		});
	}

	/**
	 * @param channelId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PutChannel(
		channelId: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_Channel
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-channel/v1.0/channels/{channelId}',
			path: {
				channelId: channelId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param channelId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteChannel(
		channelId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/channels/{channelId}',
			path: {
				channelId: channelId,
			},
		});
	}

	/**
	 * @param channelId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PatchChannel(
		channelId: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_Channel
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-channel/v1.0/channels/{channelId}',
			path: {
				channelId: channelId,
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
	public static headlessCommerceAdminChannelV10PutChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-channel/v1.0/channels/batch',
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
	public static headlessCommerceAdminChannelV10PostChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/channels/batch',
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
	public static headlessCommerceAdminChannelV10DeleteChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/channels/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param accountAddressChannelId
	 * @returns HeadlessCommerceAdminChannel_v1_0_Channel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetAccountAddressChannelChannel(
		accountAddressChannelId: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_Channel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/account-address-channels/{accountAddressChannelId}/channel',
			path: {
				accountAddressChannelId: accountAddressChannelId,
			},
		});
	}
}
