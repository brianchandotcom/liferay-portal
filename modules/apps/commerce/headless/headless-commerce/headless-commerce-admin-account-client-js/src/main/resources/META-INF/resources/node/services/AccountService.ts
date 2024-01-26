/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Account } from '../models/Account';
import type { body } from '../models/body';
import type { body_1 } from '../models/body_1';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Creates or updates an Account.
     * @returns any Created
     * @throws ApiError
     */
    public postAccountGroupByExternalReferenceCodeAccount({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Account,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accountGroups/by-externalReferenceCode/{externalReferenceCode}/accounts',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates an Account.
     * @returns any Created
     * @throws ApiError
     */
    public deleteAccountGroupByExternalReferenceCodeAccount({
        accountExternalReferenceCode,
        externalReferenceCode,
    }: {
        accountExternalReferenceCode: string,
        externalReferenceCode: string,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/accountGroups/by-externalReferenceCode/{externalReferenceCode}/accounts/{accountExternalReferenceCode}',
            path: {
                'accountExternalReferenceCode': accountExternalReferenceCode,
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Gets a list of Accounts.
     * @returns Account Successful operation
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
            url: '/headless-commerce-admin-account/v1.0/accounts',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates an Account.
     * @returns Account Created
     * @returns any Async
     * @throws ApiError
     */
    public postAccount({
        requestBody,
    }: {
        requestBody: Account,
    }): CancelablePromise<Account | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts',
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Deletes an Account by external reference code.
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
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Account by external reference code.
     * @returns Account Successful operation
     * @throws ApiError
     */
    public getAccountByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates an Account by external reference code.
     * @returns any Created
     * @throws ApiError
     */
    public patchAccountByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Account,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates an Account Logo.
     * @returns any Created
     * @throws ApiError
     */
    public postAccountByExternalReferenceCodeLogo({
        externalReferenceCode,
        formData,
    }: {
        externalReferenceCode: string,
        formData: body_1,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/logo',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Deletes an Account by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteAccount({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Account by ID.
     * @returns Account Successful operation
     * @throws ApiError
     */
    public getAccount({
        id,
    }: {
        id: number,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}',
            path: {
                'id': id,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates an Account by ID.
     * @returns any Created
     * @throws ApiError
     */
    public patchAccount({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Account,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates an Account Logo.
     * @returns any Created
     * @throws ApiError
     */
    public postAccountLogo({
        id,
        formData,
    }: {
        id: number,
        formData: body,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/logo',
            path: {
                'id': id,
            },
            formData: formData,
            mediaType: 'multipart/form-data',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
