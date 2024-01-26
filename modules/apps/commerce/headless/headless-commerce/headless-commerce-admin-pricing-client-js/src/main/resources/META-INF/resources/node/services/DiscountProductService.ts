/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DiscountProduct } from '../models/DiscountProduct';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DiscountProductService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Discount Product by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteDiscountProduct({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/discountProducts/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Discount Products.
     * @returns DiscountProduct Successful operation
     * @throws ApiError
     */
    public getDiscountByExternalReferenceCodeDiscountProductsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountProducts',
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
     * Creates or updates a Discount Product.
     * @returns DiscountProduct Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountByExternalReferenceCodeDiscountProduct({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: DiscountProduct,
    }): CancelablePromise<DiscountProduct | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountProducts',
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
     * Gets a list of Discount Products.
     * @returns DiscountProduct Successful operation
     * @throws ApiError
     */
    public getDiscountIdDiscountProductsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountProduct>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountProducts',
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
     * Creates or updates a Discount Product.
     * @returns DiscountProduct Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountIdDiscountProduct({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: DiscountProduct,
    }): CancelablePromise<DiscountProduct | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountProducts',
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
