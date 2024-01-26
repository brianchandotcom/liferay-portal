/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DiscountCategory } from '../models/DiscountCategory';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DiscountCategoryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Discount Category by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteDiscountCategory({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/discountCategories/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Discount Categories.
     * @returns DiscountCategory Successful operation
     * @throws ApiError
     */
    public getDiscountByExternalReferenceCodeDiscountCategoriesPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountCategory>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountCategories',
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
     * Creates or updates a Discount Category.
     * @returns DiscountCategory Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountByExternalReferenceCodeDiscountCategory({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: DiscountCategory,
    }): CancelablePromise<DiscountCategory | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountCategories',
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
     * Gets a list of Discount Categories.
     * @returns DiscountCategory Successful operation
     * @throws ApiError
     */
    public getDiscountIdDiscountCategoriesPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountCategory>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountCategories',
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
     * Creates or updates a Discount Category.
     * @returns DiscountCategory Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountIdDiscountCategory({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: DiscountCategory,
    }): CancelablePromise<DiscountCategory | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountCategories',
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
