/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { DiscountRule } from '../models/DiscountRule';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DiscountRuleService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Discount Rule related to a Discount.
     * @returns DiscountRule Successful operation
     * @throws ApiError
     */
    public getDiscountByExternalReferenceCodeDiscountRulesPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountRule>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discount/by-externalReferenceCode/{externalReferenceCode}/discountRules',
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
     * Creates or Updates a Discount Rule.
     * @returns DiscountRule Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountByExternalReferenceCodeDiscountRule({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: DiscountRule,
    }): CancelablePromise<DiscountRule | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discount/by-externalReferenceCode/{externalReferenceCode}/discountRules',
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
     * Deletes a Discount Rule by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteDiscountRule({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-pricing/v1.0/discountRules/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Discount Rule by ID.
     * @returns DiscountRule Successful operation
     * @throws ApiError
     */
    public getDiscountRule({
        id,
    }: {
        id: number,
    }): CancelablePromise<DiscountRule> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discountRules/{id}',
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
     * Updates a Discount Rule by ID.
     * @returns any Created
     * @throws ApiError
     */
    public patchDiscountRule({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: DiscountRule,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-pricing/v1.0/discountRules/{id}',
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
     * Gets a list of Discount Rule related to a Discount.
     * @returns DiscountRule Successful operation
     * @throws ApiError
     */
    public getDiscountIdDiscountRulesPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<DiscountRule>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountRules',
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
     * Creates or Updates a Discount Rule.
     * @returns DiscountRule Created
     * @returns any Async
     * @throws ApiError
     */
    public postDiscountIdDiscountRule({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: DiscountRule,
    }): CancelablePromise<DiscountRule | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-pricing/v1.0/discounts/{id}/discountRules',
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
