/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TermOrderType } from '../models/TermOrderType';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TermOrderTypeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Term Order Type by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteTermOrderType({
        termOrderTypeId,
    }: {
        termOrderTypeId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/term-order-types/{termOrderTypeId}',
            path: {
                'termOrderTypeId': termOrderTypeId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Term Order Types.
     * @returns TermOrderType Successful operation
     * @throws ApiError
     */
    public getTermByExternalReferenceCodeTermOrderTypesPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<TermOrderType>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/terms/by-externalReferenceCode/{externalReferenceCode}/term-order-types',
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
     * Creates or updates a Term Order Type.
     * @returns TermOrderType Created
     * @returns any Async
     * @throws ApiError
     */
    public postTermByExternalReferenceCodeTermOrderType({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: TermOrderType,
    }): CancelablePromise<TermOrderType | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/terms/by-externalReferenceCode/{externalReferenceCode}/term-order-types',
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
     * Gets a list of Term Order Types.
     * @returns TermOrderType Successful operation
     * @throws ApiError
     */
    public getTermIdTermOrderTypesPage({
        id,
        page,
        pageSize,
        search,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
        search?: string,
    }): CancelablePromise<Array<TermOrderType>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/terms/{id}/term-order-types',
            path: {
                'id': id,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
                'search': search,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a Term Order Type.
     * @returns TermOrderType Created
     * @returns any Async
     * @throws ApiError
     */
    public postTermIdTermOrderType({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: TermOrderType,
    }): CancelablePromise<TermOrderType | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/terms/{id}/term-order-types',
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
