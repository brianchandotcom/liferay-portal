/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminAccount_v1_0_Account} from '../models/HeadlessCommerceAdminAccount_v1_0_Account';
import type {HeadlessCommerceAdminAccount_v1_0_MultipartBody} from '../models/HeadlessCommerceAdminAccount_v1_0_MultipartBody';
import type {HeadlessCommerceAdminAccount_v1_0_PageAccount} from '../models/HeadlessCommerceAdminAccount_v1_0_PageAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessCommerceAdminAccountV10AccountService {

	/**
	 * @param externalReferenceCode
	 * @param formData
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountByExternalReferenceCodeLogo(
		externalReferenceCode: string,
		formData?: HeadlessCommerceAdminAccount_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/logo',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountGroupByExternalReferenceCodeAccount(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_Account
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/by-externalReferenceCode/{externalReferenceCode}/accounts',
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
	public static headlessCommerceAdminAccountV10PostAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/batch',
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
	public static headlessCommerceAdminAccountV10DeleteAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accounts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessCommerceAdminAccount_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminAccountV10DeleteAccountByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}',
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
	public static headlessCommerceAdminAccountV10PatchAccountByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_Account
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @param formData
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccountLogo(
		id: string,
		formData?: HeadlessCommerceAdminAccount_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}/logo',
			path: {
				id: id,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * @param accountExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10DeleteAccountGroupByExternalReferenceCodeAccount(
		accountExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accountGroups/by-externalReferenceCode/{externalReferenceCode}/accounts/{accountExternalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
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
	public static headlessCommerceAdminAccountV10PostAccountsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts/export-batch',
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
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessCommerceAdminAccount_v1_0_PageAccount default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccountsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_PageAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts',
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
	 * @returns HeadlessCommerceAdminAccount_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PostAccount(
		requestBody?: HeadlessCommerceAdminAccount_v1_0_Account
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-commerce-admin-account/v1.0/accounts',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param id
	 * @returns HeadlessCommerceAdminAccount_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10GetAccount(
		id: string
	): CancelablePromise<HeadlessCommerceAdminAccount_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}',
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
	public static headlessCommerceAdminAccountV10DeleteAccount(
		id: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}',
			path: {
				id: id,
			},
		});
	}

	/**
	 * @param id
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessCommerceAdminAccountV10PatchAccount(
		id: string,
		requestBody?: HeadlessCommerceAdminAccount_v1_0_Account
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-commerce-admin-account/v1.0/accounts/{id}',
			path: {
				id: id,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
