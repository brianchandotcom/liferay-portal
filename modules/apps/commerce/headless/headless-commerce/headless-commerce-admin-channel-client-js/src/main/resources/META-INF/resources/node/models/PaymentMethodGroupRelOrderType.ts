/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderType } from './OrderType';
export type PaymentMethodGroupRelOrderType = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly orderType?: OrderType;
    orderTypeExternalReferenceCode?: string;
    orderTypeId: number;
    paymentMethodGroupRelId: number;
    readonly paymentMethodGroupRelOrderTypeId?: number;
    priority?: number;
};

