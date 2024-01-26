/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PostalAddress } from '../models/PostalAddress';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PostalAddressService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves the account's postal addresses.
     * @returns PostalAddress
     * @throws ApiError
     */
    public getAccountPostalAddressesPage({
        accountId,
    }: {
        accountId: number,
    }): CancelablePromise<Array<PostalAddress>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/postal-addresses',
            path: {
                'accountId': accountId,
            },
        });
    }
    /**
     * @returns PostalAddress
     * @throws ApiError
     */
    public postAccountPostalAddress({
        accountId,
        requestBody,
    }: {
        accountId: number,
        requestBody?: PostalAddress,
    }): CancelablePromise<PostalAddress> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-admin-user/v1.0/accounts/{accountId}/postal-addresses',
            path: {
                'accountId': accountId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the organization's postal addresses.
     * @returns PostalAddress
     * @throws ApiError
     */
    public getOrganizationPostalAddressesPage({
        organizationId,
    }: {
        organizationId: string,
    }): CancelablePromise<Array<PostalAddress>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/organizations/{organizationId}/postal-addresses',
            path: {
                'organizationId': organizationId,
            },
        });
    }
    /**
     * Deletes the postal address
     * @returns void
     * @throws ApiError
     */
    public deletePostalAddress({
        postalAddressId,
    }: {
        postalAddressId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
            path: {
                'postalAddressId': postalAddressId,
            },
        });
    }
    /**
     * Retrieves the postal address.
     * @returns PostalAddress
     * @throws ApiError
     */
    public getPostalAddress({
        postalAddressId,
    }: {
        postalAddressId: number,
    }): CancelablePromise<PostalAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
            path: {
                'postalAddressId': postalAddressId,
            },
        });
    }
    /**
     * @returns PostalAddress
     * @throws ApiError
     */
    public patchPostalAddress({
        postalAddressId,
        requestBody,
    }: {
        postalAddressId: number,
        requestBody?: PostalAddress,
    }): CancelablePromise<PostalAddress> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
            path: {
                'postalAddressId': postalAddressId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns PostalAddress
     * @throws ApiError
     */
    public putPostalAddress({
        postalAddressId,
        requestBody,
    }: {
        postalAddressId: number,
        requestBody?: PostalAddress,
    }): CancelablePromise<PostalAddress> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-admin-user/v1.0/postal-addresses/{postalAddressId}',
            path: {
                'postalAddressId': postalAddressId,
            },
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * Retrieves the user's postal addresses.
     * @returns PostalAddress
     * @throws ApiError
     */
    public getUserAccountPostalAddressesPage({
        userAccountId,
    }: {
        userAccountId: number,
    }): CancelablePromise<Array<PostalAddress>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-admin-user/v1.0/user-accounts/{userAccountId}/postal-addresses',
            path: {
                'userAccountId': userAccountId,
            },
        });
    }
}
