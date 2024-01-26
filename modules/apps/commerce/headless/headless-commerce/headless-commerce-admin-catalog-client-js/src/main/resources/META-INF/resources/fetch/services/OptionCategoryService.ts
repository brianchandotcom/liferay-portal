/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OptionCategory } from '../models/OptionCategory';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OptionCategoryService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of option categories.
     * @returns OptionCategory Successful operation
     * @throws ApiError
     */
    public getOptionCategoriesPage({
        filter,
        page,
        pageSize,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<OptionCategory>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/optionCategories',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
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
     * Creates or updates a option category.
     * @returns OptionCategory Created
     * @returns any Async
     * @throws ApiError
     */
    public postOptionCategory({
        requestBody,
    }: {
        requestBody: OptionCategory,
    }): CancelablePromise<OptionCategory | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/optionCategories',
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
     * Deletes a option category by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOptionCategory({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/optionCategories/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a option category by ID.
     * @returns OptionCategory Successful operation
     * @throws ApiError
     */
    public getOptionCategory({
        id,
    }: {
        id: number,
    }): CancelablePromise<OptionCategory> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/optionCategories/{id}',
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
     * Updates a option category by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchOptionCategory({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OptionCategory,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/optionCategories/{id}',
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
