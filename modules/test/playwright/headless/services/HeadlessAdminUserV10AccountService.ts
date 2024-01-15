/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Account} from '../models/HeadlessAdminUser_v1_0_Account';
import type {HeadlessAdminUser_v1_0_PageAccount} from '../models/HeadlessAdminUser_v1_0_PageAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10AccountService {

	/**
	 * @param sourceOrganizationId
	 * @param targetOrganizationId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchOrganizationMoveAccountsByExternalReferenceCode(
		sourceOrganizationId: string,
		targetOrganizationId: string,
		requestBody?: Array<string>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/organizations/move-accounts/{sourceOrganizationId}/{targetOrganizationId}/by-external-reference-code',
			path: {
				sourceOrganizationId: sourceOrganizationId,
				targetOrganizationId: targetOrganizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessAdminUser_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Replaces the account with information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutAccountByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_Account
	): CancelablePromise<HeadlessAdminUser_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes an account.
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Updates the account with information sent in the request body. Only the provided fields are updated.
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchAccountByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_Account
	): CancelablePromise<HeadlessAdminUser_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param organizationId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostOrganizationAccountsPageExportBatch(
		organizationId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts/export-batch',
			path: {
				organizationId: organizationId,
			},
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
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/export-batch',
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
	 * Retrieves the organization's members (accounts). Results can be paginated, filtered, searched, and sorted.
	 * @param organizationId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationAccountsPage(
		organizationId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts',
			path: {
				organizationId: organizationId,
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
	 * @param organizationId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostOrganizationAccounts(
		organizationId: string,
		requestBody?: Array<number>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts',
			path: {
				organizationId: organizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param organizationId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteOrganizationAccounts(
		organizationId: string,
		requestBody?: Array<number>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts',
			path: {
				organizationId: organizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param organizationId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostOrganizationAccountsByExternalReferenceCode(
		organizationId: string,
		requestBody?: Array<string>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts/by-external-reference-code',
			path: {
				organizationId: organizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param organizationId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteOrganizationAccountsByExternalReferenceCode(
		organizationId: string,
		requestBody?: Array<string>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts/by-external-reference-code',
			path: {
				organizationId: organizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param accountId
	 * @returns HeadlessAdminUser_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccount(
		accountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}',
			path: {
				accountId: accountId,
			},
		});
	}

	/**
	 * Replaces the account with information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param accountId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutAccount(
		accountId: string,
		requestBody?: HeadlessAdminUser_v1_0_Account
	): CancelablePromise<HeadlessAdminUser_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/accounts/{accountId}',
			path: {
				accountId: accountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes an account.
	 * @param accountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccount(
		accountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/{accountId}',
			path: {
				accountId: accountId,
			},
		});
	}

	/**
	 * Updates the account with information sent in the request body. Only the provided fields are updated.
	 * @param accountId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchAccount(
		accountId: string,
		requestBody?: HeadlessAdminUser_v1_0_Account
	): CancelablePromise<HeadlessAdminUser_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/accounts/{accountId}',
			path: {
				accountId: accountId,
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
	public static headlessAdminUserV10PutAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/accounts/batch',
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
	public static headlessAdminUserV10PostAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/batch',
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
	public static headlessAdminUserV10DeleteAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param sourceOrganizationId
	 * @param targetOrganizationId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchOrganizationMoveAccounts(
		sourceOrganizationId: string,
		targetOrganizationId: string,
		requestBody?: Array<number>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/organizations/move-accounts/{sourceOrganizationId}/{targetOrganizationId}',
			path: {
				sourceOrganizationId: sourceOrganizationId,
				targetOrganizationId: targetOrganizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the accounts. Results can be paginated, filtered, searched, and sorted.
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts',
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
	 * Creates a new account
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Account default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccount(
		requestBody?: HeadlessAdminUser_v1_0_Account
	): CancelablePromise<HeadlessAdminUser_v1_0_Account> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
