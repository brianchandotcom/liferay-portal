/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Address } from '../models/Address';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AddressService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrive cart billing address.
     * @returns Address
     * @throws ApiError
     */
    public getCartBillingAddres({
        cartId,
    }: {
        cartId: number,
    }): CancelablePromise<Address> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/billing-address',
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
    /**
     * Retrive cart billing address.
     * @returns Address
     * @throws ApiError
     */
    public getCartShippingAddres({
        cartId,
    }: {
        cartId: number,
    }): CancelablePromise<Address> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/shipping-address',
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
