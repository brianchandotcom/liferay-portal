/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OptionValue } from '../models/OptionValue';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OptionValueService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a option value by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteOptionValueByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/optionValues/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets the value related to an option.
     * @returns OptionValue Successful operation
     * @throws ApiError
     */
    public getOptionValueByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<OptionValue> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/optionValues/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
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
    public patchOptionValueByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OptionValue,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/optionValues/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes a option value by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOptionValue({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-catalog/v1.0/optionValues/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets the value related to an option.
     * @returns OptionValue Successful operation
     * @throws ApiError
     */
    public getOptionValue({
        id,
    }: {
        id: number,
    }): CancelablePromise<OptionValue> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/optionValues/{id}',
            path: {
                'id': id,
            },
            errors: {
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
    public patchOptionValue({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OptionValue,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-catalog/v1.0/optionValues/{id}',
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
     * Gets a list of values related to an option.
     * @returns OptionValue Successful operation
     * @throws ApiError
     */
    public getOptionByExternalReferenceCodeOptionValuesPage({
        externalReferenceCode,
        page,
        pageSize,
        search,
        sort,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<OptionValue>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}/optionValues',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
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
     * Creates or updates a value related to an option.
     * @returns OptionValue Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postOptionByExternalReferenceCodeOptionValue({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OptionValue,
    }): CancelablePromise<OptionValue | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/options/by-externalReferenceCode/{externalReferenceCode}/optionValues',
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
     * Gets a list of values related to an option.
     * @returns OptionValue Successful operation
     * @throws ApiError
     */
    public getOptionIdOptionValuesPage({
        id,
        page,
        pageSize,
        search,
        sort,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<OptionValue>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-catalog/v1.0/options/{id}/optionValues',
            path: {
                'id': id,
            },
            query: {
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
     * Creates or updates a value related to an option.
     * @returns OptionValue Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postOptionIdOptionValue({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OptionValue,
    }): CancelablePromise<OptionValue | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-catalog/v1.0/options/{id}/optionValues',
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
