/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderType } from '../models/OrderType';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderTypeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets order type associated to the pricing object.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getOrderRuleOrderTypeOrderType({
        orderRuleOrderTypeId,
    }: {
        orderRuleOrderTypeId: number,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rule-order-types/{orderRuleOrderTypeId}/order-type',
            path: {
                'orderRuleOrderTypeId': orderRuleOrderTypeId,
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
     * Gets a List of OrderTypes.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getOrderTypesPage({
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
    }): CancelablePromise<Array<OrderType>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-types',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
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
     * Creates or updates a OrderType.
     * @returns OrderType Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postOrderType({
        requestBody,
    }: {
        requestBody: OrderType,
    }): CancelablePromise<OrderType | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-types',
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
     * Deletes an OrderType by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteOrderTypeByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an OrderType by external reference code.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getOrderTypeByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates an OrderType by external reference code.
     * @returns OrderType Succesfully update the Order Type
     * @throws ApiError
     */
    public patchOrderTypeByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderType,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes an OrderType by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOrderType({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/order-types/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an OrderType by ID.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getOrderType({
        id,
    }: {
        id: number,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-types/{id}',
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
     * Updates an OrderType by ID.
     * @returns OrderType Succesfully update the Order Type
     * @throws ApiError
     */
    public patchOrderType({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderType,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/order-types/{id}',
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
     * Gets order type associated to the term.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getTermOrderTypeOrderType({
        termOrderTypeId,
    }: {
        termOrderTypeId: number,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/term-order-types/{termOrderTypeId}/order-type',
            path: {
                'termOrderTypeId': termOrderTypeId,
            },
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
