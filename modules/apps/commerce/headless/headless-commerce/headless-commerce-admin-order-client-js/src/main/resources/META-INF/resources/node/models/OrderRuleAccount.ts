/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { Account } from './Account';
export type OrderRuleAccount = {
    readonly account?: Account;
    accountExternalReferenceCode?: string;
    accountId: number;
    readonly actions?: Record<string, Record<string, string>>;
    readonly orderRuleAccountId?: number;
    orderRuleExternalReferenceCode?: string;
    orderRuleId: number;
};

