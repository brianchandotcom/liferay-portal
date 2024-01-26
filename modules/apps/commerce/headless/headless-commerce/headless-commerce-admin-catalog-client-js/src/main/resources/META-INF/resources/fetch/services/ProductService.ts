/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Product } from '../models/Product';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of products.
     * @returns Product Successful operation
     * @throws ApiError
     */
    public getProductsPage({
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Product>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a product.
     * @returns Product Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postProduct({
        requestBody,
    }: {
        requestBody: Product,
    }): CancelablePromise<Product | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products',
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
     * Deletes a product by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteProductByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a product by external reference code.
     * @returns Product Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Product> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates a product by external reference code.
     * @returns any Async
     * @throws ApiError
     */
    public patchProductByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Product,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes a product by External Reference Code.
     * @returns void
     * @throws ApiError
     */
    public deleteProductByExternalReferenceCodeByVersion({
        externalReferenceCode,
        version,
    }: {
        externalReferenceCode: string,
        version: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/by-version/{version}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'version': version,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a product by External Reference Code.
     * @returns Product Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeByVersion({
        externalReferenceCode,
        version,
    }: {
        externalReferenceCode: string,
        version: number,
    }): CancelablePromise<Product> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/by-version/{version}',
            path: {
                'externalReferenceCode': externalReferenceCode,
                'version': version,
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
     * Clone a product.
     * @returns Product Successful operation
     * @throws ApiError
     */
    public postProductByExternalReferenceCodeClone({
        externalReferenceCode,
        catalogExternalReferenceCode,
    }: {
        externalReferenceCode: string,
        catalogExternalReferenceCode: string,
    }): CancelablePromise<Product> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/clone',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'catalogExternalReferenceCode': catalogExternalReferenceCode,
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
     * Deletes a product by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteProduct({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a product by ID.
     * @returns Product Successful operation
     * @throws ApiError
     */
    public getProduct({
        id,
    }: {
        id: number,
    }): CancelablePromise<Product> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}',
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
    public patchProduct({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Product,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}',
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
     * Deletes a product by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteProductByVersion({
        id,
        version,
    }: {
        id: number,
        version: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/by-version/{version}',
            path: {
                'id': id,
                'version': version,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a product by ID.
     * @returns Product Successful operation
     * @throws ApiError
     */
    public getProductByVersion({
        id,
        version,
    }: {
        id: number,
        version: number,
    }): CancelablePromise<Product> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/by-version/{version}',
            path: {
                'id': id,
                'version': version,
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
     * Gets a product by ID.
     * @returns Product Successful operation
     * @throws ApiError
     */
    public postProductClone({
        id,
        catalogId,
    }: {
        id: number,
        catalogId?: number,
    }): CancelablePromise<Product> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/clone',
            path: {
                'id': id,
            },
            query: {
                'catalogId': catalogId,
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
