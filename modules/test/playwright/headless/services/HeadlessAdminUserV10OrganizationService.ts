/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_Organization} from '../models/HeadlessAdminUser_v1_0_Organization';
import type {HeadlessAdminUser_v1_0_PageOrganization} from '../models/HeadlessAdminUser_v1_0_PageOrganization';
import type {HeadlessAdminUser_v1_0_PageUserAccount} from '../models/HeadlessAdminUser_v1_0_PageUserAccount';
import type {HeadlessAdminUser_v1_0_UserAccount} from '../models/HeadlessAdminUser_v1_0_UserAccount';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10OrganizationService {

	/**
	 * Assigns users to an organization by their email addresses
	 * @param organizationId
	 * @param organizationRoleIds
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_PageUserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostUserAccountsByEmailAddress(
		organizationId: string,
		organizationRoleIds?: string,
		requestBody?: Array<string>
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address',
			path: {
				organizationId: organizationId,
			},
			query: {
				organizationRoleIds: organizationRoleIds,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Removes users from an organization by their email addresses
	 * @param organizationId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteUserAccountsByEmailAddress(
		organizationId: string,
		requestBody?: Array<string>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address',
			path: {
				organizationId: organizationId,
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
	public static headlessAdminUserV10PostOrganizationsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/export-batch',
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
	 * @param organizationId
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountOrganization(
		accountId: string,
		organizationId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations/{organizationId}',
			path: {
				accountId: accountId,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param accountId
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountOrganization(
		accountId: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations/{organizationId}',
			path: {
				accountId: accountId,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param accountId
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountOrganization(
		accountId: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations/{organizationId}',
			path: {
				accountId: accountId,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted.
	 * @param accountId
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageOrganization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountOrganizationsPage(
		accountId: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations',
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
	 * @param externalReferenceCode
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Replaces the organization with information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutOrganizationByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_Organization
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes an organization.
	 * @param externalReferenceCode
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteOrganizationByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * Updates the organization with information sent in the request body. Only the provided fields are updated.
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchOrganizationByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_Organization
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the organizations. Results can be paginated, filtered, searched, and sorted.
	 * @param flatten
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageOrganization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationsPage(
		flatten?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations',
			query: {
				flatten: flatten,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Creates a new organization
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostOrganization(
		requestBody?: HeadlessAdminUser_v1_0_Organization
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the organization.
	 * @param organizationId
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganization(
		organizationId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}',
			path: {
				organizationId: organizationId,
			},
		});
	}

	/**
	 * Replaces the organization with information sent in the request body. Any missing fields are deleted unless they are required.
	 * @param organizationId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutOrganization(
		organizationId: string,
		requestBody?: HeadlessAdminUser_v1_0_Organization
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}',
			path: {
				organizationId: organizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Deletes an organization.
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteOrganization(
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}',
			path: {
				organizationId: organizationId,
			},
		});
	}

	/**
	 * Updates the organization with the information sent in the request body. Fields not present in the request body are left unchanged.
	 * @param organizationId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchOrganization(
		organizationId: string,
		requestBody?: HeadlessAdminUser_v1_0_Organization
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}',
			path: {
				organizationId: organizationId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted.
	 * @param externalReferenceCode
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageOrganization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountByExternalReferenceCodeOrganizationsPage(
		externalReferenceCode: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations',
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
	public static headlessAdminUserV10PostAccountOrganizationsPageExportBatch(
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
			url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations/export-batch',
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
	 * @param callbackUrl
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutOrganizationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/organizations/batch',
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
	public static headlessAdminUserV10PostOrganizationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/batch',
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
	public static headlessAdminUserV10DeleteOrganizationBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/organizations/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Assigns a user to an organization by their email address
	 * @param organizationId
	 * @param emailAddress
	 * @returns HeadlessAdminUser_v1_0_UserAccount default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostUserAccountByEmailAddress(
		organizationId: string,
		emailAddress: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserAccount> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}',
			path: {
				organizationId: organizationId,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * Removes a user from an organization by their email address
	 * @param organizationId
	 * @param emailAddress
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteUserAccountByEmailAddress(
		organizationId: string,
		emailAddress: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}',
			path: {
				organizationId: organizationId,
				emailAddress: emailAddress,
			},
		});
	}

	/**
	 * Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted.
	 * @param organizationId
	 * @param flatten
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageOrganization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationChildOrganizationsPage(
		organizationId: string,
		flatten?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{organizationId}/child-organizations',
			path: {
				organizationId: organizationId,
			},
			query: {
				flatten: flatten,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted.
	 * @param parentOrganizationId
	 * @param flatten
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageOrganization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetOrganizationOrganizationsPage(
		parentOrganizationId: string,
		flatten?: string,
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageOrganization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/organizations/{parentOrganizationId}/organizations',
			path: {
				parentOrganizationId: parentOrganizationId,
			},
			query: {
				flatten: flatten,
				filter: filter,
				page: page,
				pageSize: pageSize,
				search: search,
				sort: sort,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param organizationId
	 * @returns HeadlessAdminUser_v1_0_Organization default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetAccountByExternalReferenceCodeOrganization(
		externalReferenceCode: string,
		organizationId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Organization> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations/{organizationId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostAccountByExternalReferenceCodeOrganization(
		externalReferenceCode: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations/{organizationId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteAccountByExternalReferenceCodeOrganization(
		externalReferenceCode: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations/{organizationId}',
			path: {
				externalReferenceCode: externalReferenceCode,
				organizationId: organizationId,
			},
		});
	}
}
