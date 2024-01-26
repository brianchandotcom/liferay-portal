/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductOptionValue } from '../models/ProductOptionValue';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductOptionValueService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a product option value by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteProductOptionValue({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/product-option-values/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets the value related to a product option value.
     * @returns ProductOptionValue Successful operation
     * @throws ApiError
     */
    public getProductOptionValue({
        id,
    }: {
        id: number,
    }): CancelablePromise<ProductOptionValue> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/product-option-values/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates an product option value by ID.
     * @returns ProductOptionValue Updated
     * @throws ApiError
     */
    public patchProductOptionValue({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductOptionValue,
    }): CancelablePromise<ProductOptionValue> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/product-option-values/{id}',
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
     * Gets a list of values related to a product option.
     * @returns ProductOptionValue Successful operation
     * @throws ApiError
     */
    public getProductOptionIdProductOptionValuesPage({
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
    }): CancelablePromise<Array<ProductOptionValue>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/productOptions/{id}/productOptionValues',
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
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a value related to a product option.
     * @returns ProductOptionValue Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postProductOptionIdProductOptionValue({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductOptionValue,
    }): CancelablePromise<ProductOptionValue | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/productOptions/{id}/productOptionValues',
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
