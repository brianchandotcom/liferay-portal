/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_AccountGroup} from '../models/HeadlessAdminUser_v1_0_AccountGroup';
import type {HeadlessAdminUser_v1_0_PageAccountGroup} from '../models/HeadlessAdminUser_v1_0_PageAccountGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10AccountGroupService {

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
	public static headlessAdminUserV10PostAccountGroupsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/account-groups/export-batch',
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
	 * @param accountId
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminUser_v1_0_PageAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountAccountGroupsPage(
		accountId: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-groups',
			path: {
				accountId: accountId,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * @param accountId
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountAccountGroupsPageExportBatch(
		accountId: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-groups/export-batch',
			path: {
				accountId: accountId,
			},
			query: {
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * @param accountGroupId
	 * @returns HeadlessAdminUser_v1_0_AccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountGroup(
		accountGroupId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
			path: {
				accountGroupId: accountGroupId,
			},
		});
	}

	/**
	 * Replaces the account group with information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param accountGroupId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_AccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutAccountGroup(
		accountGroupId: string,
		requestBody?: HeadlessAdminUser_v1_0_AccountGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountGroup> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
			path: {
				accountGroupId: accountGroupId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes an account group.
	 * @param accountGroupId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountGroup(
		accountGroupId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
			path: {
				accountGroupId: accountGroupId,
			},
		});
	}

	/**
	 * Updates the account group with information sent in the request body. Only the provided fields are updated.
	 * @param accountGroupId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_AccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchAccountGroup(
		accountGroupId: string,
		requestBody?: HeadlessAdminUser_v1_0_AccountGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountGroup> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
			path: {
				accountGroupId: accountGroupId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param accountExternalReferenceCode
	 * @param page
	 * @param pageSize
	 * @returns HeadlessAdminUser_v1_0_PageAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountByExternalReferenceCodeAccountExternalReferenceCodeAccountGroupsPage(
		accountExternalReferenceCode: string,
		page?: string,
		pageSize?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-groups',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
			},
			query: {
				page: page,
				pageSize: pageSize,
			},
		});
	}

	/**
	 * Assigns an account by its external reference code to an account group by external reference code
	 * @param accountExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode(
		accountExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{accountExternalReferenceCode}/accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Removes an account by their external reference code from an account group by external reference code
	 * @param accountExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode(
		accountExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{accountExternalReferenceCode}/accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessAdminUser_v1_0_AccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountGroupByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Replaces the account group with information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_AccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutAccountGroupByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_AccountGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountGroup> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes an account group.
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountGroupByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Updates the account with information sent in the request body. Only the provided fields are updated.
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_AccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchAccountGroupByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_AccountGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountGroup> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
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
	public static headlessAdminUserV10PutAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/account-groups/batch',
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
	public static headlessAdminUserV10PostAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/account-groups/batch',
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
	public static headlessAdminUserV10DeleteAccountGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/account-groups/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the account groups. Results can be paginated, filtered, searched, and sorted.
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageAccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountGroupsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccountGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/account-groups',
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
	 * Creates a new account group
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_AccountGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountGroup(
		requestBody?: HeadlessAdminUser_v1_0_AccountGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/account-groups',
			body: requestBody,
			mediaType: 'application/json',
		});
	}
}
