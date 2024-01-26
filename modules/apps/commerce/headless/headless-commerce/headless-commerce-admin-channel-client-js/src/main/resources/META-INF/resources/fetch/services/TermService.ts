/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Term } from '../models/Term';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class TermService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets term associated to the payment method object.
     * @returns Term Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminChannelV10PaymentMethodGroupRelTermsTerm({
        paymentMethodGroupRelTermId,
    }: {
        paymentMethodGroupRelTermId: number,
    }): CancelablePromise<Term> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/payment-method-group-rel-terms/{paymentMethodGroupRelTermId}/term',
            path: {
                'paymentMethodGroupRelTermId': paymentMethodGroupRelTermId,
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
     * Gets term associated to the shipping fixed option object.
     * @returns Term Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminChannelV10ShippingFixedOptionTermsTerm({
        shippingFixedOptionTermId,
    }: {
        shippingFixedOptionTermId: number,
    }): CancelablePromise<Term> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-channel/v1.0/shipping-fixed-option-terms/{shippingFixedOptionTermId}/term',
            path: {
                'shippingFixedOptionTermId': shippingFixedOptionTermId,
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
