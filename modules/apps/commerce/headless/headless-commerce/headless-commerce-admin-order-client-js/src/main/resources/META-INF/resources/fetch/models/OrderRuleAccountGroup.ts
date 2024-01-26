/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderAccountGroup } from './OrderAccountGroup';
export type OrderRuleAccountGroup = {
    readonly accountGroup?: OrderAccountGroup;
    accountGroupExternalReferenceCode?: string;
    accountGroupId: number;
    readonly actions?: Record<string, Record<string, string>>;
    readonly orderRuleAccountGroupId?: number;
    orderRuleExternalReferenceCode?: string;
    orderRuleId: number;
};

