/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ShipmentItem } from '../models/ShipmentItem';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class ShipmentItemService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Shipment Item by external reference code.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminShipmentV10ShipmentItemsByExternalReferenceCode({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-shipment/v1.0/shipment-items/by-externalReferenceCode/{externalReferenceCode}',
            path: {
                'externalReferenceCode': externalReferenceCode,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Shipment Item by external reference code.
     * @returns ShipmentItem Successful operation
     * @throws ApiError
     */
    public getShipmentByExternalReferenceCodeItem({
        externalReferenceCode,
    }: {
        externalReferenceCode: string,
    }): CancelablePromise<ShipmentItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipment-items/by-externalReferenceCode/{externalReferenceCode}',
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
     * Updates a Shipment Item by external reference code.
     * @returns ShipmentItem Updated
     * @throws ApiError
     */
    public patchHeadlessCommerceAdminShipmentV10ShipmentItemsByExternalReferenceCode({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ShipmentItem,
    }): CancelablePromise<ShipmentItem> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-shipment/v1.0/shipment-items/by-externalReferenceCode/{externalReferenceCode}',
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
     * Deletes a Shipment Item by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteShipmentItem({
        shipmentItemId,
    }: {
        shipmentItemId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-shipment/v1.0/shipment-items/{shipmentItemId}',
            path: {
                'shipmentItemId': shipmentItemId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a Shipment Item by ID.
     * @returns ShipmentItem Successful operation
     * @throws ApiError
     */
    public getShipmentItem({
        shipmentItemId,
    }: {
        shipmentItemId: number,
    }): CancelablePromise<ShipmentItem> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipment-items/{shipmentItemId}',
            path: {
                'shipmentItemId': shipmentItemId,
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
     * Updates a Shipment Item by ID.
     * @returns ShipmentItem Updated
     * @throws ApiError
     */
    public patchShipmentItem({
        shipmentItemId,
        requestBody,
    }: {
        shipmentItemId: number,
        requestBody: ShipmentItem,
    }): CancelablePromise<ShipmentItem> {
        return this.httpRequest.request({
            method: 'PATCH',
            url: '/headless-commerce-admin-shipment/v1.0/shipment-items/{shipmentItemId}',
            path: {
                'shipmentItemId': shipmentItemId,
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
     * Gets a list of Shipment Items related to a Shipment.
     * @returns ShipmentItem Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCodeItems({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ShipmentItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/items',
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
     * Creates a Shipment Item.
     * @returns ShipmentItem Created
     * @returns any Async
     * @throws ApiError
     */
    public putHeadlessCommerceAdminShipmentV10ShipmentsByExternalReferenceCodeItems({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: ShipmentItem,
    }): CancelablePromise<ShipmentItem | any> {
        return this.httpRequest.request({
            method: 'PUT',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/by-externalReferenceCode/{externalReferenceCode}/items',
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
     * Gets a list of Shipment Items related to a Shipment.
     * @returns ShipmentItem Successful operation
     * @throws ApiError
     */
    public getShipmentItemsPage({
        shipmentId,
        page,
        pageSize,
    }: {
        shipmentId: number,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<ShipmentItem>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/items',
            path: {
                'shipmentId': shipmentId,
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
     * Creates a Shipment Item.
     * @returns ShipmentItem Created
     * @returns any Async
     * @throws ApiError
     */
    public postShipmentItem({
        shipmentId,
        requestBody,
    }: {
        shipmentId: number,
        requestBody: ShipmentItem,
    }): CancelablePromise<ShipmentItem | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-shipment/v1.0/shipments/{shipmentId}/items',
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
