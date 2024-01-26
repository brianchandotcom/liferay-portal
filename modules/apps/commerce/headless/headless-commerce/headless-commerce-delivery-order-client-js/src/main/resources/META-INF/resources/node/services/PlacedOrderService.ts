/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlacedOrder } from '../models/PlacedOrder';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PlacedOrderService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieves placed orders for specific account in the given channel.
     * @returns PlacedOrder Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10ChannelsAccountsPlacedOrders({
        accountId,
        channelId,
        page,
        pageSize,
    }: {
        accountId: number,
        channelId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<PlacedOrder>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/channels/{channelId}/accounts/{accountId}/placed-orders',
            path: {
                'accountId': accountId,
                'channelId': channelId,
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
     * Retrieve information of the given Placed Order.
     * @returns PlacedOrder
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrders({
        placedOrderId,
    }: {
        placedOrderId: number,
    }): CancelablePromise<PlacedOrder> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}',
            path: {
                'placedOrderId': placedOrderId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * @returns string
     * @throws ApiError
     */
    public getPlacedOrderPaymentUrl({
        placedOrderId,
        callbackUrl,
    }: {
        placedOrderId: number,
        callbackUrl?: string,
    }): CancelablePromise<string> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/payment-url',
            path: {
                'placedOrderId': placedOrderId,
            },
            query: {
                'callbackURL': callbackUrl,
            },
        });
    }
}
