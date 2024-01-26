/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductVirtualSettings } from '../models/ProductVirtualSettings';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductVirtualSettingsService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets virtual settings of a product.
     * @returns ProductVirtualSettings Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeProductVirtualSettings({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ProductVirtualSettings> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/product-virtual-settings',
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
     * Gets the product virtual settings of a product.
     * @returns ProductVirtualSettings Successful operation
     * @throws ApiError
     */
    public getProductIdProductVirtualSettings({
        id,
    }: {
        id: number,
    }): CancelablePromise<ProductVirtualSettings> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/product-virtual-settings',
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
}
