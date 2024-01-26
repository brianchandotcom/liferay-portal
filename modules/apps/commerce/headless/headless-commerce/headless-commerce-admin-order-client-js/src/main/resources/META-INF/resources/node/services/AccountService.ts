/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Account } from '../models/Account';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class AccountService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets account associated to the pricing object.
     * @returns Account Successful operation
     * @throws ApiError
     */
    public getOrderRuleAccountAccount({
        orderRuleAccountId,
    }: {
        orderRuleAccountId: number,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rule-accounts/{orderRuleAccountId}/account',
            path: {
                'orderRuleAccountId': orderRuleAccountId,
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
     * Gets a billing Address related to an Order.
     * @returns Account Successful operation
     * @throws ApiError
     */
    public getOrderByExternalReferenceCodeAccount({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/account',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
     * Gets a billing Address related to an Order.
     * @returns Account Successful operation
     * @throws ApiError
     */
    public getOrderIdAccount({
        id,
    }: {
        id: number,
    }): CancelablePromise<Account> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/account',
            path: {
                'id': id,
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
