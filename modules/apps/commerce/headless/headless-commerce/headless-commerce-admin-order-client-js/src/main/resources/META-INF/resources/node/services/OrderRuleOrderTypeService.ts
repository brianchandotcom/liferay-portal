/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderRuleOrderType } from '../models/OrderRuleOrderType';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderRuleOrderTypeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Order Rule Order Type by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOrderRuleOrderType({
        orderRuleOrderTypeId,
    }: {
        orderRuleOrderTypeId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/order-rule-order-types/{orderRuleOrderTypeId}',
            path: {
                'orderRuleOrderTypeId': orderRuleOrderTypeId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Order Rule Order Types.
     * @returns OrderRuleOrderType Successful operation
     * @throws ApiError
     */
    public getOrderRuleByExternalReferenceCodeOrderRuleOrderTypesPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<OrderRuleOrderType>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-order-types',
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
     * Creates or updates a Order Rule Order Type.
     * @returns OrderRuleOrderType Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderRuleByExternalReferenceCodeOrderRuleOrderType({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderRuleOrderType,
    }): CancelablePromise<OrderRuleOrderType | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-order-types',
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
     * Gets a list of Order rule Order Types.
     * @returns OrderRuleOrderType Successful operation
     * @throws ApiError
     */
    public getOrderRuleIdOrderRuleOrderTypesPage({
        id,
        page,
        pageSize,
        search,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
        search?: string,
    }): CancelablePromise<Array<OrderRuleOrderType>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-order-types',
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
     * Creates or updates a Order rule Order Type.
     * @returns OrderRuleOrderType Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderRuleIdOrderRuleOrderType({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderRuleOrderType,
    }): CancelablePromise<OrderRuleOrderType | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-order-types',
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
