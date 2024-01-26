/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TaxCategory } from '../models/TaxCategory';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TaxCategoryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Tax Category.
     * @returns TaxCategory Successful operation
     * @throws ApiError
     */
    public getCommerceAdminSiteSettingGroupTaxCategoryPage({
        groupId,
        page,
        pageSize,
    }: {
        groupId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<TaxCategory>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/taxCategory',
            path: {
                'groupId': groupId,
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
     * Creates a Tax Category.
     * @returns TaxCategory Created
     * @returns any Async
     * @throws ApiError
     */
    public postCommerceAdminSiteSettingGroupTaxCategory({
        groupId,
        requestBody,
    }: {
        groupId: number,
        requestBody: TaxCategory,
    }): CancelablePromise<TaxCategory | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-site-setting/v1.0/commerceAdminSiteSetting/{groupId}/taxCategory',
            path: {
                'groupId': groupId,
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
     * Deletes a Tax Category by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteTaxCategory({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Tax Category by ID.
     * @returns TaxCategory Successful operation
     * @throws ApiError
     */
    public getTaxCategory({
        id,
    }: {
        id: number,
    }): CancelablePromise<TaxCategory> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/{id}',
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
     * Updates a Tax Category by ID.
     * @returns any Async
     * @throws ApiError
     */
    public putTaxCategory({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: TaxCategory,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-admin-site-setting/v1.0/taxCategory/{id}',
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
