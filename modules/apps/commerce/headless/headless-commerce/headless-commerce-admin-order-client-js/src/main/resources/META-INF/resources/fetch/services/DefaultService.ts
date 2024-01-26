/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderRuleAccount } from '../models/OrderRuleAccount';
import type { OrderRuleAccountGroup } from '../models/OrderRuleAccountGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class DefaultService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Creates or updates a Order rule Account Group.
     * @returns OrderRuleAccountGroup Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderRuleIdOrderRuleAccountGroup({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderRuleAccountGroup,
    }): CancelablePromise<OrderRuleAccountGroup | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-account-groups',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a Order rule Account.
     * @returns OrderRuleAccount Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderRuleIdOrderRuleAccount({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderRuleAccount,
    }): CancelablePromise<OrderRuleAccount | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-accounts',
            path: {
                'id': id,
            },
            body: requestBody,
            mediaType: 'application/json',
            errors: {
                400: `Invalid input`,
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
