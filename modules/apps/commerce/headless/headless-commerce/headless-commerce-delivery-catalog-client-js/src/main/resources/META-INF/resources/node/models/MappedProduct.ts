/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Availability } from './Availability';
import type { Price } from './Price';
import type { ProductConfiguration } from './ProductConfiguration';
import type { ProductOption } from './ProductOption';
import type { SkuOption } from './SkuOption';
export type MappedProduct = {
    readonly actions?: Record<string, Record<string, string>>;
    availability?: Availability;
    readonly firstAvailableReplacementMappedProduct?: MappedProduct;
    id?: number;
    price?: Price;
    productConfiguration?: ProductConfiguration;
    productExternalReferenceCode?: string;
    productId?: number;
    readonly productName?: Record<string, string>;
    productOptions?: Array<ProductOption>;
    readonly purchasable?: boolean;
    quantity?: number;
    readonly replacementMappedProduct?: MappedProduct;
    readonly replacementMessage?: string;
    sequence?: string;
    sku?: string;
    skuExternalReferenceCode?: string;
    skuId?: number;
    skuOptions?: Array<SkuOption>;
    readonly thumbnail?: string;
    type?: 'diagram' | 'external' | 'sku';
    urls?: Record<string, string>;
};

