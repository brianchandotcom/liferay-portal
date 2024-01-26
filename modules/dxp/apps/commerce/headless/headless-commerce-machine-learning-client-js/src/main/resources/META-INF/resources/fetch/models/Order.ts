/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderItem } from './OrderItem';
export type Order = {
    accountId?: number;
    channelId: number;
    createDate?: string;
    currencyCode: string;
    customFields?: Record<string, any>;
    externalReferenceCode?: string;
    id?: number;
    modifiedDate?: string;
    orderDate?: string;
    orderItems?: Array<OrderItem>;
    orderStatus?: number;
    orderTypeExternalReferenceCode?: string;
    orderTypeId?: number;
    paymentMethod?: string;
    paymentStatus?: number;
    status?: number;
    total?: number;
    userId?: number;
};

