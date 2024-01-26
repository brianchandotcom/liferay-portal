/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductShippingConfiguration } from '../models/ProductShippingConfiguration';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductShippingConfigurationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a product shipping configuration of a product.
     * @returns ProductShippingConfiguration Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeShippingConfiguration({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ProductShippingConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/shippingConfiguration',
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
     * Updates a product shipping configuration of a product.
     * @returns any Async
     * @throws ApiError
     */
    public patchProductByExternalReferenceCodeShippingConfiguration({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ProductShippingConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/shippingConfiguration',
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
     * @returns ProductShippingConfiguration Successful operation
     * @throws ApiError
     */
    public getProductIdShippingConfiguration({
        id,
    }: {
        id: number,
    }): CancelablePromise<ProductShippingConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/shippingConfiguration',
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
    public patchProductIdShippingConfiguration({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductShippingConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/shippingConfiguration',
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
