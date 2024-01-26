/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountGroup } from '../models/AccountGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountGroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the account groups. Results can be paginated, filtered, searched, and sorted.
     * @returns AccountGroup
     * @throws ApiError
     */
    public getAccountGroupsPage({
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
    }): CancelablePromise<Array<AccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/account-groups',
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
     * Creates a new account group
     * @returns AccountGroup AccountGroup successfully created
     * @throws ApiError
     */
    public postAccountGroup({
        requestBody,
    }: {
        requestBody?: AccountGroup,
    }): CancelablePromise<AccountGroup> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/account-groups',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Removes an account by their external reference code from an account group by external reference code
     * @returns void
     * @throws ApiError
     */
    public deleteAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode({
        accountExternalReferenceCode,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{accountExternalReferenceCode}/accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Assigns an account by its external reference code to an account group by external reference code
     * @returns void
     * @throws ApiError
     */
    public postAccountGroupByExternalReferenceCodeAccountByExternalReferenceCode({
        accountExternalReferenceCode,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{accountExternalReferenceCode}/accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Deletes an account group.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountGroupByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns AccountGroup
     * @throws ApiError
     */
    public getAccountGroupByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<AccountGroup> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Updates the account with information sent in the request body. Only the provided fields are updated.
     * @returns AccountGroup AccountGroup successfully updated
     * @throws ApiError
     */
    public patchAccountGroupByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: AccountGroup,
    }): CancelablePromise<AccountGroup> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the account group with information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns AccountGroup AccountGroup successfully replaced
     * @throws ApiError
     */
    public putAccountGroupByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: AccountGroup,
    }): CancelablePromise<AccountGroup> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/account-groups/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes an account group.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountGroup({
        accountGroupId,
    }: {
        accountGroupId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
            path: {
                'accountGroupId': accountGroupId,
            },
        });
    }
    /**
     * @returns AccountGroup
     * @throws ApiError
     */
    public getAccountGroup({
        accountGroupId,
    }: {
        accountGroupId: number,
    }): CancelablePromise<AccountGroup> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
            path: {
                'accountGroupId': accountGroupId,
            },
        });
    }
    /**
     * Updates the account group with information sent in the request body. Only the provided fields are updated.
     * @returns AccountGroup AccountGroup successfully updated
     * @throws ApiError
     */
    public patchAccountGroup({
        accountGroupId,
        requestBody,
    }: {
        accountGroupId: number,
        requestBody?: AccountGroup,
    }): CancelablePromise<AccountGroup> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
            path: {
                'accountGroupId': accountGroupId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the account group with information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns AccountGroup AccountGroup successfully replaced
     * @throws ApiError
     */
    public putAccountGroup({
        accountGroupId,
        requestBody,
    }: {
        accountGroupId: number,
        requestBody?: AccountGroup,
    }): CancelablePromise<AccountGroup> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/account-groups/{accountGroupId}',
            path: {
                'accountGroupId': accountGroupId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns AccountGroup
     * @throws ApiError
     */
    public getHeadlessAdminUserV10AccountsByExternalReferenceCodeAccountGroups({
        accountExternalReferenceCode,
        page,
        pageSize,
    }: {
        accountExternalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-groups',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
    /**
     * @returns AccountGroup
     * @throws ApiError
     */
    public getHeadlessAdminUserV10AccountsAccountGroups({
        accountId,
        page,
        pageSize,
    }: {
        accountId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/account-groups',
            path: {
                'accountId': accountId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
        });
    }
}
