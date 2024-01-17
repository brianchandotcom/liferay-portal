/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_AccountAddress} from '../models/HeadlessCommerceAdminAccount_v1_0_AccountAddress';
import type {HeadlessCommerceAdminAccount_v1_0_PageAccountAddress} from '../models/HeadlessCommerceAdminAccount_v1_0_PageAccountAddress';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10AccountAddressService {

	/**
	 * @param externalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCodeAccountAddressesPage(
		externalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountAddresses',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeAccountAddress(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountAddress
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountAddress> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountAddresses',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param page
	 * @param pageSize
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountIdAccountAddressesPage(
		id: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccountAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountAddresses',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountIdAccountAddress(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountAddress
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountAddress> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountAddresses',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountAddressByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountAddressByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountAddressByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountAddress
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminAccountV10PostAccountIdAccountAddressBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/accountAddresses/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountAddress(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountAddress> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PutAccountAddress(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountAddress
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountAddress> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountAddress(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns HeadlessCommerceAdminAccount_v1_0_AccountAddress default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccountAddress(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_AccountAddress
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_AccountAddress> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/{id}',
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
	public static headlessCommerceAdminAccountV10PutAccountAddressBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/batch',
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
	public static headlessCommerceAdminAccountV10DeleteAccountAddressBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accountAddresses/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
