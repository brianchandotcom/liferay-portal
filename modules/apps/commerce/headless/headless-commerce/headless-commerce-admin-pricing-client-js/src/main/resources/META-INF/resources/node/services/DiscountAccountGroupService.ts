/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DiscountAccountGroup } from '../models/DiscountAccountGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DiscountAccountGroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Discount Account Group by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteDiscountAccountGroup({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/discountAccountGroups/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Discount Account Groups.
     * @returns DiscountAccountGroup Successful operation
     * @throws ApiError
     */
    public getDiscountByExternalReferenceCodeDiscountAccountGroupsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountAccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountAccountGroups',
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
     * Creates or updates a Discount Account Group.
     * @returns DiscountAccountGroup Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountByExternalReferenceCodeDiscountAccountGroup({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: DiscountAccountGroup,
    }): CancelablePromise<DiscountAccountGroup | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/by-externalReferenceCode/{externalReferenceCode}/discountAccountGroups',
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
     * Gets a list of Discount Account Groups.
     * @returns DiscountAccountGroup Successful operation
     * @throws ApiError
     */
    public getDiscountIdDiscountAccountGroupsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountAccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountAccountGroups',
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
     * Creates or updates a Discount Account Group.
     * @returns DiscountAccountGroup Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountIdDiscountAccountGroup({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: DiscountAccountGroup,
    }): CancelablePromise<DiscountAccountGroup | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountAccountGroups',
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
