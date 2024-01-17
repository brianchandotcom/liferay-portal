/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PageRole} from '../models/HeadlessAdminUser_v1_0_PageRole';
import type {HeadlessAdminUser_v1_0_Role} from '../models/HeadlessAdminUser_v1_0_Role';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10RoleService {

	/**
	 * Retrieves the role.
	 * @param roleId
	 * @returns HeadlessAdminUser_v1_0_Role default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetRole(
		roleId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_Role> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/roles/{roleId}',
			path: {
				roleId: roleId,
			},
		});
	}

	/**
	 * Retrieves the portal instance's roles. Results can be paginated.
	 * @param types
	 * @param page
	 * @param pageSize
	 * @param search
	 * @returns HeadlessAdminUser_v1_0_PageRole default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetRolesPage(
		types?: string,
		page?: string,
		pageSize?: string,
		search?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageRole> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/roles',
			query: {
				types: types,
				page: page,
				pageSize: pageSize,
				search: search,
			},
		});
	}

	/**
	 * Creates a new role
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_Role default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostRole(
		requestBody?: HeadlessAdminUser_v1_0_Role
	): CancelablePromise<HeadlessAdminUser_v1_0_Role> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/roles',
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param types
	 * @param search
	 * @param callbackUrl
	 * @param contentType
	 * @param fieldNames
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostRolesPageExportBatch(
		types?: string,
		search?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/roles/export-batch',
			query: {
				types: types,
				search: search,
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
	public static headlessAdminUserV10PostRoleBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/roles/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Associates a role with a user account
	 * @param roleId
	 * @param userAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostRoleUserAccountAssociation(
		roleId: string,
		userAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}',
			path: {
				roleId: roleId,
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Unassociates a role with a user account
	 * @param roleId
	 * @param userAccountId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteRoleUserAccountAssociation(
		roleId: string,
		userAccountId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}',
			path: {
				roleId: roleId,
				userAccountId: userAccountId,
			},
		});
	}

	/**
	 * Associates a site role with a user account
	 * @param roleId
	 * @param userAccountId
	 * @param siteId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostSiteRoleUserAccountAssociation(
		roleId: string,
		userAccountId: string,
		siteId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/site/{siteId}',
			path: {
				roleId: roleId,
				userAccountId: userAccountId,
				siteId: siteId,
			},
		});
	}

	/**
	 * Unassociates a site role with a user account
	 * @param roleId
	 * @param userAccountId
	 * @param siteId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteSiteRoleUserAccountAssociation(
		roleId: string,
		userAccountId: string,
		siteId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/site/{siteId}',
			path: {
				roleId: roleId,
				userAccountId: userAccountId,
				siteId: siteId,
			},
		});
	}

	/**
	 * Associates a organization role with a user account
	 * @param roleId
	 * @param userAccountId
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostOrganizationRoleUserAccountAssociation(
		roleId: string,
		userAccountId: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/organization/{organizationId}',
			path: {
				roleId: roleId,
				userAccountId: userAccountId,
				organizationId: organizationId,
			},
		});
	}

	/**
	 * Unassociates an organization role with a user account
	 * @param roleId
	 * @param userAccountId
	 * @param organizationId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteOrganizationRoleUserAccountAssociation(
		roleId: string,
		userAccountId: string,
		organizationId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/organization/{organizationId}',
			path: {
				roleId: roleId,
				userAccountId: userAccountId,
				organizationId: organizationId,
			},
		});
	}
}
