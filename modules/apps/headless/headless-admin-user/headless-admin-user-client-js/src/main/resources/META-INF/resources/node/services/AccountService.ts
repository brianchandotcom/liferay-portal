/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Account } from '../models/Account';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the accounts. Results can be paginated, filtered, searched, and sorted.
     * @returns Account
     * @throws ApiError
     */
    public getAccountsPage({
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
    }): CancelablePromise<Array<Account>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts',
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
     * Creates a new account
     * @returns Account Account successfully created
     * @throws ApiError
     */
    public postAccount({
        requestBody,
    }: {
        requestBody?: Account,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes an account.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns Account
     * @throws ApiError
     */
    public getAccountByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Updates the account with information sent in the request body. Only the provided fields are updated.
     * @returns Account Account successfully updated
     * @throws ApiError
     */
    public patchAccountByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: Account,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the account with information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns Account Account successfully replaced
     * @throws ApiError
     */
    public putAccountByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody?: Account,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/accounts/by-external-reference-code/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Deletes an account.
     * @returns void
     * @throws ApiError
     */
    public deleteAccount({
        accountId,
    }: {
        accountId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/accounts/{accountId}',
            path: {
                'accountId': accountId,
            },
        });
    }
    /**
     * @returns Account
     * @throws ApiError
     */
    public getAccount({
        accountId,
    }: {
        accountId: number,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}',
            path: {
                'accountId': accountId,
            },
        });
    }
    /**
     * Updates the account with information sent in the request body. Only the provided fields are updated.
     * @returns Account Account successfully updated
     * @throws ApiError
     */
    public patchAccount({
        accountId,
        requestBody,
    }: {
        accountId: number,
        requestBody?: Account,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/accounts/{accountId}',
            path: {
                'accountId': accountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Replaces the account with information sent in the request body. Any missing fields are deleted unless they are required.
     * @returns Account Account successfully replaced
     * @throws ApiError
     */
    public putAccount({
        accountId,
        requestBody,
    }: {
        accountId: number,
        requestBody?: Account,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/accounts/{accountId}',
            path: {
                'accountId': accountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchOrganizationMoveAccounts({
        sourceOrganizationId,
        targetOrganizationId,
        requestBody,
    }: {
        sourceOrganizationId: number,
        targetOrganizationId: number,
        /**
         * An array of accountIds to move from the source organization to the target organization
         */
        requestBody?: Array<number>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/organizations/move-accounts/{sourceOrganizationId}/{targetOrganizationId}',
            path: {
                'sourceOrganizationId': sourceOrganizationId,
                'targetOrganizationId': targetOrganizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchOrganizationMoveAccountsByExternalReferenceCode({
        sourceOrganizationId,
        targetOrganizationId,
        requestBody,
    }: {
        sourceOrganizationId: number,
        targetOrganizationId: number,
        /**
         * An array of account external reference codes to move from the source organization to the target organization
         */
        requestBody?: Array<string>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/organizations/move-accounts/{sourceOrganizationId}/{targetOrganizationId}/by-external-reference-code',
            path: {
                'sourceOrganizationId': sourceOrganizationId,
                'targetOrganizationId': targetOrganizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteOrganizationAccounts({
        organizationId,
        requestBody,
    }: {
        organizationId: number,
        /**
         * An array of accountIds to remove from an organization
         */
        requestBody?: Array<number>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts',
            path: {
                'organizationId': organizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the organization's members (accounts). Results can be paginated, filtered, searched, and sorted.
     * @returns Account
     * @throws ApiError
     */
    public getOrganizationAccountsPage({
        organizationId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        organizationId: string,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Account>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts',
            path: {
                'organizationId': organizationId,
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
    public postOrganizationAccounts({
        organizationId,
        requestBody,
    }: {
        organizationId: number,
        /**
         * An array of accountIds to add to an organization
         */
        requestBody?: Array<number>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts',
            path: {
                'organizationId': organizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public deleteOrganizationAccountsByExternalReferenceCode({
        organizationId,
        requestBody,
    }: {
        organizationId: number,
        /**
         * An array of account external reference codes to remove from an organization
         */
        requestBody?: Array<string>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts/by-external-reference-code',
            path: {
                'organizationId': organizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public postOrganizationAccountsByExternalReferenceCode({
        organizationId,
        requestBody,
    }: {
        organizationId: number,
        /**
         * An array of account external reference codes to add to an organization
         */
        requestBody?: Array<string>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/accounts/by-external-reference-code',
            path: {
                'organizationId': organizationId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
