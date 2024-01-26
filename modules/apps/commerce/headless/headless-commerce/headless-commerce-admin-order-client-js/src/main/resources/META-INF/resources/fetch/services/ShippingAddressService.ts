/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ShippingAddress } from '../models/ShippingAddress';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ShippingAddressService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets an Order Item by ID.
     * @returns ShippingAddress Successful operation
     * @throws ApiError
     */
    public getOrderItemShippingAddress({
        id,
    }: {
        id: number,
    }): CancelablePromise<ShippingAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orderItems/{id}/shippingAddress',
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
     * Gets a shipping Address related to an Order.
     * @returns ShippingAddress Successful operation
     * @throws ApiError
     */
    public getOrderByExternalReferenceCodeShippingAddress({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ShippingAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/shippingAddress',
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
     * Updates a shipping Address related to an Order.
     * @returns any Async
     * @throws ApiError
     */
    public patchOrderByExternalReferenceCodeShippingAddress({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ShippingAddress,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/orders/by-externalReferenceCode/{externalReferenceCode}/shippingAddress',
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
     * Gets a shipping Address related to an Order.
     * @returns ShippingAddress Successful operation
     * @throws ApiError
     */
    public getOrderIdShippingAddress({
        id,
    }: {
        id: number,
    }): CancelablePromise<ShippingAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/shippingAddress',
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
     * Updates a shipping Address related to an Order.
     * @returns any Async
     * @throws ApiError
     */
    public patchOrderIdShippingAddress({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: ShippingAddress,
    }): CancelablePromise<any> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-order/v1.0/orders/{id}/shippingAddress',
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
