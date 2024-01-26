/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountChannelShippingOption } from '../models/AccountChannelShippingOption';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountChannelShippingOptionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes account channel shipping option.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelShippingOptions({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-options/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel shipping option.
     * @returns AccountChannelShippingOption Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelShippingOptions({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelShippingOption> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-options/{id}',
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
     * Updates an account channel shipping option.
     * @returns AccountChannelShippingOption Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelShippingOptions({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelShippingOption,
    }): CancelablePromise<AccountChannelShippingOption> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-options/{id}',
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
     * Gets a list of account channel shipping options.
     * @returns AccountChannelShippingOption Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelShippingOption({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelShippingOption>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-option',
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
     * Creates or updates an account channel shipping option.
     * @returns AccountChannelShippingOption Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelShippingOption({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelShippingOption,
    }): CancelablePromise<AccountChannelShippingOption | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-option',
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
     * Gets a list of account channel shipping options.
     * @returns AccountChannelShippingOption Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelShippingOption({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelShippingOption>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-option',
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
     * Creates or updates an account channel shipping option.
     * @returns AccountChannelShippingOption Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelShippingOption({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelShippingOption,
    }): CancelablePromise<AccountChannelShippingOption | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-option',
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
}
