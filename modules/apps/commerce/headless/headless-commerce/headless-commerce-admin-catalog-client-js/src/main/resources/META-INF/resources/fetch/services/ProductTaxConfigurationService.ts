/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductTaxConfiguration } from '../models/ProductTaxConfiguration';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductTaxConfigurationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a product tax configuration of a product.
     * @returns ProductTaxConfiguration Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeTaxConfiguration({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ProductTaxConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/taxConfiguration',
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
     * Updates a product tax configuration of a product.
     * @returns any Async
     * @throws ApiError
     */
    public patchProductByExternalReferenceCodeTaxConfiguration({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ProductTaxConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/taxConfiguration',
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
     * @returns ProductTaxConfiguration Successful operation
     * @throws ApiError
     */
    public getProductIdTaxConfiguration({
        id,
    }: {
        id: number,
    }): CancelablePromise<ProductTaxConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/taxConfiguration',
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
    public patchProductIdTaxConfiguration({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductTaxConfiguration,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/taxConfiguration',
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
