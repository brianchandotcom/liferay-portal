/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { MappedProduct } from '../models/MappedProduct';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class MappedProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a mapped product by its ID.
     * @returns void
     * @throws ApiError
     */
    public deleteMappedProduct({
        mappedProductId,
    }: {
        mappedProductId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/mapped-products/{mappedProductId}',
            path: {
                'mappedProductId': mappedProductId,
            },
        });
    }
    /**
     * Updates a mapped product by its ID.
     * @returns MappedProduct Updated
     * @throws ApiError
     */
    public patchMappedProduct({
        mappedProductId,
        requestBody,
    }: {
        mappedProductId: number,
        requestBody: MappedProduct,
    }): CancelablePromise<MappedProduct> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/mapped-products/{mappedProductId}',
            path: {
                'mappedProductId': mappedProductId,
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
     * @returns MappedProduct Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeMappedProductsPage({
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
    }): CancelablePromise<Array<MappedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/mapped-products',
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
     * Creates a mapped product related to a product by external reference code.
     * @returns MappedProduct Created
     * @throws ApiError
     */
    public postProductByExternalReferenceCodeMappedProduct({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: MappedProduct,
    }): CancelablePromise<MappedProduct> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/mapped-products',
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
     * Gets a mapped product by sequence.
     * @returns MappedProduct Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeMappedProductBySequence({
        externalReferenceCode,
        sequence,
    }: {
        externalReferenceCode: string,
        sequence: string,
    }): CancelablePromise<MappedProduct> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/mapped-products/by-sequence/{sequence}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'sequence': sequence,
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
     * Gets a list of product.
     * @returns MappedProduct Successful operation
     * @throws ApiError
     */
    public getProductIdMappedProductsPage({
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
    }): CancelablePromise<Array<MappedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/mapped-products',
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
     * Creates a mapped product related to a product.
     * @returns MappedProduct Created
     * @throws ApiError
     */
    public postProductIdMappedProduct({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: MappedProduct,
    }): CancelablePromise<MappedProduct> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/mapped-products',
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
     * Gets a mapped product by product ID and sequence.
     * @returns MappedProduct Successful operation
     * @throws ApiError
     */
    public getProductIdMappedProductBySequence({
        id,
        sequence,
    }: {
        id: number,
        sequence: string,
    }): CancelablePromise<MappedProduct> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/mapped-products/by-sequence/{sequence}',
            path: {
                'id': id,
                'sequence': sequence,
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
