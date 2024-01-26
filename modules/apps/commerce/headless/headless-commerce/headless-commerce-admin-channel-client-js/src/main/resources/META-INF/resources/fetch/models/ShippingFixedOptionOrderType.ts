/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderType } from './OrderType';
export type ShippingFixedOptionOrderType = {
    readonly actions?: Record<string, Record<string, string>>;
    readonly orderType?: OrderType;
    orderTypeExternalReferenceCode?: string;
    orderTypeId: number;
    priority?: number;
    shippingFixedOptionId: number;
    readonly shippingFixedOptionOrderTypeId?: number;
};

