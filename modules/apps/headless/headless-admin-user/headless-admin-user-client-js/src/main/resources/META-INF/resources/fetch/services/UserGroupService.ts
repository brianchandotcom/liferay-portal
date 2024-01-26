/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { UserGroup } from '../models/UserGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class UserGroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the user's user groups.
     * @returns UserGroup
     * @throws ApiError
     */
    public getUserUserGroups({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<Array<UserGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/user-groups',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public getUserGroupsPage({
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<UserGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-groups',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public postUserGroup({
        requestBody,
    }: {
        requestBody?: UserGroup,
    }): CancelablePromise<UserGroup> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/user-groups',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteUserGroupByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public getUserGroupByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<UserGroup> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public patchUserGroupByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: UserGroup,
    }): CancelablePromise<UserGroup> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public putUserGroupByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: UserGroup,
    }): CancelablePromise<UserGroup> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/user-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteUserGroup({
        userGroupId,
    }: {
        userGroupId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
            path: {
                'userGroupId': userGroupId,
            },
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public getUserGroup({
        userGroupId,
    }: {
        userGroupId: number,
    }): CancelablePromise<UserGroup> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
            path: {
                'userGroupId': userGroupId,
            },
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public patchUserGroup({
        userGroupId,
        requestBody,
    }: {
        userGroupId: number,
        requestBody?: UserGroup,
    }): CancelablePromise<UserGroup> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
            path: {
                'userGroupId': userGroupId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns UserGroup
     * @throws ApiError
     */
    public putUserGroup({
        userGroupId,
        requestBody,
    }: {
        userGroupId: number,
        requestBody?: UserGroup,
    }): CancelablePromise<UserGroup> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/user-groups/{userGroupId}',
            path: {
                'userGroupId': userGroupId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteUserGroupUsers({
        userGroupId,
        requestBody,
    }: {
        userGroupId: number,
        requestBody?: Array<number>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/user-groups/{userGroupId}/user-group-users',
            path: {
                'userGroupId': userGroupId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public postUserGroupUsers({
        userGroupId,
        requestBody,
    }: {
        userGroupId: number,
        requestBody?: Array<number>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/user-groups/{userGroupId}/user-group-users',
            path: {
                'userGroupId': userGroupId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
