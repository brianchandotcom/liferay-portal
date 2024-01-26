/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Organization } from '../models/Organization';
import type { UserAccount } from '../models/UserAccount';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrganizationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted.
     * @returns Organization
     * @throws ApiError
     */
    public getHeadlessAdminUserV10AccountsByExternalReferenceCodeOrganizations({
        externalReferenceCode,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        externalReferenceCode: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Organization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
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
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessAdminUserV10AccountsByExternalReferenceCodeOrganizations({
        externalReferenceCode,
        organizationId,
    }: {
        externalReferenceCode: string,
        organizationId: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations/{organizationId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * @returns Organization
     * @throws ApiError
     */
    public getHeadlessAdminUserV10AccountsByExternalReferenceCodeOrganizations1({
        externalReferenceCode,
        organizationId,
    }: {
        externalReferenceCode: string,
        organizationId: string,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations/{organizationId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public postHeadlessAdminUserV10AccountsByExternalReferenceCodeOrganizations({
        externalReferenceCode,
        organizationId,
    }: {
        externalReferenceCode: string,
        organizationId: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/organizations/{organizationId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Retrieves the account's organizations. Results can be paginated, filtered, searched, and sorted.
     * @returns Organization
     * @throws ApiError
     */
    public getHeadlessAdminUserV10AccountsOrganizations({
        accountId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        accountId: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Organization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations',
            path: {
                'accountId': accountId,
            },
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
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessAdminUserV10AccountsOrganizations({
        accountId,
        organizationId,
    }: {
        accountId: number,
        organizationId: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations/{organizationId}',
            path: {
                'accountId': accountId,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * @returns Organization
     * @throws ApiError
     */
    public getHeadlessAdminUserV10AccountsOrganizations1({
        accountId,
        organizationId,
    }: {
        accountId: number,
        organizationId: string,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations/{organizationId}',
            path: {
                'accountId': accountId,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public postHeadlessAdminUserV10AccountsOrganizations({
        accountId,
        organizationId,
    }: {
        accountId: number,
        organizationId: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/organizations/{organizationId}',
            path: {
                'accountId': accountId,
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Retrieves the organizations. Results can be paginated, filtered, searched, and sorted.
     * @returns Organization
     * @throws ApiError
     */
    public getOrganizationsPage({
        flatten,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        flatten?: boolean,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Organization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations',
            query: {
                'flatten': flatten,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Creates a new organization
     * @returns Organization Organizations successfully created
     * @throws ApiError
     */
    public postOrganization({
        requestBody,
    }: {
        requestBody?: Organization,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/organizations',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes an organization.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessAdminUserV10OrganizationsByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns Organization
     * @throws ApiError
     */
    public getHeadlessAdminUserV10OrganizationsByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Updates the organization with information sent in the request body. Only the provided fields are updated.
     * @returns Organization Organization successfully updated
     * @throws ApiError
     */
    public patchHeadlessAdminUserV10OrganizationsByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: Organization,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the organization with information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns Organization Organization successfully replaced
     * @throws ApiError
     */
    public putHeadlessAdminUserV10OrganizationsByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: Organization,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/organizations/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes an organization.
     * @returns void
     * @throws ApiError
     */
    public deleteOrganization({
        organizationId,
    }: {
        organizationId: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}',
            path: {
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Retrieves the organization.
     * @returns Organization
     * @throws ApiError
     */
    public getOrganization({
        organizationId,
    }: {
        organizationId: string,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}',
            path: {
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Updates the organization with the information sent in the request body. Fields not present in the request body are left unchanged.
     * @returns Organization Organization successfully updated
     * @throws ApiError
     */
    public patchOrganization({
        organizationId,
        requestBody,
    }: {
        organizationId: string,
        requestBody?: Organization,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}',
            path: {
                'organizationId': organizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the organization with information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns Organization Organization successfully replaced
     * @throws ApiError
     */
    public putOrganization({
        organizationId,
        requestBody,
    }: {
        organizationId: string,
        requestBody?: Organization,
    }): CancelablePromise<Organization> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}',
            path: {
                'organizationId': organizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted.
     * @returns Organization
     * @throws ApiError
     */
    public getOrganizationChildOrganizationsPage({
        organizationId,
        flatten,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        organizationId: string,
        flatten?: boolean,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Organization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/child-organizations',
            path: {
                'organizationId': organizationId,
            },
            query: {
                'flatten': flatten,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
    /**
     * Removes users from an organization by their email addresses
     * @returns void
     * @throws ApiError
     */
    public deleteUserAccountsByEmailAddress({
        organizationId,
        requestBody,
    }: {
        organizationId: string,
        /**
         * A list of user email addresses to remove from the organization
         */
        requestBody: Array<string>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address',
            path: {
                'organizationId': organizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Assigns users to an organization by their email addresses
     * @returns UserAccount
     * @throws ApiError
     */
    public postUserAccountsByEmailAddress({
        organizationId,
        requestBody,
        organizationRoleIds,
    }: {
        organizationId: string,
        /**
         * A list of user email addresses to assign to the organization
         */
        requestBody: Array<string>,
        organizationRoleIds?: string,
    }): CancelablePromise<Array<UserAccount>> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address',
            path: {
                'organizationId': organizationId,
            },
            query: {
                'organizationRoleIds': organizationRoleIds,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Removes a user from an organization by their email address
     * @returns void
     * @throws ApiError
     */
    public deleteUserAccountByEmailAddress({
        organizationId,
        emailAddress,
    }: {
        organizationId: string,
        emailAddress: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'organizationId': organizationId,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Assigns a user to an organization by their email address
     * @returns UserAccount
     * @throws ApiError
     */
    public postUserAccountByEmailAddress({
        organizationId,
        emailAddress,
    }: {
        organizationId: string,
        emailAddress: string,
    }): CancelablePromise<UserAccount> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'organizationId': organizationId,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Retrieves the parent organization's child organizations. Results can be paginated, filtered, searched, and sorted.
     * @returns Organization
     * @throws ApiError
     */
    public getOrganizationOrganizationsPage({
        parentOrganizationId,
        flatten,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        parentOrganizationId: string,
        flatten?: boolean,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Organization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{parentOrganizationId}/organizations',
            path: {
                'parentOrganizationId': parentOrganizationId,
            },
            query: {
                'flatten': flatten,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
        });
    }
}
