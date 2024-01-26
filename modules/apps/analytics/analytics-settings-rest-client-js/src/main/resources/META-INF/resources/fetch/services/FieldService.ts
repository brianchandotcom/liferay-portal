/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Field } from '../models/Field';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class FieldService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * @returns Field
     * @throws ApiError
     */
    public getFieldsAccountsPage({
        keyword,
        page,
        pageSize,
        sort,
    }: {
        keyword?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Field>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/fields/accounts',
            query: {
                'keyword': keyword,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchFieldAccount({
        requestBody,
    }: {
        requestBody?: Array<Field>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/analytics-settings-rest/v1.0/fields/accounts',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Field
     * @throws ApiError
     */
    public getFieldsOrdersPage({
        keyword,
        page,
        pageSize,
        sort,
    }: {
        keyword?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Field>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/fields/orders',
            query: {
                'keyword': keyword,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchFieldOrder({
        requestBody,
    }: {
        requestBody?: Array<Field>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/analytics-settings-rest/v1.0/fields/orders',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Field
     * @throws ApiError
     */
    public getFieldsPeoplePage({
        keyword,
        page,
        pageSize,
        sort,
    }: {
        keyword?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Field>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/fields/people',
            query: {
                'keyword': keyword,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchFieldPeople({
        requestBody,
    }: {
        requestBody?: Array<Field>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/analytics-settings-rest/v1.0/fields/people',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
    /**
     * @returns Field
     * @throws ApiError
     */
    public getFieldsProductsPage({
        keyword,
        page,
        pageSize,
        sort,
    }: {
        keyword?: string,
        page?: number,
        pageSize?: number,
        sort?: string,
    }): CancelablePromise<Array<Field>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/analytics-settings-rest/v1.0/fields/products',
            query: {
                'keyword': keyword,
                'page': page,
                'pageSize': pageSize,
                'sort': sort,
            },
        });
    }
    /**
     * @returns void
     * @throws ApiError
     */
    public patchFieldProduct({
        requestBody,
    }: {
        requestBody?: Array<Field>,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/analytics-settings-rest/v1.0/fields/products',
            body: requestBody,
            mediaType: 'application/json',
        });
    }
}
