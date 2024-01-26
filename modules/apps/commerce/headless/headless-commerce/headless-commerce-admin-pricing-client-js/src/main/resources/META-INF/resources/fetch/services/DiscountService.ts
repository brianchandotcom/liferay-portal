/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Discount } from '../models/Discount';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DiscountService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Discount.
     * @returns Discount Successful operation
     * @throws ApiError
     */
    public getDiscountsPage({
        page,
        pageSize,
    }: {
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<Discount>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts',
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
     * Creates or Updates a Discount.
     * @returns Discount Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscount({
        requestBody,
    }: {
        requestBody: Discount,
    }): CancelablePromise<Discount | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts',
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
     * Deletes a Discount by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteDiscountByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Discount by external reference code.
     * @returns Discount Successful operation
     * @throws ApiError
     */
    public getDiscountByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Discount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates a Discount by external reference code.
     * @returns any Async
     * @throws ApiError
     */
    public patchDiscountByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Discount,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes a Discount by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteDiscount({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Discount by ID.
     * @returns Discount Successful operation
     * @throws ApiError
     */
    public getDiscount({
        id,
    }: {
        id: number,
    }): CancelablePromise<Discount> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}',
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
     * Updates a Discount by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchDiscount({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Discount,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}',
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
