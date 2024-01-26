/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductGroupProduct } from '../models/ProductGroupProduct';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductGroupProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a product in a product group by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteProductGroupProduct({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/product-group-products/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of products related to a product group.
     * @returns ProductGroupProduct Successful operation
     * @throws ApiError
     */
    public getProductGroupByExternalReferenceCodeProductGroupProductsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ProductGroupProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/product-groups/by-externalReferenceCode/{externalReferenceCode}/product-group-products',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
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
     * Creates or updates a product related to a product group.
     * @returns ProductGroupProduct Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postProductGroupByExternalReferenceCodeProductGroupProduct({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ProductGroupProduct,
    }): CancelablePromise<ProductGroupProduct | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/product-groups/by-externalReferenceCode/{externalReferenceCode}/product-group-products',
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
     * Gets a list of products related to a product groups.
     * @returns ProductGroupProduct Successful operation
     * @throws ApiError
     */
    public getProductGroupIdProductGroupProductsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ProductGroupProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/product-groups/{id}/product-group-products',
            path: {
                'id': id,
            },
            query: {
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
     * Creates or updates a product related to a product group.
     * @returns ProductGroupProduct Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postProductGroupIdProductGroupProduct({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductGroupProduct,
    }): CancelablePromise<ProductGroupProduct | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/product-groups/{id}/product-group-products',
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
