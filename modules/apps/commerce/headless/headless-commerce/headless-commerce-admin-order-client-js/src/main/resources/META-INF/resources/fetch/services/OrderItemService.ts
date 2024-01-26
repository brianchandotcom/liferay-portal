/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderItem } from '../models/OrderItem';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderItemService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a list of Order Items.
     * @returns OrderItem Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminOrderV10OrderItems({
        filter,
        page,
        pageSize,
        search,
        sort,
    }: {
        filter?: string,
        page?: number,
        pageSize?: number,
        search?: string,
        sort?: string,
    }): CancelablePromise<Array<OrderItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orderItems',
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
    /**
     * Deletes an Order Item by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteOrderItemByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Order Item by external reference code.
     * @returns OrderItem Successful operation
     * @throws ApiError
     */
    public getOrderItemByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<OrderItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates an Order Item by external reference code.
     * @returns any Async
     * @throws ApiError
     */
    public patchOrderItemByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderItem,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates an Order Item by external reference code.
     * @returns OrderItem Successful operation
     * @throws ApiError
     */
    public putHeadlessCommerceAdminOrderV10OrderItemsByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderItem,
    }): CancelablePromise<OrderItem> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-admin-order/v1.0/orderItems/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes an Order Item by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteOrderItem({
        id,
    }: {
        id: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
            path: {
                'id': id,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets an Order Item by ID.
     * @returns OrderItem Successful operation
     * @throws ApiError
     */
    public getOrderItem({
        id,
    }: {
        id: number,
    }): CancelablePromise<OrderItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
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
    /**
     * Updates an Order Item by ID.
     * @returns any Async
     * @throws ApiError
     */
    public patchOrderItem({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderItem,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
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
     * Update an Order Item by ID.
     * @returns OrderItem Successful operation
     * @throws ApiError
     */
    public putHeadlessCommerceAdminOrderV10OrderItems({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderItem,
    }): CancelablePromise<OrderItem> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-admin-order/v1.0/orderItems/{id}',
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
     * Gets a list of Order Items related to an Order.
     * @returns OrderItem Successful operation
     * @throws ApiError
     */
    public getOrderByExternalReferenceCodeOrderItemsPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<OrderItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/orderItems',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
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
     * Creates or updates an Order Item.
     * @returns OrderItem Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderByExternalReferenceCodeOrderItem({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: OrderItem,
    }): CancelablePromise<OrderItem | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/orderItems',
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
     * Gets a list of Order Items related to an Order.
     * @returns OrderItem Successful operation
     * @throws ApiError
     */
    public getOrderIdOrderItemsPage({
        id,
        page,
        pageSize,
    }: {
        id: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<OrderItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/orderItems',
            path: {
                'id': id,
            },
            query: {
                'page': page,
                'pageSize': pageSize,
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
     * Creates or updates an Order Item.
     * @returns OrderItem Created
     * @returns any Async
     * @throws ApiError
     */
    public postOrderIdOrderItem({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: OrderItem,
    }): CancelablePromise<OrderItem | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/orderItems',
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
