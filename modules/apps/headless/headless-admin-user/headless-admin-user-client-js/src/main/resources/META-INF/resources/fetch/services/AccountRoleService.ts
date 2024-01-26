/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountRole } from '../models/AccountRole';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountRoleService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Unassigns account users by external reference code from the account role
     * @returns void
     * @throws ApiError
     */
    public deleteAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode({
        accountExternalReferenceCode,
        accountRoleId,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        accountRoleId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'accountRoleId': accountRoleId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Assigns account users by external reference code to the account role
     * @returns void
     * @throws ApiError
     */
    public postAccountByExternalReferenceCodeAccountRoleUserAccountByExternalReferenceCode({
        accountExternalReferenceCode,
        accountRoleId,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        accountRoleId: number,
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'accountRoleId': accountRoleId,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Gets a user's account roles by their external reference code from an account by external reference code
     * @returns AccountRole
     * @throws ApiError
     */
    public getAccountByExternalReferenceCodeUserAccountByExternalReferenceCodeAccountRolesPage({
        accountExternalReferenceCode,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<Array<AccountRole>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{accountExternalReferenceCode}/user-accounts/by-external-reference-code/{externalReferenceCode}/account-roles',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Gets the account's roles
     * @returns AccountRole
     * @throws ApiError
     */
    public getAccountAccountRolesByExternalReferenceCodePage({
        externalReferenceCode,
        keywords,
        filter,
        page,
        pageSize,
        sort,
    }: {
        externalReferenceCode: string,
        keywords?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<AccountRole>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'keywords': keywords,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * Adds a role for the account
     * @returns AccountRole
     * @throws ApiError
     */
    public postAccountAccountRoleByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: AccountRole,
    }): CancelablePromise<AccountRole> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Unassigns account users by email address from the account role
     * @returns void
     * @throws ApiError
     */
    public deleteAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress({
        externalReferenceCode,
        accountRoleId,
        emailAddress,
    }: {
        externalReferenceCode: string,
        accountRoleId: number,
        emailAddress: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'accountRoleId': accountRoleId,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Assigns account users by email address to the account role
     * @returns void
     * @throws ApiError
     */
    public postAccountByExternalReferenceCodeAccountRoleUserAccountByEmailAddress({
        externalReferenceCode,
        accountRoleId,
        emailAddress,
    }: {
        externalReferenceCode: string,
        accountRoleId: number,
        emailAddress: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/account-roles/{accountRoleId}/user-accounts/by-email-address/{emailAddress}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'accountRoleId': accountRoleId,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Gets a user's account roles by their email address from an account by external reference code
     * @returns AccountRole
     * @throws ApiError
     */
    public getAccountByExternalReferenceCodeUserAccountByEmailAddressAccountRolesPage({
        externalReferenceCode,
        emailAddress,
    }: {
        externalReferenceCode: string,
        emailAddress: string,
    }): CancelablePromise<Array<AccountRole>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}/user-accounts/by-email-address/{emailAddress}/account-roles',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'emailAddress': emailAddress,
            },
        });
    }
    /**
     * Gets the account's roles
     * @returns AccountRole
     * @throws ApiError
     */
    public getAccountAccountRolesPage({
        accountId,
        keywords,
        filter,
        page,
        pageSize,
        sort,
    }: {
        accountId: number,
        keywords?: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<AccountRole>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles',
            path: {
                'accountId': accountId,
            },
            query: {
                'keywords': keywords,
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * Adds a role for the account
     * @returns AccountRole
     * @throws ApiError
     */
    public postAccountAccountRole({
        accountId,
        requestBody,
    }: {
        accountId: number,
        requestBody?: AccountRole,
    }): CancelablePromise<AccountRole> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles',
            path: {
                'accountId': accountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Unassigns account users to the account role
     * @returns void
     * @throws ApiError
     */
    public deleteAccountAccountRoleUserAccountAssociation({
        accountId,
        accountRoleId,
        userAccountId,
    }: {
        accountId: number,
        accountRoleId: number,
        userAccountId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}',
            path: {
                'accountId': accountId,
                'accountRoleId': accountRoleId,
                'userAccountId': userAccountId,
            },
        });
    }
    /**
     * Assigns account users to the account role
     * @returns void
     * @throws ApiError
     */
    public postAccountAccountRoleUserAccountAssociation({
        accountId,
        accountRoleId,
        userAccountId,
    }: {
        accountId: number,
        accountRoleId: number,
        userAccountId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/account-roles/{accountRoleId}/user-accounts/{userAccountId}',
            path: {
                'accountId': accountId,
                'accountRoleId': accountRoleId,
                'userAccountId': userAccountId,
            },
        });
    }
}
