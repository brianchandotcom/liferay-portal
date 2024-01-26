/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Role } from '../models/Role';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class RoleService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the portal instance's roles. Results can be paginated.
     * @returns Role
     * @throws ApiError
     */
    public getRolesPage({
        types,
        page,
        pageSize,
        search,
    }: {
        types?: Array<number>,
        page?: number,
        pageSize?: number,
        search?: string,
    }): CancelablePromise<Array<Role>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/roles',
            query: {
                'types': types,
                'page': page,
                'pageSize': pageSize,
                'search': search,
            },
        });
    }
    /**
     * Creates a new role
     * @returns Role Roles successfully created
     * @throws ApiError
     */
    public postRole({
        requestBody,
    }: {
        requestBody?: Role,
    }): CancelablePromise<Role> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/roles',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the role.
     * @returns Role
     * @throws ApiError
     */
    public getRole({
        roleId,
    }: {
        roleId: number,
    }): CancelablePromise<Role> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/roles/{roleId}',
            path: {
                'roleId': roleId,
            },
        });
    }
    /**
     * Unassociates a role with a user account
     * @returns void
     * @throws ApiError
     */
    public deleteRoleUserAccountAssociation({
        roleId,
        userAccountId,
    }: {
        roleId: number,
        userAccountId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}',
            path: {
                'roleId': roleId,
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Associates a role with a user account
     * @returns any
     * @throws ApiError
     */
    public postRoleUserAccountAssociation({
        roleId,
        userAccountId,
    }: {
        roleId: number,
        userAccountId: number,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}',
            path: {
                'roleId': roleId,
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Unassociates an organization role with a user account
     * @returns void
     * @throws ApiError
     */
    public deleteOrganizationRoleUserAccountAssociation({
        roleId,
        userAccountId,
        organizationId,
    }: {
        roleId: number,
        userAccountId: number,
        organizationId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/organization/{organizationId}',
            path: {
                'roleId': roleId,
                'userAccountId': userAccountId,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Associates a organization role with a user account
     * @returns any
     * @throws ApiError
     */
    public postOrganizationRoleUserAccountAssociation({
        roleId,
        userAccountId,
        organizationId,
    }: {
        roleId: number,
        userAccountId: number,
        organizationId: number,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/organization/{organizationId}',
            path: {
                'roleId': roleId,
                'userAccountId': userAccountId,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Unassociates a site role with a user account
     * @returns void
     * @throws ApiError
     */
    public deleteSiteRoleUserAccountAssociation({
        roleId,
        userAccountId,
        siteId,
    }: {
        roleId: number,
        userAccountId: number,
        siteId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/site/{siteId}',
            path: {
                'roleId': roleId,
                'userAccountId': userAccountId,
                'siteId': siteId,
            },
        });
    }
    /**
     * Associates a site role with a user account
     * @returns any
     * @throws ApiError
     */
    public postSiteRoleUserAccountAssociation({
        roleId,
        userAccountId,
        siteId,
    }: {
        roleId: number,
        userAccountId: number,
        siteId: number,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/roles/{roleId}/association/user-account/{userAccountId}/site/{siteId}',
            path: {
                'roleId': roleId,
                'userAccountId': userAccountId,
                'siteId': siteId,
            },
        });
    }
}
