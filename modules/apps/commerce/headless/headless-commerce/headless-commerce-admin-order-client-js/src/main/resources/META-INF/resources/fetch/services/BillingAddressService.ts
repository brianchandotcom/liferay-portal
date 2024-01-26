/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { BillingAddress } from '../models/BillingAddress';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class BillingAddressService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a billing Address related to an Order.
     * @returns BillingAddress Successful operation
     * @throws ApiError
     */
    public getOrderByExternalReferenceCodeBillingAddress({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<BillingAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/billingAddress',
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
     * Updates a billing Address related to an Order.
     * @returns any Async
     * @throws ApiError
     */
    public patchOrderByExternalReferenceCodeBillingAddress({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: BillingAddress,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/billingAddress',
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
     * Gets a billing Address related to an Order.
     * @returns BillingAddress Successful operation
     * @throws ApiError
     */
    public getOrderIdBillingAddress({
        id,
    }: {
        id: number,
    }): CancelablePromise<BillingAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/billingAddress',
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
     * Updates a billing Address related to an Order.
     * @returns any Async
     * @throws ApiError
     */
    public patchOrderIdBillingAddress({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: BillingAddress,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/billingAddress',
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
