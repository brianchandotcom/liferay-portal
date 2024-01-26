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
     * Gets order type associated to the payment method object.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminChannelV10PaymentMethodGroupRelOrderTypesOrderType({
        paymentMethodGroupRelOrderTypeId,
    }: {
        paymentMethodGroupRelOrderTypeId: number,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-order-types/{paymentMethodGroupRelOrderTypeId}/order-type',
            path: {
                'paymentMethodGroupRelOrderTypeId': paymentMethodGroupRelOrderTypeId,
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
     * Gets order type associated to the shipping fixed option object.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminChannelV10ShippingFixedOptionOrderTypesOrderType({
        shippingFixedOptionOrderTypeId,
    }: {
        shippingFixedOptionOrderTypeId: number,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-order-types/{shippingFixedOptionOrderTypeId}/order-type',
            path: {
                'shippingFixedOptionOrderTypeId': shippingFixedOptionOrderTypeId,
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
