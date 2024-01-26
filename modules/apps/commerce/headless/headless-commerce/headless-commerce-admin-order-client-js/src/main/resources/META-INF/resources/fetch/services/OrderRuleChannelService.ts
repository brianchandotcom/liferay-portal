/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderRuleChannel } from '../models/OrderRuleChannel';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderRuleChannelService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Order Rule Channel by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOrderRuleChannel({
        orderRuleChannelId,
    }: {
        orderRuleChannelId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/order-rule-channels/{orderRuleChannelId}',
            path: {
                'orderRuleChannelId': orderRuleChannelId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Order Rule Channel.
     * @returns OrderRuleChannel Successful operation
     * @throws ApiError
     */
    public getOrderRuleByExternalReferenceCodeOrderRuleChannelsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<OrderRuleChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-channels',
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
     * Creates or updates a Order Rule Channel.
     * @returns OrderRuleChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderRuleByExternalReferenceCodeOrderRuleChannel({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderRuleChannel,
    }): CancelablePromise<OrderRuleChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-channels',
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
     * Gets a list of Order rule Channels.
     * @returns OrderRuleChannel Successful operation
     * @throws ApiError
     */
    public getOrderRuleIdOrderRuleChannelsPage({
        id,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        id: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<OrderRuleChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-channels',
            path: {
                'id': id,
            },
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
     * Creates or updates a Order rule Channel.
     * @returns OrderRuleChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderRuleIdOrderRuleChannel({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderRuleChannel,
    }): CancelablePromise<OrderRuleChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-channels',
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
