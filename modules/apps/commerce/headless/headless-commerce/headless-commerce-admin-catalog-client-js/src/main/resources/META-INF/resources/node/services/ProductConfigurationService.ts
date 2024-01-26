/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductConfiguration } from '../models/ProductConfiguration';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductConfigurationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a product configuration of a product.
     * @returns ProductConfiguration Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeConfiguration({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ProductConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/configuration',
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
     * Updates a product configuration of a product.
     * @returns any Async
     * @throws ApiError
     */
    public patchProductByExternalReferenceCodeConfiguration({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ProductConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/configuration',
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
     * @returns ProductConfiguration Successful operation
     * @throws ApiError
     */
    public getProductIdConfiguration({
        id,
    }: {
        id: number,
    }): CancelablePromise<ProductConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/configuration',
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
    public patchProductIdConfiguration({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/configuration',
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
