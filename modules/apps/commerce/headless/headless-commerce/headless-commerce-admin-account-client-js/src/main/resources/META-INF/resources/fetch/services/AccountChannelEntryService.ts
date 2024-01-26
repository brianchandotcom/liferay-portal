/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { AccountChannelEntry } from '../models/AccountChannelEntry';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountChannelEntryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes account channel billing address.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelBillingAddresses({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-billing-addresses/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel billing address.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelBillingAddresses({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-billing-addresses/{id}',
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
     * Updates an account channel billing address.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelBillingAddresses({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-billing-addresses/{id}',
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
     * Deletes account channel currency.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelCurrencies({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-currencies/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel currency.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelCurrencies({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-currencies/{id}',
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
     * Updates an account channel currency.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelCurrencies({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-currencies/{id}',
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
     * Deletes account channel delivery term.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelDeliveryTerms({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-delivery-terms/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel delivery term.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelDeliveryTerms({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-delivery-terms/{id}',
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
     * Updates an account channel delivery term.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelDeliveryTerms({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-delivery-terms/{id}',
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
     * Deletes account channel discount.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelDiscounts({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-discounts/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel discount.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelDiscounts({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-discounts/{id}',
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
     * Updates an account channel discount.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelDiscounts({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-discounts/{id}',
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
     * Deletes account channel payment method.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelPaymentMethods({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-payment-methods/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel payment method.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelPaymentMethods({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-payment-methods/{id}',
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
     * Updates an account channel payment method.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelPaymentMethods({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-payment-methods/{id}',
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
     * Deletes account channel payment term.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelPaymentTerms({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-payment-terms/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel payment term.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelPaymentTerms({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-payment-terms/{id}',
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
     * Updates an account channel payment term.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelPaymentTerms({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-payment-terms/{id}',
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
     * Deletes account channel price list.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelPriceList({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-price-list/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel price list.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelPriceList({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-price-list/{id}',
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
     * Updates an account channel price list.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelPriceList({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-price-list/{id}',
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
     * Deletes account channel shipping address.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelShippingAddresses({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-addresses/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel shipping address.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelShippingAddresses({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-addresses/{id}',
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
     * Updates an account channel shipping address.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelShippingAddresses({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-shipping-addresses/{id}',
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
     * Deletes account channel user.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminAccountV10AccountChannelUsers({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-account/v1.0/account-channel-users/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an account channel user.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountChannelUsers({
        id,
    }: {
        id: number,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/account-channel-users/{id}',
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
     * Updates an account channel account user.
     * @returns AccountChannelEntry Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminAccountV10AccountChannelUsers({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-account/v1.0/account-channel-users/{id}',
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
     * Gets a list of channel billing addresses.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelBillingAddresses({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-billing-addresses',
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
     * Creates an account channel billing address.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelBillingAddresses({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-billing-addresses',
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
     * Gets a list of channel currencies.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelCurrencies({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-currencies',
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
     * Creates an account channel currency.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelCurrencies({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-currencies',
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
     * Gets a list of channel delivery terms.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelDeliveryTerms({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-delivery-terms',
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
     * Creates an account channel delivery term.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelDeliveryTerms({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-delivery-terms',
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
     * Gets a list of channel discounts.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelDiscounts({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-discounts',
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
     * Creates an account channel discount.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelDiscounts({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-discounts',
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
     * Gets a list of channel payment methods.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelPaymentMethods({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-methods',
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
     * Creates an account channel payment method.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelPaymentMethods({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-methods',
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
     * Gets a list of channel payment terms.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelPaymentTerms({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-terms',
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
     * Creates an account channel payment term.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelPaymentTerms({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-payment-terms',
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
     * Gets a list of channel price lists.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelPriceLists({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-price-lists',
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
     * Creates an account channel price list.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelPriceLists({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-price-lists',
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
     * Gets a list of channel shipping addresses.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelShippingAddresses({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-addresses',
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
     * Creates an account channel shipping address.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelShippingAddresses({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-shipping-addresses',
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
     * Gets a list of channel users.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelUsers({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-users',
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
     * Creates an account channel user.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsByExternalReferenceCodeAccountChannelUsers({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/by-externalReferenceCode/{externalReferenceCode}/account-channel-users',
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
     * Gets a list of channel billing addresses.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelBillingAddresses({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-billing-addresses',
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
     * Creates an account channel billing address.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelBillingAddresses({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-billing-addresses',
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
     * Gets a list of channel currencies.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelCurrencies({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-currencies',
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
     * Creates an account channel currency.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelCurrencies({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-currencies',
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
     * Gets a list of channel delivery terms.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelDeliveryTerms({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-delivery-terms',
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
     * Creates an account channel delivery term.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelDeliveryTerms({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-delivery-terms',
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
     * Gets a list of channel discounts.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelDiscounts({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-discounts',
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
     * Creates an account channel discount.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelDiscounts({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-discounts',
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
     * Gets a list of channel payment methods.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelPaymentMethods({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-methods',
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
     * Creates an account channel payment method.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelPaymentMethods({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-methods',
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
     * Gets a list of channel payment terms.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelPaymentTerms({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-terms',
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
     * Creates an account channel payment term.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelPaymentTerms({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-payment-terms',
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
     * Gets a list of channel price lists.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelPriceLists({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-price-lists',
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
     * Creates an account channel price list.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelPriceLists({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-price-lists',
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
     * Gets a list of channel shipping addresses.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelShippingAddresses({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-addresses',
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
     * Creates an account channel shipping address.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelShippingAddresses({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-shipping-addresses',
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
     * Gets a list of channel users.
     * @returns AccountChannelEntry Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminAccountV10AccountsAccountChannelUsers({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<AccountChannelEntry>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-users',
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
     * Creates an account channel user.
     * @returns AccountChannelEntry Created
     * @returns any Async
     * @throws ApiError
     */
    public postHeadlessCommerceAdminAccountV10AccountsAccountChannelUsers({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: AccountChannelEntry,
    }): CancelablePromise<AccountChannelEntry | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-account/v1.0/accounts/{id}/account-channel-users',
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
