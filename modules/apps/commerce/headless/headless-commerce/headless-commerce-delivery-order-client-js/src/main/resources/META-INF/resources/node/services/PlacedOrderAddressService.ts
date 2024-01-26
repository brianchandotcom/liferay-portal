/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlacedOrderAddress } from '../models/PlacedOrderAddress';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PlacedOrderAddressService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrieve placed order billing address.
     * @returns PlacedOrderAddress
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrdersPlacedOrderBillingAddress({
        placedOrderId,
    }: {
        placedOrderId: number,
    }): CancelablePromise<PlacedOrderAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-billing-address',
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
     * Retrieve placed order shipping address.
     * @returns PlacedOrderAddress
     * @throws ApiError
     */
    public getHeadlessCommerceDeliveryOrderV10PlacedOrdersPlacedOrderShippingAddress({
        placedOrderId,
    }: {
        placedOrderId: number,
    }): CancelablePromise<PlacedOrderAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-order/v1.0/placed-orders/{placedOrderId}/placed-order-shipping-address',
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
}
