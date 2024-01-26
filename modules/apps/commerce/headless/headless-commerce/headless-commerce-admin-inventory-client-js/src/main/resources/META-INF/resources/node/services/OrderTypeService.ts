/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderType } from '../models/OrderType';
import type { CancelablePromise } from '../core/CancelablePromise';
import type { BaseHttpRequest } from '../core/BaseHttpRequest';
export class OrderTypeService {
    constructor(public readonly httpRequest: BaseHttpRequest) {}
    /**
     * Gets order type associated to the pricing object.
     * @returns OrderType Successful operation
     * @throws ApiError
     */
    public getHeadlessCommerceAdminInventoryV10WarehouseOrderTypesOrderType({
        warehouseOrderTypeId,
    }: {
        warehouseOrderTypeId: number,
    }): CancelablePromise<OrderType> {
        return this.httpRequest.request({
            method: 'GET',
            url: '/headless-commerce-admin-inventory/v1.0/warehouse-order-types/{warehouseOrderTypeId}/order-type',
            path: {
                'warehouseOrderTypeId': warehouseOrderTypeId,
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
