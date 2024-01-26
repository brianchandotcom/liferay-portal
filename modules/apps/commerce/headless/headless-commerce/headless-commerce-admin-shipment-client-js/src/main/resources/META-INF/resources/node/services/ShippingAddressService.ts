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
     * Gets a shipping Address related to an Order.
     * @returns ShippingAddress Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCodeShippingAddress({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ShippingAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/shipping-address',
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
     * Updates a Shipping Address by external reference code.
     * @returns ShippingAddress Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCodeShippingAddress({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ShippingAddress,
    }): CancelablePromise<ShippingAddress> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/shipping-address',
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
    public getShipmentShippingAddress({
        shipmentId,
    }: {
        shipmentId: number,
    }): CancelablePromise<ShippingAddress> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/shipping-address',
            path: {
                'shipmentId': shipmentId,
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
     * Updates a Shipping Address by ID.
     * @returns ShippingAddress Updated
     * @throws ApiError
     */
    public patchShipmentShippingAddress({
        shipmentId,
        requestBody,
    }: {
        shipmentId: number,
        requestBody: ShippingAddress,
    }): CancelablePromise<ShippingAddress> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/shipping-address',
            path: {
                'shipmentId': shipmentId,
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
