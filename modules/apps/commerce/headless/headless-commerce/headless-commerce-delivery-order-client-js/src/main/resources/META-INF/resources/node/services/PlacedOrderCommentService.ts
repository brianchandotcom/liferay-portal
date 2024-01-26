/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlacedOrderComment } from '../models/PlacedOrderComment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PlacedOrderCommentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a Note by ID.
     * @returns PlacedOrderComment Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrderComments({
        placedOrderCommentId,
    }: {
        placedOrderCommentId: number,
    }): CancelablePromise<PlacedOrderComment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-order-comments/{placedOrderCommentId}',
            path: {
                'placedOrderCommentId': placedOrderCommentId,
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
     * Gets a list of Comments related to an Order.
     * @returns PlacedOrderComment Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrdersPlacedOrderComments({
        placedOrderId,
        page,
        pageSize,
    }: {
        placedOrderId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<PlacedOrderComment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-comments',
            path: {
                'placedOrderId': placedOrderId,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
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
