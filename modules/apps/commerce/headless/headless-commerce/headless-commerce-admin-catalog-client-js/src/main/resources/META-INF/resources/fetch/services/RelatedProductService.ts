/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { RelatedProduct } from '../models/RelatedProduct';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class RelatedProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of related products of a product.
     * @returns RelatedProduct Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeRelatedProductsPage({
        externalReferenceCode,
        type,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        type?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<RelatedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/relatedProducts',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'type': type,
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a related product of a product
     * @returns RelatedProduct Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postProductByExternalReferenceCodeRelatedProduct({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: RelatedProduct,
    }): CancelablePromise<RelatedProduct | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/relatedProducts',
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
     * Gets a list of related products of a product.
     * @returns RelatedProduct Successful operation
     * @throws ApiError
     */
    public getProductIdRelatedProductsPage({
        id,
        type,
        page,
        pageSize,
    }: {
        id: number,
        type?: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<RelatedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/relatedProducts',
            path: {
                'id': id,
            },
            query: {
                'type': type,
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a related product of a product
     * @returns RelatedProduct Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postProductIdRelatedProduct({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: RelatedProduct,
    }): CancelablePromise<RelatedProduct | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/relatedProducts',
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
     * Deletes a related product by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteRelatedProduct({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/relatedProducts/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a related product by ID.
     * @returns RelatedProduct Successful operation
     * @throws ApiError
     */
    public getRelatedProduct({
        id,
    }: {
        id: number,
    }): CancelablePromise<RelatedProduct> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/relatedProducts/{id}',
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
