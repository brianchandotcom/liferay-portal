/* generated using openapi-typescript-codegen -- do no edit */
/* istanbul ignore file */
/* tslint:disable */
/* eslint-disable */
import type { OrderRuleAccount } from './OrderRuleAccount';
import type { OrderRuleAccountGroup } from './OrderRuleAccountGroup';
import type { OrderRuleChannel } from './OrderRuleChannel';
import type { OrderRuleOrderType } from './OrderRuleOrderType';
import type { Status } from './Status';
export type OrderRule = {
    readonly actions?: Record<string, Record<string, string>>;
    active?: boolean;
    author?: string;
    createDate?: string;
    description?: string;
    displayDate?: string;
    expirationDate?: string;
    externalReferenceCode?: string;
    id?: number;
    name: string;
    neverExpire?: boolean;
    orderRuleAccount?: Array<OrderRuleAccount>;
    orderRuleAccountGroup?: Array<OrderRuleAccountGroup>;
    orderRuleChannel?: Array<OrderRuleChannel>;
    orderRuleOrderType?: Array<OrderRuleOrderType>;
    priority?: number;
    type: string;
    typeSettings?: string;
    readonly workflowStatusInfo?: Status;
};

