/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlacedOrderItem } from '../models/PlacedOrderItem';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PlacedOrderItemService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieve information of the given Placed Order.
     * @returns PlacedOrderItem
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrderItems({
        placedOrderItemId,
    }: {
        placedOrderItemId: number,
    }): CancelablePromise<PlacedOrderItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-order-items/{placedOrderItemId}',
            path: {
                'placedOrderItemId': placedOrderItemId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Retrieve placed order items.
     * @returns PlacedOrderItem Return the items of the placed order
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrdersPlacedOrderItems({
        placedOrderId,
        skuId,
        page,
        pageSize,
    }: {
        placedOrderId: number,
        skuId?: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<PlacedOrderItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-items',
            path: {
                'placedOrderId': placedOrderId,
            },
            query: {
                'skuId': skuId,
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
}
