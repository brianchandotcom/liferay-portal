/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SkuVirtualSettings } from '../models/SkuVirtualSettings';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SkuVirtualSettingsService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets virtual settings of a sku.
     * @returns SkuVirtualSettings Successful operation
     * @throws ApiError
     */
    public getSkuByExternalReferenceCodeSkuVirtualSettings({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<SkuVirtualSettings> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/sku-virtual-settings',
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
     * Gets virtual settings of a sku.
     * @returns SkuVirtualSettings Successful operation
     * @throws ApiError
     */
    public getSkuIdSkuVirtualSettings({
        id,
    }: {
        id: number,
    }): CancelablePromise<SkuVirtualSettings> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/sku-virtual-settings',
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
