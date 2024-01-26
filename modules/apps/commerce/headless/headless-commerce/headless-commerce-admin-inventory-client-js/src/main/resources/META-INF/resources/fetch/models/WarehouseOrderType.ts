/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderType } from './OrderType';
export type WarehouseOrderType = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly orderType?: OrderType;
    orderTypeExternalReferenceCode?: string;
    orderTypeId: number;
    priority?: number;
    warehouseExternalReferenceCode?: string;
    warehouseId: number;
    readonly warehouseOrderTypeId?: number;
};

