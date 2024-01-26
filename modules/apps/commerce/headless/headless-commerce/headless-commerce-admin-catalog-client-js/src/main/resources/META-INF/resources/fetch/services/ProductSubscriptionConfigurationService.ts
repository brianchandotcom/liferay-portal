/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductSubscriptionConfiguration } from '../models/ProductSubscriptionConfiguration';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductSubscriptionConfigurationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a product subscription configuration of a product.
     * @returns ProductSubscriptionConfiguration Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeSubscriptionConfiguration({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ProductSubscriptionConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/subscriptionConfiguration',
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
     * Updates a product subscription configuration of a product.
     * @returns any Async
     * @throws ApiError
     */
    public patchProductByExternalReferenceCodeSubscriptionConfiguration({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ProductSubscriptionConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/subscriptionConfiguration',
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
     * Gets a product by ID.
     * @returns ProductSubscriptionConfiguration Successful operation
     * @throws ApiError
     */
    public getProductIdSubscriptionConfiguration({
        id,
    }: {
        id: number,
    }): CancelablePromise<ProductSubscriptionConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/subscriptionConfiguration',
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
     * Updates a product by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchProductIdSubscriptionConfiguration({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductSubscriptionConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/subscriptionConfiguration',
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
