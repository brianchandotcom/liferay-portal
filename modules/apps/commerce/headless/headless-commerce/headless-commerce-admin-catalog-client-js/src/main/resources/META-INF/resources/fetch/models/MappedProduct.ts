/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
export type MappedProduct = {
    readonly actions?: Record<string, Record<string, string>>;
    customFields?: Array<CustomField>;
    id?: number;
    productExternalReferenceCode?: string;
    productId?: number;
    readonly productName?: Record<string, string>;
    quantity?: number;
    sequence?: string;
    sku?: string;
    skuExternalReferenceCode?: string;
    skuId?: number;
    type?: 'diagram' | 'external' | 'sku';
};

