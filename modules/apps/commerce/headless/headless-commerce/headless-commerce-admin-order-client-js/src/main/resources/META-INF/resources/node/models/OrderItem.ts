/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { CustomField } from './CustomField';
import type { ShippingAddress } from './ShippingAddress';
import type { VirtualItem } from './VirtualItem';
export type OrderItem = {
    bookedQuantityId?: number;
    customFields?: Array<CustomField>;
    decimalQuantity?: number;
    deliveryGroup?: string;
    discountAmount?: number;
    readonly discountManuallyAdjusted?: boolean;
    discountPercentageLevel1?: number;
    discountPercentageLevel1WithTaxAmount?: number;
    discountPercentageLevel2?: number;
    discountPercentageLevel2WithTaxAmount?: number;
    discountPercentageLevel3?: number;
    discountPercentageLevel3WithTaxAmount?: number;
    discountPercentageLevel4?: number;
    discountPercentageLevel4WithTaxAmount?: number;
    discountWithTaxAmount?: number;
    externalReferenceCode?: string;
    finalPrice?: number;
    finalPriceWithTaxAmount?: number;
    readonly formattedQuantity?: string;
    id?: number;
    name?: Record<string, string>;
    options?: string;
    orderExternalReferenceCode?: string;
    orderId?: number;
    readonly priceManuallyAdjusted?: boolean;
    printedNote?: string;
    promoPrice?: number;
    promoPriceWithTaxAmount?: number;
    quantity?: number;
    readonly replacedSku?: string;
    replacedSkuId?: number;
    requestedDeliveryDate?: string;
    shippedQuantity?: number;
    shippingAddress?: ShippingAddress;
    shippingAddressId?: number;
    sku?: string;
    skuExternalReferenceCode?: string;
    skuId?: number;
    subscription?: boolean;
    unitOfMeasure?: string;
    unitOfMeasureKey?: string;
    unitPrice?: number;
    unitPriceWithTaxAmount?: number;
    readonly virtualItemURLs?: Array<string>;
    virtualItems?: Array<VirtualItem>;
};

