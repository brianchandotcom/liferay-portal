/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderRuleAccountGroup } from '../models/OrderRuleAccountGroup';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderRuleAccountGroupService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Order Rule Account Group by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminOrderV10OrderRuleAccountGroups({
        orderRuleAccountGroupId,
    }: {
        orderRuleAccountGroupId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/order-rule-account-groups/{orderRuleAccountGroupId}',
            path: {
                'orderRuleAccountGroupId': orderRuleAccountGroupId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Order Rule Account Groups.
     * @returns OrderRuleAccountGroup Successful operation
     * @throws ApiError
     */
    public getOrderRuleByExternalReferenceCodeOrderRuleAccountGroupsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<OrderRuleAccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-account-groups',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Creates or updates a Order Rule Account Group.
     * @returns OrderRuleAccountGroup Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderRuleByExternalReferenceCodeOrderRuleAccountGroup({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderRuleAccountGroup,
    }): CancelablePromise<OrderRuleAccountGroup | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/order-rules/by-externalReferenceCode/{externalReferenceCode}/order-rule-account-groups',
            path: {
                'externalReferenceCode': externalReferenceCode,
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
     * Gets a list of Order rule Account Groups.
     * @returns OrderRuleAccountGroup Successful operation
     * @throws ApiError
     */
    public getOrderRuleIdOrderRuleAccountGroupsPage({
        id,
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        id: number,
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<OrderRuleAccountGroup>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/order-rules/{id}/order-rule-account-groups',
            path: {
                'id': id,
            },
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
}
