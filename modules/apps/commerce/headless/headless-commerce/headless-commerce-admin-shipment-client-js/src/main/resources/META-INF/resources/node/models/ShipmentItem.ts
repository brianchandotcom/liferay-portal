/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
export type ShipmentItem = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly createDate?: string;
    externalReferenceCode?: string;
    readonly id?: number;
    readonly modifiedDate?: string;
    orderItemId: number;
    quantity: number;
    shipmentExternalReferenceCode?: string;
    readonly shipmentId?: number;
    unitOfMeasureKey?: string;
    readonly userName?: string;
    validateInventory?: boolean;
    warehouseId: number;
};

