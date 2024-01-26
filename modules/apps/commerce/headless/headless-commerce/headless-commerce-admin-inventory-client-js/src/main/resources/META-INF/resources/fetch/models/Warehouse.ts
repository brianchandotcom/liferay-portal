/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { WarehouseItem } from './WarehouseItem';
export type Warehouse = {
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    city?: string;
    countryISOCode?: string;
    description?: Record<string, string>;
    externalReferenceCode?: string;
    id?: number;
    latitude?: number;
    longitude?: number;
    name?: Record<string, string>;
    regionISOCode?: string;
    street1?: string;
    street2?: string;
    street3?: string;
    type?: string;
    warehouseItems?: Array<WarehouseItem>;
    zip?: string;
};

