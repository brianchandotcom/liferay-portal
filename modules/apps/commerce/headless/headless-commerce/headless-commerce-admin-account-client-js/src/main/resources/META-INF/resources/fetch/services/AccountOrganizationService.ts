/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountOrganization } from '../models/AccountOrganization';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountOrganizationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Organizations related to an Account.
     * @returns AccountOrganization Successful operation
     * @throws ApiError
     */
    public getAccountByExternalReferenceCodeAccountOrganizationsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountOrganization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
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
     * Creates an Account Organization.
     * @returns AccountOrganization Created
     * @returns any Async
     * @throws ApiError
     */
    public postAccountByExternalReferenceCodeAccountOrganization({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountOrganization,
    }): CancelablePromise<AccountOrganization | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations',
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
     * Deletes an Account Organization by Account external reference code and Organization ID.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountByExternalReferenceCodeAccountOrganization({
        externalReferenceCode,
        organizationId,
    }: {
        externalReferenceCode: string,
        organizationId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations/{organizationId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'organizationId': organizationId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Account Organization by Account external reference code and Organization ID.
     * @returns AccountOrganization Successful operation
     * @throws ApiError
     */
    public getAccountByExternalReferenceCodeAccountOrganization({
        externalReferenceCode,
        organizationId,
    }: {
        externalReferenceCode: string,
        organizationId: number,
    }): CancelablePromise<AccountOrganization> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/accountOrganizations/{organizationId}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'organizationId': organizationId,
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
     * Gets a list of Organizations related to an Account.
     * @returns AccountOrganization Successful operation
     * @throws ApiError
     */
    public getAccountIdAccountOrganizationsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountOrganization>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations',
            path: {
                'id': id,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
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
     * Creates an Account Organization.
     * @returns AccountOrganization Created
     * @returns any Async
     * @throws ApiError
     */
    public postAccountIdAccountOrganization({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountOrganization,
    }): CancelablePromise<AccountOrganization | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations',
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
     * Deletes an Account Member by Account ID and Organization ID.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountIdAccountOrganization({
        id,
        organizationId,
    }: {
        id: number,
        organizationId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations/{organizationId}',
            path: {
                'id': id,
                'organizationId': organizationId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Account Organization by Account ID and Organization ID.
     * @returns AccountOrganization Successful operation
     * @throws ApiError
     */
    public getAccountIdAccountOrganization({
        id,
        organizationId,
    }: {
        id: number,
        organizationId: number,
    }): CancelablePromise<AccountOrganization> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/accountOrganizations/{organizationId}',
            path: {
                'id': id,
                'organizationId': organizationId,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
