/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Attachment} from './HeadlessCommerceDeliveryCatalog_v1_0_Attachment';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Category} from './HeadlessCommerceDeliveryCatalog_v1_0_Category';
import type {HeadlessCommerceDeliveryCatalog_v1_0_CustomField} from './HeadlessCommerceDeliveryCatalog_v1_0_CustomField';
import type {HeadlessCommerceDeliveryCatalog_v1_0_LinkedProduct} from './HeadlessCommerceDeliveryCatalog_v1_0_LinkedProduct';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductOption} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductOption';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductSpecification} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductSpecification';
import type {HeadlessCommerceDeliveryCatalog_v1_0_RelatedProduct} from './HeadlessCommerceDeliveryCatalog_v1_0_RelatedProduct';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Sku} from './HeadlessCommerceDeliveryCatalog_v1_0_Sku';
export type HeadlessCommerceDeliveryCatalog_v1_0_Product = {
	'attachments'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Attachment>;
	readonly 'catalogName'?: string;
	'categories'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Category>;
	'createDate'?: string;
	readonly 'customFields'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_CustomField>;
	'description'?: string;
	'expando'?: Record<string, Record<string, any>>;
	'externalReferenceCode'?: string;
	readonly 'id'?: number;
	'images'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Attachment>;
	'linkedProducts'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_LinkedProduct>;
	'metaDescription'?: string;
	'metaKeyword'?: string;
	'metaTitle'?: string;
	'modifiedDate'?: string;
	'multipleOrderQuantity'?: number;
	'name'?: string;
	'productConfiguration'?: HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration;
	readonly 'productId'?: number;
	'productOptions'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_ProductOption>;
	'productSpecifications'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_ProductSpecification>;
	'productType'?: string;
	'relatedProducts'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_RelatedProduct>;
	'shortDescription'?: string;
	'skus'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_Sku>;
	'slug'?: string;
	'tags'?: Array<string>;
	'urlImage'?: string;
	readonly 'urls'?: Record<string, string>;
	readonly 'x-class-name'?: string;
};
