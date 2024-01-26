/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Price } from './Price';
import type { TierPrice } from './TierPrice';
export type SkuUnitOfMeasure = {
    incrementalOrderQuantity?: number;
    key?: string;
    name?: string;
    precision?: number;
    price?: Price;
    primary?: boolean;
    priority?: number;
    rate?: number;
    tierPrices?: Array<TierPrice>;
};

