/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Address } from './Address';
import type { CartComment } from './CartComment';
import type { CartItem } from './CartItem';
import type { Status } from './Status';
import type { Summary } from './Summary';
export type Cart = {
    readonly account?: string;
    accountId?: number;
    readonly author?: string;
    billingAddress?: Address;
    billingAddressId?: number;
    cartItems?: Array<CartItem>;
    readonly channelId?: number;
    couponCode?: string;
    readonly createDate?: string;
    currencyCode?: string;
    customFields?: Record<string, any>;
    errorMessages?: Array<string>;
    readonly id?: number;
    readonly lastPriceUpdateDate?: string;
    readonly modifiedDate?: string;
    notes?: Array<CartComment>;
    readonly orderStatusInfo?: Status;
    orderTypeExternalReferenceCode?: string;
    orderTypeId?: number;
    readonly orderUUID?: string;
    paymentMethod?: string;
    readonly paymentMethodLabel?: string;
    readonly paymentStatus?: number;
    readonly paymentStatusInfo?: Status;
    readonly paymentStatusLabel?: string;
    printedNote?: string;
    purchaseOrderNumber?: string;
    shippingAddress?: Address;
    shippingAddressId?: number;
    shippingMethod?: string;
    shippingOption?: string;
    readonly status?: string;
    summary?: Summary;
    useAsBilling?: boolean;
    readonly valid?: boolean;
    readonly workflowStatusInfo?: Status;
};

