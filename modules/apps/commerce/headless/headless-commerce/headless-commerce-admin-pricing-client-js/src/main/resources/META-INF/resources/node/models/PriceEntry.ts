/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { TierPrice } from './TierPrice';
export type PriceEntry = {
    customFields?: Record<string, any>;
    externalReferenceCode?: string;
    hasTierPrice?: boolean;
    id?: number;
    price: number;
    priceListExternalReferenceCode?: string;
    priceListId?: number;
    promoPrice?: number;
    sku?: string;
    skuExternalReferenceCode?: string;
    skuId?: number;
    tierPrices?: Array<TierPrice>;
};

