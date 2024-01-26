/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { ProductOption } from './ProductOption';
import type { ProductSpecification } from './ProductSpecification';
import type { Sku } from './Sku';
export type Product = {
    catalogId: number;
    categoryIds?: Array<number>;
    createDate?: string;
    customFields?: Record<string, any>;
    description?: Record<string, string>;
    displayDate?: string;
    expirationDate?: string;
    externalReferenceCode?: string;
    readonly id?: number;
    metaDescription?: Record<string, string>;
    metaKeyword?: Record<string, string>;
    metaTitle?: Record<string, string>;
    modifiedDate?: string;
    name: Record<string, string>;
    productChannelIds?: Array<number>;
    readonly productId?: number;
    productOptions?: Array<ProductOption>;
    productSpecifications?: Array<ProductSpecification>;
    productType: string;
    skus?: Array<Sku>;
    status?: number;
    subscriptionEnabled?: boolean;
    tags?: Array<string>;
    urls?: Record<string, string>;
};

