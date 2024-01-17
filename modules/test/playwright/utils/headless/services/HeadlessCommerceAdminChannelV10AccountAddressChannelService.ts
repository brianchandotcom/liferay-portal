/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel} from '../models/HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel';
import type {HeadlessCommerceAdminChannel_v1_0_PageAccountAddressChannel} from '../models/HeadlessCommerceAdminChannel_v1_0_PageAccountAddressChannel';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminChannelV10AccountAddressChannelService {

	/**
	 * @param addressId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageAccountAddressChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetAccountAddressIdAccountAddressChannelsPage(
		addressId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageAccountAddressChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/account-addresses/{addressId}/account-address-channels',
			path: {
				addressId: addressId,
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
	 * @param addressId
	 * @param requestBody
	 * @returns HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostAccountAddressIdAccountAddressChannel(
		addressId: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/account-addresses/{addressId}/account-address-channels',
			path: {
				addressId: addressId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param accountAddressChannelId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteAccountAddressChannel(
		accountAddressChannelId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/account-address-channels/{accountAddressChannelId}',
			path: {
				accountAddressChannelId: accountAddressChannelId,
			},
		});
	}

	/**
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10DeleteAccountAddressChannelBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-channel/v1.0/account-address-channels/batch',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_PageAccountAddressChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10GetAccountAddressByExternalReferenceCodeAccountAddressChannelsPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_PageAccountAddressChannel> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-channel/v1.0/account-addresses/by-externalReferenceCode/{externalReferenceCode}/account-address-channels',
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
	 * @returns HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminChannelV10PostAccountAddressByExternalReferenceCodeAccountAddressChannel(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel
	): CancelablePromise<HeadlessCommerceAdminChannel_v1_0_AccountAddressChannel> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-channel/v1.0/account-addresses/by-externalReferenceCode/{externalReferenceCode}/account-address-channels',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
