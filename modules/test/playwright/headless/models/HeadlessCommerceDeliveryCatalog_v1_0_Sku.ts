/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type {HeadlessCommerceDeliveryCatalog_v1_0_Availability} from './HeadlessCommerceDeliveryCatalog_v1_0_Availability';
import type {HeadlessCommerceDeliveryCatalog_v1_0_CustomField} from './HeadlessCommerceDeliveryCatalog_v1_0_CustomField';
import type {HeadlessCommerceDeliveryCatalog_v1_0_DDMOption} from './HeadlessCommerceDeliveryCatalog_v1_0_DDMOption';
import type {HeadlessCommerceDeliveryCatalog_v1_0_Price} from './HeadlessCommerceDeliveryCatalog_v1_0_Price';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration} from './HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration';
import type {HeadlessCommerceDeliveryCatalog_v1_0_ReplacementSku} from './HeadlessCommerceDeliveryCatalog_v1_0_ReplacementSku';
import type {HeadlessCommerceDeliveryCatalog_v1_0_SkuOption} from './HeadlessCommerceDeliveryCatalog_v1_0_SkuOption';
import type {HeadlessCommerceDeliveryCatalog_v1_0_SkuUnitOfMeasure} from './HeadlessCommerceDeliveryCatalog_v1_0_SkuUnitOfMeasure';
import type {HeadlessCommerceDeliveryCatalog_v1_0_TierPrice} from './HeadlessCommerceDeliveryCatalog_v1_0_TierPrice';
export type HeadlessCommerceDeliveryCatalog_v1_0_Sku = {
	readonly 'DDMOptions'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_DDMOption>;
	'allowedOrderQuantities'?: Array<string>;
	'availability'?: HeadlessCommerceDeliveryCatalog_v1_0_Availability;
	readonly 'backOrderAllowed'?: boolean;
	readonly 'customFields'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_CustomField>;
	'depth'?: number;
	'discontinued'?: boolean;
	'discontinuedDate'?: string;
	'displayDate'?: string;
	'displayDiscountLevels'?: boolean;
	'expirationDate'?: string;
	'gtin'?: string;
	'height'?: number;
	readonly 'id'?: number;
	'incomingQuantityLabel'?: string;
	'manufacturerPartNumber'?: string;
	'maxOrderQuantity'?: number;
	'minOrderQuantity'?: number;
	'neverExpire'?: boolean;
	'price'?: HeadlessCommerceDeliveryCatalog_v1_0_Price;
	'productConfiguration'?: HeadlessCommerceDeliveryCatalog_v1_0_ProductConfiguration;
	readonly 'productId'?: number;
	'published'?: boolean;
	'purchasable'?: boolean;
	'replacementSku'?: HeadlessCommerceDeliveryCatalog_v1_0_ReplacementSku;
	'replacementSkuExternalReferenceCode'?: string;
	'replacementSkuId'?: number;
	'sku'?: string;
	'skuOptions'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_SkuOption>;
	'skuUnitOfMeasures'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_SkuUnitOfMeasure>;
	'tierPrices'?: Array<HeadlessCommerceDeliveryCatalog_v1_0_TierPrice>;
	'weight'?: number;
	'width'?: number;
	readonly 'x-class-name'?: string;
};
