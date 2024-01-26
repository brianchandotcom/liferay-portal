/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlacedOrderItemShipment } from './PlacedOrderItemShipment';
import type { Price } from './Price';
import type { Settings } from './Settings';
import type { VirtualItem } from './VirtualItem';
export type PlacedOrderItem = {
    readonly adaptiveMediaImageHTMLTag?: string;
    readonly customFields?: Record<string, any>;
    readonly errorMessages?: Array<string>;
    readonly id?: number;
    readonly name?: string;
    readonly options?: string;
    readonly parentOrderItemId?: number;
    readonly placedOrderItemShipments?: Array<PlacedOrderItemShipment>;
    readonly placedOrderItems?: Array<PlacedOrderItem>;
    readonly price?: Price;
    readonly productId?: number;
    readonly productURLs?: Record<string, string>;
    readonly quantity?: number;
    readonly replacedSku?: string;
    readonly settings?: Settings;
    readonly sku?: string;
    readonly skuId?: number;
    readonly subscription?: boolean;
    readonly thumbnail?: string;
    readonly unitOfMeasureKey?: string;
    readonly valid?: boolean;
    readonly virtualItemURLs?: Array<string>;
    readonly virtualItems?: Array<VirtualItem>;
};

