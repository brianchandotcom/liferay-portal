/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlacedOrderItemShipment } from '../models/PlacedOrderItemShipment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PlacedOrderItemShipmentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieve shipments of the given Placed Order Item.
     * @returns PlacedOrderItemShipment
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrderItemsPlacedOrderItemShipments({
        placedOrderItemId,
    }: {
        placedOrderItemId: number,
    }): CancelablePromise<Array<PlacedOrderItemShipment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-order-items/{placedOrderItemId}/placed-order-item-shipments',
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
}
