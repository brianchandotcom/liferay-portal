/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption} from '../models/HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption';
import type {HeadlessCommerceAdminAccount_v1_0_PageAccountChannelShippingOption} from '../models/HeadlessCommerceAdminAccount_v1_0_PageAccountChannelShippingOption';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10AccountChannelShippingOptionService {

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountChannelShippingOption(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-options/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountChannelShippingOption(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-options/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountChannelShippingOption(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-options/{id}',
			path: {
				id: id,
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
	public static headlessCommerceAdminAccountV10DeleteAccountChannelShippingOptionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-options/batch',
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
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelShippingOptionBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/account-channel-shipping-option/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelShippingOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountChannelShippingOptionPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelShippingOption> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-option',
			path: {
				id: id,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountChannelShippingOption(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-option',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountChannelShippingOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountChannelShippingOptionPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountChannelShippingOption> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-option',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountChannelShippingOption(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountChannelShippingOption> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-option',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
