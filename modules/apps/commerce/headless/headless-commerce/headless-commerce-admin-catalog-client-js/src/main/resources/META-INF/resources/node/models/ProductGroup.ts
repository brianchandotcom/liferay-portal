/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductGroupProduct } from './ProductGroupProduct';
export type ProductGroup = {
    readonly customFields?: Record<string, any>;
    description?: Record<string, string>;
    externalReferenceCode?: string;
    id?: number;
    products?: Array<ProductGroupProduct>;
    productsCount?: number;
    title?: Record<string, string>;
};

