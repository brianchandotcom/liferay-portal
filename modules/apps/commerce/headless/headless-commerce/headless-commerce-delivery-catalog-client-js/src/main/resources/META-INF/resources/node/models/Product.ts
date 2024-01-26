/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Attachment } from './Attachment';
import type { Category } from './Category';
import type { CustomField } from './CustomField';
import type { LinkedProduct } from './LinkedProduct';
import type { ProductConfiguration } from './ProductConfiguration';
import type { ProductOption } from './ProductOption';
import type { ProductSpecification } from './ProductSpecification';
import type { RelatedProduct } from './RelatedProduct';
import type { Sku } from './Sku';
export type Product = {
    attachments?: Array<Attachment>;
    readonly catalogName?: string;
    categories?: Array<Category>;
    createDate?: string;
    customFields?: Array<CustomField>;
    description?: string;
    expando?: Record<string, any>;
    externalReferenceCode?: string;
    readonly id?: number;
    images?: Array<Attachment>;
    linkedProducts?: Array<LinkedProduct>;
    metaDescription?: string;
    metaKeyword?: string;
    metaTitle?: string;
    modifiedDate?: string;
    multipleOrderQuantity?: number;
    name?: string;
    readonly productConfiguration?: ProductConfiguration;
    readonly productId?: number;
    productOptions?: Array<ProductOption>;
    productSpecifications?: Array<ProductSpecification>;
    productType?: string;
    relatedProducts?: Array<RelatedProduct>;
    shortDescription?: string;
    skus?: Array<Sku>;
    slug?: string;
    tags?: Array<string>;
    urlImage?: string;
    readonly urls?: Record<string, string>;
};

