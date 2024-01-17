/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessAdminUser_v1_0_PageUserGroup} from '../models/HeadlessAdminUser_v1_0_PageUserGroup';
import type {HeadlessAdminUser_v1_0_UserGroup} from '../models/HeadlessAdminUser_v1_0_UserGroup';
import type {CancelablePromise} from '../core/CancelablePromise';
import {OpenAPI} from '../core/OpenAPI';
import {request as __request} from '../core/request';
export class HeadlessAdminUserV10UserGroupService {

	/**
	 * @param filter
	 * @param page
	 * @param pageSize
	 * @param search
	 * @param sort
	 * @returns HeadlessAdminUser_v1_0_PageUserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserGroupsPage(
		filter?: string,
		page?: string,
		pageSize?: string,
		search?: string,
		sort?: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-groups',
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
	 * @returns HeadlessAdminUser_v1_0_UserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostUserGroup(
		requestBody?: HeadlessAdminUser_v1_0_UserGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_UserGroup> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-groups',
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
	public static headlessAdminUserV10PostUserGroupsPageExportBatch(
		filter?: string,
		search?: string,
		sort?: string,
		callbackUrl?: string,
		contentType?: string,
		fieldNames?: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-groups/export-batch',
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
	 * @param userGroupId
	 * @returns HeadlessAdminUser_v1_0_UserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserGroup(
		userGroupId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
			path: {
				userGroupId: userGroupId,
			},
		});
	}

	/**
	 * @param userGroupId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutUserGroup(
		userGroupId: string,
		requestBody?: HeadlessAdminUser_v1_0_UserGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_UserGroup> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
			path: {
				userGroupId: userGroupId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param userGroupId
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteUserGroup(
		userGroupId: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
			path: {
				userGroupId: userGroupId,
			},
		});
	}

	/**
	 * @param userGroupId
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchUserGroup(
		userGroupId: string,
		requestBody?: HeadlessAdminUser_v1_0_UserGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_UserGroup> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
			path: {
				userGroupId: userGroupId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param userGroupId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PostUserGroupUsers(
		userGroupId: string,
		requestBody?: Array<number>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-groups/{userGroupId}/user-group-users',
			path: {
				userGroupId: userGroupId,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param userGroupId
	 * @param requestBody
	 * @returns any default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10DeleteUserGroupUsers(
		userGroupId: string,
		requestBody?: Array<number>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/user-groups/{userGroupId}/user-group-users',
			path: {
				userGroupId: userGroupId,
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
	public static headlessAdminUserV10PutUserGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/user-groups/batch',
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
	public static headlessAdminUserV10PostUserGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'POST',
			url: '/headless-admin-user/v1.0/user-groups/batch',
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
	public static headlessAdminUserV10DeleteUserGroupBatch(
		callbackUrl?: string,
		requestBody?: Record<string, any>
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/user-groups/batch',
			query: {
				callbackURL: callbackUrl,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * @param externalReferenceCode
	 * @returns HeadlessAdminUser_v1_0_UserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserGroupByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<HeadlessAdminUser_v1_0_UserGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PutUserGroupByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_UserGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_UserGroup> {
		return __request(OpenAPI, {
			method: 'PUT',
			url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
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
	public static headlessAdminUserV10DeleteUserGroupByExternalReferenceCode(
		externalReferenceCode: string
	): CancelablePromise<any> {
		return __request(OpenAPI, {
			method: 'DELETE',
			url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
		});
	}

	/**
	 * @param externalReferenceCode
	 * @param requestBody
	 * @returns HeadlessAdminUser_v1_0_UserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10PatchUserGroupByExternalReferenceCode(
		externalReferenceCode: string,
		requestBody?: HeadlessAdminUser_v1_0_UserGroup
	): CancelablePromise<HeadlessAdminUser_v1_0_UserGroup> {
		return __request(OpenAPI, {
			method: 'PATCH',
			url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
			path: {
				externalReferenceCode: externalReferenceCode,
			},
			body: requestBody,
			mediaType: 'application/json',
		});
	}

	/**
	 * Retrieves the user's user groups.
	 * @param userAccountId
	 * @returns HeadlessAdminUser_v1_0_PageUserGroup default response
	 * @throws ApiError
	 */
	public static headlessAdminUserV10GetUserUserGroups(
		userAccountId: string
	): CancelablePromise<HeadlessAdminUser_v1_0_PageUserGroup> {
		return __request(OpenAPI, {
			method: 'GET',
			url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/user-groups',
			path: {
				userAccountId: userAccountId,
			},
		});
	}
}
