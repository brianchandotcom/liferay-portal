/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductOptionValue } from './ProductOptionValue';
export type ProductOption = {
    catalogId?: number;
    description?: string;
    fieldType?: string;
    readonly id?: number;
    key?: string;
    name?: string;
    optionId?: number;
    priority?: number;
    productOptionValues?: Array<ProductOptionValue>;
    readonly required?: boolean;
    readonly skuContributor?: boolean;
};

