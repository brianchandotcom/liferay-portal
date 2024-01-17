/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceAdminCatalog_v1_0_CustomField} from './HeadlessCommerceAdminCatalog_v1_0_CustomField';
import type {HeadlessCommerceAdminCatalog_v1_0_SkuOption} from './HeadlessCommerceAdminCatalog_v1_0_SkuOption';
import type {HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration} from './HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration';
import type {HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure} from './HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure';
import type {HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings} from './HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings';
export type HeadlessCommerceAdminCatalog_v1_0_Sku = {
	'cost'?: number;
	'customFields'?: Array<HeadlessCommerceAdminCatalog_v1_0_CustomField>;
	'depth'?: number;
	'discontinued'?: boolean;
	'discontinuedDate'?: string;
	'displayDate'?: string;
	'expirationDate'?: string;
	'externalReferenceCode'?: string;
	'gtin'?: string;
	'height'?: number;
	readonly 'id'?: number;
	readonly 'inventoryLevel'?: number;
	'manufacturerPartNumber'?: string;
	'neverExpire'?: boolean;
	'price'?: number;
	readonly 'productId'?: number;
	readonly 'productName'?: Record<string, string>;
	'promoPrice'?: number;
	'published'?: boolean;
	'purchasable'?: boolean;
	'replacementSkuExternalReferenceCode'?: string;
	'replacementSkuId'?: number;
	'sku': string;
	'skuOptions'?: Array<HeadlessCommerceAdminCatalog_v1_0_SkuOption>;
	'skuSubscriptionConfiguration'?: HeadlessCommerceAdminCatalog_v1_0_SkuSubscriptionConfiguration;
	'skuUnitOfMeasures'?: Array<HeadlessCommerceAdminCatalog_v1_0_SkuUnitOfMeasure>;
	'skuVirtualSettings'?: HeadlessCommerceAdminCatalog_v1_0_SkuVirtualSettings;
	readonly 'unitOfMeasureKey'?: string;
	readonly 'unitOfMeasureName'?: Record<string, string>;
	readonly 'unitOfMeasureSkuId'?: string;
	'unspsc'?: string;
	'weight'?: number;
	'width'?: number;
	readonly 'x-class-name'?: string;
};
