/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
import type { ProductOptionValue } from './ProductOptionValue';
export type ProductOption = {
    catalogId?: number;
    customFields?: Array<CustomField>;
    definedExternally?: boolean;
    description?: Record<string, string>;
    facetable?: boolean;
    fieldType: string;
    readonly id?: number;
    infoItemServiceKey?: string;
    key: string;
    name: Record<string, string>;
    optionId: number;
    priceType?: string;
    priority?: number;
    productOptionValues?: Array<ProductOptionValue>;
    required?: boolean;
    skuContributor?: boolean;
    typeSettings?: string;
};

