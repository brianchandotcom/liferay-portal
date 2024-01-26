/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Option } from '../models/Option';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OptionService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of option.
     * @returns Option Successful operation
     * @throws ApiError
     */
    public getOptionsPage({
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<Option>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/options',
            query: {
                'filter': filter,
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
     * Creates or updates an option.
     * @returns Option Created
     * @returns any Async
     * @throws ApiError
     */
    public postOption({
        requestBody,
    }: {
        requestBody: Option,
    }): CancelablePromise<Option | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/options',
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
     * Deletes a option by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteOptionByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an option by external reference code.
     * @returns Option Successful operation
     * @throws ApiError
     */
    public getOptionByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Option> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates an option by external reference code.
     * @returns any Async
     * @throws ApiError
     */
    public patchOptionByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Option,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes a option by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOption({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/options/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an option by ID.
     * @returns Option Successful operation
     * @throws ApiError
     */
    public getOption({
        id,
    }: {
        id: number,
    }): CancelablePromise<Option> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/options/{id}',
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
     * Updates an option by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchOption({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: Option,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/options/{id}',
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
