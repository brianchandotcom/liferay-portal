/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Channel } from '../models/Channel';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ChannelService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets channel associated to the pricing object.
     * @returns Channel Successful operation
     * @throws ApiError
     */
    public getOrderRuleChannelChannel({
        orderRuleChannelId,
    }: {
        orderRuleChannelId: number,
    }): CancelablePromise<Channel> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rule-channels/{orderRuleChannelId}/channel',
            path: {
                'orderRuleChannelId': orderRuleChannelId,
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
     * Gets channel associated to the order type object.
     * @returns Channel Successful operation
     * @throws ApiError
     */
    public getOrderTypeChannelChannel({
        orderTypeChannelId,
    }: {
        orderTypeChannelId: number,
    }): CancelablePromise<Channel> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-type-channels/{orderTypeChannelId}/channel',
            path: {
                'orderTypeChannelId': orderTypeChannelId,
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
     * Gets a channel related to an Order.
     * @returns Channel Successful operation
     * @throws ApiError
     */
    public getOrderByExternalReferenceCodeChannel({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Channel> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/channel',
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
     * Gets a channel related to an Order.
     * @returns Channel Successful operation
     * @throws ApiError
     */
    public getOrderIdChannel({
        id,
    }: {
        id: number,
    }): CancelablePromise<Channel> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/channel',
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
}
