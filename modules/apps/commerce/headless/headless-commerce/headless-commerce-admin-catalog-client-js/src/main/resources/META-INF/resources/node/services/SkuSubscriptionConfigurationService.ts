/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SkuSubscriptionConfiguration } from '../models/SkuSubscriptionConfiguration';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SkuSubscriptionConfigurationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a sku subscription configuration of a product.
     * @returns SkuSubscriptionConfiguration Successful operation
     * @throws ApiError
     */
    public getSkuByExternalReferenceCodeSkuSubscriptionConfiguration({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<SkuSubscriptionConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/skuSubscriptionConfiguration',
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
     * Gets a sku subscription configuration of a sku.
     * @returns SkuSubscriptionConfiguration Successful operation
     * @throws ApiError
     */
    public getSkuIdSkuSubscriptionConfiguration({
        id,
    }: {
        id: number,
    }): CancelablePromise<SkuSubscriptionConfiguration> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/skuSubscriptionConfiguration',
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
