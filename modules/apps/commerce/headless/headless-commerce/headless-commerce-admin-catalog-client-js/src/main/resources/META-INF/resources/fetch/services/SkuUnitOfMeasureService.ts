/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { SkuUnitOfMeasure } from '../models/SkuUnitOfMeasure';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class SkuUnitOfMeasureService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a sku unit of measure by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteSkuUnitOfMeasure({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/sku-unit-of-measures/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a sku unit of measure by ID.
     * @returns SkuUnitOfMeasure Successful operation
     * @throws ApiError
     */
    public getSkuUnitOfMeasure({
        id,
    }: {
        id: number,
    }): CancelablePromise<SkuUnitOfMeasure> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/sku-unit-of-measures/{id}',
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
     * Updates a sku unit of measure by ID.
     * @returns SkuUnitOfMeasure Updated
     * @returns any Async
     * @throws ApiError
     */
    public patchSkuUnitOfMeasure({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: SkuUnitOfMeasure,
    }): CancelablePromise<SkuUnitOfMeasure | any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/sku-unit-of-measures/{id}',
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
     * Gets unit of measures of a sku by external reference code.
     * @returns SkuUnitOfMeasure Successful operation
     * @throws ApiError
     */
    public getSkuByExternalReferenceCodeSkuUnitOfMeasuresPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<SkuUnitOfMeasure>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/sku-unit-of-measures',
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
     * Creates or updates a unit of measure related to a sku by external reference code.
     * @returns SkuUnitOfMeasure Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postSkuByExternalReferenceCodeSkuUnitOfMeasure({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: SkuUnitOfMeasure,
    }): CancelablePromise<SkuUnitOfMeasure | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/skus/by-externalReferenceCode/{externalReferenceCode}/sku-unit-of-measures',
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
     * Gets unit of measures of a sku.
     * @returns SkuUnitOfMeasure Successful operation
     * @throws ApiError
     */
    public getSkuIdSkuUnitOfMeasuresPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<SkuUnitOfMeasure>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/sku-unit-of-measures',
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
     * Creates or updates a unit of measure related to a sku.
     * @returns SkuUnitOfMeasure Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postSkuIdSkuUnitOfMeasure({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: SkuUnitOfMeasure,
    }): CancelablePromise<SkuUnitOfMeasure | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/skus/{id}/sku-unit-of-measures',
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
