/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Price } from './Price';
import type { Settings } from './Settings';
import type { SkuUnitOfMeasure } from './SkuUnitOfMeasure';
export type CartItem = {
    readonly adaptiveMediaImageHTMLTag?: string;
    cartItems?: Array<CartItem>;
    customFields?: Record<string, any>;
    errorMessages?: Array<string>;
    readonly id?: number;
    readonly name?: string;
    options?: string;
    readonly parentCartItemId?: number;
    price?: Price;
    productId?: number;
    readonly productURLs?: Record<string, string>;
    quantity?: number;
    readonly replacedSku?: string;
    replacedSkuId?: number;
    settings?: Settings;
    readonly sku?: string;
    skuId: number;
    skuUnitOfMeasure?: SkuUnitOfMeasure;
    readonly subscription?: boolean;
    readonly thumbnail?: string;
    readonly valid?: boolean;
};

