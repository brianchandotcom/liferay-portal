/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { PlacedOrderAddress } from './PlacedOrderAddress';
import type { PlacedOrderComment } from './PlacedOrderComment';
import type { PlacedOrderItem } from './PlacedOrderItem';
import type { Status } from './Status';
import type { Summary } from './Summary';
export type PlacedOrder = {
    readonly account?: string;
    readonly accountId?: number;
    readonly author?: string;
    readonly channelId?: number;
    readonly couponCode?: string;
    readonly createDate?: string;
    readonly currencyCode?: string;
    readonly customFields?: Record<string, any>;
    readonly errorMessages?: Array<string>;
    readonly id?: number;
    readonly lastPriceUpdateDate?: string;
    readonly modifiedDate?: string;
    readonly orderStatusInfo?: Status;
    readonly orderTypeExternalReferenceCode?: string;
    readonly orderTypeId?: number;
    readonly orderUUID?: string;
    readonly paymentMethod?: string;
    readonly paymentMethodLabel?: string;
    readonly paymentStatus?: number;
    readonly paymentStatusInfo?: Status;
    readonly paymentStatusLabel?: string;
    readonly placedOrderBillingAddress?: PlacedOrderAddress;
    readonly placedOrderBillingAddressId?: number;
    readonly placedOrderComments?: Array<PlacedOrderComment>;
    readonly placedOrderItems?: Array<PlacedOrderItem>;
    readonly placedOrderShippingAddress?: PlacedOrderAddress;
    readonly placedOrderShippingAddressId?: number;
    readonly printedNote?: string;
    readonly purchaseOrderNumber?: string;
    readonly shippingMethod?: string;
    readonly shippingOption?: string;
    readonly status?: string;
    readonly summary?: Summary;
    readonly useAsBilling?: boolean;
    readonly valid?: boolean;
    readonly workflowStatusInfo?: Status;
};

