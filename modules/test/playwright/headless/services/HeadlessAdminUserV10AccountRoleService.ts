/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_AccountRole} from '../models/HeadlessAdminUser_v1_0_AccountRole';
import type {HeadlessAdminUser_v1_0_PageAccountRole} from '../models/HeadlessAdminUser_v1_0_PageAccountRole';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10AccountRoleService {

	/**
	 * @param accountId
	 * @param keywords
	 * @param filter
	 * @param sort
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountAccountRolesPageExportBatch(
		accountId: string,
		keywords?: string,
		filter?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles/export-batch',
			path: {
				accountId: accountId,
			},
			query: {
				keywords: keywords,
				filter: filter,
				sort: sort,
				callbackURL: callbackUrl,
				contentType: contentType,
				fieldNames: fieldNames,
			},
		});
	}

	/**
	 * Assigns account users to the account role
	 * @param accountId
	 * @param accountRoleId
	 * @param userAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountAccountRoleUserAccountAssociation(
		accountId: string,
		accountRoleId: string,
		userAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}',
			path: {
				accountId: accountId,
				accountRoleId: accountRoleId,
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Unassigns account users to the account role
	 * @param accountId
	 * @param accountRoleId
	 * @param userAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountAccountRoleUserAccountAssociation(
		accountId: string,
		accountRoleId: string,
		userAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}',
			path: {
				accountId: accountId,
				accountRoleId: accountRoleId,
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Assigns account users by email address to the account role
	 * @param externalReferenceCode
	 * @param accountRoleId
	 * @param emailAddress
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
		externalReferenceCode: string,
		accountRoleId: string,
		emailAddress: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}',
			path: {
				externalReferenceCode: externalReferenceCode,
				accountRoleId: accountRoleId,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * Unassigns account users by email address from the account role
	 * @param externalReferenceCode
	 * @param accountRoleId
	 * @param emailAddress
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress(
		externalReferenceCode: string,
		accountRoleId: string,
		emailAddress: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}',
			path: {
				externalReferenceCode: externalReferenceCode,
				accountRoleId: accountRoleId,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * Assigns account users by external reference code to the account role
	 * @param accountExternalReferenceCode
	 * @param accountRoleId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(
		accountExternalReferenceCode: string,
		accountRoleId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				accountRoleId: accountRoleId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Unassigns account users by external reference code from the account role
	 * @param accountExternalReferenceCode
	 * @param accountRoleId
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode(
		accountExternalReferenceCode: string,
		accountRoleId: string,
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{externalReferenceCode}',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				accountRoleId: accountRoleId,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Gets the account's roles
	 * @param externalReferenceCode
	 * @param keywords
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageAccountRole default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountAccountRolesByExternalReferenceCodePage(
		externalReferenceCode: string,
		keywords?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccountRole> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			query: {
				keywords: keywords,
				filter: filter,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * Adds a role for the account
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_AccountRole default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountAccountRoleByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_AccountRole
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountRole> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Gets the account's roles
	 * @param accountId
	 * @param keywords
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageAccountRole default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountAccountRolesPage(
		accountId: string,
		keywords?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccountRole> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles',
			path: {
				accountId: accountId,
			},
			query: {
				keywords: keywords,
				filter: filter,
				page: page,
				pageSize: pageSize,
				sort: sort,
			},
		});
	}

	/**
	 * Adds a role for the account
	 * @param accountId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_AccountRole default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountAccountRole(
		accountId: string,
		requestBody?: HeadlessAdminUser_v1_0_AccountRole
	): CancelablePromise<HeadlessAdminUser_v1_0_AccountRole> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles',
			path: {
				accountId: accountId,
			},
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
	public static headlessAdminUserV10PostAccountAccountRoleBatch(
		accountId: string,
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles/batch',
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
	 * Gets a user's account roles by their email address from an account by external reference code
	 * @param externalReferenceCode
	 * @param emailAddress
	 * @returns HeadlessAdminUser_v1_0_PageAccountRole default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage(
		externalReferenceCode: string,
		emailAddress: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccountRole> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}/account-roles',
			path: {
				externalReferenceCode: externalReferenceCode,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * Gets a user's account roles by their external reference code from an account by external reference code
	 * @param accountExternalReferenceCode
	 * @param externalReferenceCode
	 * @returns HeadlessAdminUser_v1_0_PageAccountRole default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage(
		accountExternalReferenceCode: string,
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageAccountRole> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}/account-roles',
			path: {
				accountExternalReferenceCode: accountExternalReferenceCode,
				externalReferenceCode: externalReferenceCode,
			},
		});
	}
}
