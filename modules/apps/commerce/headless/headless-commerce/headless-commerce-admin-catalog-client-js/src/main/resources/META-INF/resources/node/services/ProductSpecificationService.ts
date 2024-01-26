/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductSpecification } from '../models/ProductSpecification';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ProductSpecificationService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a product specification by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteProductSpecification({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/productSpecifications/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a product specification by ID.
     * @returns ProductSpecification Successful operation
     * @throws ApiError
     */
    public getProductSpecification({
        id,
    }: {
        id: number,
    }): CancelablePromise<ProductSpecification> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/productSpecifications/{id}',
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
     * Updates a product specification by ID.
     * @returns ProductSpecification Successful operation
     * @returns any Async
     * @throws ApiError
     */
    public patchProductSpecification({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductSpecification,
    }): CancelablePromise<ProductSpecification | any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/productSpecifications/{id}',
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
     * Gets a list of values related to a specification.
     * @returns ProductSpecification Successful operation
     * @throws ApiError
     */
    public getProductIdProductSpecificationsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ProductSpecification>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/productSpecifications',
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
     * Creates or updates a value related to a specification
     * @returns ProductSpecification Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postProductIdProductSpecification({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ProductSpecification,
    }): CancelablePromise<ProductSpecification | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/productSpecifications',
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
