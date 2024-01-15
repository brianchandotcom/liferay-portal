/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_MultipartBody} from '../models/HeadlessAdminUser_v1_0_MultipartBody';
import type {HeadlessAdminUser_v1_0_PageUserAccount} from '../models/HeadlessAdminUser_v1_0_PageUserAccount';
import type {HeadlessAdminUser_v1_0_UserAccount} from '../models/HeadlessAdminUser_v1_0_UserAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10UserAccountService {

	/**
	 * Gets a user assigned to an account
	 * @param accountId
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountUserAccount(
		accountId: string,
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/{userAccountId}',
			path: {
				accountId: accountId,
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Removes a user assigned to an account
	 * @param accountId
	 * @param userAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountUserAccount(
		accountId: string,
		userAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/{userAccountId}',
			path: {
				accountId: accountId,
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Assigns users to an account by their email addresses
	 * @param accountId
	 * @param accountRoleIds
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccountsByEmailAddress(
		accountId: string,
		accountRoleIds?: string,
		requestBody?: Array<string>
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address',
			path: {
				accountId: accountId,
			},
			query: {
				accountRoleIds: accountRoleIds,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Removes users from an account by their email addresses
	 * @param accountId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountUserAccountsByEmailAddress(
		accountId: string,
		requestBody?: Array<string>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address',
			path: {
				accountId: accountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param siteId
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostSiteUserAccountsPageExportBatch(
		siteId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/sites/{siteId}/user-accounts/export-batch',
			path: {
				siteId: siteId,
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
	public static headlessAdminUserV10PostUserAccountsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts/export-batch',
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
	 * @param filter
	 * @param search
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccountsPageExportBatch(
		accountId: string,
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/export-batch',
			path: {
				accountId: accountId,
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
	 * Retrieves the user account.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccount(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
			path: {
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Replaces the user account with information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param userAccountId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutUserAccount(
		userAccountId: string,
		requestBody?: HeadlessAdminUser_v1_0_UserAccount
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
			path: {
				userAccountId: userAccountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes the user account
	 * @param userAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteUserAccount(
		userAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
			path: {
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Updates the user account with information sent in the request body. Only the provided fields are updated.
	 * @param userAccountId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchUserAccount(
		userAccountId: string,
		requestBody?: HeadlessAdminUser_v1_0_UserAccount
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}',
			path: {
				userAccountId: userAccountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the organization's members (users). Results can be paginated, filtered, searched, and sorted.
	 * @param organizationId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationUserAccountsPage(
		organizationId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts',
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
	 * Gets the users assigned to an account
	 * @param accountId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountUserAccountsPage(
		accountId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts',
			path: {
				accountId: accountId,
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
	 * Creates a user and assigns them to the account
	 * @param accountId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccount(
		accountId: string,
		requestBody?: HeadlessAdminUser_v1_0_UserAccount
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts',
			path: {
				accountId: accountId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the site members' user accounts. Results can be paginated, filtered, searched, and sorted.
	 * @param siteId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetSiteUserAccountsPage(
		siteId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/sites/{siteId}/user-accounts',
			path: {
				siteId: siteId,
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutUserAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/user-accounts/batch',
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
	public static headlessAdminUserV10PostUserAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts/batch',
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
	public static headlessAdminUserV10DeleteUserAccountBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/user-accounts/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Gets the users assigned to an account
	 * @param externalReferenceCode
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountUserAccountsByExternalReferenceCodePage(
		externalReferenceCode: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts',
			path: {
				externalReferenceCode: externalReferenceCode,
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
	 * Creates a user and assigns them to the account
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccountByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_UserAccount
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Assigns users to an account by their email addresses
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccountsByExternalReferenceCodeByEmailAddress(
		externalReferenceCode: string,
		requestBody?: Array<string>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Removes users from an account by their email addresses
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountUserAccountsByExternalReferenceCodeByEmailAddress(
		externalReferenceCode: string,
		requestBody?: Array<string>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Gets a user by their external reference code to an account by external reference code
	 * @param accountExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
		accountExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Assigns a user by their external reference code to an account by external reference code
	 * @param accountExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
		accountExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Removes a user by their external reference code from an account by external reference code
	 * @param accountExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountByExternalReferenceCodeUserAccountByExternalReferenceCode(
		accountExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Retrieves the user accounts. Results can be paginated, filtered, searched, and sorted.
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts',
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
	 * Creates a new user account
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostUserAccount(
		requestBody?: HeadlessAdminUser_v1_0_UserAccount
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param accountId
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccountBatch(
		accountId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/batch',
			path: {
				accountId: accountId,
			},
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves information about the user who made the request.
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetMyUserAccount(): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/my-user-account',
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
	public static headlessAdminUserV10PostOrganizationUserAccountsPageExportBatch(
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
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/export-batch',
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
	 * Assigns a user to an account by external reference code by their email address
	 * @param externalReferenceCode
	 * @param emailAddress
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccountByExternalReferenceCodeByEmailAddress(
		externalReferenceCode: string,
		emailAddress: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}',
			path: {
				externalReferenceCode: externalReferenceCode,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * Removes a user from an account by external reference code by their email address
	 * @param externalReferenceCode
	 * @param emailAddress
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountUserAccountByExternalReferenceCodeByEmailAddress(
		externalReferenceCode: string,
		emailAddress: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}',
			path: {
				externalReferenceCode: externalReferenceCode,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutUserAccountByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_UserAccount
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}',
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
	public static headlessAdminUserV10DeleteUserAccountByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/user-accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param emailAddress
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountByEmailAddress(
		emailAddress: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/by-email-address/{emailAddress}',
			path: {
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * @param userAccountId
	 * @param formData
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostUserAccountImage(
		userAccountId: string,
		formData?: HeadlessAdminUser_v1_0_MultipartBody
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/image',
			path: {
				userAccountId: userAccountId,
			},
			formData: formData,
			mediaType: 'multipart/form-data',
		});
	}

	/**
	 * Assigns a user to an account by their email address
	 * @param accountId
	 * @param emailAddress
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountUserAccountByEmailAddress(
		accountId: string,
		emailAddress: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}',
			path: {
				accountId: accountId,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * Removes a user from an account by their email address
	 * @param accountId
	 * @param emailAddress
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountUserAccountByEmailAddress(
		accountId: string,
		emailAddress: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/user-accounts/by-email-address/{emailAddress}',
			path: {
				accountId: accountId,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * @param status
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserAccountsByStatusPage(
		status: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/by-status/{status}',
			path: {
				status: status,
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
}
