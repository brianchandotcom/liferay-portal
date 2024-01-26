/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ShippingMethod } from '../models/ShippingMethod';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ShippingMethodService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrive payment methods available for the Cart.
     * @returns ShippingMethod Retrieve the available shipping methods for the order
     * @throws ApiError
     */
    public getCartShippingMethodsPage({
        cartId,
    }: {
        cartId: number,
    }): CancelablePromise<Array<ShippingMethod>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/shipping-methods',
            path: {
                'cartId': cartId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
