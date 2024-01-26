/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Availability } from './Availability';
import type { CustomField } from './CustomField';
import type { DDMOption } from './DDMOption';
import type { Price } from './Price';
import type { ProductConfiguration } from './ProductConfiguration';
import type { ReplacementSku } from './ReplacementSku';
import type { SkuOption } from './SkuOption';
import type { SkuUnitOfMeasure } from './SkuUnitOfMeasure';
import type { TierPrice } from './TierPrice';
export type Sku = {
    readonly DDMOptions?: Array<DDMOption>;
    allowedOrderQuantities?: Array<string>;
    availability?: Availability;
    readonly backOrderAllowed?: boolean;
    readonly customFields?: Array<CustomField>;
    depth?: number;
    discontinued?: boolean;
    discontinuedDate?: string;
    displayDate?: string;
    displayDiscountLevels?: boolean;
    expirationDate?: string;
    gtin?: string;
    height?: number;
    readonly id?: number;
    incomingQuantityLabel?: string;
    manufacturerPartNumber?: string;
    maxOrderQuantity?: number;
    minOrderQuantity?: number;
    neverExpire?: boolean;
    price?: Price;
    readonly productConfiguration?: ProductConfiguration;
    readonly productId?: number;
    published?: boolean;
    purchasable?: boolean;
    readonly replacementSku?: ReplacementSku;
    replacementSkuExternalReferenceCode?: string;
    replacementSkuId?: number;
    sku?: string;
    skuOptions?: Array<SkuOption>;
    skuUnitOfMeasures?: Array<SkuUnitOfMeasure>;
    tierPrices?: Array<TierPrice>;
    weight?: number;
    width?: number;
};

