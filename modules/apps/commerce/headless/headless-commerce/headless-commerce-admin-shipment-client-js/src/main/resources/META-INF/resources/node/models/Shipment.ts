/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
import type { ShipmentItem } from './ShipmentItem';
import type { ShippingAddress } from './ShippingAddress';
import type { Status } from './Status';
export type Shipment = {
    readonly accountId?: number;
    readonly actions?: Record<string, Record<string, string>>;
    carrier?: string;
    readonly createDate?: string;
    customFields?: Array<CustomField>;
    expectedDate?: string;
    externalReferenceCode?: string;
    readonly id?: number;
    readonly modifiedDate?: string;
    orderId?: number;
    shipmentItems?: Array<ShipmentItem>;
    shippingAddress?: ShippingAddress;
    shippingAddressId?: number;
    shippingDate?: string;
    shippingMethodId?: number;
    readonly shippingOptionName?: string;
    readonly status?: Status;
    trackingNumber?: string;
    trackingURL?: string;
    readonly userName?: string;
};

