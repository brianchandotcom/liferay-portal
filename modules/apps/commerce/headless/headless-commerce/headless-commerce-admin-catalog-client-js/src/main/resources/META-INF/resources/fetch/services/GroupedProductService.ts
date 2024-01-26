/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { GroupedProduct } from '../models/GroupedProduct';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class GroupedProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a grouped product by its ID.
     * @returns void
     * @throws ApiError
     */
    public deleteGroupedProduct({
        groupedProductId,
    }: {
        groupedProductId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/grouped-products/{groupedProductId}',
            path: {
                'groupedProductId': groupedProductId,
            },
        });
    }
    /**
     * Updates a group product by its ID.
     * @returns GroupedProduct Updated
     * @throws ApiError
     */
    public patchGroupedProduct({
        groupedProductId,
        requestBody,
    }: {
        groupedProductId: number,
        requestBody: GroupedProduct,
    }): CancelablePromise<GroupedProduct> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/grouped-products/{groupedProductId}',
            path: {
                'groupedProductId': groupedProductId,
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
     * Gets a list of grouped product by external reference code.
     * @returns GroupedProduct Successful operation
     * @throws ApiError
     */
    public getProductByExternalReferenceCodeGroupedProductsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<GroupedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/grouped-products',
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
     * Creates a grouped product related to a product by external reference code.
     * @returns GroupedProduct Created
     * @throws ApiError
     */
    public postProductByExternalReferenceCodeGroupedProduct({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: GroupedProduct,
    }): CancelablePromise<GroupedProduct> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/by-externalReferenceCode/{externalReferenceCode}/grouped-products',
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
     * Gets a list of grouped product.
     * @returns GroupedProduct Successful operation
     * @throws ApiError
     */
    public getProductIdGroupedProductsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<GroupedProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/grouped-products',
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
     * Creates a grouped product related to a product.
     * @returns GroupedProduct Created
     * @throws ApiError
     */
    public postProductIdGroupedProduct({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: GroupedProduct,
    }): CancelablePromise<GroupedProduct> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/products/{id}/grouped-products',
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
