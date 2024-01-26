/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderTypeChannel } from '../models/OrderTypeChannel';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderTypeChannelService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Order Type Channel by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOrderTypeChannel({
        orderTypeChannelId,
    }: {
        orderTypeChannelId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/order-type-channels/{orderTypeChannelId}',
            path: {
                'orderTypeChannelId': orderTypeChannelId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Order Type Channels.
     * @returns OrderTypeChannel Successful operation
     * @throws ApiError
     */
    public getOrderTypeByExternalReferenceCodeOrderTypeChannelsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<OrderTypeChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}/order-type-channels',
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
     * Creates or updates a Order Type Channel.
     * @returns OrderTypeChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderTypeByExternalReferenceCodeOrderTypeChannel({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderTypeChannel,
    }): CancelablePromise<OrderTypeChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-types/by-externalReferenceCode/{externalReferenceCode}/order-type-channels',
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
     * Gets a list of Order Type Channels.
     * @returns OrderTypeChannel Successful operation
     * @throws ApiError
     */
    public getOrderTypeIdOrderTypeChannelsPage({
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
    }): CancelablePromise<Array<OrderTypeChannel>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-types/{id}/order-type-channels',
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
     * Creates or updates a Order Type Channel.
     * @returns OrderTypeChannel Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderTypeIdOrderTypeChannel({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderTypeChannel,
    }): CancelablePromise<OrderTypeChannel | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-types/{id}/order-type-channels',
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
