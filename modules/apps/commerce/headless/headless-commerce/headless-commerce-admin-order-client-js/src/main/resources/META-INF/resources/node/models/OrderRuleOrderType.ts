/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderType } from './OrderType';
export type OrderRuleOrderType = {
    readonly actions?: Record<string, Record<string, string>>;
    orderRuleExternalReferenceCode?: string;
    orderRuleId: number;
    readonly orderRuleOrderTypeId?: number;
    readonly orderType?: OrderType;
    orderTypeExternalReferenceCode?: string;
    orderTypeId: number;
};

