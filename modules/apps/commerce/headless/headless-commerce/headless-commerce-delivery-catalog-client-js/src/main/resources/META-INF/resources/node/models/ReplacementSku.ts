/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Price } from './Price';
import type { ProductConfiguration } from './ProductConfiguration';
import type { SkuOption } from './SkuOption';
import type { SkuUnitOfMeasure } from './SkuUnitOfMeasure';
export type ReplacementSku = {
    readonly price?: Price;
    readonly productConfiguration?: ProductConfiguration;
    readonly sku?: string;
    readonly skuId?: number;
    readonly skuOptions?: Array<SkuOption>;
    skuUnitOfMeasures?: Array<SkuUnitOfMeasure>;
    readonly urls?: Record<string, string>;
};

