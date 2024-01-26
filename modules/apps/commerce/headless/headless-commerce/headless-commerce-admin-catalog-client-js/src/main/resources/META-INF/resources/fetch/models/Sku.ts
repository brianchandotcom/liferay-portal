/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
import type { SkuOption } from './SkuOption';
import type { SkuSubscriptionConfiguration } from './SkuSubscriptionConfiguration';
import type { SkuUnitOfMeasure } from './SkuUnitOfMeasure';
import type { SkuVirtualSettings } from './SkuVirtualSettings';
export type Sku = {
    cost?: number;
    customFields?: Array<CustomField>;
    depth?: number;
    discontinued?: boolean;
    discontinuedDate?: string;
    displayDate?: string;
    expirationDate?: string;
    externalReferenceCode?: string;
    gtin?: string;
    height?: number;
    readonly id?: number;
    readonly inventoryLevel?: number;
    manufacturerPartNumber?: string;
    neverExpire?: boolean;
    price?: number;
    readonly productId?: number;
    readonly productName?: Record<string, string>;
    promoPrice?: number;
    published?: boolean;
    purchasable?: boolean;
    replacementSkuExternalReferenceCode?: string;
    replacementSkuId?: number;
    sku: string;
    skuOptions?: Array<SkuOption>;
    skuSubscriptionConfiguration?: SkuSubscriptionConfiguration;
    skuUnitOfMeasures?: Array<SkuUnitOfMeasure>;
    skuVirtualSettings?: SkuVirtualSettings;
    readonly unitOfMeasureKey?: string;
    readonly unitOfMeasureName?: Record<string, string>;
    readonly unitOfMeasureSkuId?: string;
    unspsc?: string;
    weight?: number;
    width?: number;
};

