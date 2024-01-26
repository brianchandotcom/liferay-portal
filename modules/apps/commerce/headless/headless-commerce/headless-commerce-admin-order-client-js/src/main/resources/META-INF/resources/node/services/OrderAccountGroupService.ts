/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderAccountGroup } from '../models/OrderAccountGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderAccountGroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets account group associated to the pricing object.
     * @returns OrderAccountGroup Successful operation
     * @throws ApiError
     */
    public getOrderRuleAccountGroupAccountGroup({
        orderRuleAccountGroupId,
    }: {
        orderRuleAccountGroupId: number,
    }): CancelablePromise<OrderAccountGroup> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rule-account-groups/{orderRuleAccountGroupId}/account-group',
            path: {
                'orderRuleAccountGroupId': orderRuleAccountGroupId,
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
