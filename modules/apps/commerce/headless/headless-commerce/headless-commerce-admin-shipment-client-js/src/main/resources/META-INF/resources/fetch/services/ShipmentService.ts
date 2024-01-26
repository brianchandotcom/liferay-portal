/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Shipment } from '../models/Shipment';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ShipmentService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets a List of Shipments.
     * @returns Shipment Successful operation
     * @throws ApiError
     */
    public getShipmentsPage({
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
    }): CancelablePromise<Array<Shipment>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipments',
            query: {
                'filter': filter,
                'page': page,
                'pageSize': pageSize,
                'search': search,
                'sort': sort,
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
     * Creates a Shipment.
     * @returns Shipment Created
     * @returns any Accepted - Async
     * @throws ApiError
     */
    public postShipment({
        requestBody,
    }: {
        requestBody: Shipment,
    }): CancelablePromise<Shipment | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments',
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
     * Deletes a Shipment by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Retrive information of the given Shipment.
     * @returns Shipment
     * @throws ApiError
     */
    public getHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
                404: `The specified resource was not found`,
                500: `Unexpected error`,
            },
        });
    }
    /**
     * Updates a Shipment.
     * @returns Shipment Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Shipment,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates a Shipment.
     * @returns Shipment Updated
     * @throws ApiError
     */
    public putHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: Shipment,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}',
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
     * @returns Shipment
     * @throws ApiError
     */
    public postHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCodeStatusDelivered({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/status-delivered',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns Shipment
     * @throws ApiError
     */
    public postHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCodeStatusFinishProcessing({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/status-finish-processing',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * @returns Shipment
     * @throws ApiError
     */
    public postShipmentByExternalReferenceCodeStatusShipped({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/status-shipped',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
        });
    }
    /**
     * Deletes a Shipment by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteShipment({
        shipmentId,
    }: {
        shipmentId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}',
            path: {
                'shipmentId': shipmentId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Shipment by ID.
     * @returns Shipment Successful operation
     * @throws ApiError
     */
    public getShipment({
        shipmentId,
    }: {
        shipmentId: number,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}',
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
     * Updates a Shipment by ID.
     * @returns Shipment Updated
     * @throws ApiError
     */
    public patchShipment({
        shipmentId,
        requestBody,
    }: {
        shipmentId: number,
        requestBody: Shipment,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}',
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
    /**
     * @returns Shipment
     * @throws ApiError
     */
    public postShipmentStatusDelivered({
        shipmentId,
    }: {
        shipmentId: number,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/status-delivered',
            path: {
                'shipmentId': shipmentId,
            },
        });
    }
    /**
     * @returns Shipment
     * @throws ApiError
     */
    public postShipmentStatusFinishProcessing({
        shipmentId,
    }: {
        shipmentId: number,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/status-finish-processing',
            path: {
                'shipmentId': shipmentId,
            },
        });
    }
    /**
     * @returns Shipment
     * @throws ApiError
     */
    public postShipmentStatusShipped({
        shipmentId,
    }: {
        shipmentId: number,
    }): CancelablePromise<Shipment> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/status-shipped',
            path: {
                'shipmentId': shipmentId,
            },
        });
    }
}
