/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WarehouseOrderType } from '../models/WarehouseOrderType';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class WarehouseOrderTypeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Deletes a Warehouse Order Type by ID.
     * @returns void
     * @throws ApiError
     */
    public deleteHeadlessCommerceAdminInventoryV10WarehouseOrderTypes({
        warehouseOrderTypeId,
    }: {
        warehouseOrderTypeId: number,
    }): CancelablePromise<void> {
        return this.httpRequest.request({
            method: 'DELETE',
            url: '/headless-commerce-admin-inventory/v1.0/warehouse-order-types/{warehouseOrderTypeId}',
            path: {
                'warehouseOrderTypeId': warehouseOrderTypeId,
            },
            errors: {
                401: `Authentication information is missing or invalid`,
            },
        });
    }
    /**
     * Gets a list of Warehouse Order Types.
     * @returns WarehouseOrderType Successful operation
     * @throws ApiError
     */
    public getWarehouseByExternalReferenceCodeWarehouseOrderTypesPage({
        externalReferenceCode,
        page,
        pageSize,
    }: {
        externalReferenceCode: string,
        page?: number,
        pageSize?: number,
    }): CancelablePromise<Array<WarehouseOrderType>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-order-types',
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
     * Creates or updates a Warehouse Order Type.
     * @returns WarehouseOrderType Created
     * @returns any Async
     * @throws ApiError
     */
    public postWarehouseByExternalReferenceCodeWarehouseOrderType({
        externalReferenceCode,
        requestBody,
    }: {
        externalReferenceCode: string,
        requestBody: WarehouseOrderType,
    }): CancelablePromise<WarehouseOrderType | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/by-externalReferenceCode/{externalReferenceCode}/warehouse-order-types',
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
     * Gets a list of Warehouse Order Types.
     * @returns WarehouseOrderType Successful operation
     * @throws ApiError
     */
    public getWarehouseIdWarehouseOrderTypesPage({
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
    }): CancelablePromise<Array<WarehouseOrderType>> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-order-types',
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
    /**
     * Creates or updates a Warehouse Order Type.
     * @returns WarehouseOrderType Created
     * @returns any Async
     * @throws ApiError
     */
    public postWarehouseIdWarehouseOrderType({
        id,
        requestBody,
    }: {
        id: number,
        requestBody: WarehouseOrderType,
    }): CancelablePromise<WarehouseOrderType | any> {
        return this.httpRequest.request({
            method: 'POST',
            url: '/headless-commerce-admin-inventory/v1.0/warehouses/{id}/warehouse-order-types',
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
