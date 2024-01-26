/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PaymentMethod } from '../models/PaymentMethod';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class PaymentMethodService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Retrive payment methods available for the Cart.
     * @returns PaymentMethod
     * @throws ApiError
     */
    public getCartPaymentMethodsPage({
        cartId,
    }: {
        cartId: number,
    }): CancelablePromise<Array<PaymentMethod>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-delivery-cart/v1.0/carts/{cartId}/payment-methods',
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
