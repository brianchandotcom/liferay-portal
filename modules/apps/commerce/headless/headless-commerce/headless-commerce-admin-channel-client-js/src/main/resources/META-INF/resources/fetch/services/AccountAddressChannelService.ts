/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountAddressChannel } from '../models/AccountAddressChannel';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountAddressChannelService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Account Address Channel by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteAccountAddressChannel({
        accountAddressChannelId,
    }: {
        accountAddressChannelId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-channel/v1.0/account-address-channels/{accountAddressChannelId}',
            path: {
                'accountAddressChannelId': accountAddressChannelId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Account Address Channels.
     * @returns AccountAddressChannel Successful operation
     * @throws ApiError
     */
    public getAccountAddressByExternalReferenceCodeAccountAddressChannelsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountAddressChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/account-addresses/by-externalReferenceCode/{externalReferenceCode}/account-address-channels',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a Account Address Channel.
     * @returns AccountAddressChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postAccountAddressByExternalReferenceCodeAccountAddressChannel({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountAddressChannel,
    }): CancelablePromise<AccountAddressChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-channel/v1.0/account-addresses/by-externalReferenceCode/{externalReferenceCode}/account-address-channels',
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
     * Gets a list of Account Address Channels.
     * @returns AccountAddressChannel Successful operation
     * @throws ApiError
     */
    public getAccountAddressIdAccountAddressChannelsPage({
        addressId,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        addressId: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<AccountAddressChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/account-addresses/{addressId}/account-address-channels',
            path: {
                'addressId': addressId,
            },
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
     * Creates or updates a Account Address Channel.
     * @returns AccountAddressChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postAccountAddressIdAccountAddressChannel({
        addressId,
        requestBody,
    }: {
        addressId: number,
        requestBody: AccountAddressChannel,
    }): CancelablePromise<AccountAddressChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-channel/v1.0/account-addresses/{addressId}/account-address-channels',
            path: {
                'addressId': addressId,
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
