/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Pin } from '../models/Pin';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PinService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a pin by its ID.
     * @returns void
     * @throws ApiError
     */
    public deletePin({
        pinId,
    }: {
        pinId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/pins/{pinId}',
            path: {
                'pinId': pinId,
            },
        });
    }
    /**
     * Updates a pin by its ID.
     * @returns Pin Updated
     * @throws ApiError
     */
    public patchPin({
        pinId,
        requestBody,
    }: {
        pinId: number,
        requestBody: Pin,
    }): CancelablePromise<Pin> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/pins/{pinId}',
            path: {
                'pinId': pinId,
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
     * Gets a list of product by external reference code.
     * @returns Pin Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodePinsPage({
        externalReferenceCode,
        page,
        pageSize,
        search,
        sort,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Pin>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/pins',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
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
     * Creates a pin related to a product by external reference code.
     * @returns Pin Created
     * @throws ApiError
     */
    public postProductByExternalReferenceCodePin({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Pin,
    }): CancelablePromise<Pin> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/pins',
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
     * Gets a list of product.
     * @returns Pin Successful operation
     * @throws ApiError
     */
    public getProductIdPinsPage({
        id,
        page,
        pageSize,
        search,
        sort,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Pin>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/pins',
            path: {
                'id': id,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
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
     * Creates a pin related to a product.
     * @returns Pin Created
     * @throws ApiError
     */
    public postProductIdPin({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Pin,
    }): CancelablePromise<Pin> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/pins',
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
